package misson20000.games.steampunk.models;

/**
 * Created by misson20000 on 11/12/14.
 */
public class Point {
  public int x;
  public int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point(Point p) {
    this.x = p.x;
    this.y = p.y;
  }
}
