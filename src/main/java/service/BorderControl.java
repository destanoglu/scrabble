package service;

import exception.MovementException;
import model.Board;
import model.Move;
import model.MoveDirection;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

public class BorderControl implements MoveControl {

    @Autowired private BoardBorder boardBorder;

    @Override
    public void Control(Board board, Move move) throws MovementException {
        EnsureInitialPoint(move);
        EnsureBorders(move);
    }

    private void EnsureInitialPoint(Move move) throws MovementException {
        Point initial = move.getInitialPoint();
        if (initial.x < 0 || initial.x >= boardBorder.getSize()){
            throw new MovementException(move, "Initial point is out of range");
        }
        if (initial.y < 0 || initial.y >= boardBorder.getSize()){
            throw new MovementException(move, "Initial point is out of range");
        }
    }

    private void EnsureBorders(Move move) throws MovementException {
        Point initial = move.getInitialPoint();
        if (move.getDirection() == MoveDirection.Vertical && (initial.x + move.getText().length() - 1) >= boardBorder.getSize()){
            throw new MovementException(move, "End point is out of range");
        }
        if (move.getDirection() == MoveDirection.Horizontal && (initial.y + move.getText().length() - 1) >= boardBorder.getSize()){
            throw new MovementException(move, "End point is out of range");
        }
    }
}
