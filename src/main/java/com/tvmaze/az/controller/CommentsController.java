package com.tvmaze.az.controller;

import com.tvmaze.az.dto.ComentsRequest;
import com.tvmaze.az.model.Coments;
import com.tvmaze.az.service.CommentsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shows")
@Validated
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/{showId}/comments")
    public ResponseEntity<Map<String, String>> createComment(
            @PathVariable @Positive Integer showId,
            @Valid @RequestBody ComentsRequest request
            ){
        Coments savedComents =
                commentsService.createComments(showId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "status", "success",
                        "message", "Comentario guardado correctamente",
                        "comment_id", savedComents.id()
                ));
    }
}
