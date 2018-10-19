package galkon;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class FileController {

	private static final List<FileSprite> spriteFiles = new ArrayList<>();

	/**
	 * Returns the list of sprite files in the sprites folder.
	 * @return
	 */
	public static List<FileSprite> getSpriteFiles() {
		return spriteFiles;
	}

	/**
	 * Builds the list of sprites in the sprites folder, images only.
	 */
	public static void buildSpriteList() {
		Main.log("Building list of image files in the sprites folder...");
		File[] files = new File(Constants.getSpritesLocation()).listFiles();
		if (files == null) {
			return;
		}
		int id = 0;
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				int n1 = extractNumber(o1.getName());
				int n2 = extractNumber(o2.getName());
				return n1 - n2;
			}


			private int extractNumber(String name) {
				int i = 0;
				try {
					int s = name.indexOf(" ") + 1;
					int e = name.lastIndexOf(".png");
					String number = name.substring(s, e);
					i = Integer.parseInt(number);
				} catch (Exception e) {
					i = 0; // if filename does not match the format
					// then default to 0
				}
				return i;
			}
		});
		for (File f : files) {
			if (f.isDirectory()) {
				continue;
			}
			if (!f.getName().contains(".png")) {
				continue;
			}
			spriteFiles.add(new FileSprite(id, fileToByteArray(f)));
			System.out.println(f.getName() + ", " + id);
			id++;
		}
		Main.log("Found " + spriteFiles.size() + " images in the sprites folder!");
	}

	private static byte[] fileToByteArray(File file) {
		try {
			byte[] fileData = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(fileData);
			fis.close();
			return fileData;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void buildCache() {
		if(spriteFiles.isEmpty()) {
			JOptionPane.showMessageDialog(null, "There are no sprites to pack.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			DataOutputStream dat = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(Constants.getDataFile())));
			for (int index = 0; index < SpriteLoader.totalSprites; index++) {
				SpriteBean sprite = SpriteLoader.cache[index];
				if (sprite.id != -1) {
					dat.writeByte(1);
					dat.writeShort(sprite.id);
				}
				if (sprite.name != null) {
					dat.writeByte(2);
					dat.writeUTF(sprite.name);
				}
				if (sprite.drawOffsetX != 0) {
					dat.writeByte(3);
					dat.writeShort(sprite.drawOffsetX);
				}
				if (sprite.drawOffsetY != 0) {
					dat.writeByte(4);
					dat.writeShort(sprite.drawOffsetY);
				}
				if (sprite.data != null && sprite.data.length > 0) {
					dat.writeByte(5);
					dat.write(sprite.data);
				}
				dat.writeByte(0);
			}
			dat.flush();
			dat.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An error occurred while writing the sprite data cache.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		try {
			DataOutputStream indexFile = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(Constants.getIndexFile())));
			indexFile.writeInt(SpriteLoader.totalSprites);
			for(int index = 0; index < SpriteLoader.totalSprites; index++) {
				SpriteBean sprite = SpriteLoader.cache[index];
				indexFile.writeInt(sprite.id);
				indexFile.writeInt(sprite.data.length);
			}
			indexFile.flush();
			indexFile.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An error occurred while writing the sprite index cache.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		JOptionPane.showMessageDialog(null, "Sprite cache written successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	}

}