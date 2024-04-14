package hh.sof03.musicplaylist.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository <Song, Long> {

    Song findByNameAndDurationAndArtist(String name, Double duration, Artist artist);

}
