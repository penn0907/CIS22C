import java.util.*;

public class MenuInterface {
	public static final String[] MENU = { "1. Insert a song", "2. Delete a song", "3. Search a song by primary key",
			"4. Seach a song by keywords", "5. Modify a song", "6. Display statistics of a song",
			"7. Print all songs and exit" };

	public static void displayMenu(String[] menu) {
		for (int i = 0; i < menu.length; i++) {
			System.out.println(menu[i]);
		}
	}

	public static void main(String[] args) {
		SearchEngine searchEngine1 = new SearchEngine();

		System.out.println("Welcome to Pop Music Interface!");

		System.out.println("Here are some ways you can interact with it:");

		Scanner sc = new Scanner(System.in);
		int choice;

		do {
			System.out.println();
			displayMenu(MENU);
			System.out.println("\nEnter 1-7 for the operation you want to take place: ");
			choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1:
				System.out.println("\nYou have chosen to insert a new song! Proceed to next steps.");

				System.out.print("Enter the song's title: ");
				String songTitle = sc.nextLine();

				System.out.printf("Enter the song's artist: ");
				String songArtist = sc.nextLine();

				System.out.println("Enter the song's release year: ");
				int songYear = sc.nextInt();

				System.out.println("Enter the song's lyrics: "); // FIX ME - doesn't correctly accept multiple-lined
				sc.nextLine();
				StringBuilder lyrics = new StringBuilder();
				do {

					String string = sc.nextLine();

					if (string.equals("")) {

						break;

					}

					lyrics.append(string).append("\n");

				} while (true);

				String songLyrics = lyrics.toString();

				PopMusic popSong = new PopMusic(songTitle, songArtist, songYear, songLyrics);

				if (searchEngine1.addPopMusic(popSong)) {
					System.err.println("Successfully added.");
				}

				break;

			case 2:
				System.out.println("\nYou have chosen to delete a song! Proceed to next steps.");

				System.out.println("Here are the list of the songs you can delete: ");
				searchEngine1.printDetails();

				System.out.println("Enter the song's title ");
				String title = sc.nextLine();

				System.out.println("Enter the song's artist: ");
				String artist = sc.nextLine();

				if (searchEngine1.deletePopMusic(title, artist)) {
					System.err.println("Successfully deleted.");
				} else {
					System.err.println("Can't delete the song because it's not in the list!");
				}

				break;

			case 3: // Search by primary key
				System.out.println("You have chosen to search for a song! Proceed to next steps.");

				System.out.println("Here are the list of the songs: ");
				// searchEngine1.printDetails();

				System.out.println("Enter the song's title: ");
				String title1 = sc.nextLine();

				System.out.println("Enter the song's artist: ");
				String artist1 = sc.nextLine();
				
				PopMusic pop = searchEngine1.searSongByPrimaryKey(title1, artist1);

				if (pop != null) {
					System.out.print(pop.toString());
				} else {
					System.out.println("Can't search for the song because it's not in the list!");
				}

				break;

			case 4: // Search by keywords
				System.out.println("\nYou have chosen to search for a song by its lyrics! Proceed to next steps.");

				System.out.println("Enter the lyrics of the song you're searching for: ");
				String lyrics1 = sc.nextLine();

				BST<PopMusic> p = searchEngine1.searchByWord(lyrics1);
				if (p != null) {
					System.out.println(p.inOrderString());
				} else {
					System.out.println("Can't search for the song because it's not in the list!");
				}

				break;
			case 5: // Modify a song
				System.out.println("You have chosen to modify a song! Proceed to next steps.");

				System.out.println("Here are the list of the songs: ");
				searchEngine1.printDetails();

				System.out.println("Enter the song's title you want to modify: ");
				String title2 = sc.nextLine();
				System.out.println("Enter the song's artist you want to modify: ");
				String artist2 = sc.nextLine();
				

				/*
				 * if (searchEngine1.searchByTitle(title2) != null) {
				 * System.out.println("Enter the new song's year: "); String newYear =
				 * sc.nextLine(); System.out.println("Enter the new song's lyrics: "); // FIX ME
				 * - doesn't properly take in lyrics Scanner scanIn = new Scanner(System.in);
				 * StringBuilder lyric1 = new StringBuilder(); while (scanIn.hasNextLine()) {
				 * lyric1.append(scanIn.nextLine()); } String newLyrics = lyric1.toString();
				 * 
				 * searchEngine1.updatePopMusic(new PopMusic(title2), newYear, newLyrics); }
				 * else {
				 * System.out.println("Can't modify the song because it's not in the list!"); }
				 */
				break;
			case 6: // Display statistics
			    System.out.println("Pop music Statistics"); //title
			    
			    // Statistics 1
			    System.out.println("Pop music is the most popular music genre in the world, "
			         + "with around 64% of the 19,000 respondents from 18 countries. (STATISTA)");
			    System.out.println(); // skip a line
			    
			    // Statistics 2
			    System.out.println("Sales of digital assets through blockchain grew to a size of tens of million of U.S. dollars in early 2021."
			         + "\nThe music world sales and overall respect and valus for NFTs, "
			         + "this is something that will likely be the norm in the future. (STATISTA)");
			    System.out.println(); 
			    
			    // Statistics 3
			    System.out.println("The streamed music distribution in the U.S. in 2021 by genra: "
			         + "R&B/Hip-hop 29.9%, Rock 17%, Pop 13.3%, and others (STATISTA)");
			    System.out.println(); 
			    
				break;
			case 7: // Display all songs and exit
				System.out.println("Enter option 7");
				
				break;
			default:
				System.out.println("You must enter a number from 1 to 7. Enter a number: ");
				choice = Integer.parseInt(sc.nextLine());
			}

		} while (choice >= 1 || choice <= 7);
		sc.close();
	}

}
