package io.mjmoore.service.sprint;

import io.mjmoore.model.Story;

import java.util.function.Predicate;

/**
 * Stateful filter for calculating capacity
 */
public class EstimationLimit implements Predicate<Story> {

    private final int devCapacity;
    private long capacityLimit;
    private long capacity = 0;

    public EstimationLimit(final long devs, final int devCapacity) {
        this.devCapacity = devCapacity;
        this.capacityLimit = devs * devCapacity;
    }

    @Override
    public boolean test(final Story story) {

        final boolean capacityAvailable = capacity + story.getEstimate() <= capacityLimit;
        final boolean devCapable = devCapacity >= story.getEstimate();

        final boolean completable = capacityAvailable && devCapable;

        if(completable) {
            capacity += story.getEstimate();
        }

        return completable;
    }
}
