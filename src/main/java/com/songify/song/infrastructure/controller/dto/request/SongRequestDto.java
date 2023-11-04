package com.songify.song.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SongRequestDto(
        @NotNull(message = "songName must not be null")
        @NotEmpty(message = "songName must not be empty")
        @Size(min = 3, message = "size of song must be above 2")
        String songName,
        @NotNull(message = "artist must not be null")
        @NotEmpty(message = "artist must not be empty")
        @Size(min = 3, message = "size of artist must be above 2")
        String artist) {

}
