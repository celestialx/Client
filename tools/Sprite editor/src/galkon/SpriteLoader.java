package galkon;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public final class SpriteLoader {

	public static void load() {
		/**
		 * Attempt to load sprite cache first.
		 */
		Main.log("Attempting to load sprite cache files...");

		spritesInFolder = FileController.getSpriteFiles().size();
		totalSprites = spritesInFolder;

		loadSpriteCache();

		if (spritesInCache > spritesInFolder) {
			totalSprites = spritesInCache;
		}

		SpriteBean[] newcache = new SpriteBean[totalSprites];
		System.arraycopy(cache, 0, newcache, 0, cache.length);

		cache = new SpriteBean[totalSprites];
		System.arraycopy(newcache, 0, cache, 0, newcache.length);

		loadSpriteFolder();
	}

	/**
	 * Load the sprites from the sprite cache.
	 */
	public static void loadSpriteCache() {
		DataInputStream indexFile = null, dataFile = null;
		try {
			indexFile = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(readFile(Constants.getIndexFile()))));
			dataFile = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(readFile(Constants.getDataFile()))));
			spritesInCache = indexFile.readInt();
			cache = new SpriteBean[spritesInCache];
			for (int fileIndex = 0; fileIndex < spritesInCache; fileIndex++) {
				indexFile.readInt();
				SpriteBean spriteBean = new SpriteBean();
				spriteBean.readValues(indexFile, dataFile);
				cache[spriteBean.id] = spriteBean;
			}
			Main.log("Read sprite cache successfully!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No sprite cache was found! Attempting to use the sprites folder...", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			try {
				if (indexFile != null) {
					indexFile.close();
				}
				if (dataFile != null) {
					dataFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Load the sprites from the sprites folder into the cache.
	 */
	public static void loadSpriteFolder() {
		if (FileController.getSpriteFiles().isEmpty()) {
			Main.log("Sprites folder is empty, skipping loading from sprites folder...");
			return;
		}
		Main.log("Attempting to load sprites from sprites folder, sprite cache images will be overridden...");
		int readCount = 0;
		for (int index = 0; index < totalSprites; index++) {
			FileSprite fileSprite = FileController.getSpriteFiles().get(index);
			if (fileSprite == null) {
				continue;
			}
			SpriteBean spriteBean = new SpriteBean();
			spriteBean.id = fileSprite.getId();
			spriteBean.data = fileSprite.getData();
			cache[spriteBean.id] = spriteBean;
			readCount++;
		}
		Main.log("Loaded " + readCount + " sprites from the sprites folder!");
	}

	public static BufferedImage grabImage(int index) {
		SpriteBean spriteBean = cache[index];
		if (spriteBean == null) {
			return null;
		}
		try {
			spriteBean.spriteImage = byteArrayToImage(spriteBean.data);
			return spriteBean.spriteImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage byteArrayToImage(byte[] data) throws IOException {
		InputStream in = new ByteArrayInputStream(data);
		BufferedImage image = ImageIO.read(in);
		return image;
	}

	public static byte[] readFile(String name) {
		try {
			RandomAccessFile raf = new RandomAccessFile(name, "r");
			ByteBuffer buf = raf.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, raf.length());
			try {
				if (buf.hasArray()) {
					return buf.array();
				} else {
					byte[] array = new byte[buf.remaining()];
					buf.get(array);
					return array;
				}
			} finally {
				raf.close();
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getName(int index) {
		SpriteBean spriteBean = cache[index];
		if (spriteBean.name != null) {
			return spriteBean.name;
		} else {
			return "null";
		}
	}

	public static void setName(int index, String name) {
		cache[index].name = name;
	}

	public static int getOffsetX(int index) {
		return cache[index].drawOffsetX;
	}

	public static void setOffsetX(int index, int offsetX) {
		cache[index].drawOffsetX = offsetX;
	}

	public static int getOffsetY(int index) {
		return cache[index].drawOffsetY;
	}

	public static void setOffsetY(int index, int offsetY) {
		cache[index].drawOffsetY = offsetY;
	}

	public static int getWidth(int index) {
		return cache[index].spriteImage.getWidth();
	}

	public static int getHeight(int index) {
		return cache[index].spriteImage.getHeight();
	}

	public static void buildSpriteList() {
		for (int index = 0; index < totalSprites; index++) {
			UserInterface.model.addElement(Integer.toString(index));
		}
		UserInterface.frame.setTitle("Sprites - " + totalSprites + " total");
	}

	public static SpriteBean[] cache;
	public static int spritesInCache;
	public static int spritesInFolder;
	public static int totalSprites;

}