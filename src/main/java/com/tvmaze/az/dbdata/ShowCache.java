package com.tvmaze.az.dbdata;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "cache")
public class ShowCache {

    @Id
    private Long id;

    private Map<String, Object> showDato;

    public ShowCache(){
    }

    public ShowCache(Long id, Map<String, Object> showDato) {
        this.id = id;
        this.showDato = showDato;
    }

    public Long getId(){
        return id;
    }

    public Map<String, Object> getShowDato(){
        return showDato;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShowDato(Map<String, Object> showDato) {
        this.showDato = showDato;
    }
}
