package io.mjmoore.repository;

import io.mjmoore.model.Story;

import java.util.List;
import java.util.Map;

public interface EstimatedStoryRepository {

    Map<Integer, List<Story>> getStoriesByEstimate();
}
