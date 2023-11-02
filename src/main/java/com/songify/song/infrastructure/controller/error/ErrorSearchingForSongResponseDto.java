package com.songify.song.infrastructure.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorSearchingForSongResponseDto(String message, HttpStatus httpStatus) {
}
