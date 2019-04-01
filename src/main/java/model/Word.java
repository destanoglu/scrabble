package model;

public class Word {
    private String word;
    private Integer score;

    public Word(String word, Integer score) {
        this.word = word;
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public Integer getScore() {
        return score;
    }
}
