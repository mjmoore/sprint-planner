package io.mjmoore.repository;

import io.mjmoore.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface UserRepository extends PagingAndSortingRepository<User, Long> { }
