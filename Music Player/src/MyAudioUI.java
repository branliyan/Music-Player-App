import java.util.*;

// Simulation of a Simple Text-based Music App (like Apple Music)
// Name: Brandon

public class MyAudioUI
{
	public static void main(String[] args) {
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();

		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine()) {
			String action = scanner.nextLine();

			if (action == null || action.equals("")) {
				System.out.print("\n>");
				continue;
			} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;

			else if (action.equalsIgnoreCase("STORE"))    // List all songs
			{
				store.listAll();
			} else if (action.equalsIgnoreCase("SONGS"))    // List all songs
			{
				mylibrary.listAllSongs();
			} else if (action.equalsIgnoreCase("BOOKS"))    // List all songs
			{
				mylibrary.listAllAudioBooks();
			} else if (action.equalsIgnoreCase("PODCASTS"))    // List all songs
			{
				mylibrary.listAllPodcasts();
			} else if (action.equalsIgnoreCase("ARTISTS"))    // List all songs
			{
				mylibrary.listAllArtists();
			} else if (action.equalsIgnoreCase("PLAYLISTS"))    // List all play lists
			{
				mylibrary.listAllPlaylists();
			}
			// Download audiocontent (song/audiobook/podcast) from the store
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) {
				int index1 = 0;
				int index2 =0;
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt()) {
					index1 = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt()){
					index2 = scanner.nextInt();
					scanner.nextLine();
				}
				for (int i = index1; i<=index2; i++) {
					AudioContent content = store.getContent(i);
					if (content == null) {
						System.out.println("\nContent Not Found in Store");
						break;
					}
					try {
						mylibrary.download(content);
					} catch (ContentAlreadyDownloaded e){
						System.out.println(e.getMessage());
					}
				}

			} else if (action.equalsIgnoreCase("DOWNLOADA")){
				String artist = "";
				System.out.print("Artist Name: ");
				if (scanner.hasNext()){
					artist = scanner.nextLine();
				}

				ArrayList<Integer> indices = store.getArtistIndices(artist); //gets the indices of the content pertaining to the artists
				for (int index: indices) { //runs through each of the indices
					index++;
					try {
						mylibrary.download(store.getContent(index)); //attempts to download each song/audiobook
					} catch (ContentAlreadyDownloaded e) {
						System.out.println(e.getMessage());
					}
				}
			} else if (action.equalsIgnoreCase("DOWNLOADG")){ //downloads based off of the genre
				String genre = "";
				System.out.print("Genre: ");
				if (scanner.hasNext()){
					genre = scanner.nextLine();
				}
				ArrayList<Integer> indices = store.getGenreIndices(genre);
				for (int index: indices){
					index++;
					try{
						mylibrary.download(store.getContent(index));
					} catch (ContentAlreadyDownloaded e){
						System.out.println(e.getMessage());
					}

				}
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song
			else if (action.equalsIgnoreCase("PLAYSONG")) {
				int index =0;

				System.out.print("Song Number: ");
				if (scanner.hasNextLine()){ //gets the user to enter a song number
					index = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					mylibrary.playSong(index); //if it cannot complete the task, then it will print the error message
				} catch (AudioContentNotFound e){
					System.out.println(e.getMessage());
				}

			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) {
				int index =0;
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextLine()){ //gets the user to enter an audiobook number
					index = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					mylibrary.printAudioBookTOC(index); //it cannot complete the task then it will print the error message
				}catch (AudioContentNotFound e) {
					System.out.println(e.getMessage());
				}

				// Print error message if the book doesn't exist in the library
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) {
				int index =0;
				int chapter =0;
				System.out.print("Audio Book Number: "); //gets the user to enter the audiobook number
				if (scanner.hasNextLine()){
					index = scanner.nextInt();
					scanner.nextLine();
				}

				System.out.print("Chapter: "); //gets the user to enter a chapter
				if (scanner.hasNext()) {
					chapter = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					mylibrary.playAudioBook(index, chapter); //it if cannot complete the task then it will print the error message
				} catch (AudioContentNotFound | ChapterNotFound e){
					System.out.println(e.getMessage());
				}


			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts,
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) {


			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts,
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) {

			}
			// Specify a playlist title (string)
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) {
				String playlistTitle="";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext()) { //gets the use to enter the title of the playlist
					playlistTitle = scanner.nextLine();
				}
				try {
					mylibrary.playPlaylist(playlistTitle);//plays the playlist or returns a message
				} catch (PlayListNotFound e){
					System.out.println(e.getMessage());
				}


			}
			// Specify a playlist title (string)
			// Read the index of a song/audiobook/podcast in the playist from the keyboard
			// Play all the audio content
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) {
				String playlistTitle = "";
				int index = 0;
				System.out.print("Playlist Title: "); //gets the user to enter the playlist title
				if (scanner.hasNext()){
					playlistTitle = scanner.nextLine();
				}
				System.out.print("Content Number: "); //gets the user to enter the content number
				try {
					if (scanner.hasNext()){
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.playPlaylist(playlistTitle, index);
				} catch (PlayListNotFound | InputMismatchException e){
					System.out.println(e.getMessage());
				}
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) {
				int index =0;
				System.out.print("Library Song #: "); //gets the user to enter the library song number
				if (scanner.hasNext()){
					index = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					mylibrary.deleteSong(index); //if it cannot delete the song then it will print the proper error message
				} catch (AudioContentNotFound e){
					System.out.println(e.getMessage());
				}


			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) {
				String playlistName = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext()) {
					playlistName = scanner.nextLine(); //gets the string from the user
				}
				try {
					mylibrary.makePlaylist(playlistName);  //makes the playlist or sends a message back if there is already a playlist under the same name
				} catch (DuplicatePlaylist e){
					System.out.println(e.getMessage());
				}
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))    // print playlist content
			{
				String playlistName = "";
				System.out.print("Playlist Title: "); //gets the playlist title
				if (scanner.hasNext()){
					playlistName = scanner.nextLine();
				}
				try {
					mylibrary.printPlaylist(playlistName); //prints the error message when applicable
				} catch (PlayListNotFound e){
					System.out.println(e.getMessage());
				}
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) {
				String playlistName = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext()){
					playlistName = scanner.nextLine(); //gets the playlist title
				}
				String type = "";
				System.out.print("Content Type [SONG,AUDIOBOOK]: ");
				if (scanner.hasNext()){
					type = scanner.nextLine(); //gets the content type
				}

				try {
					int index = 0;
					System.out.print("Library Content #: ");
					if (scanner.hasNext()) {
						index = scanner.nextInt(); //gets the content number
						scanner.nextLine();
					}
					mylibrary.addContentToPlaylist(type, index, playlistName);
				} catch (InputMismatchException |PlayListNotFound | AudioContentNotFound | AudioContentAlreadyFound e){
					System.out.println(e.getMessage());
				}
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) {

				String playlistName = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext()){
					playlistName = scanner.nextLine(); //gets the playlist title
				}

				try {
					int index = 0;
					System.out.print("Playlist Content #: ");
					if (scanner.hasNext()) {
						index = scanner.nextInt(); //gets the content number
						scanner.nextLine();
					}
					mylibrary.delContentFromPlaylist(index, playlistName);
				}catch (InputMismatchException | PlayListNotFound e){
					System.out.println(e.getMessage());
				}


			} else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			} else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			} else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			} else if (action.equalsIgnoreCase("SEARCH")){ //searches for store content by the title, displays index
				System.out.print("Title: ");
				String title = scanner.nextLine();
				try {
					store.printTitleSearch(title.toLowerCase());
				} catch (NullPointerException e){
					System.out.println("No Matches For "+title);
				}
			} else if (action.equalsIgnoreCase("SEARCHA")){
				System.out.print("Artist: ");
				String artist = scanner.nextLine();
				try {
					store.printArtistSearch(artist);
				} catch (NullPointerException e){
					System.out.println("No Matches For "+artist);
				}
			} else if (action.equalsIgnoreCase("SEARCHG")){
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				String genre = scanner.nextLine();
				try {
					store.printGenreSearch(genre);
				} catch (NullPointerException e){
					System.out.println("No Matches For "+genre);
				}
			}

			System.out.print("\n>");
		}
	}
}
