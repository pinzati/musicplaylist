package hh.sof03.musicplaylist.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {

    List<Playlist> findByPlaylistid(Long playlistid);
    List<Playlist> findByName(String name);
    List<Playlist> findByUserUsername(String username);
}
