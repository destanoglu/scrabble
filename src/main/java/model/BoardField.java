package model;

import java.io.Serializable;

public class BoardField implements Serializable {
    private MoveDirection direction;
    private Character character;
    private Integer usageCount;

    public BoardField(MoveDirection direction, Character character, Integer usageCount) {
        this.direction = direction;
        this.character = character;
        this.usageCount = usageCount;
    }

    public BoardField(MoveDirection direction, Character character) {
        this.direction = direction;
        this.character = character;
        this.usageCount = 0;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public Character getCharacter() {
        return character;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void incrementUsageCount(){
        ++usageCount;
    }
}
