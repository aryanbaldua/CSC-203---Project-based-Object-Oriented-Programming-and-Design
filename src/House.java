import processing.core.PImage;

import java.util.List;

public class House extends Entity{
    public House(String id, Point position, List<PImage> images, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, animationPeriod, health, healthLimit);
    }

    /*
    @Override
    public double getAnimationPeriod() {
        throw new UnsupportedOperationException("getAnimationPeriod not supported for House");
    }

     */
}
