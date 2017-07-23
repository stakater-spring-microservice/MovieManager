package com.stakater.moviemanager.service.impl;

import com.stakater.moviemanager.service.MovieService;
import com.stakater.moviemanager.domain.Movie;
import com.stakater.moviemanager.repository.MovieRepository;
import com.stakater.moviemanager.repository.search.MovieSearchRepository;
import com.stakater.moviemanager.service.dto.MovieDTO;
import com.stakater.moviemanager.service.mapper.MovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Movie.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService{

    private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    private final MovieSearchRepository movieSearchRepository;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper, MovieSearchRepository movieSearchRepository) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.movieSearchRepository = movieSearchRepository;
    }

    /**
     * Save a movie.
     *
     * @param movieDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MovieDTO save(MovieDTO movieDTO) {
        log.debug("Request to save Movie : {}", movieDTO);
        Movie movie = movieMapper.toEntity(movieDTO);
        movie = movieRepository.save(movie);
        MovieDTO result = movieMapper.toDto(movie);
        movieSearchRepository.save(movie);
        return result;
    }

    /**
     *  Get all the movies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Movies");
        return movieRepository.findAll(pageable)
            .map(movieMapper::toDto);
    }

    /**
     *  Get one movie by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MovieDTO findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        Movie movie = movieRepository.findOneWithEagerRelationships(id);
        return movieMapper.toDto(movie);
    }

    /**
     *  Delete the  movie by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.delete(id);
        movieSearchRepository.delete(id);
    }

    /**
     * Search for the movie corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovieDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Movies for query {}", query);
        Page<Movie> result = movieSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(movieMapper::toDto);
    }
}
