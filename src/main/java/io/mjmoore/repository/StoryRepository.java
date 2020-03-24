package io.mjmoore.repository;

import io.mjmoore.model.Story;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface StoryRepository extends PagingAndSortingRepository<Story, Long> {

    @RestResource(exported = false)
    <S extends Story> S save(S entity);

    @RestResource(exported = false)
    void deleteById(Long aLong);

    @RestResource(exported = false)
    void delete(Story entity);

    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Story> entities);

    @RestResource(exported = false)
    void deleteAll();
}
