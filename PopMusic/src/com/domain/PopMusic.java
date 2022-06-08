package com.domain;

public class PopMusic {

	private String id;
	
	private String title;
	
	private String artist;
	
	private int year;
	
	private String lyric;
	

	public PopMusic() {
		super();
	}

	public PopMusic(String id, String title, String singer, int year, String lyric) {
		super();
		this.id = id;
		this.title = title;
		this.artist = singer;
		this.year = year;
		this.lyric = lyric;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSinger() {
		return artist;
	}

	public void setSinger(String singer) {
		this.artist = singer;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	@Override
	public String toString() {
		return "title: " + title + "\nsinger: " + artist + "\nyear: " + year + "\nlyric: " + lyric + "\n";
	}
	
	

}
