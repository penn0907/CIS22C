

public class WordID {
	
	private String word;
	
	private int id;
	
	public WordID() {
		
	}
	
	public WordID(String word) {
		super();
		this.word = word;
	}
	
	public WordID(String word, int id) {
		super();
		this.word = word;
		this.id = id;
	}
	

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "WordID [word=" + word + ", id=" + id + "]";
	}

}
