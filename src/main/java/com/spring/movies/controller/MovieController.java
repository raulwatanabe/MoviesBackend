package com.spring.movies.controller;

import com.spring.movies.document.MovieDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class MovieController {

    @Autowired
    com.spring.movies.service.MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<Page<MovieDocument>> getAllMovies(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                          @RequestParam(required = false) String flag){
        Page<MovieDocument> moviePage = movieService.findAll(pageable, flag);
        if(moviePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Page<MovieDocument>>(moviePage, HttpStatus.OK);
        }
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDocument> getOneMovie(@PathVariable(value="id") String id){
        Optional<MovieDocument> movieO = movieService.findById(id);
        if(!movieO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<MovieDocument>(movieO.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/movies")
    public ResponseEntity<MovieDocument> saveMovie(@RequestBody @Valid MovieDocument movie) {
        movie.setRegistrationDate(LocalDateTime.now());
        return new ResponseEntity<MovieDocument>(movieService.save(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable(value="id") String id) {
        Optional<MovieDocument> movieO = movieService.findById(id);
        if(!movieO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            movieService.delete(movieO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<MovieDocument> updateMovie(@PathVariable(value="id") String id,
                                                      @RequestBody @Valid MovieDocument movieDocument) {
        Optional<MovieDocument> movieO = movieService.findById(id);
        if(!movieO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            movieDocument.setId(movieO.get().getId());
            return new ResponseEntity<MovieDocument>(movieService.save(movieDocument), HttpStatus.OK);
        }
    }
}
