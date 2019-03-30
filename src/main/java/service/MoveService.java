package service;

import model.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveService {
    @Autowired private BoardService boardService;

    public boolean AddMove(Move move){

        return true;
    }
}
