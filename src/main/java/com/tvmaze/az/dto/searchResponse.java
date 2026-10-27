package com.tvmaze.az.dto;

import java.util.List;

public record searchResponse(
        Long id,
        String name,
        String channel,
        String summary,
        List<String> genres
) {
}
