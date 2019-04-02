package service;

import exception.MovementException;
import model.Board;
import model.Move;
import model.MoveDirection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BorderControlTest {
    @InjectMocks private BorderControl borderControl = new BorderControl();
    @Mock private BoardBorder boardBorder;

    private Board board;

    @Test
    public void Should_NotThrow_When_MoveIsHorizontalAndStaysInBorders() throws MovementException {
        reset(boardBorder);
        when(boardBorder.getSize()).thenReturn(10);
        board = new Board();

        Move movement = new Move(MoveDirection.Horizontal, new Point(0, 0), "test", 1);
        borderControl.Control(board, movement);
    }

    @Test
    public void Should_NotThrow_When_MoveIsVerticalAndStaysInBorders() throws MovementException {
        reset(boardBorder);
        when(boardBorder.getSize()).thenReturn(10);
        board = new Board();

        Move movement = new Move(MoveDirection.Vertical, new Point(0, 0), "test", 1);
        borderControl.Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_ThrowMovementException_When_MoveStartsAtOutOfBorders() throws MovementException {
        board = new Board();

        Move movement = new Move(MoveDirection.Vertical, new Point(-1, 0), "test", 1);
        borderControl.Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_ThrowMovementException_When_MoveIsHorizontalAndEndsAtOutOfBorders() throws MovementException {
        reset(boardBorder);
        when(boardBorder.getSize()).thenReturn(10);
        board = new Board();

        Move movement = new Move(MoveDirection.Horizontal, new Point(0, 0), "testtesttest", 1);
        borderControl.Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_ThrowMovementException_When_MoveIsVerticalAndEndsAtOutOfBorders() throws MovementException {
        reset(boardBorder);
        when(boardBorder.getSize()).thenReturn(10);
        board = new Board();

        Move movement = new Move(MoveDirection.Vertical, new Point(0, 0), "testtesttest", 1);
        borderControl.Control(board, movement);
    }
}
