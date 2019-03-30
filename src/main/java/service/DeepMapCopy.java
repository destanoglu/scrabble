package service;

import model.BoardField;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DeepMapCopy {
    public static Map<Point, BoardField> Copy(Map<Point, BoardField> original)
    {
        Map<Point, BoardField> copy = new HashMap();
        for (Map.Entry<Point, BoardField> entry : original.entrySet())
        {
            copy.put(new Point(entry.getKey()), new BoardField(entry.getValue().getDirection(), entry.getValue().getCharacter()));
        }
        return copy;
    }
}
