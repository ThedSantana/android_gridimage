package com.yahoo.ddosch.gridimagesearch.models;

public class Settings {

	public static enum ImageSize {
		icon, small, medium, large, xlarge, xxlarge, huge
	}
	
	public static enum ImageColor {
		black, blue, brown, gray, green, orange, pink, purple, red, teal, white, yellow
	}
	
	public static enum ImageType {
		face, photo, clipart, lineart
	}
	
	private ImageSize imageSize;
	private ImageColor imageColor;
	private ImageType imageType;
	private String site;
	private String query;
	private int start;
	
	private static Settings instance = new Settings();
	
	private Settings() {
	}
	
	public static Settings getInstance() {
		return instance;
	}
	
	public ImageSize getImageSize() {
		return imageSize;
	}
	public void setImageSize(ImageSize imageSize) {
		this.imageSize = imageSize;
	}
	public ImageColor getImageColor() {
		return imageColor;
	}
	public void setImageColor(ImageColor imageColor) {
		this.imageColor = imageColor;
	}
	public ImageType getImageType() {
		return imageType;
	}
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}	
}
