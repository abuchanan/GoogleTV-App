package edu.gentoomen.utilities;

import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.StringTokenizer;

import jcifs.smb.SmbFile;
import android.annotation.SuppressLint;
import android.util.Log;

public class Utils {

	private static final String TAG = "utils";
	
	private static Hashtable<String, String> theMimeTypes = new Hashtable<String, String>();
	static {
		StringTokenizer st = new StringTokenizer(					
						"gif		image/gif "+
						"jpg		image/jpeg "+
						"jpeg		image/jpeg "+
						"png		image/png "+
						"mp3		audio/mpeg "+
						"m3u		audio/mpeg-url " +
						"mp4		video/mp4 " +
						"mkv		video/x-matroska " +
						"avi 		video/x-msvideo " +
						"ogv		video/ogg " +
						"mov		video/quicktime " +
						"ogm		video/ogg " +
						"ogg		application/x-ogg ");
		while (st.hasMoreTokens())
			theMimeTypes.put(st.nextToken(), st.nextToken());
	}
	
	@SuppressLint("DefaultLocale")
	public static String getMimeType(String fileName) {
		
		//return null if this is a folder
		if(fileName.endsWith("/"))
			return null;
		
		if(!fileName.contains("."))
			return null;

		String mime;
		int extensionStart = fileName.lastIndexOf('.');
		Log.d(TAG, "extension found " + fileName.substring(extensionStart));
		mime = theMimeTypes.get(fileName.substring(extensionStart + 1).toLowerCase());
		return mime;

	}
	
	public static String getExtension(String fileName) {
		
		String extension = "";
		Log.d(TAG, "getting extension of: " + fileName);
		int extensionStart = fileName.lastIndexOf('.');
		
		try {
			extension = fileName.substring(extensionStart);
		} catch (Exception e) {
			Log.d(TAG, "Not a valid file. File might be a folder");
		}
		
		return extension;
	}
	
	public static boolean isRoot(String filePath) {
		
		try {
			return (new SmbFile("smb://" + filePath).getParent().toString().equalsIgnoreCase("smb://"));
		} catch (MalformedURLException e) {
			Log.d(TAG, "MalformedURLException caught on checking for root on path " + filePath);			
		}
		
		Log.d(TAG, "isRoot: Return value false");
		return false;
		
	}
	
}
