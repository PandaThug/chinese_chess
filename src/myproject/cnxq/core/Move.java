package myproject.cnxq.core;


import java.io.Serializable;
/**
    每一个Walker都会调用的Walker类，用于承载从那一个点到那一个点的事件，从form 到 to ，记录信息，便于逻辑判断和处理
*/
public class Move implements Serializable{
    private static final long serialVersionUID = -1158771405035884820L;
    private Point from;
    private Point to;

    public Move(int fromX, int fromY, int toX, int toY) {
        from = new Point(fromX, fromY);
        to = new Point(toX, toY);
    }

    public Move(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }
    //检查两者是否是同一个point
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (from != null ? !from.equals(move.from) : move.from != null) return false;
        return to != null ? to.equals(move.to) : move.to == null;
    }

    //检查该格子是否有被占领
    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
