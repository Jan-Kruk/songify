package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SongRemover {
    private final SongRepository songRepository;

    public SongRemover(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    public Song deleteSongById(Integer id) {
        if (songRepository.findSong(id) == null) {
            throw new SongNotFoundException("Song with id:  " + id + "  not found");
        }
        return songRepository.deleteSong(id);
    }
}
