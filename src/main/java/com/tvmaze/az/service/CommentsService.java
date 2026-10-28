package com.tvmaze.az.service;

import com.tvmaze.az.dbdata.CommentsRepository;
import com.tvmaze.az.dto.ComentsRequest;
import com.tvmaze.az.dto.CommentsResponse;
import com.tvmaze.az.model.Coments;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public Coments createComments(
            Integer showId,
            ComentsRequest request
    ){
        Coments comments = new Coments(
                UUID.randomUUID().toString(),
                showId,
                request.comment().trim(),
                request.rating(),
                Instant.now()
        );

        return commentsRepository.save(comments);
    }

    public List<CommentsResponse> getComments(Integer showId){
        return commentsRepository.findByShowId(showId)
                .stream()
                .map(coments -> new CommentsResponse(
                        coments.comment(),
                        coments.rating(),
                        coments.createdAt()
                ))
                .toList();
    }
}
