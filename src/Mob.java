import processing.core.PImage;

import java.util.List;
import java.util.function.Predicate;


public abstract class Mob extends ExecuteTransform{
    protected final int resourceLimit;
    protected int resourceCount;

    public Mob(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod, health, healthLimit);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    abstract public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
    private static boolean withinReach(Point p1, Point p2)
    {
        return (Math.abs(p1.x - p2.x) == 1 && p1. y == p2.y) ||
                (Math.abs(p1.y - p2.y) == 1 && p1.x == p2.x);
    }

    public boolean canPassThrough(Point p, WorldModel world){
        return world.withinBounds(p) && !(world.getOccupant(p).isPresent() && !(world.getOccupant(p).get() instanceof Stump));
    }
    public Point nextPosition(WorldModel world, Point destPos){
        Predicate<Point> canPassThrough = p -> canPassThrough(p, world);
        PathingStrategy strat = new AStarPathingStrategy();
        List<Point> path = strat.computePath(
                getPosition(),
                destPos, canPassThrough, (p1, p2) -> withinReach(p1,p2),
                PathingStrategy.CARDINAL_NEIGHBORS
        );
        if(path.size() < 1){
            return getPosition();
        }
        return path.get(0);
    }

    }