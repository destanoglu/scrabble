package persistence;

import exception.BoardNotFoundException;
import model.Board;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import persistence.model.BoardEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardRepository {

    private static final Logger log = LoggerFactory.getLogger(BoardRepository.class);
    @Autowired private HibernateUtil hibernateUtil;

    public Long CreateBoard() throws HibernateException, IOException {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Long id = (Long)session.save(new BoardEntity());
            session.getTransaction().commit();
            return id;
        } catch (HibernateException ex){
            log.error(ex.toString());
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public List<Board> ListBoards() throws HibernateException, IOException, ClassNotFoundException {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            List<Board> boards = new ArrayList<>();
            List<BoardEntity> entities = session.createQuery("from BoardEntity").list();
            for (BoardEntity entity : entities) {
                boards.add(new Board(entity.getBoardId(), entity.getStructuredMoves(), entity.getStructuredInstance(), entity.getStructuredActivityStatus()));
            }
            return boards;
        } catch (HibernateException ex) {
            log.error(ex.toString());
            throw ex;
        } finally {
            session.close();
        }
    }

    public Board GetBoard(Long id) throws HibernateException, BoardNotFoundException, IOException, ClassNotFoundException {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            BoardEntity entity = (BoardEntity) session.get(BoardEntity.class, id);
            if (entity == null){
                throw new BoardNotFoundException();
            }

            return new Board(entity.getBoardId(), entity.getStructuredMoves(), entity.getStructuredInstance(), entity.getStructuredActivityStatus());
        } catch (HibernateException ex) {
            log.error(ex.toString());
            throw ex;
        } finally {
            session.close();
        }
    }

    public void UpdateBoard(Board board) throws BoardNotFoundException, IOException {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            BoardEntity entity = (BoardEntity) session.get(BoardEntity.class, board.getBoardId());
            if (entity == null){
                throw new BoardNotFoundException();
            }

            entity.updateActivityStatus(board.getActivityStatus());
            entity.updateInstance(board.getBoardInstance());
            entity.updateMoves(board.getMoves());

            session.saveOrUpdate(entity);
            session.flush();
            session.getTransaction().commit();
        }
        catch (HibernateException ex){
            log.error(ex.toString());
            throw ex;
        }
        finally {
            session.close();
        }
    }
}
