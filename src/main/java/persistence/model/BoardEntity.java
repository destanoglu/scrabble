package persistence.model;

import model.BoardField;
import model.Move;

import javax.persistence.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Column(name = "activityStatus")
    private Integer activityStatus;

    @Lob
    @Column(name = "moves", columnDefinition="BLOB", length = 999999)
    private byte[] moves;

    @Lob
    @Column(name = "instance", columnDefinition="BLOB", length = 999999)
    private byte[] instance;

    public BoardEntity() throws IOException {
        this.updateInstance(new HashMap<>());
        this.updateActivityStatus(true);
        this.updateMoves(new HashMap<>());
    }

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

    public byte[] getInstance() {
        return instance;
    }

    public void setInstance(byte[] instance) {
        this.instance = instance;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public void updateMoves(Map<Integer, List<Move>> moves) throws IOException {

        List<Move> unorderedMoves = new ArrayList<>();
        for (Map.Entry<Integer, List<Move>> entry : moves.entrySet()) {
            unorderedMoves.addAll(entry.getValue());
        }

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(unorderedMoves);
        this.moves = byteOut.toByteArray();
    }

    public List<Move> getStructuredMoves() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(this.moves);
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<Move> data = (List<Move>) in.readObject();
        return data;
    }

    public void updateInstance(Map<Point, BoardField> instance) throws IOException {
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

    public boolean getStructuredActivityStatus(){
        return activityStatus > 0 ? true : false;
    }

    public void updateActivityStatus(boolean status){
        this.activityStatus = status == true ? 1 : 0;
    }

    @Override
    public String toString() {
        return "Board id " + getBoardId();
    }
}