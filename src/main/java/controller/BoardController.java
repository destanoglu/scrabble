package controller;

import exception.BoardNotFoundException;
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
//            List<Move> moves = new ArrayList<Move >();
//            Move move1 = new Move(MoveDirection.Horizontal, new Point(1, 1), "ali", 1);
//            move1.setMainSequence(1);
//            Move move2 = new Move(MoveDirection.Vertical, new Point(1, 1), "anne", 2);
//            move2.setMainSequence(1);
//            moves = Arrays.asList(move1, move2);
//
//            Map<Point, BoardField> fields = new HashMap<>();
//            fields.put(new Point(1, 1), new BoardField(MoveDirection.Horizontal, 'a'));
//            fields.put(new Point(1, 2), new BoardField(MoveDirection.Horizontal, 'l'));
//            fields.put(new Point(1, 3), new BoardField(MoveDirection.Horizontal, 'i'));
//            fields.put(new Point(2, 1), new BoardField(MoveDirection.Vertical, 'n'));
//            fields.put(new Point(3, 1), new BoardField(MoveDirection.Vertical, 'n'));
//            fields.put(new Point(4, 1), new BoardField(MoveDirection.Vertical, 'e'));
//
//            Board theBoard = new Board(moves, fields);

            Board theBoard = new Board();
            Long id = boardService.AddBoard(theBoard);
            log.info("Created id = " + id);
            return new CreationResponse(id);
        } catch (HibernateException e){
            throw new RestException("Error adding board", e);
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Board> listBoards() throws RestException, IOException, ClassNotFoundException {
        try{
            return boardService.ListBoards();
        } catch (HibernateException e){
            throw new RestException("Error listing boards", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Board getBoard(@PathParam("id") Long id) throws RestException, BoardNotFoundException, IOException, ClassNotFoundException {
        try{
            return boardService.GetBoard(id);
        } catch (HibernateException e){
            throw new RestException("Error reading board", e);
        }
    }

    @POST
    @Path("/{id}/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Board addMovement(@PathParam("id") Long id, List<Move> moves) throws BoardNotFoundException, RestException, IOException, ClassNotFoundException {
        try{
            List<model.Move> modelMoves = moves.stream().map(move -> move.MoveModel()).collect(Collectors.toList());
            return boardService.AddMovement(id, modelMoves);
        } catch (HibernateException e){
            throw new RestException("Error modifying board", e);
        }
    }
}
