package chapter9;

import static java.util.Comparator.comparing;

import java.util.Comparator;
import java.util.List;

public class Point {

  // 람다를 필드에 저장해 재사용 및 테스트 가능
  public final static Comparator<Point> compareByXAndThenY = comparing(Point::getX).thenComparing(
      Point::getY);
  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
    return points.stream()
        .map(point -> new Point(point.getX() + x, point.getY()))
        .toList();
  }

  public int getX() {
    return x;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Point point = (Point) o;
    return x == point.x && y == point.y;
  }

  public int getY() {
    return y;
  }

  public Point moveRightBy(int x) {
    return new Point(this.x + x, this.y);
  }
}
