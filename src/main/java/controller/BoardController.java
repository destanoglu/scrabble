package controller;

import exception.BoardNotFoundException;
import exception.RestException;
import model.EmployeeEntity;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import responseModel.CreationResponse;
import service.EmployeeService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/board")
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    @Autowired private EmployeeService employeeService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public CreationResponse createBoard() throws RestException {
        try {
            EmployeeEntity newEntity = new EmployeeEntity();
            newEntity.setLastName("asd");
            newEntity.setFirstName("asda");
            newEntity.setEmail("as@gmail.com");
            Long id = employeeService.AddBoard(newEntity);
            log.info("Created id = " + id);
            return new CreationResponse(id);
        } catch (HibernateException e){
            throw new RestException("Error adding board", e);
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmployeeEntity> listBoards() throws RestException {
        try{
            return employeeService.ListBoards();
        } catch (HibernateException e){
            throw new RestException("Error listing boards", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public EmployeeEntity getBoard(@PathParam("id") Long id) throws RestException, BoardNotFoundException {
        try{
            return employeeService.GetBoard(id);
        } catch (HibernateException e){
            throw new RestException("Error reading board", e);
        }
    }
}
