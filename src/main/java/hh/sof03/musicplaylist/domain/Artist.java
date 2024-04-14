package hh.sof03.musicplaylist.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistid;
    private String artistName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artist")
    private List<Song> songs;

    /*@ManyToMany(mappedBy = "artists")
    @JsonIgnoreProperties("artists")
    private List<Playlist> playlists = new ArrayList<>();
   */

    public Artist() {
            
       }

    public Artist(String artistName) {
        this.artistName = artistName;
    }


    public Long getArtistid() {
        return artistid;
    }

    public void setArtistid(Long artistid) {
        this.artistid = artistid;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    /*
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
    */


    @Override
    public String toString() {
        return "Artist [artistid=" + artistid + ", artistName=" + artistName + "]";
    }


}
