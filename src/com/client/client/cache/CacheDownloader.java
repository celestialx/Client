package com.client.client.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

import com.client.client.Client;
import com.client.client.signlink;

/**
 * Downloads cache if one does not exist or one is outdated.
 * 
 * @author Professor Oak
 */
public class CacheDownloader {

	private static final String CACHE_NAME = "cache.zip";
	private static final String CACHE_URL = "http://www.CelestialX.org/cache.zip";
	private static final String VERSION_URL = "http://www.CelestialX.org/version.txt";

	public static void init(boolean force) {
		double current = getCurrentVersion();
		double latest = getLatestVersion();

		if (latest > current || force) {
			try {

				/**
				 * Download latest cache
				 */
				download();

				/**
				 * Unzip the downloaded cache file
				 */
				Unzip.unzip(new File(signlink.findcachedir() + CACHE_NAME));

				/**
				 * Write new version
				 */
				File version = new File(signlink.findcachedir() + "version.txt");
				try (FileWriter f = new FileWriter(version)) {
					f.write("" + latest + "");
					f.close();
				}

			} catch (Exception e) {
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Cache could not be downloaded.\n\nHost appears to be offline right now.\n\nWould you like to try again?",
						"Warning", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					init(force);
				} else {
					System.exit(1);
				}
			}
		}
	}

	private static void download() throws Exception {
		URL url = new URL(CACHE_URL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.addRequestProperty("User-Agent", "Mozilla/4.76");
		int responseCode = httpConn.getResponseCode();
		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = signlink.findcachedir() + File.separator + CACHE_NAME;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[4096];
			long startTime = System.currentTimeMillis();
			int downloaded = 0;
			long numWritten = 0;
			int length = httpConn.getContentLength();
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
				numWritten += bytesRead;
				downloaded += bytesRead;
				int percentage = (int) (((double) numWritten / (double) length) * 100D);
				int downloadSpeed = (int) ((downloaded / 1024)
						/ (1 + ((System.currentTimeMillis() - startTime) / 1000)));
				Client.instance.setLoadingText(percentage,
						"Downloading cache " + percentage + "% @ " + downloadSpeed + "Kb/s");
			}

			outputStream.close();
			inputStream.close();

		} else {
			throw new Exception("Cache host replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

	/**
	 * You would simply just have 'version' in a text file and uploaded to web host
	 * For example: version.txt will have "1.0" and nothing else, just the version
	 * variable
	 * 
	 * @return
	 */
	private static double getCurrentVersion() {
		double version = 1.5;
		try {
			File file = new File(signlink.findcachedir() + "version.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			version = Double.parseDouble(br.readLine());
			br.close();
		} catch (Exception ex) {
			
		}
		return version;
	}

	private static double getLatestVersion() {
		double version = 0.0;
		try {
			URL url = new URL(VERSION_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.76");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			version = Double.parseDouble(br.readLine());
			br.close();
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		return version;
	}
}
