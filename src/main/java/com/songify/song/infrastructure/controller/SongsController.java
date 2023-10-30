package com.songify.song.infrastructure.controller;

import com.songify.song.domain.service.SongMapper;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.model.Song;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongsController {
    private final SongMapper songMapper;


    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("shawnmendes song1", "Shawn Mendes"),
            2, new Song("arriana grande song2", "Arriana Grande"),
            3, new Song("arriana3 g3rande song232", "Arriana Grande"),
            4, new Song("arriana4 g4rande song122", "Arriana Grande")
    ));

    public SongsController(SongMapper songMapper) {
        this.songMapper = songMapper;
    }


    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(
                    database.entrySet()
                            .stream()
                            .limit(limit)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = new GetAllSongsResponseDto(database);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSingleSongResponseDto> getSongById(@PathVariable Integer id,
                                                                @RequestHeader(name = "Request-Id", required = false)
                                                                String requestId) {
        log.info(requestId);
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with id:  " + id + "  not found");
        }
        Song song = database.get(id);
        GetSingleSongResponseDto response = new GetSingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSingleSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request) {
        Song song = songMapper.mapFromCreateSongRequestDtoToSong(request);
        database.put(database.size() + 1, song);
        log.info("adding new song: " + song);
        CreateSingleSongResponseDto body = songMapper.mapFromSongToCreateSongResponseDto(song);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with id:  " + id + "  not found");
        }
        database.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("Song with id: " + id + " is deleted", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSong(@PathVariable Integer id, @RequestBody @Valid UpdateSongRequestDto request) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with id:  " + id + "  not found");
        }
        Song newSong = new Song(request.songName(), request.artistName());
        Song oldSong = database.put(id, newSong);
        log.info("Updated song with id: " + id + " with old song name: " + oldSong + " to new song name: " + newSong);
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.songName(), newSong.artist()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id,
                                                                              @RequestBody @Valid PartiallyUpdateSongRequestDto request) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with id:  " + id + "  not found");
        }
        Song song = database.get(id);
        Song updatedSong;
        if (request.songName() != null && request.artistName() != null) {
            updatedSong = new Song(request.songName(), request.artistName());
        } else if (request.songName() != null) {
            updatedSong = new Song(request.songName(), song.artist());
        } else {
            updatedSong = new Song(song.songName(), request.artistName());
        }
        Song oldSong = database.replace(id, updatedSong);
        Song newSong = database.get(id);
        log.info("Updated song with id: " + id + " with old song name: " + oldSong + " to new song name: " + newSong);
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(newSong.songName(), newSong.artist()));
    }
}
