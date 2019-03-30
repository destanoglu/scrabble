package persistence.model;

import model.BoardField;
import model.Move;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="BOARD")
public class BoardEntity implements Serializable
{
    private static final long serialVersionUID = -1798070786993154676L;
    @Id
    @GeneratedValue
    private Long boardId;

    @org.hibernate.annotations.Type(type = "org.hibernate.type.SerializableToBlobType",
            parameters = { @org.hibernate.annotations.Parameter( name = "MOVES", value = "java.util.ArrayList" ) })
    private List<Move> moves;

    @org.hibernate.annotations.Type(type = "org.hibernate.type.SerializableToBlobType",
            parameters = { @org.hibernate.annotations.Parameter( name = "INSTANCE", value = "java.util.HashMap" ) })
    private Map<Point, BoardField> instance;

    public Long getBoardId()
    {
        return boardId;
    }

    public void setBoardId(Long employeeId)
    {
        this.boardId = employeeId;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Map<Point, BoardField> getInstance() {
        return instance;
    }

    public void setInstance(Map<Point, BoardField> instance) {
        this.instance = instance;
    }

    @Override
    public String toString() {
        return "Board id " + getBoardId();
    }
}