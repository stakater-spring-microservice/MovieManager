package com.stakater.moviemanager.service.impl;

import com.stakater.moviemanager.service.GenreService;
import com.stakater.moviemanager.domain.Genre;
import com.stakater.moviemanager.repository.GenreRepository;
import com.stakater.moviemanager.repository.search.GenreSearchRepository;
import com.stakater.moviemanager.service.dto.GenreDTO;
import com.stakater.moviemanager.service.mapper.GenreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Genre.
 */
@Service
@Transactional
public class GenreServiceImpl implements GenreService{

    private final Logger log = LoggerFactory.getLogger(GenreServiceImpl.class);

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    private final GenreSearchRepository genreSearchRepository;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper, GenreSearchRepository genreSearchRepository) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
        this.genreSearchRepository = genreSearchRepository;
    }

    /**
     * Save a genre.
     *
     * @param genreDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GenreDTO save(GenreDTO genreDTO) {
        log.debug("Request to save Genre : {}", genreDTO);
        Genre genre = genreMapper.toEntity(genreDTO);
        genre = genreRepository.save(genre);
        GenreDTO result = genreMapper.toDto(genre);
        genreSearchRepository.save(genre);
        return result;
    }

    /**
     *  Get all the genres.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GenreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Genres");
        return genreRepository.findAll(pageable)
            .map(genreMapper::toDto);
    }

    /**
     *  Get one genre by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GenreDTO findOne(Long id) {
        log.debug("Request to get Genre : {}", id);
        Genre genre = genreRepository.findOne(id);
        return genreMapper.toDto(genre);
    }

    /**
     *  Delete the  genre by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Genre : {}", id);
        genreRepository.delete(id);
        genreSearchRepository.delete(id);
    }

    /**
     * Search for the genre corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GenreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Genres for query {}", query);
        Page<Genre> result = genreSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(genreMapper::toDto);
    }
}
