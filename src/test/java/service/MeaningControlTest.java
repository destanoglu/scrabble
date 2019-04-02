package service;

import exception.MovementException;
import model.Board;
import model.Move;
import model.MoveDirection;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;

public class MeaningControlTest {
    private MeaningControl meaningControl = new MeaningControl();

    public MeaningControlTest() throws IOException {
    }

    @Test
    public void Should_NotThrow_When_ValidSentenceNotAffectOthers() throws MovementException {
        Board board = new Board();
        Move movement = new Move(MoveDirection.Horizontal, new Point(0, 0), "aba", 1);

        board.AddMove(0, movement);
        meaningControl.Control(board, movement);
    }

    @Test
    public void Should_NotThrow_When_VerticalValidSentenceNotAffectOthers() throws MovementException {
        Board board = new Board();
        Move movement = new Move(MoveDirection.Vertical, new Point(0, 0), "aba", 1);

        board.AddMove(0, movement);
        meaningControl.Control(board, movement);
    }

    @Test
    public void Should_NotThrow_When_ValidSentenceAffectOthersAndNotDisturbTheirValidations() throws MovementException {
        Board board = new Board();
        Move initial = new Move(MoveDirection.Horizontal, new Point(0, 0), "araba", 1);
        initial.setMainSequence(0);
        Move secondary = new Move(MoveDirection.Vertical, new Point(0, 3), "baba", 1);
        secondary.setMainSequence(1);
        Move movement = new Move(MoveDirection.Horizontal, new Point(1, 3), "at", 1);
        movement.setMainSequence(2);

        board.AddMove(0, initial);
        board.AddMove(1, secondary);
        board.AddMove(2, movement);
        meaningControl.Control(board, movement);
    }

    @Test
    public void Should_NotThrow_When_ValidVerticalSentenceAffectOthersAndNotDisturbTheirValidations() throws MovementException {
        Board board = new Board();
        Move initial = new Move(MoveDirection.Vertical, new Point(0, 0), "araba", 1);
        initial.setMainSequence(0);
        Move secondary = new Move(MoveDirection.Horizontal, new Point(3, 0), "baba", 1);
        secondary.setMainSequence(1);
        Move movement = new Move(MoveDirection.Vertical, new Point(3, 1), "at", 1);
        movement.setMainSequence(2);

        board.AddMove(0, initial);
        board.AddMove(1, secondary);
        board.AddMove(2, movement);
        meaningControl.Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_ThrowMovementException_When_SentenceIsNotValid() throws MovementException {
        Board board = new Board();
        Move movement = new Move(MoveDirection.Horizontal, new Point(0, 0), "aeioö", 1);
        movement.setMainSequence(0);

        board.AddMove(0, movement);
        meaningControl.Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_ThrowMovementException_When_VerticalSentenceIsNotValid() throws MovementException {
        Board board = new Board();
        Move movement = new Move(MoveDirection.Vertical, new Point(0, 0), "aeioö", 1);
        movement.setMainSequence(0);

        board.AddMove(0, movement);
        meaningControl.Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_ThrowMovementException_When_ValidSentenceAffectOthersAndDisturbTheirValidations() throws MovementException {
        Board board = new Board();
        Move initial = new Move(MoveDirection.Horizontal, new Point(0, 0), "araba", 1);
        initial.setMainSequence(0);
        Move secondary = new Move(MoveDirection.Vertical, new Point(0, 0), "araba", 1);
        secondary.setMainSequence(1);
        Move movement = new Move(MoveDirection.Horizontal, new Point(1, 0), "rol", 1);
        movement.setMainSequence(2);

        board.AddMove(0, initial);
        board.AddMove(1, secondary);
        board.AddMove(2, movement);
        meaningControl.Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_ThrowMovementException_When_VerticalValidSentenceAffectOthersAndDisturbTheirValidations() throws MovementException {
        Board board = new Board();
        Move initial = new Move(MoveDirection.Vertical, new Point(0, 0), "araba", 1);
        initial.setMainSequence(0);
        Move secondary = new Move(MoveDirection.Horizontal, new Point(0, 0), "araba", 1);
        secondary.setMainSequence(1);
        Move movement = new Move(MoveDirection.Vertical, new Point(0, 1), "rol", 1);
        movement.setMainSequence(2);

        board.AddMove(0, initial);
        board.AddMove(1, secondary);
        board.AddMove(2, movement);
        meaningControl.Control(board, movement);
    }
}
