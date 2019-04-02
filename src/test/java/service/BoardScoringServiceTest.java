package service;

import exception.BoardNotFoundException;
import exception.MovementException;
import model.Board;
import model.Move;
import model.MoveDirection;
import model.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import persistence.BoardRepository;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardScoringServiceTest {
    @InjectMocks private BoardScoringService boardScoringService = new BoardScoringService();
    @Mock private BoardBorder boardBorder;
    @Mock private BoardRepository boardRepository;

    @Test
    public void Should_CalculateSuccessfully() throws MovementException, BoardNotFoundException, IOException, ClassNotFoundException {
        when(boardBorder.getSize()).thenReturn(10);
        Board board = new Board();
        String word_1 = "zşpö";
        Move initial = new Move(MoveDirection.Horizontal, new Point(0, 0), word_1, 1);
        initial.setMainSequence(0);
        String word_2 = "zalim";
        Move secondary = new Move(MoveDirection.Vertical, new Point(0, 0), word_2, 2);
        secondary.setMainSequence(0);

        board.AddMove(0, initial);
        board.AddMove(0, secondary);

        when(boardRepository.GetBoard(1L)).thenReturn(board);

        List<Word> words = boardScoringService.Score(1L);

        assertEquals(words.size(), 2);

        assertEquals(20, words.stream().filter(word -> word.getWord().equals(word_1)).findFirst().get().getScore().intValue());
        assertEquals(9, words.stream().filter(word -> word.getWord().equals(word_2)).findFirst().get().getScore().intValue());
    }
}
