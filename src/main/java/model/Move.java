package model;

import java.awt.*;
import java.io.Serializable;

public class Move implements Serializable {
    private MoveDirection direction;
    private Point initialPoint;
    private String text;

    public Move(MoveDirection direction, Point initialPoint, String text) {
        this.direction = direction;
        this.initialPoint = initialPoint;
        this.text = text;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public Point getInitialPoint() {
        return initialPoint;
    }

    public String getText() {
        return text;
    }
}
