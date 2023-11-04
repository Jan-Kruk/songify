package com.songify.song.infrastructure.controller;

import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SongMapper {
    public Song mapFromCreateSongRequestDtoToSong(SongRequestDto dto) {
        return new Song(dto.songName(),dto.artist());
    }
    public CreateSingleSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        return new CreateSingleSongResponseDto(song);
    }
    public GetSingleSongResponseDto mapFromSongToGetSingleSongResponseDto(Song song) {
        return new GetSingleSongResponseDto(song);
    }
    public GetAllSongsResponseDto mapFromDatabaseToGetAllSongsResponseDto( Map<Integer,Song> database) {
        return new GetAllSongsResponseDto(database);
    }

    public UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(Song song) {
        return new UpdateSongResponseDto(song.songName(), song.artist());
    }
    public Song mapUpdateSongRequestDtoToSong(UpdateSongRequestDto request) {
        return new Song(request.songName(), request.artist());
    }

    public PartiallyUpdateSongResponseDto mapSongToPartiallyUpdateSongResponseDto(Song newSong) {
        return new PartiallyUpdateSongResponseDto(newSong.songName(), newSong.artist());
    }
}
