package persistence;

import exception.BoardNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import persistence.model.BoardEntity;

import java.util.Iterator;
import java.util.List;

@Repository
public class BoardRepository {

    private static final Logger log = LoggerFactory.getLogger(BoardRepository.class);
    @Autowired private HibernateUtil hibernateUtil;

    public Long AddBoard(BoardEntity boardEntity) throws HibernateException{
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Long id = (Long)session.save(boardEntity);
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

    public List<BoardEntity> ListBoards() throws HibernateException {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            List<BoardEntity> boards = session.createQuery("from BoardEntity").list();
            for (Iterator<BoardEntity> iterator = boards.iterator(); iterator.hasNext();){
                BoardEntity entity = iterator.next();
                log.info(entity.toString());
            }
            return boards;
        } catch (HibernateException ex) {
            log.error(ex.toString());
            throw ex;
        } finally {
            session.close();
        }
    }

    public BoardEntity GetBoard(Long id) throws HibernateException, BoardNotFoundException {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            BoardEntity boardEntity = (BoardEntity) session.get(BoardEntity.class, id);
            if (boardEntity == null){
                throw new BoardNotFoundException();
            }

            return boardEntity;
        } catch (HibernateException ex) {
            log.error(ex.toString());
            throw ex;
        } finally {
            session.close();
        }
    }

    public void UpdateBoard(BoardEntity entity) {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
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
