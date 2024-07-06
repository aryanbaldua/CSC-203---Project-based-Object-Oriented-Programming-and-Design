public class Activity implements Action{
    /**
     * An action that can be taken by an entity.
     * Actions can be either an activity (involving movement, gaining health, etc)
     * or an animation (updating the image being displayed).
     */
        private final ExecuteTransform entity;
        private final WorldModel world;
        private final ImageStore imageStore;

        public Activity(ExecuteTransform entity, WorldModel world, ImageStore imageStore) {
            this.entity = entity;
            this.world = world;
            this.imageStore = imageStore;
        }
        public void executeAction(EventScheduler scheduler) {
            entity.executeActivity(world, imageStore, scheduler);
        }

}
