package service;

import exception.BoardNotFoundException;
import model.Board;
import model.BoardField;
import model.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.BoardRepository;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class BoardScoringService {

    private static final Logger log = LoggerFactory.getLogger(BoardScoringService.class);
    private Map<Character, Integer> scoreMap;
    @Autowired private BoardBorder boardBorder;

    @Autowired private BoardRepository boardRepository;

    public BoardScoringService(){
        scoreMap = new HashMap<>();
        scoreMap.put('a', 1);
        scoreMap.put('b', 3);
        scoreMap.put('c', 4);
        scoreMap.put('ç', 4);
        scoreMap.put('d', 3);
        scoreMap.put('e', 1);
        scoreMap.put('f', 7);
        scoreMap.put('g', 5);
        scoreMap.put('ğ', 8);
        scoreMap.put('h', 5);
        scoreMap.put('ı', 2);
        scoreMap.put('i', 1);
        scoreMap.put('j', 10);
        scoreMap.put('k', 1);
        scoreMap.put('l', 1);
        scoreMap.put('m', 2);
        scoreMap.put('n', 1);
        scoreMap.put('o', 2);
        scoreMap.put('ö', 7);
        scoreMap.put('p', 5);
        scoreMap.put('r', 1);
        scoreMap.put('s', 2);
        scoreMap.put('ş', 4);
        scoreMap.put('t', 1);
        scoreMap.put('u', 2);
        scoreMap.put('ü', 3);
        scoreMap.put('v', 7);
        scoreMap.put('y', 3);
        scoreMap.put('z', 4);
    }

    public List<Word> Score(Long id) throws BoardNotFoundException, IOException, ClassNotFoundException {
        List<Word> scores = new ArrayList<>();

        Board board = boardRepository.GetBoard(id);
        Map<Point, BoardField> instance = board.getBoardInstance();

        scanHorizontalWords(scores, instance);
        scanVerticalWords(scores, instance);

        return scores;
    }

    private void scanHorizontalWords(List<Word> scores, Map<Point, BoardField> instance) {
        for (int i = 0; i < boardBorder.getSize(); ++i){
            String text = "";
            for (int j = 0; j < boardBorder.getSize(); ++j){
                Point loc = new Point(i, j);
                BoardField field = instance.get(loc);
                if (field != null){
                    text += field.getCharacter();
                }
                else{
                    if (text.length() > 1){
                        scores.add(calculateScore(text));
                    }
                    text = "";
                }
            }
        }
    }

    private void scanVerticalWords(List<Word> scores, Map<Point, BoardField> instance) {
        for (int j = 0; j < boardBorder.getSize(); ++j){
            String text = "";
            for (int i = 0; i < boardBorder.getSize(); ++i){
                Point loc = new Point(i, j);
                BoardField field = instance.get(loc);
                if (field != null){
                    text += field.getCharacter();
                }
                else{
                    if (text.length() > 1){
                        scores.add(calculateScore(text));
                    }
                    text = "";
                }
            }
        }
    }

    private Word calculateScore(String text){
        Locale trlocale= Locale.forLanguageTag("tr-TR");
        Integer score = 0;

        text = text.toLowerCase(trlocale).trim();
        for(int i = 0; i < text.length(); ++i){
            Integer charScore = scoreMap.get(text.charAt(i));
            if (charScore != null){
                score += charScore;
            }
            else{
                log.error("Unexpected character for scoring : " + text.charAt(i));
            }
        }

        return new Word(text, score);
    }
}
