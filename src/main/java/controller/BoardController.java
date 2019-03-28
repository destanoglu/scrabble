package controller;

import exception.BoardNotFoundException;
import exception.RestException;
import model.Board;
import model.Move;
import model.MoveDirection;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import responseModel.CreationResponse;
import service.BoardService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/board")
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    @Autowired private BoardService boardService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public CreationResponse createBoard() throws RestException {
        try {
            Map<Integer, List<Move>> moves = new HashMap<Integer, List<Move>>();
            moves.put(1, Arrays.asList(
                    new Move(MoveDirection.Horizontal, new Point(1, 1), "ali"),
                    new Move(MoveDirection.Vertical, new Point(1, 1), "anne")));

            Board theboard = new Board(1L, moves);

            Long id = boardService.AddBoard(theboard);
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
        } catch (HibernateException e){
            throw new RestException("Error listing boards", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Board getBoard(@PathParam("id") Long id) throws RestException, BoardNotFoundException {
        try{
            return boardService.GetBoard(id);
        } catch (HibernateException e){
            throw new RestException("Error reading board", e);
        }
    }
}
