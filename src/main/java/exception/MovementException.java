package exception;

import model.Move;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MovementException extends Exception implements ExceptionMapper<MovementException> {
    public MovementException() {
    }

    public MovementException(Move move, String message) {
        super(move.toString() + " " + message);
    }

    public MovementException(Move move, String message, Throwable cause) {
        super((move.toString() + " " + message + " : " + cause.getMessage()), cause);
    }

    public Response toResponse(MovementException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ex.getMessage())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
