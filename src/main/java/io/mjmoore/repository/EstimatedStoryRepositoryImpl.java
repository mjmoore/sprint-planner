package io.mjmoore.repository;

import io.mjmoore.model.Story;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstimatedStoryRepositoryImpl implements EstimatedStoryRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Map<Integer, List<Story>> getStoriesByEstimate() {

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Story> query = criteriaBuilder.createQuery(Story.class);

        final Root<Story> story = query.from(Story.class);
        query.multiselect(story)
                .where(criteriaBuilder.equal(story.get("status"), Story.Status.Estimated))
                .orderBy(criteriaBuilder.asc(story.get("id")));

        return entityManager.createQuery(query).getResultStream()
                .collect(Collectors.groupingBy(Story::getEstimate));
    }
}
