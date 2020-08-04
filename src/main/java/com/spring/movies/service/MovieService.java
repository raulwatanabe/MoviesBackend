package com.spring.movies.service;

import com.spring.movies.document.MovieDocument;
import com.spring.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Page<MovieDocument> findAll(Pageable pageable, String flag){
        if(flag != null && flag.equals("torelease")){
            return movieRepository.findByReleaseDateAfterOrderByReleaseDateAsc(LocalDateTime.now(), pageable);
        }else if(flag != null && flag.equals("available")){
            return movieRepository.findByReleaseDateBeforeOrderByReleaseDateDesc(LocalDateTime.now(), pageable);
        }else{
            return movieRepository.findAll(pageable);
        }
    }

    public Optional<MovieDocument> findById(String id){
        return movieRepository.findById(id);
    }

    public MovieDocument save(MovieDocument movieDocument){
        return movieRepository.save(movieDocument);
    }

    public void delete(MovieDocument movieDocument){
        movieRepository.delete(movieDocument);
    }
}
