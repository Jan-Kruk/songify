package com.songify.song.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateSongRequestDto(@NotNull(message = "songName must not be null")
                                   @NotEmpty(message = "songName must not be empty")
                                   @Size(min = 3, message = "size of song must be above 2")
                                   String songName,
                                   @NotNull(message = "artistName must not be null")
                                   @NotEmpty(message = "artistName must not be empty")
                                   @Size(min = 3, message = "size of artistName must be above 2")
                                   String artistName) {
}
