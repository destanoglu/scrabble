package service;

import exception.BoardNotFoundException;
import model.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import persistence.BoardRepository;

import java.io.IOException;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {
    @InjectMocks
    private BoardService boardService = new BoardService();
    @Mock private BoardRepository boardRepository;

    @Test
    public void Should_AddBoard() throws IOException {
        boardService.AddBoard();
        verify(boardRepository).CreateBoard();
    }

    @Test
    public void Should_ListBoard() throws IOException, ClassNotFoundException {
        boardService.ListBoards();
        verify(boardRepository).ListBoards();
    }

    @Test
    public void Should_ReturnBoard() throws BoardNotFoundException, IOException, ClassNotFoundException {
        boardService.GetBoard(1L);
        verify(boardRepository).GetBoard(1L);
    }

    @Test
    public void Should_DeactivateBoard() throws BoardNotFoundException, IOException, ClassNotFoundException {
        Board testBoard = new Board();
        when(boardRepository.GetBoard(1L)).thenReturn(testBoard);
        boardService.Deactivate(1L);

        verify(boardRepository).UpdateBoard(argThat(board -> board.getActivityStatus() == false));
    }
}
