package persistence.model;

import model.BoardField;
import model.Move;

import javax.persistence.*;
import java.awt.*;
import java.io.*;
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

    @Lob
    @Column(name = "moves", columnDefinition="BLOB", length = 999999)
    private byte[] moves;

    @Lob
    @Column(name = "instance", columnDefinition="BLOB", length = 999999)
    private byte[] instance;

    public Long getBoardId()
    {
        return boardId;
    }

    public void setBoardId(Long employeeId)
    {
        this.boardId = employeeId;
    }

    public byte[] getMoves() {
        return moves;
    }

    public void setMoves(byte[] moves) {
        this.moves = moves;
    }

    public void setStructuredMoves(List<Move> moves) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(moves);
        this.moves = byteOut.toByteArray();
    }

    public List<Move> getStructuredMoves() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(this.moves);
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<Move> data = (List<Move>) in.readObject();
        return data;
    }

    public byte[] getInstance() {
        return instance;
    }

    public void setInstance(byte[] instance) {
        this.instance = instance;
    }

    public void setStructuredInstance(Map<Point, BoardField> instance) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(instance);
        this.instance = byteOut.toByteArray();
    }

    public Map<Point, BoardField> getStructuredInstance() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(this.instance);
        ObjectInputStream in = new ObjectInputStream(byteIn);
        Map<Point, BoardField> data = (Map<Point, BoardField>) in.readObject();
        return data;
    }

    @Override
    public String toString() {
        return "Board id " + getBoardId();
    }
}