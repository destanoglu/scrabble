package model;

import exception.MovementException;
import model.Board;
import model.BoardField;
import model.Move;
import model.MoveDirection;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardTest {
    private Board testBoard;

    @Before
    public void SetUp(){
        List<Move> moves = new ArrayList<>();
        Move initial = new Move(MoveDirection.Horizontal, new Point(0, 0), "aba", 0);
        initial.setMainSequence(0);
        moves.add(initial);
        Move secondary = new Move(MoveDirection.Vertical, new Point(0, 0), "ata", 1);
        secondary.setMainSequence(0);
        moves.add(secondary);
        Move third = new Move(MoveDirection.Horizontal, new Point(2, 0), "anne", 0);
        third.setMainSequence(1);
        moves.add(third);

        Map<Point, BoardField> boardInstance = new HashMap<>();
        boardInstance.put(new Point(0, 0), new BoardField(MoveDirection.Horizontal, 'a', 2));
        boardInstance.put(new Point(0, 1), new BoardField(MoveDirection.Horizontal, 'b', 1));
        boardInstance.put(new Point(0, 2), new BoardField(MoveDirection.Horizontal, 'a', 1));

        boardInstance.put(new Point(1, 0), new BoardField(MoveDirection.Vertical, 't', 1));
        boardInstance.put(new Point(2, 0), new BoardField(MoveDirection.Vertical, 'a', 2));

        boardInstance.put(new Point(2, 1), new BoardField(MoveDirection.Horizontal, 'n', 1));
        boardInstance.put(new Point(2, 2), new BoardField(MoveDirection.Horizontal, 'n', 1));
        boardInstance.put(new Point(2, 3), new BoardField(MoveDirection.Horizontal, 'e', 1));

        testBoard = new Board(moves, boardInstance);
    }

    @Test
    public void Should_InitializeBorderStatus(){
        assert (testBoard.getActivityStatus() == true);
    }

    @Test
    public void Should_InitializeMoveSequenceCount(){
        assert (testBoard.getMoves().size() == 2);
    }

    @Test
    public void Should_InitializeFirstMoveSequence(){
        assert (testBoard.getMoves().get(0).size() == 2);
        assert (testBoard.getMoves().get(0).stream().anyMatch(move -> move.getText() == "aba"));
        assert (testBoard.getMoves().get(0).stream().anyMatch(move -> move.getText() == "ata"));
    }

    @Test
    public void Should_InitializeSecondMoveSequence(){
        assert (testBoard.getMoves().get(1).size() == 1);
        assert (testBoard.getMoves().get(1).stream().anyMatch(move -> move.getText() == "anne"));
    }

    @Test
    public void Should_InitializeBoardInstance(){
        assert (testBoard.getBoardInstance().size() == 8);
    }

    @Test(expected = MovementException.class)
    public void Should_Throw_When_AddingMovementIntoDeactivatedBoard() throws MovementException {
        testBoard.setActivityStatus(false);
        testBoard.AddMove(2, new Move(MoveDirection.Horizontal, new Point(1, 1), "test", 1));
    }

    @Test
    public void Should_AddMovementAndUpdateMoves() throws MovementException {
        testBoard.AddMove(2, new Move(MoveDirection.Horizontal, new Point(0, 0), "abala", 1));
        assert (testBoard.getMoves().get(2).size() == 1);
        assert (testBoard.getMoves().get(2).stream().anyMatch(move -> move.getText() == "abala"));
    }

    @Test
    public void Should_AddMovementAndUpdateBoardField() throws MovementException {
        testBoard.AddMove(2, new Move(MoveDirection.Horizontal, new Point(0, 0), "abala", 1));
        assert (testBoard.getBoardInstance().size() == 10);
        assert (testBoard.getBoardInstance().get(new Point(0, 0)).getUsageCount() == 3);
    }
}
