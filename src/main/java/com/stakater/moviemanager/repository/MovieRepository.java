package com.stakater.moviemanager.repository;

import com.stakater.moviemanager.domain.Movie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Movie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    
    @Query("select distinct movie from Movie movie left join fetch movie.genres")
    List<Movie> findAllWithEagerRelationships();

    @Query("select movie from Movie movie left join fetch movie.genres where movie.id =:id")
    Movie findOneWithEagerRelationships(@Param("id") Long id);
    
}
