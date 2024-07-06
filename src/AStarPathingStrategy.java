import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getFScore));
        Map<Point, Integer> openListMap = new HashMap<>();
        Set<Point> closedList = new HashSet<>();

        Node startNode = new Node(start, null);
        openList.add(startNode);
        openListMap.put(start, startNode.getFScore());
        List<Point> computedPath = new ArrayList<>();

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            openListMap.remove(current.myPoint);

            if (withinReach.test(current.myPoint, end)) {
                while (current.previous != null) {
                    computedPath.add(0, current.myPoint);
                    current = current.previous;
                }
                return computedPath;
            }

            List<Point> neighbors = potentialNeighbors.apply(current.myPoint)
                    .filter(canPassThrough)
                    .filter(point -> !closedList.contains(point))
                    .toList();

            for (Point neighbor : neighbors) {
                Node nextTo = new Node(neighbor, current);
                nextTo.calcScores(end);

                if (openListMap.containsKey(nextTo.myPoint) && openListMap.get(nextTo.myPoint) <= nextTo.getFScore()) {
                    continue;
                }

                openList.add(nextTo);
                openListMap.put(nextTo.myPoint, nextTo.getFScore());
            }

            closedList.add(current.myPoint);
        }
        return computedPath;
    }


    static int distance(Point a, Point b){
        return (int) Math.sqrt(Math.pow(a.y - b.y,2) + Math.pow(a.x - b.x,2));
    }

    class Node{
        private Point myPoint;
        private int gScore;
        private int hScore;
        private Node previous;

        Node(Point p, Node lastNode){
            myPoint = p;
            previous = lastNode;
        }

        void calcScores(Point end){
            if (previous != null){
                gScore = previous.gScore + 1;
            }else{
                gScore = 0;
            }

            hScore = distance(myPoint, end);
        }

        int getFScore(){
            return gScore + hScore;
        }

        @Override
        public boolean equals(Object other)
        {
            return myPoint.equals(((Node)other).myPoint);

        }
    }
}
