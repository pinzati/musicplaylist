package hh.sof03.musicplaylist.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.sof03.musicplaylist.domain.Song;
import hh.sof03.musicplaylist.domain.SongRepository;

@Controller
public class SongRestController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/songs")
    public @ResponseBody List<Song> getSongListRest() {
        return (List<Song>)songRepository.findAll();
    }

}
