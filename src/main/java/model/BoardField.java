package model;

public class BoardField {
    private MoveDirection direction;
    private Character character;

    public BoardField(MoveDirection direction, Character character) {
        this.direction = direction;
        this.character = character;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public Character getCharacter() {
        return character;
    }
}
