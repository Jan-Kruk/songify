package com.songify;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class SongsController {

    Map<Integer, String> database = new HashMap<>(Map.of(
            1, "shawnmendes song1",
            2, "arriana grande song2",
            3, "arriana3 g3rande song232",
            4, "arriana4 g4rande song122"
    ));


    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            SongResponseDto response = new SongResponseDto(
                    database.entrySet()
                            .stream()
                            .limit(limit)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return ResponseEntity.ok(response);
        }
        SongResponseDto response = new SongResponseDto(database);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongById(@PathVariable Integer id,
                                                             @RequestHeader(name = "Request-Id", required = false)
                                                             String requestId) {
        log.info(requestId);
        String song = database.get(id);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }
//    @PostMapping
//    public ResponseEntity<SingleSongResponseDto> postSong(@RequestBody String songName){
//
//    }
}
