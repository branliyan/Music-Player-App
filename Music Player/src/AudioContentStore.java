import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library
// Name: Brandon Liyanage

public class AudioContentStore {
	private ArrayList<AudioContent> contents;
	private HashMap<String, Integer> titleMap;
	private HashMap<String, ArrayList<Integer>> artistMap;
	private HashMap<String, ArrayList<Integer>> genreMap;


	public AudioContentStore() {
		contents = contentFarm();
		titleMap = createTitleMap();
		artistMap = createArtistMap();
		genreMap = createGenreMap();
	}

	private ArrayList<AudioContent> contentFarm() {
		ArrayList<AudioContent> contents = new ArrayList<>();
		Scanner in = null;
		try {
			in = new Scanner(new File("store.txt"));
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		while (in.hasNextLine()) {
			//creates the proper variables that will use the audiocontent objects
			String id = null;
			String title = null;
			int year = 0;
			String composer = null;
			String artist = null;
			int length = 0;
			int numLines = 0;
			String genre = null;
			String file = ""; // this will store the lyrics/content

			//Specific got the AUDIOBOOK
			String author = null; //Author of AudioBook
			String narrator = null;
			int numChap = 0; //number of chapters
			ArrayList<String> chapterTitles = new ArrayList<>(); //used to store the chapterTitles
			ArrayList<String> chapterLines = new ArrayList<>();
			String contentType = in.nextLine();
			if (contentType.equals("SONG")) { //will check the song type. It will follow this logic if it finds the
				System.out.println("LOADING SONG");
				id = in.nextLine(); //gets the id
				title = in.nextLine();//gets the title
				year = in.nextInt(); //gets the year
				length = in.nextInt(); //gets the length
				in.nextLine();
				composer = in.nextLine(); //gets the composer
				artist = in.nextLine(); //gets the artist
				genre = in.nextLine(); //gets the genre from the text file
				Song.Genre genreType = null; //creates a Song.Genre object that will return the Genre type
				switch (genre) {
					case "POP":
						genreType = Song.Genre.POP;
						break;
					case "ROCK":
						genreType = Song.Genre.ROCK;
						break;
					case "JAZZ":
						genreType = Song.Genre.JAZZ;
						break;
					case "HIPHOP":
						genreType = Song.Genre.HIPHOP;
						break;
					case "RAP":
						genreType = Song.Genre.RAP;
						break;
					case "CLASSICAL":
						genreType = Song.Genre.CLASSICAL;
						break;
				}
				numLines = in.nextInt();
				in.nextLine();
				//this will add all of the lyrics to
				for (int i = 0; i < numLines; i++) { //this adds all of the lyrics and stops once it reaches another SONG or AUDIOBOOK line
					file += (in.nextLine() + "\r\n");
				}
				// creates the new song object
				contents.add(new Song(title, year, id, Song.TYPENAME, file, length, composer, artist, genreType, file));
			} else if (contentType.equals("AUDIOBOOK")) { //if it reads that the next audiocontent is an audiobook
				System.out.println("LOADING AUDIOBOOK");
				id = in.nextLine();
				title = in.nextLine();
				year = in.nextInt();
				length = in.nextInt();
				in.nextLine();
				author = in.nextLine();
				narrator = in.nextLine();
				numChap = in.nextInt(); // this will read the number of chapters
				in.nextLine();
				String lines = "";
				for (int i = 0; i < numChap; i++) {//gets all of the chapters
					chapterTitles.add(in.nextLine());
				}
				for (int i = 0; i < numChap; i++) { //each time this iterates, it adds a new chapter to the audiobook
					numLines = in.nextInt();
					in.nextLine();
					lines = "";
					for (int j = 0; j < numLines; j++) { //adds all of the lines of the chapter
						lines += "\n" + in.nextLine();
					}
					chapterLines.add(lines);
				}
				contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapterLines));
			}
		}
		return contents;
	}

	private HashMap<String, Integer> createTitleMap() {
		HashMap<String, Integer> titleMap = new HashMap<>();
		for (int i = 0; i < contents.size(); i++) {
			titleMap.put(contents.get(i).getTitle().toLowerCase(),i); //this adds the title as the key paired with an empty arraylist
		}
		return titleMap;
	}

	private HashMap<String, ArrayList<Integer>> createArtistMap() {
		HashMap<String, ArrayList<Integer>> artistMap = new HashMap<>();
		//finds all of the artists in the store and uses each as a key
		for (int i = 0; i < contents.size(); i++) {
			if (contents.get(i).getType().equals(Song.TYPENAME)){ //checks to see if it is a song type
				Song song = (Song) contents.get(i); //creates a song object
				if (artistMap.containsKey(song.getArtist().toLowerCase())){ //checks to see if the song artist (key) is already registered in the map
					artistMap.get(song.getArtist().toLowerCase()).add(i); //gets the value (song arraylist) mapped by the key (artist) and adds the song to it
				} else { //this means that the song
					artistMap.put(song.getArtist().toLowerCase(),new ArrayList<>()); //creates the key (artist) and value (arraylist of songs, initially empty)
					artistMap.get(song.getArtist().toLowerCase()).add(i); //adds the respective song to the arraylist corresponding to the artist key
				}
			} else if (contents.get(i).getType().equals(AudioBook.TYPENAME)){ //checks to see if the audiocontent type is an arraylist
				AudioBook book = (AudioBook)contents.get(i); //casts the audiocontent into an audiobook object
				if (artistMap.containsKey(book.getAuthor().toLowerCase())){
					artistMap.get(book.getAuthor().toLowerCase()).add(i); //adds the index to the arraylist corresponding to the author (key)
				} else { //this would mean that the author is not already in the Map
					artistMap.put(book.getAuthor().toLowerCase(),new ArrayList<>()); //adds the author (key) to the map and assigns an empty integer arraylist as the key
					artistMap.get(book.getAuthor().toLowerCase()).add(i);//adds the index to the arraylist
				}
			}

		}
		return artistMap;
	}

	private HashMap<String,ArrayList<Integer>> createGenreMap(){ //creates a map Genre (Key) to an arraylist of indices of songs that have the genre (value)
		HashMap<String,ArrayList<Integer>> genreMap = new HashMap<>();
		genreMap.put("CLASSICAL", new ArrayList<>());
		genreMap.put("ROCK", new ArrayList<>());
		genreMap.put("JAZZ", new ArrayList<>());
		genreMap.put("POP", new ArrayList<>());
		genreMap.put("RAP", new ArrayList<>());
		genreMap.put("HIPHOP", new ArrayList<>());
		for (int i = 0; i <contents.size(); i++) {
			if (contents.get(i).getType().equals(Song.TYPENAME)){ //checks to see if the object is a song since only songs have Genres
				Song song = (Song) contents.get(i);
				if (song.getGenre().equals(Song.Genre.CLASSICAL)){ //checks to see if the song is Classical
					genreMap.get("CLASSICAL").add(i);
				} else if (song.getGenre().equals(Song.Genre.ROCK)){ //checks to see if the song is Rock
					genreMap.get("ROCK").add(i);
				} else if (song.getGenre().equals(Song.Genre.JAZZ)){ //checks to see if the song is Jazz
					genreMap.get("JAZZ").add(i);
				} else if (song.getGenre().equals(Song.Genre.POP)){ //checks to see if the song is Pop
					genreMap.get("POP").add(i);
				} else if (song.getGenre().equals(Song.Genre.RAP)){ //checks to see if the song is Rap
					genreMap.get("RAP").add(i);
				} else if (song.getGenre().equals(Song.Genre.HIPHOP)){ //checks to see if the song is Hiphop
					genreMap.get("HIPHOP").add(i);
				}
			}
		}
		return genreMap;
	}


	public ArrayList<Integer> getArtistIndices(String artist){ //returns the arraylist of indices (values of the map)
		return artistMap.get(artist.toLowerCase()); //will return the value corresponding to the key
	}

	public ArrayList<Integer> getGenreIndices(String genre){
		return genreMap.get(genre.toUpperCase()); //will return the value of the key in the genre map
	}


	public void printTitleSearch(String title){ //displays the index of the title that the user entered
		System.out.print((titleMap.get(title.toLowerCase())+1)+". ");
		contents.get(titleMap.get(title)).printInfo();//gets the index of the song by its title using the map
	}

	public void printArtistSearch(String artist){
		for (int index: artistMap.get(artist.toLowerCase())){ //iterates through each of the index values of the song
			System.out.print((index+1)+". ");
			contents.get(index).printInfo(); //plays the song corresponding to each index
			System.out.println();
		}
	}

	public void printGenreSearch(String genre){
		for (int index :genreMap.get(genre.toUpperCase())){
			System.out.print((index+1)+". ");
			contents.get(index).printInfo();
			System.out.println();
		}
	}

	public AudioContent getContent(int index) {
		if (index < 1 || index > contents.size()) {
			return null;
		}
		return contents.get(index-1);
	}

	public void listAll() {
		for (int i = 0; i < contents.size(); i++) {
			int index = i + 1;
			System.out.print(index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}
}
