package com.client.client.cache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.client.client.Client;
import com.client.client.signlink;

public class Unzip {

	/**
	 * Unzip it
	 * 
	 * @param zipFile
	 *            input zip file
	 * @param output
	 *            zip file output folder
	 * @param deleteAfter
	 *            Should the zip file be deleted afterwards?
	 */
	public static void unzip(final File file) {
		try {
			InputStream in =  new BufferedInputStream(new FileInputStream(file));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry e;
			while((e=zin.getNextEntry()) != null) {
				if(e.isDirectory()) {
					(new File(signlink.findcachedir() + e.getName())).mkdir();
				} else {
					if (e.getName().equals(file.getName())) {
						unzipPartlyArchive(zin, file.getName());
						break;
					}
					unzipPartlyArchive(zin, signlink.findcachedir() + e.getName());
				}
			}
			zin.close();
			file.delete();
		} catch(Exception e) {}
	}
	
	/**
	 * Unzips a partly archive
	 * @param zin	The zip inputstream
	 * @param s		The location of the zip file
	 * @throws IOException	The method can throw an IOException.
	 */
	public static void unzipPartlyArchive(ZipInputStream zin, String s) throws Exception {
		FileOutputStream out = new FileOutputStream(s);
		Client.getClient().drawLoadingText(100, "Unpacking data...");
		byte [] b = new byte[1024];
		int len = 0;

		while ((len = zin.read(b)) != -1) {
			out.write(b,0,len);
		}
		out.close();
	}
}
