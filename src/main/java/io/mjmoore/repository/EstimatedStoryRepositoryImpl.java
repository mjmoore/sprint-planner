package io.mjmoore.repository;

import io.mjmoore.model.Story;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EstimatedStoryRepositoryImpl implements EstimatedStoryRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Map<Integer, List<Story>> getStoriesByEstimate() {

        return Collections.emptyMap();
    }
}
