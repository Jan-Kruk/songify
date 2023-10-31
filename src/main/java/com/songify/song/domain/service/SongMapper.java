package com.songify.song.domain.service;

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
        return new Song(dto.songName(),dto.artistName());
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
    public GetAllSongsResponseDto mapFromDatabaseToGetAllSongsResponseDtoWithLimit(Integer limit, Map<Integer,Song> database) {
        return new GetAllSongsResponseDto(
                database.entrySet()
                        .stream()
                        .limit(limit)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(Song song) {
        return new UpdateSongResponseDto(song.songName(), song.artist());
    }
    public Song mapUpdateSongRequestDtoToSong(UpdateSongRequestDto request) {
        return new Song(request.songName(), request.artistName());
    }
   public Song mapPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto request, Song song) {
        Song updatedSong;
        if (request.songName() != null && request.artistName() != null) {
            updatedSong = new Song(request.songName(), request.artistName());
        } else if (request.songName() != null) {
            updatedSong = new Song(request.songName(), song.artist());
        } else {
            updatedSong = new Song(song.songName(), request.artistName());
        }
        return updatedSong;
    }
    public PartiallyUpdateSongResponseDto mapSongToPartiallyUpdateSongResponseDto(Song newSong) {
        return new PartiallyUpdateSongResponseDto(newSong.songName(), newSong.artist());
    }
}
