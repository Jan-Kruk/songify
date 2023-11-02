package com.songify.song.domain.repository;

import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {
    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("shawnmendes song1", "Shawn Mendes"),
            2, new Song("arriana grande song2", "Arriana Grande"),
            3, new Song("arriana3 g3rande song232", "Arriana Grande"),
            4, new Song("arriana4 g4rande song122", "Arriana Grande")
    ));
    public Song saveToDatabase(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }

    public Map<Integer,Song> findAll(){
        return database;
    }
    public Song findSong(Integer id){
        return database.get(id);
    }
    public Song deleteSong(Integer id){
        return database.remove(id);
    }
    public Song updateSong(Integer id, Song newSong){
       return database.replace(id,newSong);
    }
}
