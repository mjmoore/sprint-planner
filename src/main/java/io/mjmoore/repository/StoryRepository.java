package io.mjmoore.repository;

import io.mjmoore.model.Story;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface StoryRepository extends PagingAndSortingRepository<Story, Long>, EstimatedStoryRepository {

    @Query(value = "Select * From Story s Left Join Issue i on s.Issue_Id = i.id Where s.Status = 'Estimated'", nativeQuery = true)
    @RestResource(exported = false)
    List<Story> getEstimatedStories();

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
