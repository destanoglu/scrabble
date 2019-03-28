package persistence;

import exception.BoardNotFoundException;
import persistence.model.BoardEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public class BoardRepository {

    private static final Logger log = LoggerFactory.getLogger(BoardRepository.class);
    @Autowired private HibernateUtil hibernateUtil;

    public Long AddEmployee(BoardEntity employee) throws HibernateException{
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Long id = (Long)session.save(employee);
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
            List<BoardEntity> employees = session.createQuery("from BoardEntity").list();
            for (Iterator<BoardEntity> iterator = employees.iterator(); iterator.hasNext();){
                BoardEntity entity = iterator.next();
                log.info(entity.toString());
            }
            return employees;
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
            BoardEntity employee = (BoardEntity) session.get(BoardEntity.class, id);
            if (employee == null){
                throw new BoardNotFoundException();
            }

            return employee;
        } catch (HibernateException ex) {
            log.error(ex.toString());
            throw ex;
        } finally {
            session.close();
        }
    }
}
