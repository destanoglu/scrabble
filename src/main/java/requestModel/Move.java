package requestModel;

import model.MoveDirection;

import java.awt.*;

public class Move {
    private Integer innerSequence;
    private MoveDirection direction;
    private String text;
    private Point initialPoint;

    public Integer getInnerSequence() {
        return innerSequence;
    }

    public void setInnerSequence(Integer innerSequence) {
        this.innerSequence = innerSequence;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public void setDirection(MoveDirection direction) {
        this.direction = direction;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Point getInitialPoint() {
        return initialPoint;
    }

    public void setInitialPoint(Point initialPoint) {
        this.initialPoint = initialPoint;
    }

    public model.Move MoveModel(){
        return new model.Move(this.direction, this.initialPoint, this.text, this.innerSequence);
    }
}
