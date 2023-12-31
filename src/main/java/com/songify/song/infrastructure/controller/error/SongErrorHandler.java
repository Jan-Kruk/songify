package com.songify.song.infrastructure.controller.error;

import com.songify.song.infrastructure.controller.SongsController;
import com.songify.song.domain.model.SongNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = SongsController.class)
@Log4j2
public class SongErrorHandler {
    @ExceptionHandler(SongNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorSearchingForSongResponseDto handleException(SongNotFoundException exception){
        log.warn("SongNotFoundException while accessing song");
        return new ErrorSearchingForSongResponseDto(exception.getMessage(),HttpStatus.NOT_FOUND);
    }
}
