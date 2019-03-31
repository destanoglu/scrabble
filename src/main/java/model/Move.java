package model;

import java.awt.*;
import java.io.Serializable;

public class Move implements Serializable, Comparable<Move> {
    private MoveDirection direction;
    private Point initialPoint;
    private String text;
    private Integer mainSequence;
    private Integer innersequence;

    public Move(MoveDirection direction, Point initialPoint, String text, Integer innersequence) {
        this.direction = direction;
        this.initialPoint = initialPoint;
        this.text = text;
        this.innersequence = innersequence;
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

    public Integer getMainSequence() {
        return mainSequence;
    }

    public void setMainSequence(Integer mainSequence) {
        this.mainSequence = mainSequence;
    }

    public Integer getInnersequence() {
        return innersequence;
    }

    @Override
    public int compareTo(Move o) {
        return getInnersequence() - o.getInnersequence();
    }

    @Override
    public String toString() {
        return "Move seq: " + this.mainSequence + ", inner seq: " + this.innersequence + ", text: " + this.text;
    }
}
