package responseModel;

import java.io.Serializable;

public class WordResponse implements Serializable {
    public String word;
    public Integer score;

    public WordResponse(String word, Integer score) {
        this.word = word;
        this.score = score;
    }
}
