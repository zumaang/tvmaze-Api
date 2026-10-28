package com.tvmaze.az.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "comments")
public record Coments(
        @Id
        String id,
        Integer showId,
        String comment,
        Integer rating,
        Instant createdAt
) {
}
