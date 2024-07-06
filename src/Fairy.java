import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Fairy extends Mob{
    public Fairy(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), getAnimationPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(position, new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().position;

            if (moveTo(world, fairyTarget.get(), scheduler)) {

                Entity sapling = Factory.createSapling(WorldLoader.SAPLING_KEY + "_" + fairyTarget.get().id, tgtPos, imageStore.getImageList(WorldLoader.SAPLING_KEY));

                world.tryAddEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), actionPeriod);

    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return true;
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (position.adjacent(target.position)) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.position);

            if (!position.equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
    @Override
    public boolean canPassThrough(Point p, WorldModel world){
        return world.withinBounds(p) && !(world.getOccupant(p).isPresent() && !(world.getOccupant(p).get() instanceof House));
    }
}
