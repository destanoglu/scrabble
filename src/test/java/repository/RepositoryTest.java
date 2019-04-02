package repository;

import exception.BoardNotFoundException;
import model.Board;
import model.BoardField;
import model.Move;
import model.MoveDirection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import persistence.BoardRepository;
import persistence.HibernateUtil;
import persistence.model.BoardEntity;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {
    @InjectMocks private BoardRepository boardRepository;

    @Spy private static HibernateUtil hibernateUtil;

    @BeforeClass
    public static void OnetimeSetUp(){
        hibernateUtil = new HibernateUtil();
    }

    @Test
    public void Should_CreateBoard() throws IOException {
        assert(boardRepository.CreateBoard() > 0);
    }

    @Test
    public void Should_GetRequestedBoard() throws IOException, ClassNotFoundException, BoardNotFoundException {
        Board expected = this.CreateBoard();
        Long id = this.StoreBoard(expected);

        Board actual = boardRepository.GetBoard(id);
        assertEquals(id, actual.getBoardId());
        assertEquals(expected.getBoardInstance().size(), actual.getBoardInstance().size());
        assertEquals(expected.getMoves().size(), actual.getMoves().size());
    }

    @Test
    public void Should_UpdateGivenBoard() throws IOException, ClassNotFoundException, BoardNotFoundException {
        Board expected = this.CreateBoard();
        Long id = this.StoreBoard(expected);

        Board actual = boardRepository.GetBoard(id);
        actual.setActivityStatus(false);
        boardRepository.UpdateBoard(actual);

        Board modifiedBoard = boardRepository.GetBoard(id);
        assertEquals(false, modifiedBoard.getActivityStatus());
    }

    private Board CreateBoard(){
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

        return new Board(moves, boardInstance);
    }

    private Long StoreBoard(Board board) throws HibernateException, IOException {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            BoardEntity entity = new BoardEntity();
            entity.updateActivityStatus(board.getActivityStatus());
            entity.updateMoves(board.getMoves());
            entity.updateInstance(board.getBoardInstance());

            Long id = (Long)session.save(entity);
            session.getTransaction().commit();
            return id;
        } catch (HibernateException ex){
            throw ex;
        }
        finally {
            session.close();
        }
    }
}
