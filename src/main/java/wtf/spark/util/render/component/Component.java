package wtf.spark.util.render.component;

import java.util.ArrayList;
import java.util.List;

public abstract class Component extends AbstractComponent {

    protected final List<Component> children = new ArrayList<>();
    protected double x, y, width, height;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<Component> getChildren() {
        return children;
    }

    public boolean isInBounds(int mouseX, int mouseY) {
        return isInBounds(mouseX, mouseY, getX(), getY(), getWidth(), getHeight());
    }
}
