import processing.core.PImage;

import java.util.List;

public class Tree extends ExecuteTransform{
    public Tree(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), getAnimationPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!transform(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), actionPeriod);
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (health <= 0) {
            Entity stump = Factory.createStump(WorldLoader.STUMP_KEY + "_" + id, position, imageStore.getImageList(WorldLoader.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.tryAddEntity(stump);

            return true;
        }
        return false;
    }
}
