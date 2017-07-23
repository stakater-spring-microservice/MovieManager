package com.stakater.moviemanager.service.mapper;

import com.stakater.moviemanager.domain.*;
import com.stakater.moviemanager.service.dto.MovieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Movie and its DTO MovieDTO.
 */
@Mapper(componentModel = "spring", uses = {GenreMapper.class, })
public interface MovieMapper extends EntityMapper <MovieDTO, Movie> {
    
    
    default Movie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setId(id);
        return movie;
    }
}
