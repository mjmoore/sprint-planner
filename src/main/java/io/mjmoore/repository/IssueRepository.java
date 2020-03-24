package io.mjmoore.repository;

import io.mjmoore.model.Issue;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
@RestResource(exported = false)
public interface IssueRepository extends PagingAndSortingRepository<Issue, Long> { }
