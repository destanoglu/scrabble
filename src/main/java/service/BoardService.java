package service;

import exception.BoardNotFoundException;
import model.Board;
import model.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.BoardRepository;
import persistence.model.BoardEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired private BoardRepository boardRepository;

    public Long AddBoard(Board board) throws IOException {
        BoardEntity entity = new BoardEntity();
        entity.setStructuredMoves(board.UnstructuredMoves());
        entity.setStructuredInstance(board.getBoardInstance());
        return boardRepository.AddBoard(entity);
    }

    public List<Board> ListBoards() throws IOException, ClassNotFoundException {
        List<BoardEntity> entities = boardRepository.ListBoards();
        List<Board> boards = new ArrayList<Board>();
        for (int i = 0; i < entities.size(); ++i){
            BoardEntity entity = entities.get(i);
            boards.add(new Board(entity.getBoardId(), entity.getStructuredMoves(), entity.getStructuredInstance()));
        }
        return boards;
    }

    public Board GetBoard(Long id) throws BoardNotFoundException, IOException, ClassNotFoundException {
        BoardEntity entity = boardRepository.GetBoard(id);
        return new Board(entity.getBoardId(), entity.getStructuredMoves(), entity.getStructuredInstance());
    }

    public Board AddMovement(Long id, List<Move>moves) throws BoardNotFoundException, IOException, ClassNotFoundException {
        BoardEntity entity = boardRepository.GetBoard(id);
        Board board = new Board(entity.getBoardId(), entity.getStructuredMoves(), entity.getStructuredInstance());
        Integer sequences = board.getMoves().size() + 1;
        for (Move move:moves) {
            move.setMainSequence(sequences);
            board.AddMove(sequences, move);
        }

        entity.setStructuredMoves(board.UnstructuredMoves());
        entity.setStructuredInstance(board.getBoardInstance());
        boardRepository.UpdateBoard(entity);
        return board;
    }
}
