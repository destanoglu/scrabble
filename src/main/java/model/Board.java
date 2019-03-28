package model;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Board {
    private Long boardId;
    private Map<Point, BoardField> boardInstance;
    private Map<Integer, List<Move>> moves;

    public Board(Long boardId, Map<Integer, List<Move>> moves) {
        this.boardId = boardId;
        this.moves = moves;
    }

    public Board(Map<Integer, List<Move>> moves) {
        this.moves = moves;
    }

    public Long getBoardId() {
        return boardId;
    }

    public Map<Point, BoardField> getBoardInstance() {
        return boardInstance;
    }

    public Map<Integer, List<Move>> getMoves() {
        return moves;
    }
}
