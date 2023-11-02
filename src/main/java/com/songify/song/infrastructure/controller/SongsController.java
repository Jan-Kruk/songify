package com.songify.song.infrastructure.controller;

import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongRemover;
import com.songify.song.domain.service.SongRetriever;
import com.songify.song.domain.service.SongUpdater;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.Song;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongsController {
    private final SongMapper songMapper;
    private final SongAdder songAdder;
    private final SongRemover songRemover;
    private final SongUpdater songUpdater;
    private final SongRetriever songRetriever;


    public SongsController(SongMapper songMapper, SongAdder songAdder, SongRemover songRemover, SongUpdater songUpdater, SongRetriever songRetriever) {
        this.songMapper = songMapper;
        this.songAdder = songAdder;
        this.songRemover = songRemover;
        this.songUpdater = songUpdater;
        this.songRetriever = songRetriever;
    }


    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        Map<Integer, Song> database = songRetriever.findAll();
        if (limit != null) {
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(songRetriever.findAllLimited(limit));
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = songMapper.mapFromDatabaseToGetAllSongsResponseDto(database);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSingleSongResponseDto> getSongById(@PathVariable Integer id,
                                                                @RequestHeader(name = "Request-Id", required = false)
                                                                String requestId) {
        log.info(requestId);
        Song song = songRetriever.findSong(id);
        GetSingleSongResponseDto response = songMapper.mapFromSongToGetSingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSingleSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request) {
        Song song = songMapper.mapFromCreateSongRequestDtoToSong(request);
        Song addedSong = songAdder.addSong(song);
        CreateSingleSongResponseDto body = songMapper.mapFromSongToCreateSongResponseDto(addedSong);
        return ResponseEntity.ok(body);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id) {
        Song removedSong = songRemover.deleteSongById(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("Song with id: " + id + " " + removedSong +  " is deleted", HttpStatus.NOT_FOUND));
    }



    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSong(@PathVariable Integer id, @RequestBody @Valid UpdateSongRequestDto request) {
        Song newSong = songMapper.mapUpdateSongRequestDtoToSong(request);
        songUpdater.updateSong(newSong,id);
        UpdateSongResponseDto songResponseDto = songMapper.mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(songResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id,
                                                                              @RequestBody @Valid PartiallyUpdateSongRequestDto request) {
        Song newSong = songUpdater.partiallyUpdateSong(id, request);
        PartiallyUpdateSongResponseDto songResponseDto = songMapper.mapSongToPartiallyUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(songResponseDto);
    }

}
