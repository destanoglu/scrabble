package model;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
    private Long boardId;
    private Map<Point, BoardField> instance;
    private Map<Integer, List<Move>> moves;

    public Board(Long boardId, List<Move> moves, Map<Point, BoardField> instance) {
        this.boardId = boardId;
        this.instance = instance;
        this.moves = OrderMoves(moves);
    }

    public Board(List<Move> moves, Map<Point, BoardField> instance) {
        this.instance = instance;
        this.moves = OrderMoves(moves);
    }

    public Board(){
        this.instance = new HashMap<>();
        this.moves = new HashMap<>();
    }

    public Long getBoardId() {
        return boardId;
    }

    public Map<Point, BoardField> getBoardInstance() {
        return instance;
    }

    public Map<Integer, List<Move>> getMoves() {
        return moves;
    }

    public List<Move> UnstructuredMoves(){
        List<Move> unorderedMoves = new ArrayList<>();
        for (Map.Entry<Integer, List<Move>> entry : moves.entrySet()) {
            unorderedMoves.addAll(entry.getValue());
        }
        return unorderedMoves;
    }

    private Map<Integer, List<Move>> OrderMoves(List<Move> moves){
        Map<Integer, List<Move>> orderedMoves = new HashMap<Integer, List<Move>>();

        boolean doesSequenceExist = true;
        Integer sequence = 1;
        while(doesSequenceExist){
            Integer finalSequence = sequence;
            List<Move> sequenceMoves = moves.stream().filter(move -> move.getMainSequence().equals(finalSequence)).collect(Collectors.toList());
            if (sequenceMoves.size() > 0){
                Collections.sort(sequenceMoves);
                orderedMoves.put(sequence, sequenceMoves);
                ++sequence;
            }
            else{
                doesSequenceExist = false;
            }
        }

        return orderedMoves;
    }

    public void AddMove(Integer sequence, Move move){
        if (moves.containsKey(sequence)){
            moves.get(sequence).add(move);
        }
        else {
            moves.put(sequence, Arrays.asList(move));
        }
    }

    private void applyMoveToBoardInstance(Move move){
        Integer offset = 0;
        for (Character ch : move.getText().toCharArray()){
            Point location = new Point(move.getInitialPoint());
            if (move.getDirection().equals(MoveDirection.Horizontal)){
                location.move(location.x, location.y + offset);
                this.instance.put(location, new BoardField(move.getDirection(), ch));
                ++offset;
            }
        }
    }
}
