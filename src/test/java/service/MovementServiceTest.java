package service;

import exception.BoardNotFoundException;
import exception.MovementException;
import model.Board;
import model.Move;
import model.MoveDirection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import persistence.BoardRepository;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovementServiceTest {
    @InjectMocks private MovementService movementService = new MovementService();
    @Mock private BoardRepository boardRepository;

    @Spy private ArrayList<MoveControl> contollers;
    @Mock private MeaningControl firstController;
    @Mock private PlacementControl secondController;

    @Before
    public void SetUp(){
        contollers.clear();
        contollers.add(firstController);
        contollers.add(secondController);
    }

    @Test
    public void Should_CheckAllControllers() throws ClassNotFoundException, BoardNotFoundException, MovementException, IOException {
        Move movement = new Move(MoveDirection.Horizontal, new Point(0, 0), "test", 1);
        movement.setMainSequence(0);
        List<Move> moveList = new ArrayList<>();
        Board board = new Board();
        moveList.add(movement);

        when(boardRepository.GetBoard(1L)).thenReturn(board);

        movementService.AddMovement(1L, moveList);

        verify(firstController).Control(board, movement);
        verify(secondController).Control(board, movement);
    }

    @Test(expected = MovementException.class)
    public void Should_Throw_When_AControllerFailsToCheck() throws ClassNotFoundException, BoardNotFoundException, MovementException, IOException {
        Move movement = new Move(MoveDirection.Horizontal, new Point(0, 0), "test", 1);
        movement.setMainSequence(0);
        List<Move> moveList = new ArrayList<>();
        Board board = new Board();
        moveList.add(movement);

        when(boardRepository.GetBoard(1L)).thenReturn(board);
        doThrow(new MovementException()).when(firstController).Control(board, movement);

        movementService.AddMovement(1L, moveList);
    }
}
