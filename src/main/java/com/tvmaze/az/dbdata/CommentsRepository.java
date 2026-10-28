package com.tvmaze.az.dbdata;

import com.tvmaze.az.model.Coments;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentsRepository extends MongoRepository<Coments, String> {

    List<Coments> findByShowId(Integer showId);
}
