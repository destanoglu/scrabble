package service;

import exception.MovementException;
import model.Board;
import model.BoardField;
import model.Move;
import model.MoveDirection;

import java.awt.*;
import java.util.Map;

public class PlacementControl implements MoveControl {
    private final Integer InitialMoveSize = 1;
    @Override
    public void Control(Board board, Move move) throws MovementException {
        if (board.getMoves().size() == InitialMoveSize && board.getMoves().get(0).size() == InitialMoveSize){
            return;
        }
        Map<Point, BoardField> boardInstance = board.getBoardInstance();
        Point initial = move.getInitialPoint();
        for (int i = 0; i < move.getText().length(); ++i){
            BoardField field = null;
            if (move.getDirection() == MoveDirection.Horizontal){
                field = boardInstance.get(new Point(initial.x, initial.y + i));
            }
            else{
                field = boardInstance.get(new Point(initial.x + i, initial.y));
            }
            if (field.getUsageCount() > 1){
                return;
            }
        }
        throw new MovementException(move, "Wrong location");
    }
}
