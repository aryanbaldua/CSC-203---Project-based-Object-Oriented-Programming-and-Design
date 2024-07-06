import processing.core.PImage;

import java.util.List;

public abstract class ExecuteTransform extends Entity{
    protected final double actionPeriod;

    public ExecuteTransform(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, animationPeriod, health, healthLimit);
        this.actionPeriod = actionPeriod;
    }

    abstract public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    abstract public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    }
