package com.spring.movies.repository;

import com.spring.movies.document.MovieDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;


public interface MovieRepository extends MongoRepository<MovieDocument, String> {
    Page<MovieDocument> findByReleaseDateAfterOrderByReleaseDateAsc(LocalDateTime date, Pageable pageable);
    Page<MovieDocument> findByReleaseDateBeforeOrderByReleaseDateDesc(LocalDateTime date, Pageable pageable);
}
