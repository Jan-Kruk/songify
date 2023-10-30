package com.songify.song.domain.service;

import com.songify.song.infrastructure.controller.dto.request.SongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.CreateSingleSongResponseDto;
import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {
    public Song mapFromCreateSongRequestDtoToSong(SongRequestDto dto) {
        return new Song(dto.songName(),dto.artistName());
    }
    public CreateSingleSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        return new CreateSingleSongResponseDto(song);
    }
}
