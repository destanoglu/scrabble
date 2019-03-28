package persistence;

import exception.BoardNotFoundException;
import model.EmployeeEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public class EmployeeRepository {

    private static final Logger log = LoggerFactory.getLogger(EmployeeRepository.class);
    @Autowired private HibernateUtil hibernateUtil;

    public Long AddEmployee(EmployeeEntity employee) throws HibernateException{
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

    public List<EmployeeEntity> ListBoards() throws HibernateException {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            List<EmployeeEntity> employees = session.createQuery("from EmployeeEntity").list();
            for (Iterator<EmployeeEntity> iterator = employees.iterator(); iterator.hasNext();){
                EmployeeEntity entity = iterator.next();
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

    public EmployeeEntity GetBoard(Long id) throws HibernateException, BoardNotFoundException {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            EmployeeEntity employee = (EmployeeEntity) session.get(EmployeeEntity.class, id);
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
