package exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BoardNotFoundException extends Exception implements ExceptionMapper<BoardNotFoundException> {
    public BoardNotFoundException() {
    }

    public BoardNotFoundException(String message) {
        super(message);
    }

    public BoardNotFoundException(String message, Throwable cause) {
        super(message + " : " + cause.getMessage(), cause);
    }

    public Response toResponse(BoardNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ex.getMessage())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
