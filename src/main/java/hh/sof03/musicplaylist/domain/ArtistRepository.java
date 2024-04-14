package hh.sof03.musicplaylist.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

    List<Artist> findByArtistName(String artistName);
}
