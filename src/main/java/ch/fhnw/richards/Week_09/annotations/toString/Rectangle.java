package ch.fhnw.richards.Week_09.annotations.toString;

@ToString // Use the ToString annotation for this class
public class Rectangle {
    @ToString(includeName = false) private Point topLeft;
    @ToString private int width;
    @ToString private int height;

    public Rectangle(int x, int y, int width, int height) {
        topLeft = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
