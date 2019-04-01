package responseModel;

import model.MoveDirection;

import java.awt.*;
import java.io.Serializable;

public class MoveResponse implements Serializable {
    public MoveDirection direction;
    public Point initialPoint;
    public String text;
    public Integer mainSequence;
    public Integer innersequence;

    public MoveResponse(MoveDirection direction, Point initialPoint, String text, Integer mainSequence, Integer innersequence) {
        this.direction = direction;
        this.initialPoint = initialPoint;
        this.text = text;
        this.mainSequence = mainSequence;
        this.innersequence = innersequence;
    }
}
