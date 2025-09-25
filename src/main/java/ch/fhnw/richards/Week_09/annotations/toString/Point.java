package ch.fhnw.richards.Week_09.annotations.toString;

@ToString(includeName = false) // Use the ToString annotation for this class
public class Point {
    @ToString(includeName = false) private int x; // when processing x, do not include the name
    @ToString(includeName = false) private int y; // when processing y, do not include the name

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
