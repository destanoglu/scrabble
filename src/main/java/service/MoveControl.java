package service;

import exception.MovementException;
import model.Board;
import model.Move;

public interface MoveControl {
    void Control(Board board, Move move) throws MovementException;
}
