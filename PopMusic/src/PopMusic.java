
import java.io.Serializable;

public class PopMusic implements Serializable, Comparable<PopMusic> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;

	private String artist;

	private int year;

	private String lyric;

	public PopMusic() {
		super();
	}

	public static void main(String[] args) {
		System.out.println(3000 % 999);
	}

	public PopMusic(String title, String artist, int year, String lyric) {
		super();
		this.title = title;
		this.artist = artist;
		this.year = year;
		this.lyric = lyric;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
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

	/*
	 * public String toString() { return title + "\n" + artist + "\n" + year + "\n"
	 * + lyric + "\n"; }
	 */

	@Override
	public int compareTo(PopMusic o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		String key = title + artist;
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += key.charAt(i);
		}
		return sum;
	}


	@Override
	public String toString() {
		return "title: " + title + "\nartist: " + artist + "\nyear: " + year + "\nlyric: " + lyric
				+ "\n";
	}
	
	public String toStringIntro() {
		return "\ntitle: " + title + "\nartist: " + artist + "\nyear: " + year;
	}

}
