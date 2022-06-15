import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchEngine {

	public static final String MUSIC_FILEPATH = "Songs.txt";

	public static final String[] STOP_WORDS = { "a", "en", "go", "he", "if", "me", "mi", "mr", "ms", "Sí", "Ya", "ah",
			"an", "by", "em", "be", "it", "it's", "at", "on", "to", "of", "the", "and", "uh", "oh", "ohh", "yeah",
			"hmm", "mm", "mmm", "then", "just", "for", "from", "in", "is", "not", "no", "one", "under", "around",
			"every", "b", "i", "n", "p", "s", "v", "y", "c", "d", "f", "m", "o", "p", "s", "t", "y", "okay", "everyone",
			"gonna", "gotta", "that", "than", "do", "bo", "too", "as", "does", "whoa", "title:", "artist:", "year:",
			"lyric:", "ID:", "" };

	private List<BST<PopMusic>> invertedIndex;

	private HashTable<WordID> wordIds;

	private HashTable<PopMusic> musicTable;

	public SearchEngine() {

		musicTable = (HashTable<PopMusic>) readFile(SearchEngine.MUSIC_FILEPATH);
		initialIndex();

	}

	public PopMusic searchByWord(String word) {
		int wordId = PopMusic.bigHashCode(word.toLowerCase(), wordIds.getTableSize());
		BST<PopMusic> musics = invertedIndex.get(wordId);
		if(musics.getSize() <= 0) {
			return null;
		}
		return musics.getRoot();
	}

	/**
	 * Searches the hash table by key
	 *
	 * @param title
	 * @param artist
	 */
	public PopMusic searchByKey(int primaryKey) {
		return musicTable.get(primaryKey).getFirst();
	}

	public static void main(String[] args) {

		SearchEngine s = new SearchEngine();
		System.out.println(s.searchByKey(1292).toString());

	}

	private boolean isContain(String[] arr, String str) {

		for (String string : arr) {
			if (string.equalsIgnoreCase(str)) {
				return true;
			}
		}

		return false;
	}

	public void initialIndex() {

		Set<String> words = new HashSet<String>();
		String musicStr = musicTable.toString();
		String[] wordsArr = musicStr.split(" |\\,|\\.|\\!|\\r?\\n|\\r|\\(|\\)|\"|\'|\\?|\\-");

		words.addAll(Arrays.asList(wordsArr));
		/*
		 * words.remove("title:"); words.remove("artist:"); words.remove("year:");
		 * words.remove("lyric:");
		 */
		int wordIdSize = words.size() * 300;
		wordIds = new HashTable<WordID>(wordIdSize);
		invertedIndex = new ArrayList<BST<PopMusic>>(wordIdSize);

		for (int i = 0; i < wordIdSize; i++) {
			invertedIndex.add(new BST<PopMusic>());
		}

		for (String word : words) {
			if (!isContain(STOP_WORDS, word)) {
				int wordHash = PopMusic.bigHashCode(word.toLowerCase(), wordIdSize);
				WordID wId = new WordID(word, wordHash);
				wordIds.addWithBucketNum(wordHash, wId);

				ArrayList<LinkedList<PopMusic>> table = musicTable.getTable();

				for (int i = 0; i < table.size(); i++) {
					LinkedList<PopMusic> mList = table.get(i);
					mList.positionIterator();
					while (!mList.offEnd()) {
						PopMusic p = mList.getIterator();

						String str = p.getTitle() + " " + p.getArtist() + " " + p.getLyric() + " " + p.getYear();
						if (str.toLowerCase().contains(word.toLowerCase())) {
							invertedIndex.get(wordHash).insert(p);
						}
						mList.advanceIterator();
					}

				}

			}

		}

		/*
		 * System.out.println(wordIds.toString());
		 * 
		 * for (int i = 0; i < invertedIndex.size(); i++) { BST<PopMusic> a =
		 * invertedIndex.get(i); if (a != null) { String str = a.inOrderString(); if
		 * (str != null || !str.equals("") || str.equals("\\n")) { //
		 * System.out.println(i + ": " + str); } } }
		 */

	}

	public Object readFile(String filePath) {

		Object o = null;

		FileInputStream fis = null;
		ObjectInputStream oi = null;

		try {
			fis = new FileInputStream(filePath);
			oi = new ObjectInputStream(fis);

			o = oi.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return o;

	}

	public void writeFile(Object o, String filePath) {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(filePath);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(o);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.flush();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void refreshData() {
		writeFile(musicTable, MUSIC_FILEPATH);
		initialIndex();
	}

	public boolean addPopMusic(PopMusic popSong) {
		musicTable.add(popSong);
		refreshData();
		return true;
	}

	public void printDetails() {
		ArrayList<LinkedList<PopMusic>> musics = musicTable.getTable();
		for (LinkedList<PopMusic> linkedList : musics) {
			linkedList.positionIterator();
			while (!linkedList.offEnd()) {

				PopMusic p = linkedList.getIterator();
				System.out.println(p.toStringIntro());

				linkedList.advanceIterator();
			}
		}
	}

	public boolean deletePopMusic(String title, String artist) {

		PopMusic p = new PopMusic(title, artist, 0, "");
		int bucket = p.hashCode() % musicTable.getTableSize();
		LinkedList<PopMusic> list = musicTable.get(bucket);
		if (list.getLength() == 1) {
			return deleteMuscis(list.getFirst());
		} else if (list.getLength() > 1) {

			list.positionIterator();

			while (!list.offEnd()) {
				PopMusic music = list.getIterator();
				if (music.equals(p)) {
					return deleteMuscis(list.getFirst());
				}
				list.advanceIterator();
			}
		}

		return false;
	}

	private boolean deleteMuscis(PopMusic music) {
		boolean flag = musicTable.delete(music);
		refreshData();
		return flag;
	}

}
