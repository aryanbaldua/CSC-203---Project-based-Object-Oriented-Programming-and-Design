import processing.core.PImage;

import java.util.List;

public class Obstacle extends Entity{
    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, animationPeriod, health, healthLimit);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), getAnimationPeriod());

    }
}
