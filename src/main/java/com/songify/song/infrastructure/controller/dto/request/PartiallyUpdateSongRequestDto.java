package com.songify.song.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PartiallyUpdateSongRequestDto(
                                            String songName,

                                            String artist) {
}
