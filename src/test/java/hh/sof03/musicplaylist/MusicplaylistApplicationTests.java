package hh.sof03.musicplaylist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.sof03.musicplaylist.web.ArtistController;
import hh.sof03.musicplaylist.web.PlaylistController;
import hh.sof03.musicplaylist.web.SongController;
import hh.sof03.musicplaylist.web.UserController;

@SpringBootTest
class MusicplaylistApplicationTests {

	@Autowired
	private ArtistController artistController;

	@Autowired
	private PlaylistController playlistController;

	@Autowired
	private SongController songController;

	@Autowired
	private UserController userController;

	@Test
	public void artistControllerNotNull() throws Exception {
		assertThat(artistController).isNotNull();
	}

	@Test
	public void playlistControllerNotNull() throws Exception {
		assertThat(playlistController).isNotNull();
	}

	@Test
	public void songControllerNotNull() throws Exception {
		assertThat(songController).isNotNull();
	}

	@Test
	public void userControllerNotNull() throws Exception {
		assertThat(userController).isNotNull();
	}

}
