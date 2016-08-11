package com.kjuns.model;

public class Banner extends BaseModel{
	
	private String title;
	
	private String content;
	
	private String url;
	
	private String background;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

}
