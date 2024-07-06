import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
abstract public class Entity {
    protected final String id;
    protected Point position;
    protected final List<PImage> images;
    private int imageIndex;
    protected final double animationPeriod;
    protected int health;
    protected final int healthLimit;

    public Entity(String id, Point position, List<PImage> images, double animationPeriod, int health, int healthLimit) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public void nextImage() {
        imageIndex = imageIndex + 1;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }

    public PImage getCurrentImage() {
        return this.images.get(this.imageIndex % this.images.size());

    }

    public double getAnimationPeriod() {
        return animationPeriod;
    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }
    public void setPosition(Point pos) {
        this.position = pos;
    }
}
