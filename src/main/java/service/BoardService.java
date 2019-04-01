package service;

import exception.BoardNotFoundException;
import model.Board;
import model.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.BoardRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    @Autowired private BoardRepository boardRepository;

    public Long AddBoard() throws IOException {
        return boardRepository.CreateBoard();
    }

    public List<Board> ListBoards() throws IOException, ClassNotFoundException {
        List<Board> boards = boardRepository.ListBoards();
        return boards;
    }

    public Board GetBoard(Long id) throws BoardNotFoundException, IOException, ClassNotFoundException {
        return boardRepository.GetBoard(id);
    }

    public Board Deactivate(Long id) throws BoardNotFoundException, IOException, ClassNotFoundException {
        Board board = boardRepository.GetBoard(id);
        board.setActivityStatus(false);
        boardRepository.UpdateBoard(board);
        return board;
    }

    public Map<Integer, List<Move>> SequenceMoves(Long id, Integer sequence) throws BoardNotFoundException, IOException, ClassNotFoundException {
        Board board = boardRepository.GetBoard(id);
        Map<Integer, List<Move>> sequentialMoves = new HashMap<>();
        for (int i = 0; i < sequence; ++i){
            List<Move> sequenceMoves = board.getMoves().get(i);
            if (sequenceMoves == null){
                break;
            }
            sequentialMoves.put(i, sequenceMoves);
        }
        return sequentialMoves;
    }

}
