package model;

import exception.MovementException;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private Long boardId;
    private Map<Point, BoardField> instance;
    private Map<Integer, List<Move>> moves;
    private boolean activityStatus;

    public Board(Long boardId, List<Move> moves, Map<Point, BoardField> instance, boolean status ) {
        this.boardId = boardId;
        this.instance = instance;
        this.moves = OrderMoves(moves);
        this.activityStatus = status;
    }

    public Board(List<Move> moves, Map<Point, BoardField> instance) {
        this.instance = instance;
        this.moves = OrderMoves(moves);
        this.activityStatus = true;
    }

    public Board(){
        this.instance = new HashMap<>();
        this.moves = new HashMap<>();
        this.activityStatus = true;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setActivityStatus(boolean activityStatus) {
        this.activityStatus = activityStatus;
    }

    public boolean getActivityStatus(){
        return this.activityStatus;
    }

    public Map<Point, BoardField> getBoardInstance() {
        return instance;
    }

    public Map<Integer, List<Move>> getMoves() {
        return moves;
    }

    private Map<Integer, List<Move>> OrderMoves(List<Move> moves){
        Map<Integer, List<Move>> orderedMoves = new HashMap<Integer, List<Move>>();

        boolean doesSequenceExist = true;
        Integer sequence = 0;
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

    public void AddMove(Integer sequence, Move move) throws MovementException {
        if (activityStatus == false){
            throw new MovementException("Board is disabled");
        }

        if (moves.containsKey(sequence)){
            List<Move> sequenceMoves = moves.get(sequence);
            sequenceMoves.add(move);
        }
        else {
            List<Move> newSequenceMoves = new ArrayList<>();
            newSequenceMoves.add(move);
            moves.put(sequence, newSequenceMoves);
        }
        applyMoveToBoardInstance(move);
    }

    private void applyMoveToBoardInstance(Move move) throws MovementException {
        for (int i = 0; i < move.getText().length(); ++i){
            Point location = new Point(move.getInitialPoint());
            if (move.getDirection().equals(MoveDirection.Horizontal)){
                location.move(location.x, location.y + i);
            }
            else{
                location.move(location.x + i, location.y);
            }
            BoardField existingField = this.instance.get(location);
            if (existingField == null){
                this.instance.put(location, new BoardField(move.getDirection(), move.getText().charAt(i), 1));
            }
            else{
                if (existingField.getCharacter() != move.getText().charAt(i)){
                    throw new MovementException(move, "Conflicting with existing character");
                }
                existingField.incrementUsageCount();
            }
        }
    }
}
