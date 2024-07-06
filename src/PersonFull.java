import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonFull extends Mob{

    public PersonFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), getAnimationPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(position, new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && moveTo(world, fullTarget.get(), scheduler)) {
            transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), actionPeriod);
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity dude = Factory.createPersonSearching(id, position, actionPeriod, animationPeriod, resourceLimit, images);

        world.removeEntity(scheduler, this);

        world.tryAddEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        //is this right
        return true;
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (position.adjacent(target.position)) {
            return true;
        } else {
            Point nextPos = nextPosition(world, target.position);

            if (!position.equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
}
//        return super.canPassThrough(p,world) && world.getOccupant(p).isEmpty() || (world.getOccupant(p).isPresent() && world.getOccupant(p).get() instanceof Stump);
