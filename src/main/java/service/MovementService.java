package service;

import exception.BoardNotFoundException;
import exception.MovementException;
import model.Board;
import model.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.BoardRepository;

import java.io.IOException;
import java.util.List;

@Service
public class MovementService {
    @Autowired private BoardRepository boardRepository;

    @Autowired private List<MoveControl> movementControls;

    public Board AddMovement(Long id, List<Move>moves) throws BoardNotFoundException, IOException, ClassNotFoundException, MovementException {
        Board board = boardRepository.GetBoard(id);

        Integer sequences = board.getMoves().size();
        for (Move move:moves) {
            move.setMainSequence(sequences);
            board.AddMove(sequences, move);

            for (MoveControl ctrl: movementControls){
                ctrl.Control(board, move);
            }
        }

        boardRepository.UpdateBoard(board);
        return board;
    }
}
