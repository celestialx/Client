package galkon;


import java.io.File;

public final class Constants {

	public static String getSpritesLocation() {
		return "." + File.separator + "sprites" + File.separator;
	}

	public static String indexLocation(int index) {
		return getSpritesLocation() + index + ".png";
	}

	public static String getDataFile() {
		return "sprites.dat";
	}

	public static String getIndexFile() {
		return "sprites.idx";
	}

}
