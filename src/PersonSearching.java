import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PersonSearching extends Mob{

    public PersonSearching(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), getAnimationPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(position, new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (target.isEmpty() || !moveTo(world, target.get(), scheduler) || !transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), actionPeriod);
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (resourceCount >= resourceLimit) {
            Entity dude = Factory.createPersonFull(id, position, actionPeriod, animationPeriod, resourceLimit, images);

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.tryAddEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (position.adjacent(target.position)) {
            resourceCount += 1;
            target.health--;
            return true;
        } else {
            Point nextPos = nextPosition(world, target.position);

            if (!position.equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
    //@Override
    //public boolean canPassThrough(Point p, WorldModel world){
      //  return super.canPassThrough(p,world) && world.getOccupant(p).isEmpty() || (world.getOccupant(p).isPresent() && world.getOccupant(p).get() instanceof Stump);
    //}

}
