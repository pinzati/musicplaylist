package hh.sof03.musicplaylist.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.sof03.musicplaylist.domain.Artist;
import hh.sof03.musicplaylist.domain.ArtistRepository;

@Controller
public class ArtistRestController {

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/artists")
    public @ResponseBody List<Artist> getArtistListRest() {
        return (List<Artist>)artistRepository.findAll();
    }

}
