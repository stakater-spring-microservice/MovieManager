package com.stakater.moviemanager.repository.search;

import com.stakater.moviemanager.domain.Genre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Genre entity.
 */
public interface GenreSearchRepository extends ElasticsearchRepository<Genre, Long> {
}
