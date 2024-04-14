package hh.sof03.musicplaylist.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songid;
    private String name;
    private Double duration;
   
    @ManyToOne
    @JsonIgnoreProperties("songs")
    @JoinColumn(name = "artistid")
    private Artist artist;

    @ManyToMany(mappedBy = "songs")
    @JsonIgnoreProperties("songs")
    private List<Playlist> playlists = new ArrayList<>();


    public Song() {
    
    }
   
    public Song(String name, Double duration, Artist artist) {
        this.name = name;
        this.duration = duration;
        this.artist = artist;
    }


    public Long getSongid() {
        return songid;
    }

    public void setSongid(Long songid) {
        this.songid = songid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist newArtist) {
        this.artist = newArtist;
    }
    
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }  
    

    @Override
    public String toString() {
        return "Song [songid=" + songid + ", name=" + name + ", duration=" + duration + ", artist=" + artist.getArtistName() + "]";
    }

    public void setArtist(List<Artist> existingArtist) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setArtist'");
    }    

}
