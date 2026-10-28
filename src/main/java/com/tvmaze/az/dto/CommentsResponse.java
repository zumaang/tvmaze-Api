package com.tvmaze.az.dto;

import java.time.Instant;

public record CommentsResponse(String comment, Integer rating, Instant createdAt) {
}
