package persistence.model;

import model.Move;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
            parameters = { @org.hibernate.annotations.Parameter( name = "MOVES", value = "java.util.HashMap" ) })
    private Map<Integer, List<Move>> moves;

    public Long getBoardId()
    {
        return boardId;
    }

    public void setBoardId(Long employeeId)
    {
        this.boardId = employeeId;
    }

    public Map<Integer, List<Move>> getMoves() {
        return moves;
    }

    public void setMoves(Map<Integer, List<Move>> board) {
        this.moves = board;
    }

    @Override
    public String toString() {
        return "Board id " + getBoardId();
    }

}