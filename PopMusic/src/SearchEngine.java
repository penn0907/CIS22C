import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SearchEngine {
	
	public static final String[] MENU = { "1. xxx", "2. xxx" };

	public static final String MUSIC_FILEPATH = "Songs.txt";
	
	public static void main(String[] args) {
		SearchEngine s = new SearchEngine();
		HashTable<PopMusic> musicTable = (HashTable<PopMusic>) s.readFile(SearchEngine.MUSIC_FILEPATH);
		s.initialIndex(musicTable);
		
	}
	
	public void initialIndex(HashTable<PopMusic> popMusic) {
		
		Set<String> words = new HashSet<String>();
		String musicStr = popMusic.toString();
		String[] wordsArr = musicStr.split(" |\\,|\\.|\\!|\\r?\\n|\\r|\\(|\\)|\"|\'|\\?|\\:");
		
		words.addAll(Arrays.asList(wordsArr));
		for (String string : words) {
			System.out.println(string);
		}
		// |,|.|!|\\r\\n
		
		//StringTokenizer st = new StringTokenizer(musicStr, ", !, \r");
		
		
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

}
