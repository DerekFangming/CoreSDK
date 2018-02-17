package com.fmning.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import javax.imageio.ImageIO;

public class Util {
	
	public static String imagePath = "/Volumes/Data/images/";// This is configurable from outside of core SDK
	public final static int nullInt = -1;
	public final static int tokenTimeout = 7;//7 days

	public static String verifyImageType(String type) {
		if (type == null)
			return ImageType.OTHERS.getName();
		
		for (ImageType it : ImageType.values()) {
	        if (it.getName().toUpperCase().equals(type.toUpperCase())) {
	            return type;
	        }
	    }
		
		return ImageType.OTHERS.getName();
    }
	
	public static String shrinkImageAndGetDimension(int id) throws IOException {
		String fileName = imagePath + Integer.toString(id) + ".jpg";
		File outputfile = new File(fileName); 
		BufferedImage img;
		img = ImageIO.read(outputfile);
		int width = img.getWidth();
		int height = img.getHeight();
		if (width > height) {
			if (width > 2000) {
				int newHeight = height * 2000 / width;
				BufferedImage newImage = new BufferedImage(2000, newHeight, BufferedImage.TYPE_INT_RGB);

				Graphics g = newImage.createGraphics();
				g.drawImage(img, 0, 0, 2000, newHeight, null);
				g.dispose();
				ImageIO.write(newImage, "jpg", outputfile);
				return "width=\"2000\" height=\"" + Integer.toString(newHeight) + "\"";
			}
		} else {
			if (height > 2000) {
				int newWidth = width * 2000 / height;
				BufferedImage newImage = new BufferedImage(newWidth, 2000, BufferedImage.TYPE_INT_RGB);

				Graphics g = newImage.createGraphics();
				g.drawImage(img, 0, 0, newWidth, 2000, null);
				g.dispose();
				ImageIO.write(newImage, "jpg", outputfile);
				return "width=\"" + Integer.toString(newWidth) + "\" height=\"2000\"";
			}
		}
		return "width=\"" + Integer.toString(width) + "\" height=\"" + Integer.toString(height) + "\"";
	}
	
	public static String nullToEmptyString(String input){
		if(input == null){
			return "";
		}else{
			return input;
		}
	}
	
	public static String emptyStringToNull(String input){
		if(input == null) {
			return null;
		}
		if(input.trim().equals("")){
			return null;
		}else{
			return input.trim();
		}
	}
	
	public static Date parseDate(Instant instant) {
		try {
			return Date.from(instant);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Instant parseTimestamp(Timestamp timestamp) {
		return timestamp == null ? null : timestamp.toInstant();
	}
	
	public static int getNullableInt(ResultSet rs, String colName) throws SQLException {
		return rs.getObject(colName) != null ? rs.getInt(colName) : nullInt;
	}
	
	public static double getNullableDouble(ResultSet rs, String colName) throws SQLException {
		return rs.getObject(colName) != null ? rs.getDouble(colName) : nullInt;
	}
	
	public static String MD5(String plaintext) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(plaintext.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1,digest);
		String hashtext = bigInt.toString(16);
		while(hashtext.length() < 32 ){
			hashtext = "0"+hashtext;
		}
		return hashtext;
	}
}
