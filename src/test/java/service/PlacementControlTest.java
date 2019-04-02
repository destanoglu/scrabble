package service;

import exception.MovementException;
import model.Board;
import model.Move;
import model.MoveDirection;
import org.junit.Test;

import java.awt.*;

public class PlacementControlTest {
    private PlacementControl placementControl = new PlacementControl();

    @Test
    public void Should_NotThrow_When_TheBoardIsEmpty() throws MovementException {
        Board board = new Board();
        Move movement = new Move(MoveDirection.Horizontal, new Point(0, 0), "test", 1);

        board.AddMove(0, movement);

        placementControl.Control(board, movement);
    }

    @Test
    public void Should_NotThrow_When_TheMoveCrossWithAnExistingMoveAtTheSameCharacter() throws MovementException {
        Board board = new Board();
        Move initial = new Move(MoveDirection.Horizontal, new Point(1, 0), "test", 1);
        initial.setMainSequence(0);
        Move movement = new Move(MoveDirection.Vertical, new Point(0, 1), "test", 2);
        movement.setMainSequence(0);

        board.AddMove(0, initial);
        board.AddMove(0, movement);

        placementControl.Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_ThrowMovementException_When_TheMoveNotCrossWithAnExistingMoveAtTheSameCharacter() throws MovementException {
        Board board = new Board();
        Move initial = new Move(MoveDirection.Horizontal, new Point(1, 0), "test", 1);
        initial.setMainSequence(0);
        Move movement = new Move(MoveDirection.Vertical, new Point(0, 2), "test", 2);
        movement.setMainSequence(0);

        board.AddMove(0, initial);
        board.AddMove(0, movement);

        placementControl.Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_Throw_When_TheMoveNotCrossWithAnExistingMove() throws MovementException {
        Board board = new Board();
        Move initial = new Move(MoveDirection.Horizontal, new Point(1, 0), "test", 1);
        initial.setMainSequence(0);
        Move movement = new Move(MoveDirection.Horizontal, new Point(0, 1), "test", 2);
        initial.setMainSequence(0);

        board.AddMove(0, initial);
        board.AddMove(0, movement);

        placementControl.Control(board, movement);
    }
}
