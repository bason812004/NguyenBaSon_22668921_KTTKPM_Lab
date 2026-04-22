package com.movie.movieservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.movie.movieservice.dto.CreateMovieRequest;
import com.movie.movieservice.model.Movie;

@Service
public class MovieCatalogService {

    private final Map<Long, Movie> movies = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public MovieCatalogService() {
        createMovie(defaultMovie("Inception", "Sci-Fi", 148, "Dreams inside dreams"));
        createMovie(defaultMovie("Interstellar", "Sci-Fi", 169, "Journey through space-time"));
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies.values());
    }

    public Movie getMovieById(Long id) {
        Movie movie = movies.get(id);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found");
        }
        return movie;
    }

    public Movie createMovie(CreateMovieRequest request) {
        Movie movie = Movie.builder()
                .id(idGenerator.incrementAndGet())
                .title(request.getTitle())
                .genre(request.getGenre())
                .durationMinutes(request.getDurationMinutes())
                .description(request.getDescription())
                .build();

        movies.put(movie.getId(), movie);
        return movie;
    }

    public Movie updateMovie(Long id, CreateMovieRequest request) {
        if (!movies.containsKey(id)) {
            throw new IllegalArgumentException("Movie not found");
        }

        Movie updated = Movie.builder()
                .id(id)
                .title(request.getTitle())
                .genre(request.getGenre())
                .durationMinutes(request.getDurationMinutes())
                .description(request.getDescription())
                .build();

        movies.put(id, updated);
        return updated;
    }

    public void deleteMovie(Long id) {
        if (movies.remove(id) == null) {
            throw new IllegalArgumentException("Movie not found");
        }
    }

    private CreateMovieRequest defaultMovie(String title, String genre, int duration, String description) {
        CreateMovieRequest request = new CreateMovieRequest();
        request.setTitle(title);
        request.setGenre(genre);
        request.setDurationMinutes(duration);
        request.setDescription(description);
        return request;
    }
}
