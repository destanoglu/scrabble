package service;

import exception.BoardNotFoundException;
import model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.BoardRepository;
import persistence.model.BoardEntity;

import java.util.ArrayList;
import java.util.List;

public class BoardService {
    @Autowired private BoardRepository boardRepository;

    public Long AddBoard(Board board){
        BoardEntity entity = new BoardEntity();
        entity.setMoves(board.getMoves());
        return boardRepository.AddEmployee(entity);
    }

    public List<Board> ListBoards(){
        List<BoardEntity> entities = boardRepository.ListBoards();
        List<Board> boards = new ArrayList<Board>();
        for (int i = 0; i < entities.size(); ++i){
            BoardEntity entity = entities.get(i);
            boards.add(new Board(entity.getBoardId(), entity.getMoves()));
        }
        return boards;
    }

    public Board GetBoard(Long id) throws BoardNotFoundException {
        BoardEntity entity = boardRepository.GetBoard(id);
        return new Board(entity.getBoardId(), entity.getMoves());
    }
}
