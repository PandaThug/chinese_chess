package myproject.cnxq;

import myproject.cnxq.core.ChessBoardItem;
import myproject.cnxq.core.WinEnum;
import myproject.cnxq.core.WalkState;
import myproject.cnxq.core.Move;
import myproject.cnxq.walk.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对于是否获胜，将军等等情况狂的判断，发生于每一次走棋之后
 */

public class XqWalkState implements WalkState, Constants {

    private XqChessBoard chessBoard;

    public XqWalkState(XqChessBoard chessBoard) {
        this.chessBoard = chessBoard;

        registerWalker(new MaWalker(), redMa, blackMa);
        registerWalker(new XiangWalker(), redXiang, blackXiang);
        registerWalker(new ShiWalker(), redShi, blackShi);
        registerWalker(new CheWalker(), redChe, blackChe);
        registerWalker(new RedZuWalker());
        registerWalker(new BlackZuWalker());
        registerWalker(new JiangWalker(), redJiang, blackJiang);
        registerWalker(new PaoWalker(), redPao, blackPao);
    }


    @Override
    public XqChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(XqChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    //判断某个棋子是否可以移动
    @Override
    public boolean canMove(boolean red, Move move) {
        int x = move.getFrom().getX();
        int y = move.getFrom().getY();
        int px = move.getTo().getX();
        int py = move.getTo().getY();

        if (!chessBoard.inBoard(x, y) || !chessBoard.inBoard(px, py)) {
            return false;
        }
        if (move.getFrom().equals(move.getTo())) {
            return false;
        }
        int state = chessBoard.getState(x, y);

        if (state == EMPTY || (red ? isBlack(state) : isRed(state))) {
            return false;
        }

        int targetState = chessBoard.getState(px, py);
        if (targetState != EMPTY && (red ? isRed(targetState) : isBlack(targetState))) {
            return false;
        }

        Walker walker = walkerMap.get(state);
        return walker.canMove(red, move);
    }


    public int[] getUcpcSquares() {
        return chessBoard.getUcpcSquares();
    }

    @Override
    public boolean canSelect(boolean red, int x, int y) {

        if (!chessBoard.inBoard(x, y)) {
            return false;
        }
        int state = chessBoard.getState(x, y);
        if (state == EMPTY) {
            return false;
        }

        if (red) {
            return isRed(state);
        } else {
            return isBlack(state);
        }
    }


    @Override
    public List<Move> getAllMove(boolean red, int x, int y) {
        if (!chessBoard.inBoard(x, y)) {
            return new ArrayList<>();
        }
        int state = chessBoard.getState(x, y);
        Walker walker = walkerMap.get(state);
        List<Move> move = walker.getAllMove(red, x, y);
        List<Move> moveList = new ArrayList<>();
        for (Move m : move) {
            int s = chessBoard.getState(m.getTo().getX(), m.getTo().getY());
            if (chessBoard.inBoard(m.getFrom().getX(), m.getFrom().getY()) && chessBoard.inBoard(m.getTo().getX(), m.getTo().getY()) && !isSelfState(s, red)) {
                moveList.add(m);
            }
        }
        return moveList;
    }


    //判断棋子的颜色非常简单——(state & 8) != 0 表示红方的棋子，(state & 16) != 0 表示黑方的棋子。
    public static boolean isRed(int state) {
        return state != EMPTY && (state & 8) != 0;
    }

    public static boolean isBlack(int state) {
        return state != EMPTY && (state & 16) != 0;
    }

    public static boolean isSelfState(int state, boolean red) {
        return state != EMPTY && (red ? isRed(state) : isBlack(state));
    }
    //这里存放了剩余棋子的每一个Walker对象
    private Map<Integer, Walker> walkerMap = new HashMap<>();

    public void registerWalker(Walker walker) {
        walkerMap.put(walker.getState(), walker);
        walker.setState(this);
    }

    public void registerWalker(Walker walker, int... states) {
        for (int state : states) {
            walkerMap.put(state, walker);
        }
        walker.setState(this);
    }

    //red方走了一步后，判断是否将军的情况
    @Override
    public WinEnum hasWin(boolean red) {
        int selfX = red ? chessBoard.getShuai().getX() : chessBoard.getJiang().getX();
        int selfY = red ? chessBoard.getShuai().getY() : chessBoard.getJiang().getY();
        int enemyX = red ? chessBoard.getJiang().getX() : chessBoard.getShuai().getX();
        int enemyY = red ? chessBoard.getJiang().getY() : chessBoard.getShuai().getY();
        if (isJiangJun(red, selfX, selfY)) {
            return red ? WinEnum.RED : WinEnum.BLACK;
        }
        if (isJiangJun(!red, enemyX, enemyY)) {
            boolean die = true; //是否将死
            for (ChessBoardItem item : chessBoard) {
                if (red ? isBlack(item.getState()) : isRed(item.getState())) { //找到自己的棋子
                    List<Move> moveList = getAllMove(!red, item.getX(), item.getY());
                    for (Move move : moveList) {

                        int fromState = chessBoard.getState(move.getFrom().getX(), move.getFrom().getY());
                        int toState = chessBoard.setState(move.getTo().getX(), move.getTo().getY(), fromState);
                        if (!isJiangJun(!red, enemyX, enemyY)) {
                            die = false;
                        }
                        chessBoard.setState(move.getFrom().getX(), move.getFrom().getY(), fromState);
                        chessBoard.setState(move.getTo().getX(), move.getTo().getY(), toState);
                        if (!die) break;
                    }
                    if (!die) break;
                }
            }
            if (die) {
                return red ? WinEnum.BLACK_KILL : WinEnum.RED_KILL;
            } else {
                return red ? WinEnum.BLACK : WinEnum.RED;
            }
        }
        //如果所有人只剩下象士帅，则和棋
        boolean draw = true;
        for (ChessBoardItem chessBoardItem : chessBoard) {
            int state = chessBoardItem.getState();
            if (state != EMPTY && state != redXiang && state != blackXiang && state != redShi && state != blackShi && state != blackJiang && state != redJiang) {
                draw = false;
                break;
            }
        }
        if (draw) {
            return WinEnum.DRAW;
        }
        return WinEnum.NO;
    }


    /*判断是否将军x,y为将或者帅的坐标.
    * 其实判断帅(将)是否被将军的过程并不复杂：
    * (1) 假设帅(将)是车，判断它是否能吃到对方的车和将(帅)(中国象棋中有将帅不能对脸的规则)；
    * (2) 假设帅(将)是炮，判断它是否能吃到对方的炮；
    * (3) 假设帅(将)是马，判断它是否能吃到对方的马，需要注意的是，帅(将)此时使用的马腿不同
    * (4) 假设帅(将)是过河的兵(卒)，判断它是否能吃到对方的卒(兵)。
    */
    private boolean isJiangJun(boolean red, int x, int y) {

        Walker cheWalker = walkerMap.get(redChe); //假设帅(将)是车
        List<Move> cheMoves = cheWalker.getAllMove(red, x, y);
        for (Move cheMove : cheMoves) {
            int state = chessBoard.getState(cheMove.getTo().getX(), cheMove.getTo().getY());
            if (state == (red ? blackChe : redChe) || state == (red ? blackJiang : redJiang)) {
                return true;
            }
        }
        Walker paoWalker = walkerMap.get(redPao); //假设帅(将)是炮
        List<Move> paoMoves = paoWalker.getAllMove(red, x, y);
        for (Move paoMove : paoMoves) {
            int state = chessBoard.getState(paoMove.getTo().getX(), paoMove.getTo().getY());
            if (state == (red ? blackPao : redPao)) {
                return true;
            }
        }

        List<Move> maMoves = jjMaWalker.getAllMove(red, x, y); //假设帅(将)是马
        for (Move maMove : maMoves) {
            int state = chessBoard.getState(maMove.getTo().getX(), maMove.getTo().getY());
            if (state == (red ? blackMa : redMa)) {
                return true;
            }
        }

        //假设帅(将)是过河的兵(卒)
        List<Move> zuMoves = (red ? jjRedZuWalker : jjBlackZuWalker).getAllMove(red, x, y);
        for (Move zuMove : zuMoves) {
            int state = chessBoard.getState(zuMove.getTo().getX(), zuMove.getTo().getY());
            if (state == (red ? blackZu : redZu)) {
                return true;
            }
        }
        return false;
    }

    private Walker jjMaWalker = new JiangJunMaWalker(this);
    private Walker jjRedZuWalker = new JiangJunRedZuWalker(this);
    private Walker jjBlackZuWalker = new JiangJunBlackZuWalker(this);


    //与马走法对立的走法，也就是判断马能否吃到将/帅
    private static class JiangJunMaWalker extends SlashWalker {
        JiangJunMaWalker(WalkState walkState) {
            super(
                    new int[][]{{-33, -31}, {-18, 14}, {-14, 18}, {31, 33}},
                    new int[]{-17, -15, 15, 17} //只是马腿的偏移不同
            );
            setState(walkState);
        }
    }

    private static class JiangJunRedZuWalker extends RedZuWalker {

        JiangJunRedZuWalker(WalkState walkState) {
            setState(walkState);
        }

        @Override
        public boolean isCrossRiver(int x, int y, boolean red) {
            return true;
        }
    }

    private static class JiangJunBlackZuWalker extends BlackZuWalker{
        JiangJunBlackZuWalker(WalkState walkState){
            setState(walkState);
        }

        @Override
        public boolean isCrossRiver(int x, int y, boolean red) {
            return true;
        }
    }



}
