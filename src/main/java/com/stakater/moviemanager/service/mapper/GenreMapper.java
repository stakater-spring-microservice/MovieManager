package com.stakater.moviemanager.service.mapper;

import com.stakater.moviemanager.domain.*;
import com.stakater.moviemanager.service.dto.GenreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Genre and its DTO GenreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenreMapper extends EntityMapper <GenreDTO, Genre> {
    
    @Mapping(target = "movies", ignore = true)
    Genre toEntity(GenreDTO genreDTO); 
    default Genre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}
