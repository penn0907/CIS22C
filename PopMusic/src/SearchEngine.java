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

	public static final int HASHTABLE_SIZE_MUTIPLE = 3;

	private List<BST<PopMusic>> invertedIndex;

	private HashTable<WordID> wordIds;

	private HashTable<PopMusic> musicTable;

	public SearchEngine() {

		musicTable = (HashTable<PopMusic>) readFile(SearchEngine.MUSIC_FILEPATH);
		initialIndex();

	}

	public BST<PopMusic> searchByWord(String word) {
		WordID wordId = new WordID(word);
		int bucket = wordId.hashCode() % wordIds.getTableSize();
		LinkedList<WordID> ids = wordIds.get(bucket);
		
		if(ids.getLength() <= 0) {	// cannot find the word
			return null;
		} else if (ids.getLength() == 1) {	//no collision
			
			return getMuscisByWordId(ids.getFirst().getID());
			
		} else {						//has collision
			//check collision, get the word id from
			int id = getWordId(ids, word);
			if(id < 0) {
				return null;
			}
			
			return getMuscisByWordId(id);
			
		}
		
	}
	
	private BST<PopMusic> getMuscisByWordId(int bucket){
		BST<PopMusic> musics = invertedIndex.get(bucket);
		if (musics.getSize() <= 0) {
			return null;
		}
		return musics;
	}
	
	private int getWordId(LinkedList<WordID> ids, String word) {
		ids.positionIterator();
		while (!ids.offEnd()) {
			WordID id = ids.getIterator();
			if(id.getWord().equals(word)) {
				return id.getID();
			}
			ids.advanceIterator();
		}
		
		return -1;
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
		//System.out.println(s.searchByWord("arms").inOrderString());

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
		int wordIdSize = words.size() * HASHTABLE_SIZE_MUTIPLE;
		wordIds = new HashTable<WordID>(wordIdSize);
		invertedIndex = new ArrayList<BST<PopMusic>>(wordIdSize);

		for (int i = 0; i < wordIdSize; i++) {
			invertedIndex.add(new BST<PopMusic>());
		}

		int idCount = 0;
		for (String word : words) {
			if (!isContain(STOP_WORDS, word)) {
				// int wordHash = bigHashCode(word.toLowerCase(), wordIdSize);
				idCount++;
				
				WordID wId = new WordID(word, idCount);
				wordIds.add(wId);

				ArrayList<PopMusic> musicsT = musicTable.getElementContains(word);

				if (musicsT.size() <= 0) {
					break;
				}

				for (PopMusic music : musicsT) {
					invertedIndex.get(wId.getID()).insert(music);
				}

			}

		}

		//System.out.println(wordIds.toString());

		for (int i = 0; i < invertedIndex.size(); i++) {
			BST<PopMusic> a = invertedIndex.get(i);
			if (a != null) {
				String str = a.inOrderString();
				if (str != null || !str.equals("") || str.equals("\\n")) { //
					//System.out.println(i + ": " + str);
				}
			}
		}

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
	
	public PopMusic searSongByPrimaryKey(String title, String artist) {
		PopMusic p = new PopMusic(title, artist, 0, "");
		int bucket = p.hashCode() % musicTable.getTableSize();
		LinkedList<PopMusic> list = musicTable.get(bucket);
		
		if (list.getLength() == 1) {
			return list.getFirst();
		} 
		
		return null;
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

	public static int bigHashCode(String str, int size) {
		int code = str.hashCode() & Integer.MAX_VALUE;
		return code % size;
	}

}
