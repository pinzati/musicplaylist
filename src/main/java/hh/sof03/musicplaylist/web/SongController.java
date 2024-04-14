package hh.sof03.musicplaylist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hh.sof03.musicplaylist.domain.Artist;
import hh.sof03.musicplaylist.domain.ArtistRepository;
import hh.sof03.musicplaylist.domain.Playlist;
import hh.sof03.musicplaylist.domain.PlaylistRepository;
import hh.sof03.musicplaylist.domain.Song;
import hh.sof03.musicplaylist.domain.SongRepository;

@Controller
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/songlist")
    public String songList(Model model) {
        model.addAttribute("songs", songRepository.findAll());

        return "songlist";
    }

    @GetMapping("/addsong/{playlistid}")
    public String addSong(@PathVariable("playlistid") Long playlistid, Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        Playlist playlist = playlistRepository.findById(playlistid).orElse(null);

        if (userDetails != null) {
            model.addAttribute("currentUser", userDetails.getUsername());
        }

        if (playlist == null || userDetails == null ||
                !userDetails.getUsername().equals(playlist.getUser().getUsername())) {
            return "redirect:/playlistlist";
        }

        model.addAttribute("playlistid", playlistid);
        model.addAttribute("newsong", new Song());
        model.addAttribute("playlistName", playlist.getName());
        model.addAttribute("playlist", playlist);

        String playlistCreator = playlist.getUser().getUsername();
        model.addAttribute("playlistCreator", playlistCreator);

        return "addsong";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addsongadmin/{playlistid}")
    public String addSongAdmin(@PathVariable("playlistid") Long playlistid, Model model) {

        Playlist playlist = playlistRepository.findById(playlistid).orElse(null);

        model.addAttribute("playlistid", playlistid);
        model.addAttribute("newsong", new Song());
        model.addAttribute("playlistName", playlist.getName());

        return "addsong";
    }

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
    }

    @PostMapping("/savesong")
    public String saveSongToPlaylist(@ModelAttribute Song newSong, Long playlistid, @RequestParam String artistName,
            @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {

        if (userDetails == null) {
            return "redirect:/playlistlist";
        }

        Playlist playlist = playlistRepository.findById(playlistid).orElse(null);

        if (playlist == null || !userDetails.getUsername().equals(playlist.getUser().getUsername())) {

            return "redirect:/playlistlist";
        }

        if (newSong.getName() == null || newSong.getName().isEmpty() ||
                artistName == null || artistName.isEmpty() ||
                newSong.getDuration() == null || newSong.getDuration().isNaN()) {

            redirectAttributes.addFlashAttribute("error", "Missing fields. Please fill out all required fields.");
            return "redirect:/addsong/" + playlistid;
        }

        Artist existingArtist = null;

        for (Artist artist : artistRepository.findAll()) {
            if (artist.getArtistName().equals(artistName)) {
                existingArtist = artist;
                break;
            }
        }

        if (existingArtist == null) {
            existingArtist = new Artist();
            existingArtist.setArtistName(artistName);
            artistRepository.save(existingArtist);
        }

        Song existingSong = songRepository.findByNameAndDurationAndArtist(newSong.getName(), newSong.getDuration(),
                existingArtist);

        if (existingSong != null && existingSong.getPlaylists().contains(playlist)) {
            return "redirect:/showplaylist/" + playlistid + "?songExists=true";
        }

        if (existingSong == null) {
            newSong.setArtist(existingArtist);
        } else {
            existingSong.getPlaylists().add(playlist);
            playlist.getSongs().add(existingSong);

            songRepository.save(existingSong);
            playlistRepository.save(playlist);

            return "redirect:/showplaylist/" + playlistid;
        }

        newSong.getPlaylists().add(playlist);
        playlist.getSongs().add(newSong);

        songRepository.save(newSong);
        playlistRepository.save(playlist);

        return "redirect:/showplaylist/" + playlistid;
    }

    @GetMapping("/deletesong/{playlistid}/{songid}")
    public String deleteSongFromPlaylist(@PathVariable("playlistid") Long playlistid,
            @PathVariable("songid") Long songid,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        Playlist playlist = playlistRepository.findById(playlistid).orElse(null);
        Song song = songRepository.findById(songid).orElse(null);

        if (playlist != null && song != null && userDetails != null) {
            if (userDetails.getUsername().equals(playlist.getUser().getUsername()) || isAdmin(userDetails)) {
                // Removing song and not deleting it from database
                playlist.getSongs().remove(song);
                playlistRepository.save(playlist);
            } else {
                return "redirect:/showplaylist/" + playlistid;
            }
        }

        return "redirect:/showplaylist/" + playlistid;
    }

}
