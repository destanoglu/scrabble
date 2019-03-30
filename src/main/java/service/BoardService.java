package service;

import exception.BoardNotFoundException;
import model.Board;
import model.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.BoardRepository;
import persistence.model.BoardEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired private BoardRepository boardRepository;

    public Long AddBoard(Board board){
        BoardEntity entity = new BoardEntity();
        entity.setMoves(board.UnstructuredMoves());
        entity.setInstance(board.getBoardInstance());
        return boardRepository.AddEmployee(entity);
    }

    public List<Board> ListBoards(){
        List<BoardEntity> entities = boardRepository.ListBoards();
        List<Board> boards = new ArrayList<Board>();
        for (int i = 0; i < entities.size(); ++i){
            BoardEntity entity = entities.get(i);
            boards.add(new Board(entity.getBoardId(), entity.getMoves(), entity.getInstance()));
        }
        return boards;
    }

    public Board GetBoard(Long id) throws BoardNotFoundException {
        BoardEntity entity = boardRepository.GetBoard(id);
        return new Board(entity.getBoardId(), entity.getMoves(), entity.getInstance());
    }

    public Board AddMovement(Long id, List<Move>moves) throws BoardNotFoundException {
        BoardEntity entity = boardRepository.GetBoard(id);
        Board board = new Board(entity.getBoardId(), entity.getMoves(), entity.getInstance());
        Integer totalSequences = board.getMoves().size();
        for (Move move:moves) {
            board.AddMove(totalSequences + 1, move);
        }
        entity.setMoves(board.UnstructuredMoves());
        entity.setInstance(board.getBoardInstance());
        boardRepository.UpdateBoard(entity);
        return board;
    }
}
