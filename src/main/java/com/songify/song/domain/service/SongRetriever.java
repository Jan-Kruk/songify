package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SongRetriever {
    private final SongRepository songRepository;

    public SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Map<Integer, Song> findAll() {
        Map<Integer, Song> songMap = songRepository.findAll();
        log.info("retrieving all songs: " + songMap);
        return songMap;
    }

    public Map<Integer, Song> findAllLimited(Integer limit) {
        Map<Integer, Song> songMap = songRepository.findAll();
        log.info("retrieving all songs: ");
        return songMap.entrySet()
                .stream()
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
    public Song findSong(Integer id) throws SongNotFoundException {
        if (songRepository.findSong(id) == null) {
            throw new SongNotFoundException("Song with id:  " + id + "  not found");
        }
        return songRepository.findSong(id);
    }

}
