package hh.sof03.musicplaylist.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import hh.sof03.musicplaylist.domain.Artist;
import hh.sof03.musicplaylist.domain.Playlist;
import hh.sof03.musicplaylist.domain.PlaylistRepository;
import hh.sof03.musicplaylist.domain.Song;
import hh.sof03.musicplaylist.domain.SongRepository;
import hh.sof03.musicplaylist.domain.User;
import hh.sof03.musicplaylist.domain.UserRepository;

@Controller
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/playlistlist")
    public String playlistList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Iterable<Playlist> playlistsIterable = playlistRepository.findAll();

        List<Playlist> playlists = new ArrayList<>();
        playlistsIterable.forEach(playlists::add);

        if (userDetails != null) {
            model.addAttribute("currentUser", userDetails.getUsername());
        }

        Map<String, String> playlistCreators = new HashMap<>();

        if (userDetails != null) {
            for (Playlist playlist : playlists) {
                if (playlist.getUser() != null) {
                    playlistCreators.put(playlist.getName(), playlist.getUser().getUsername());
                }
            }
        }

        model.addAttribute("playlists", playlists);
        model.addAttribute("playlistCreators", playlistCreators);

        return "playlistlist";
    }

    @GetMapping("/showplaylist/{playlistid}")
    public String showPlaylist(@PathVariable("playlistid") Long playlistid, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Playlist playlist = playlistRepository.findById(playlistid).orElse(null);
        List<Song> songs = playlist.getSongs();

        Iterable<Playlist> playlistsIterable = playlistRepository.findAll();

        List<Playlist> playlists = new ArrayList<>();
        playlistsIterable.forEach(playlists::add);

        if (userDetails != null) {
            model.addAttribute("currentUser", userDetails.getUsername());
        }

        Map<String, String> playlistCreators = new HashMap<>();

        if (userDetails != null) {
            for (Playlist playList : playlists) {
                if (playList.getUser() != null) {
                    playlistCreators.put(playList.getName(), playList.getUser().getUsername());
                }
            }
        }

        model.addAttribute("playlists", playlists);
        model.addAttribute("playlistCreators", playlistCreators);


        model.addAttribute("playlist", playlist);
        model.addAttribute("playlistid", playlistid);
        model.addAttribute("playlists", playlistRepository.findByPlaylistid(playlistid));
        model.addAttribute("songs", songs);

        return "showplaylist";
    }

    @RequestMapping("/add")
    public String showAddPlaylist(Model model) {
        model.addAttribute("playlist", new Playlist());
        model.addAttribute("song", new Song());
        model.addAttribute("artist", new Artist());

        return "addplaylist";
    }

    @PostMapping("/add")
    public String addPlaylist(@ModelAttribute Playlist playlist, Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username);
            if (user != null) {
                playlist.setUser(user);
                playlistRepository.save(playlist);
                model.addAttribute("playlistCreator", username);
            }
        }

        return "redirect:/playlistlist";
    }

    @GetMapping("/deleteplaylist/{playlistid}")
    public String deletePlaylistByUser(@PathVariable("playlistid") Long playlistid, Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        Playlist playlist = playlistRepository.findById(playlistid).orElse(null);

        if (playlist != null && userDetails != null
                && userDetails.getUsername().equals(playlist.getUser().getUsername())) {
            playlistRepository.deleteById(playlistid);
        }

        return "redirect:/playlistlist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{playlistid}", method = RequestMethod.GET)
    public String deletePlaylist(@PathVariable("playlistid") Long playlistid, Model model) {
        playlistRepository.deleteById(playlistid);

        return "redirect:/playlistlist";
    }

}
