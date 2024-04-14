package hh.sof03.musicplaylist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.sof03.musicplaylist.domain.Artist;
import hh.sof03.musicplaylist.domain.ArtistRepository;
import hh.sof03.musicplaylist.domain.Playlist;
import hh.sof03.musicplaylist.domain.PlaylistRepository;
import hh.sof03.musicplaylist.domain.Song;
import hh.sof03.musicplaylist.domain.SongRepository;
import hh.sof03.musicplaylist.domain.User;
import hh.sof03.musicplaylist.domain.UserRepository;

@SpringBootApplication
public class MusicplaylistApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicplaylistApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(SongRepository songRepository, ArtistRepository artistRepository, PlaylistRepository playlistRepository, UserRepository userRepository) {
		return (args) -> {

			
			User user = new User("user", "$2a$10$Yk8FqPXrdQuXYpJqBYZ7.uMVMfXRAGSO2jz06GzgJY8EPsRs3aPsa", "user@user.com", "USER");
			User user1 = new User("user1", "$2a$10$NJlvdDktB8lvygGk801FcOCebwXus.BQTh8QwbCt3ys8NS.DycsXi", "user1@user1.com", "user");
			User user2 = new User("admin", "$2a$10$yVwvDPA5dWYK0R0vxSjcw.9PBgCwvdgb5a8PqR775MtBuSFZlenmS", "admin@admin.com", "ADMIN");

			userRepository.save(user);
			userRepository.save(user1);
			userRepository.save(user2);

			Playlist playlist1 = new Playlist("music", user1);
			Playlist playlist2 = new Playlist("softer", user);
			// Playlist playlist3 = new Playlist("my playlist");

			playlistRepository.save(playlist1);
			playlistRepository.save(playlist2);
			// playlistRepository.save(playlist3);
			
			Artist artist1 = new Artist("Hi-Rez");
			Artist artist2 = new Artist("Shaker");
			Artist artist3 = new Artist("SoMo");
			Artist artist4 = new Artist("Bon Jovi");
			Artist artist5 = new Artist("MARO");

			artistRepository.save(artist1);
			artistRepository.save(artist2);
			artistRepository.save(artist3);
			artistRepository.save(artist4);
			artistRepository.save(artist5);

			Song song1 = new Song("Never Hold Back", 3.18, artist1);
		//	song1.setArtist(artist1);
			Song song2 = new Song("Next", 2.57, artist2);
		//	song2.setArtist(artist2);
			Song song3 = new Song("You Can Buy Everything", 3.00, artist3);
		//	song3.setArtist(artist3);
			Song song4 = new Song("Bed Of Roses", 6.34, artist4);
		//	song4.setArtist(artist4);
			Song song5 = new Song("saudade, saudade", 3.00, artist5);
		//	song5.setArtist(artist5);

			songRepository.save(song1);
			songRepository.save(song2);
			songRepository.save(song3);
			songRepository.save(song4);
			songRepository.save(song5);

			playlist1.getSongs().add(song1);
			playlist1.getSongs().add(song2);
			playlist2.getSongs().add(song3);
			playlist2.getSongs().add(song4);
			playlist2.getSongs().add(song5);

			playlistRepository.save(playlist1);
			playlistRepository.save(playlist2);			
		};
	}

}
