package responseModel;

import model.BoardField;
import model.Move;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardResponse implements Serializable {

    public Long boardId;
    public Map<Point, BoardField> instance;
    public Map<Integer, List<MoveResponse>> moves;
    public boolean activityStatus;

    public BoardResponse(Long boardId, Map<Point, BoardField> instance, Map<Integer, List<Move>> moves, boolean activityStatus) {
        this.boardId = boardId;
        this.instance = instance;
        this.activityStatus = activityStatus;
        this.moves = new HashMap<>();
        moves.forEach((mainSequence, moveList) -> {
            this.moves.put(mainSequence,
                    moveList.stream().map(move -> new MoveResponse(move.getDirection(),
                            move.getInitialPoint(),
                            move.getText(),
                            move.getMainSequence(),
                            move.getInnersequence())).collect(Collectors.toList()));
        });
    }
}
