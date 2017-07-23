package com.stakater.moviemanager.repository.search;

import com.stakater.moviemanager.domain.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Movie entity.
 */
public interface MovieSearchRepository extends ElasticsearchRepository<Movie, Long> {
}
