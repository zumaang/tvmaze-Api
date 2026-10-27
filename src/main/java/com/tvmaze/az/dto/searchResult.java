package com.tvmaze.az.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record searchResult(
        ShowResult show
) {

    public record ShowResult(
            Long id,
            String name,
            String summary,
            List<String> genres,
            Network network,
            @JsonProperty("webChannel") Network webChannel
    ){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Network(String name) {
    }
}
