package com.client;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;


/**
 * @author KingFox
 */
public class Launcher {
	
	/** The direct url where you wish to download the jar from */
    static final String downloadUrl = "https://www.CelestialX.org/CelestialX.jar";
    
    /** The name of the jar file you download, can be anything */
	static final String fileName = "CelestialX.jar";
	
	/** Displayed on the loading bar, can change to anything */
	static final String serverName = "CelestialX";
	
	/** The direct link to an image for the splash background */
	static final String backgroundImageUrl = "http://near-reality.org/forums/uploads/monthly_2018_01/5a5b6b72a6d4c_bgpsych.jpg.8921143f84ff0e38f929b200dd6a4b5b.jpg";
	
	/** The directory of where you wish to store the jar file */
	 static String saveDirectory = System.getProperty("user.home")+"/";
	
	/**
	 * Launches the application
	 * @param args
	 */
    public static void main(String[] args) {
    	new Launcher();
    }
    
    public JButton leftButton;
    public JButton rightButton;
    public JFrame frame;
    public JLabel imglabel;
    public ImageIcon img;
    public URL url;
    public JProgressBar pbar;
    public Thread t = null;
    
    /**
     * The constructor, creates the frame and adds all of the child components
     * displays the splash screen and determines if the jar file needs updated
     * or redownloaded. You can change the size by editing in two spots where
     * you see 543, 391. 
     */
    public Launcher() {
    	frame = new JFrame();
        try {
	        frame.setSize(543, 391);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setLocationRelativeTo(null);
	        frame.setUndecorated(true);
			img = new ImageIcon(new URL(backgroundImageUrl));
	        
			leftButton = new JButton("Start");
			leftButton.setBounds(180, 320, 85, 30);
			leftButton.setBorderPainted(false);
			frame.add(leftButton);
			leftButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					initializeClient();
				}
			});
	        leftButton.setVisible(true);
	        
	        rightButton = new JButton("Exit");
	        rightButton.setBounds(275, 320, 85, 30);
	        rightButton.setBorderPainted(false);
			frame.add(rightButton);
			rightButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Button pressed");
					
				}
			});
			rightButton.setVisible(true);
	        
	        imglabel = new JLabel(img);
	        frame.add(imglabel);
	        frame.setLayout(null);
	        pbar = new JProgressBar();
	        pbar.setMinimum(0);
	        pbar.setMaximum(100);
	        pbar.setStringPainted(true);
	        pbar.setBorderPainted(false);
	        imglabel.setBounds(0, 0, 543, 391);
	        frame.add(pbar);
	        pbar.setPreferredSize(new Dimension(310, 30));
	        pbar.setBounds(70, 320, 404, 20);
	        
	        frame.setVisible(true);
	       
        } catch (Exception e) {
        	System.err.println("Unable to retrieve Jar file. Please check to make sure you have an internet connection and try again.");
        }
    }
    
    public void initializeClient() {
    	try {
    		File file = new File(saveDirectory + fileName);
        	url = new URL(downloadUrl);
        	
            if (file.exists()) {
            	URLConnection connection = url.openConnection();
            	connection.connect();
            	long localFileTime = file.lastModified(); // timestamp in milliseconds of the local jar
    			long remoteFileTime = connection.getLastModified();// timestamp in milliseconds of the remote jar
    			long urlSize = connection.getContentLengthLong(); // size in kb of the remote jar
    			long sizeOnFile = file.length(); // size in kb of the local jar
    			if (isCorrupted(sizeOnFile, urlSize)) {
    				if (!confirmPatch()) { // display dialogue
						System.exit(0);
						return;
					}
    				startDownload(); // starts the download
    				return;
    			}
    			if (updateAvailable(localFileTime, remoteFileTime)) {
					if (!confirmUpdate()) { // display dialogue
						System.exit(0);
						return;
					}
					startDownload(); // starts the download
					return;
				}
    			startApplication();
    			return;
            } 
            startDownload();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
	/**
     * Checks if the remote jar's last modified time is newer than the local jar file.
     * @param localDate of the last modified time of the local jar, in milliseconds
     * @param remoteDate of the last modified time of the remote jar, in milliseconds
     * @return true if the jar stored on the webserver is newer
     */
    
    public boolean updateAvailable(long localDate, long remoteDate) { 
    	System.out.println(" localDate: "+localDate);
    	System.out.println("remoteDate: "+remoteDate);

    	return localDate < remoteDate;
    }
    
    /**
     * Checks if the local jar file is smaller than remote jar, possibly from
     * an incomplete download or corrupted jar.
     * @param localSize of the locally stored jar file
     * @param remoteSize of the remotely stored jar file
     * @return true if the local file is smaller than the remote file
     */
    public boolean isCorrupted(long localSize, long remoteSize) {
    	System.out.println(" localSize: "+localSize);
    	System.out.println("remoteSize: "+remoteSize);
    	return localSize < remoteSize;
    }
    
    /**
     * Displays a dialogue message to confirm the patch if the jar file is corrupted,
     * possibly from an incomplete download
     * @return true if the user clicks Yes, false if no
     */
    public boolean confirmPatch() {
        int selection = JOptionPane.showConfirmDialog(null, "Jar file is corrupt. Do you wish to redownload?", "Corrupt Jar File", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        return selection == JOptionPane.OK_OPTION;
    }
    
    /**
     * Displays a dialogue message to confirm the update if a newer jar exists on the remote server
     * @return true if the user clicks Yes, false if no
     */
    public boolean confirmUpdate() {
        int selection = JOptionPane.showConfirmDialog(null, "An update is available. Do you wish to download?", "Update Available", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        return selection == JOptionPane.OK_OPTION;
    }
    
    /**
     * Launches the downloaded jar file by using the java Runtime library,
     * which also has some other helpful methods in there as well. Will
     * exit this script after the jar has launched.
     */
    public static void startApplication() {
    	try {
			Runtime.getRuntime().exec("java -jar "+(saveDirectory + fileName)+"");
			System.exit(0);
		} catch (IOException e)  {
			e.printStackTrace();
		}
    }
    
    /**
     * Downloads the jar file from a remote url and stores it in specified directory
     * which can be found at the top of this class
     * @see {@link #saveDirectory}
     * @see {@link #fileName}
     * @see {@link #serverName}
     */
    public void startDownload() {
    	Thread t = new Thread() {
    		 
            public void run() {
            	OutputStream dest = null;
            	URLConnection download;
            	InputStream readFileToDownload = null;
            	try {
            		dest = new BufferedOutputStream(new FileOutputStream(saveDirectory + fileName)); 
            		download = url.openConnection();
            		readFileToDownload = download.getInputStream();
            		byte[] data = new byte[1024];
            		int numRead;
            		long numWritten = 0;
            		int length = download.getContentLength();
            		while ((numRead = readFileToDownload.read(data)) != -1) {
            			dest.write(data, 0, numRead);
            			numWritten += numRead;
            			int percent = (int)(((double)numWritten / (double)length) * 100D);
            			pbar.setValue(percent);
            			pbar.setString("Downloading "+serverName+" - "+percent+"%");
            		}
            	} catch (Exception exception) {
            		System.err.println("Unable to download Jar file. Please check to make sure you have an internet connection and try again.");
            	} finally {
            		try {
            			if (readFileToDownload != null)
            				readFileToDownload.close();
            			if (dest != null)
            				dest.close();
            			Thread.sleep(2000L);
            			pbar.setVisible(false);
            			Thread.sleep(1000L);
            			startApplication();
            		} catch (IOException | InterruptedException ioe) {
            			
            		}
            	}
            }
        };
        t.start();
    }
    
   
}