package service;

import exception.MovementException;
import model.Board;
import model.BoardField;
import model.Move;
import model.MoveDirection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MeaningControl implements MoveControl {

    private static final Logger log = LoggerFactory.getLogger(MeaningControl.class);
    private Map<String, Boolean> dictionary;

    public MeaningControl() throws IOException {
        InitializeDictionary();
    }

    @Override
    public void Control(Board board, Move move) throws MovementException {
        EnsureMainSentence(move, board);
        EnsureAffectedSentences(move, board);
    }

    private void EnsureAffectedSentences(Move move, Board board) throws MovementException {
    }

    private void EnsureMainSentence(Move move, Board board) throws MovementException {
        Point initial = move.getInitialPoint();
        Map<Point, BoardField> instance = board.getBoardInstance();
        String text = move.getText();
        Integer horizontalCoeff = move.getDirection() == MoveDirection.Horizontal ? 1 : 0;
        Integer verticalCoeff = move.getDirection() == MoveDirection.Vertical ? 1 : 0;

        // Find the beginning of the sentence
        Integer beginOffset = -1;
        while (true){
            BoardField field = instance.get(new Point(initial.x + (verticalCoeff * beginOffset), initial.y + (horizontalCoeff * beginOffset)));
            if (field == null){
                break;
            }
            text = field.getCharacter() + text;
            --beginOffset;
        }

        Integer endOffset = move.getText().length();
        while (true){
            BoardField field = instance.get(new Point(initial.x + (verticalCoeff * endOffset), initial.y + (horizontalCoeff * endOffset)));
            if (field == null){
                break;
            }
            text += field.getCharacter();
            ++endOffset;
        }

        if (!dictionary.containsKey(text)){
            throw new MovementException(move, "Wrong main sentence : " + text);
        }
    }

    private void InitializeDictionary() throws IOException {
        dictionary = new HashMap<>();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(MeaningControl.class.getResource("/scrabble_turkish_dictionary.txt").getPath()));
            String line = reader.readLine();
            while (line != null){
                if (!dictionary.containsKey(line.trim())){
                    dictionary.put(line.trim(), true);
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            log.error("Dictionary file is missing");
            throw e;
        } catch (IOException e) {
            log.error("Dictionary file read error");
            throw e;
        }
        finally {
            reader.close();
        }
    }
}
