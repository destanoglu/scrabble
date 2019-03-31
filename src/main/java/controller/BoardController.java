package controller;

import exception.BoardNotFoundException;
import exception.MovementException;
import exception.RestException;
import model.Board;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import requestModel.Move;
import responseModel.CreationResponse;
import service.BoardService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/board")
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    @Autowired private BoardService boardService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public CreationResponse createBoard() throws RestException, IOException {
        try {
            Long id = boardService.AddBoard();
            log.info("Created id = " + id);
            return new CreationResponse(id);
        } catch (HibernateException e){
            throw new RestException("Error adding board", e);
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Board> listBoards() throws RestException {
        try{
            return boardService.ListBoards();
        } catch (HibernateException | IOException | ClassNotFoundException e){
            throw new RestException("Error listing boards", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Board getBoard(@PathParam("id") Long id) throws RestException, BoardNotFoundException {
        try{
            return boardService.GetBoard(id);
        } catch (HibernateException | IOException | ClassNotFoundException e){
            throw new RestException("Error reading board", e);
        }
    }

    @POST
    @Path("/{id}/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Board addMovement(@PathParam("id") Long id, List<Move> moves) throws BoardNotFoundException, RestException, MovementException {
        try{
            List<model.Move> modelMoves = moves.stream().map(move -> move.MoveModel()).collect(Collectors.toList());
            return boardService.AddMovement(id, modelMoves);
        } catch (HibernateException | IOException | ClassNotFoundException e){
            throw new RestException("Error modifying board", e);
        }
    }
}
