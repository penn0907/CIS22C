package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import domain.HashTable;
import domain.PopMusic;

public class IOService {

	private File song = new File("Songs.txt");

	@SuppressWarnings("unchecked")
	public HashTable<PopMusic> initMusicList() {

		HashTable<PopMusic> musics = null;

		FileInputStream fis = null;
		ObjectInputStream oi = null;

		try {
			fis = new FileInputStream(song);
			oi = new ObjectInputStream(fis);

			Object o = oi.readObject();
			if (o instanceof HashTable) {
				musics = (HashTable<PopMusic>) o;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return musics;

	}

	public void writeMusicsToFile(HashTable<PopMusic> music) {
		

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(song);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(music);

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
