package myproject.cnxq.walk;

import myproject.cnxq.Constants;
import myproject.cnxq.core.Move;

import java.util.ArrayList;
import java.util.List;
/**
//黑卒的走棋方式（只能向下走，所以要红黑分开）
 */
public class BlackZuWalker extends AbstractWalker {

    @Override
    public int getState() {
        return Constants.blackZu;
    }

    @Override
    public List<Move> getAllMove(boolean red, int x, int y) {
        List<Move> moveList = new ArrayList<>();
        if (isCrossRiver(x, y, red)) {
            if (x > 0) moveList.add(new Move(x, y, x - 1, y));
            if (x < walkState.getChessBoard().getMaxX() - 1) moveList.add(new Move(x, y, x + 1, y));
            if (y < walkState.getChessBoard().getMaxY() - 1) moveList.add(new Move(x, y, x, y + 1));
        } else {
            moveList.add(new Move(x, y, x, y + 1));
        }
        return moveList;
    }

    @Override
    protected boolean canMove(boolean red, int x, int y, int tx, int ty) {
        return getAllMove(red, x, y).contains(new Move(x, y, tx, ty));
    }
}
