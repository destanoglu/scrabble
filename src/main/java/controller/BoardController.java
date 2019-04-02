package controller;

import exception.BoardNotFoundException;
import exception.MovementException;
import exception.RestException;
import model.Board;
import model.Word;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import requestModel.MoveRequest;
import responseModel.BoardResponse;
import responseModel.CreationResponse;
import responseModel.MoveResponse;
import responseModel.WordResponse;
import service.BoardScoringService;
import service.BoardService;
import service.MovementService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/board")
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    @Autowired private BoardService boardService;
    @Autowired private MovementService movementService;
    @Autowired private BoardScoringService boardScoringService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBoard() throws RestException, IOException {
        try {
            Long id = boardService.AddBoard();
            log.info("A board instance is created with id = " + id);
            return Response.status(Response.Status.CREATED).entity(new CreationResponse(id)).build();
        } catch (HibernateException e){
            log.error(e.toString());
            throw new RestException("Error adding board", e);
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BoardResponse> listBoards() throws RestException {
        try{
            log.info("Boards are listed");
            return boardService.ListBoards().stream()
                    .map(board -> new BoardResponse(board.getBoardId(), board.getBoardInstance(), board.getMoves(), board.getActivityStatus()))
                    .collect(Collectors.toList());
        } catch (HibernateException | IOException | ClassNotFoundException e){
            log.error(e.toString());
            throw new RestException("Error listing boards", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BoardResponse getBoard(@PathParam("id") Long id) throws RestException, BoardNotFoundException {
        try{
            log.info("Board " + id + " is requested");
            Board theBoard = boardService.GetBoard(id);
            return new BoardResponse(theBoard.getBoardId(), theBoard.getBoardInstance(), theBoard.getMoves(), theBoard.getActivityStatus());
        } catch (HibernateException | IOException | ClassNotFoundException e){
            log.error(e.toString());
            throw new RestException("Error reading board", e);
        }
    }

    @POST
    @Path("/{id}/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BoardResponse addMovement(@PathParam("id") Long id, List<MoveRequest> moveRequests) throws BoardNotFoundException, RestException, MovementException {
        try{
            log.info("A new move is played on the board " + id);
            List<model.Move> modelMoves = moveRequests.stream().map(moveRequest -> moveRequest.MoveModel()).collect(Collectors.toList());
            Board theBoard = movementService.AddMovement(id, modelMoves);
            return new BoardResponse(theBoard.getBoardId(), theBoard.getBoardInstance(), theBoard.getMoves(), theBoard.getActivityStatus());
        } catch (HibernateException | IOException | ClassNotFoundException e){
            log.error(e.toString());
            throw new RestException("Error modifying board", e);
        }
    }

    @POST
    @Path("/{id}/deactivate")
    @Produces(MediaType.APPLICATION_JSON)
    public BoardResponse deactivateBoard(@PathParam("id") Long id) throws RestException, BoardNotFoundException {
        try{
            log.info("Board " + id + " is deactivated");
            Board theBoard = boardService.Deactivate(id);
            return new BoardResponse(theBoard.getBoardId(), theBoard.getBoardInstance(), theBoard.getMoves(), theBoard.getActivityStatus());
        } catch (HibernateException | IOException | ClassNotFoundException e){
            log.error(e.toString());
            throw new RestException("Error disabling board", e);
        }
    }

    @GET
    @Path("/{id}/words")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WordResponse> getWords(@PathParam("id") Long id) throws RestException, BoardNotFoundException {
        try{
            log.info("Listing words of board " + id);
            List<Word> words = boardScoringService.Score(id);
            return words.stream().map(word -> new WordResponse(word.getWord(), word.getScore())).collect(Collectors.toList());
        } catch (HibernateException | IOException | ClassNotFoundException e){
            log.error(e.toString());
            throw new RestException("Error modifying board", e);
        }
    }

    @GET
    @Path("/{id}/moves/{sequence}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Integer, List<MoveResponse>> getMoves(@PathParam("id") Long id, @PathParam("sequence") Integer sequence) throws RestException, BoardNotFoundException {
        try{
            log.info("Listing movements on the board " + id);
            Map<Integer, List<MoveResponse>> response = new HashMap<>();
            boardService.SequenceMoves(id, sequence).forEach((mainSequence, moveList) -> {
                response.put(mainSequence,
                        moveList.stream().map(move -> new MoveResponse(move.getDirection(),
                                move.getInitialPoint(),
                                move.getText(),
                                move.getMainSequence(),
                                move.getInnersequence())).collect(Collectors.toList()));
            });

            return response;
        } catch (HibernateException | IOException | ClassNotFoundException e){
            log.error(e.toString());
            throw new RestException("Error modifying board", e);
        }
    }
}
