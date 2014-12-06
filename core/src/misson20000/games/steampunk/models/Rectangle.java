package misson20000.games.steampunk.models;

/**
 * Created by misson20000 on 12/1/14.
 */
public class Rectangle {
  private int x1;
  private int x2;
  private int y1;
  private int y2;

  public int x;
  public int y;
  public int w;
  public int h;

  public void updateRect() {
    this.x = x1;
    this.y = y1;
    this.w = x2-x1;
    this.h = y2-y1;
  }

  public Rectangle(int x1, int y1, int w, int h) {
    this.x1 = x1; this.y1 = y1;
    this.x2 = x1+w; this.y2 = y1+h;
    updateRect();
  }
  public Rectangle(Point a, Point b) {
    this.x1 = a.x; this.y1 = a.y;
    this.x2 = b.x; this.y2 = b.y;
    updateRect();
  }

  public Point ul() { return new Point(x1, y1); }
  public Point ur() { return new Point(x2, y1); }
  public Point bl() { return new Point(x1, y2); }
  public Point br() { return new Point(x2, y2); }

  public int left() { return x1; }
  public int right() { return x2; }
  public int top() { return y1; }
  public int bottom() { return y2; }
}
