package hh.sof03.musicplaylist.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.sof03.musicplaylist.domain.Playlist;
import hh.sof03.musicplaylist.domain.PlaylistRepository;
import hh.sof03.musicplaylist.domain.Song;

@Controller
public class PlaylistRestController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @GetMapping("/playlists")
    public @ResponseBody List<Playlist> getPlaylistListRest() {
        return (List<Playlist>)playlistRepository.findAll();
    }

}
