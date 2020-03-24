package io.mjmoore.issue.repository;

import io.mjmoore.issue.model.Bug;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface BugRepository extends PagingAndSortingRepository<Bug, Long> {

    @RestResource(exported = false)
    <S extends Bug> S save(S entity);

    @RestResource(exported = false)
    void deleteById(Long aLong);

    @RestResource(exported = false)
    void delete(Bug entity);

    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Bug> entities);

    @RestResource(exported = false)
    void deleteAll();
}

