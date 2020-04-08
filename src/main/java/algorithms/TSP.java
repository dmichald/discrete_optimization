package algorithms;

import lombok.Getter;
import utils.Point;

import java.util.ArrayList;
import java.util.List;


public class TSP {

    @Getter
    private int[][] distanceMatrix;
    private int[] distances;
    private List<Integer> route;

    public TSP(List<Point> points) {
        computeDistances(points);
        distances = new int[distanceMatrix.length];
        route = new ArrayList<>();

    }


    private void computeDistances(List<Point> points) {
        distanceMatrix = new int[points.size()][points.size()];
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix.length; j++) {
                Point point = points.get(i);
                distanceMatrix[i][j] = point.distanceTo(points.get(j));
            }
        }
    }

    public List<Integer> solve(int startVertex) {
        int vertexAmount = distances.length;
        final int USED = -999;
        System.arraycopy(distanceMatrix[startVertex], 0, distances, 0, distances.length);
        route.add(startVertex);
        route.add(startVertex);
        distances[startVertex] = USED;

        while (route.size() <= vertexAmount) {
            int indexWithMaxValue = getIndexWithMaxValue(distances);
            int position = findTheBestPosition(indexWithMaxValue);
            distances[indexWithMaxValue] = USED;
            route.add(position, indexWithMaxValue);
            findAndChaneElements(distanceMatrix[indexWithMaxValue]);
        }
        int cost = (int) computeCost(route);
        route.add(cost);
        return route;

    }

    private int findTheBestPosition(int vertex) {
        int index = 1;
        int cost = 999999999;
        int tmpCost;
        for (int i = 0; i < route.size() - 1; i++) {
            tmpCost = distanceMatrix[route.get(i)][vertex] + distanceMatrix[vertex][route.get(i + 1)] - distanceMatrix[route.get(i)][route.get(i + 1)];
            if (tmpCost < cost) {
                index = i + 1;
                cost = tmpCost;
            }
        }
        return index;
    }

    private void findAndChaneElements(int[] toCompare) {
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] > toCompare[i]) {
                distances[i] = toCompare[i];
            }
        }
    }

    private int getIndexWithMaxValue(int[] array) {
        int max = array[0];
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                index = i;
            }
        }
        return index;
    }

    private double computeCost(List<Integer> route) {
        double cost = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int a = route.get(i);
            int b = route.get(i + 1);
            cost += distanceMatrix[a][b];
        }
        return cost;
    }


}
