package com.tvmaze.az.dbdata;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CacheRepository extends MongoRepository<ShowCache, Long> {
}
