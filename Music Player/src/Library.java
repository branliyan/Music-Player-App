import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks.
 * Name: Brandon
 */
public class Library {
	private ArrayList<Song> songs;
	private ArrayList<AudioBook> audiobooks;
	private ArrayList<Playlist> playlists;

	//private ArrayList<Podcast> 	podcasts;

	// Public methods in this class set errorMesg string
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";

	public String getErrorMessage() {
		return errorMsg;
	}

	public Library() {
		songs = new ArrayList<Song>();
		audiobooks = new ArrayList<AudioBook>();
		playlists = new ArrayList<Playlist>();
		//	podcasts = new ArrayList<Podcast>();
	}

	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 *
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content) {
		if (content.getType().equals(Song.TYPENAME)) { //checks to see if the content is of type song
			Song newSong = (Song) content; // turns the AudioContent object into a song object
			int index = songs.indexOf(newSong);
			if (!(index == -1)) { //checks to see if the song is already part of the library. If it is, then it addds it
				throw new ContentAlreadyDownloaded("SONG "+newSong.getTitle()+" Already Downloaded");//throws the exception if it finds that the song is already downloaded
			} else {
				System.out.println("SONG "+newSong.getTitle()+" Added to Library");
				songs.add(newSong);
			}
		}
		if (content.getType().equals(AudioBook.TYPENAME)) { //checks to see if the content is of type audiobook
			AudioBook newAudioBook = (AudioBook) content; // turns the AudioContent object into a audiobook object
			int index = audiobooks.indexOf(newAudioBook); //searches the list of audiobooks to see if it can find an identical copy
			if (!(index == -1)) { // if index==-1, then it means that there is no identical copy
				throw new ContentAlreadyDownloaded("AUDIOBOOK "+newAudioBook.getTitle()+" Already Downloaded");
			} else {
				System.out.println("AUDIOBOOK "+newAudioBook.getTitle()+" Added to Library");
				audiobooks.add(newAudioBook);
			}
		}
	}

	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs() {
		if (songs.size() == 0) { // if there are no songs in the playlists
			System.out.println("You Have No Songs Downloaded");
		}

		for (int i = 0; i < songs.size(); i++) { // prints out all of the information of the songs using the songs arraylist
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();
		}
	}

	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks() {
		if (audiobooks.size() == 0){ //checks to see if the amount of audiobooks in the audiobooks arraylist is zero
			System.out.println("You Have No Audiobooks Downloaded");
		}

		for (int i = 0; i < audiobooks.size(); i++) { //runs through a loop to print all the information
			System.out.print(" " + (i + 1) + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}

	}

	// Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts() {


	}

	// Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists() {
		if (playlists.size() == 0) { // checks to see if there are no playlists that the user has
			System.out.println("No Playlists Found In Your Library");
		}

		for (int i = 0; i < playlists.size(); i++) { //runs through a loop to print all of the playlist names
			System.out.print(" " + (i + 1) + ". ");
			System.out.println(playlists.get(i).getTitle());
		}
	}

	// Print the name of all artists.
	public void listAllArtists() {
		// First create a new (empty) array list of string
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist array list is complete, print the artists names
		ArrayList<String> artists = new ArrayList<String>();
		for (int i = 0; i < songs.size(); i++) { //runs through each of the songs in the library
			if (!artists.contains(songs.get(i).getArtist())) { //if the arraylist of artists does not contain the specific artist at i, then it adds it
				artists.add(songs.get(i).getArtist());
			}
		}
		if (artists.size() > 0) { // checks to see if there are any artists in the library
			for (int i = 0; i <artists.size(); i++) {
				System.out.println((i+1)+". "+artists.get(i));
			}
		} else {
			System.out.println("There Are Currently No Artists In Your Library");
		}


	}

	// Delete a song from the library (i.e. the songs list) -
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index) {
		index = index - 1;
		Song songToDelete;
		//deletes the song from the library
		if (index >= 0 && index < songs.size()) { // checks to see if the index is able to remove the song
			songToDelete = songs.get(index); //creates a song object used to find it in the playlists once it's done searching for the song in the library
			songs.remove(index); //removes the song from the list of songs
		} else { //if it does not find the song in the library
			throw new AudioContentNotFound("Song Not Found");
		}
		//deletes the song from each playlist
		for (int i = 0; i < playlists.size(); i++) { // iterates through each of the playlist
			for (int j = 0; j < playlists.get(i).getContent().size(); j++) { //runs through every element of the playlist
				if (playlists.get(i).getContent().get(j).getType().equals(Song.TYPENAME)) {//checks to see if the playlist content is a song
					if (playlists.get(i).getContent().get(j).equals(songToDelete)) {  // checks the type of the media of the playlist contents(arraylist)
						playlists.get(i).getContent().remove(j); //removes the song form the playlist
					}
				}
			}
		}
	}

	//Sort songs in library by year
	public void sortSongsByYear() {
		// Use Collections.sort()
		Collections.sort(songs, new SongYearComparator()); //sorts by year

	}

	// Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song> {
		public int compare(Song song1, Song song2) {
			if (song1.getYear() < song2.getYear()) {
				return -1;
			} else if (song1.getYear() > song2.getYear()) {
				return 1;
			}
			return 0;
		}

	}

	// Sort songs by length
	public void sortSongsByLength() {
		// Use Collections.sort()
		Collections.sort(songs, new SongLengthComparator());
	}

	// Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song> {
		public int compare(Song song1, Song song2) {
			if (song1.getLength() > song2.getLength()) {
				return 1;
			} else if (song1.getLength() < song2.getLength()) {
				return -1;
			}
			return 0;
		}

	}

	// Sort songs by title
	public void sortSongsByName() {
		Collections.sort(songs);

	}
	/*
	 * Play Content
	 */

	// Play song from songs list
	public void playSong(int index) {
		if (index < 1 || index > songs.size()) { //checks to see if the index is in range
			throw new AudioContentNotFound("Song Not Found");
		}
		songs.get(index - 1).play(); //plays the song if it is within the index that the user entered
	}

	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode) {
		return false;
	}

	// Print the episode titles of a specified season
	// Bonus
	public boolean printPodcastEpisodes(int index, int season) {
		return false;
	}

	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter) {
		if (index < 1 || index > audiobooks.size()) { //checks to see if the index is out of range
			throw new AudioContentNotFound("AudioBook Not Found");
		}

		if (!(chapter>0 && chapter<=audiobooks.get(index-1).getNumberOfChapters())){ // checks to see if the chapter exists
			throw new ChapterNotFound("Chapter Not Found");
		}
		audiobooks.get(index - 1).selectChapter(chapter); //selects the entered chapter
		audiobooks.get(index - 1).play(); // will play the content of the audiobook with the proper chapter
	}

	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index) {
		if (index < 1 || index > audiobooks.size()) { //checks to see if the index is within range
			throw new AudioContentNotFound("AudioBook Not Found");
		}
		audiobooks.get(index - 1).printTOC(); //prints the table of contents for the specific auiobook
	}

	/*
	 * Playlist Related Methods
	 */

	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title) {
		for (int i = 0; i < playlists.size(); i++) { //will iterate through each of the playlists
			if (playlists.get(i).getTitle().equals(title)) { // checks if any of the playlists already have the same song
				errorMsg = "Playlist "+title+" Already Exists";
				throw new DuplicatePlaylist("Playlist "+title+" Already Exists");
			}
		}
		playlists.add(new Playlist(title));
	}

	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title) {
		for (int i = 0; i < playlists.size(); i++) { //will iterate through each of the playlists
			if (playlists.get(i).getTitle().equals(title)) { // checks if any of the playlists already have the same name
				playlists.get(i).printContents();
				return;
			}
		}
		throw new PlayListNotFound("Playlist Not Found");
	}

	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) {

		for (int i = 0; i < playlists.size(); i++) { //will iterate through each of the playlists
			if (playlists.get(i).getTitle().equals(playlistTitle)) { // checks if any of the playlists already have the same name
				playlists.get(i).playAll();
				return;
			}
		}
		//if it reaches this then it means that there are no plalists with the given name
		throw new PlayListNotFound("PlayList Not Found");
	}

	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) {
		indexInPL = indexInPL - 1;
		for (int i = 0; i < playlists.size(); i++) { //will iterate through each of the playlists
			if (playlists.get(i).getTitle().equals(playlistTitle)) { // checks if any of the playlists already have the same name
				if (indexInPL >= 0 && indexInPL < playlists.get(i).getContent().size()) {// checks to see if the index is present
					System.out.println(playlistTitle);
					playlists.get(i).getContent().get(indexInPL).play(); //plays the specific content
					return;
				}
			}
		}
		throw new PlayListNotFound("Cannot Find Content Or PlayList");
	}

	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle) {
		index = index - 1;
		boolean found = false;
		for (int i = 0; i < playlists.size(); i++) { //this checks to see if the playlist exists
			if (playlists.get(i).getTitle().equals(playlistTitle)) {
				found = true;
			}
		}
		if (!found) { //if it does not find the playlist name
			throw new PlayListNotFound("PlayList Not Found");
		}
		if (type.equalsIgnoreCase(Song.TYPENAME)) { // this means that they want to add a song
			//needs to check if the index of belongs in the list of songs
			if (!(index >= 0 && index < songs.size())) { // this checks to see if the song index exists
				throw new AudioContentNotFound("Cannot Find Song In Library");
			}

			for (int i = 0; i < playlists.size(); i++) { //finds the playlists with the proper title
				//finds the playlists with the proper title
				if (playlists.get(i).getTitle().equals(playlistTitle)){ // once it finds it, it adds the song from the library to the playlist
					//need to make sure that you are only checking for songs
					boolean foundSameSong = false;
					Song newSong = songs.get(index);
					for (int j = 0; j < playlists.get(i).getContent().size(); j++) { //iterates through each of the contents
						if (playlists.get(i).getContent().get(j).getType().equals("SONG")) { //checks to see if the type is an song
							if (playlists.get(i).getContent().get(j).equals(newSong)) { //checks to see if it is already in the playlist
								foundSameSong = true;
							}
						}
					}
					if (!foundSameSong) {
						playlists.get(i).getContent().add(songs.get(index));
						return;
					} else {
						throw new AudioContentAlreadyFound("Song Already Exists");
					}

				}
			}
		}
		if (type.equalsIgnoreCase(AudioBook.TYPENAME)) { // this means that they want to add a song
			if (!(index >= 0 && index < audiobooks.size())) { // this checks to see if the audiobooks index exists in the library
				throw new AudioContentNotFound("Cannot Find AudioBook In Library");
			}
			for (int i = 0; i < playlists.size(); i++) { //finds the playlists with the proper title
				if (playlists.get(i).getTitle().equals(playlistTitle)) { // once it finds it, it adds the audiobook from the library to the playlist
					//need to make sure that you are only checking for otherbooks
					boolean foundSameAudiobook = false;
					AudioBook newAudioBook = audiobooks.get(index);
					for (int j = 0; j < playlists.get(i).getContent().size(); j++) { //iterates through each of the contents
						if (playlists.get(i).getContent().get(j).getType().equals("AUDIOBOOK")) { //checks to see if the type is an audiobook
							if (playlists.get(i).getContent().get(j).equals(newAudioBook)) { //checks to see if it is already in the playlist
								foundSameAudiobook = true;
							}
						}
					}
					if (!foundSameAudiobook) {
						playlists.get(i).getContent().add(audiobooks.get(index));
						return;
					} else {
						throw new AudioContentAlreadyFound("AudioBook Already Exists");
					}
				}
			}
		}
		//it will only reach this part of the program if it does not find the playlist
		throw new PlayListNotFound("PlayList Or Song Not Found");
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
  // Make sure the given index of the song/audiobook/podcast in the playlist is valid
  public void delContentFromPlaylist(int index, String title) {

	  for (int i = 0; i <playlists.size(); i++) { //iterates through each of the playlsits
		  if (index>0 && index <= playlists.get(i).getContent().size()){ //checks to see if the index is within the playlist
			  if (playlists.get(i).getTitle().equals(title)){ //checks to see if it finds the right title
				  playlists.get(i).deleteContent(index); //deletes the content
				  return;
			  }
		  }
	  }
	  throw new PlayListNotFound("Cannot Find Content Or PlayList");
  }

}

class AudioContentNotFound extends RuntimeException{ //creates the Audiocontent not found exception
	public AudioContentNotFound(String message){
		super(message);
	}
}

class AudioContentAlreadyFound extends RuntimeException{
	public AudioContentAlreadyFound(String message){
		super(message);
	}
}

class ContentAlreadyDownloaded extends RuntimeException{
	public ContentAlreadyDownloaded(String message){
		super(message);
	}
}

class ChapterNotFound extends RuntimeException{
	public ChapterNotFound(String message){
		super(message);
	}
}

class PlayListNotFound extends RuntimeException{
	public PlayListNotFound(String message){
		super(message);
	}
}
class DuplicatePlaylist extends RuntimeException{
	public DuplicatePlaylist(String message){
		super(message);
	}
}

