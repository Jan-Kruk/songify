package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.repository.SongRepository;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SongUpdater {
    private final SongRepository songRepository;

    public SongUpdater(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song updateSong(Song newSong, Integer id) {
        if (songRepository.findSong(id) == null) {
            throw new SongNotFoundException("Song with id:  " + id + "  not found");
        }
        Song oldSong = songRepository.updateSong(id, newSong);
        log.info("Updated song with id: " + id + " with old song name: " + oldSong + " to new song name: " + newSong);
        return oldSong;
    }

    public Song partiallyUpdateSong(Integer id, PartiallyUpdateSongRequestDto request) throws SongNotFoundException {
        if (songRepository.findSong(id) == null) {
            throw new SongNotFoundException("Song with id:  " + id + "  not found");
        }
        Song song = songRepository.findSong(id);
        Song updatedSong;
        if (request.songName() != null && request.artist() != null) {
            updatedSong = new Song(request.songName(), request.artist());
        } else if (request.songName() != null) {
            updatedSong = new Song(request.songName(), song.artist());
        } else {
            updatedSong = new Song(song.songName(), request.artist());
        }
        Song oldSong = songRepository.updateSong(id, updatedSong);
        Song newSong = songRepository.findSong(id);
        log.info("Updated song with id: " + id + " with old song name: " + oldSong + " to new song name: " + newSong);
        return newSong;
    }
}
