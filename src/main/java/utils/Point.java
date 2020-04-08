package utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Point {
    int x;
    int y;

    private static Point getRandomPoint(int XUpperBound, int XLowerBound, int YUpperBound, int YLowerBound) {
        int x = ThreadLocalRandom.current().nextInt(XLowerBound, XUpperBound + 1);
        int y = ThreadLocalRandom.current().nextInt(YLowerBound, YUpperBound + 1);


        return Point.of(x, y);
    }

    private static List<Point> getRandomPoints(int quantity, Point lowerLeftPoint, Point upperRightPoint) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            points.add(getRandomPoint(upperRightPoint.getX(), lowerLeftPoint.getX(), upperRightPoint.getY(), lowerLeftPoint.getY()));
        }
        return points;
    }

    public static List<String> getPointsAsStrings(int quantity, Point lowerLeftPoint, Point upperRightPoint) {
        List<Point> points = getRandomPoints(quantity, lowerLeftPoint, upperRightPoint);
        List<String> pointsAsString = new ArrayList<>();
        pointsAsString.add(String.valueOf(lowerLeftPoint.getX()));
        pointsAsString.add(String.valueOf(lowerLeftPoint.getY()));
        for (Point p : points) {
            pointsAsString.add(String.valueOf(p.getX()));
            pointsAsString.add(String.valueOf(p.getY()));
        }

        return pointsAsString;
    }

    public int distanceTo(Point point) {
        int x = Math.abs(getX() - point.getX());
        int y = Math.abs(getY() - point.getY());

        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}

