package dominoes.models;

public class Domino {

    private int sideTop;
    private int sideBottom;


    private Side side;

    public enum Side {TOP, LEFT, RIGHT, BOTTOM}


    public Domino(int sideTop, int sideBottom) {
        this.sideTop = sideTop;
        this.sideBottom = sideBottom;
        this.side = Side.TOP;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public int getSideTop() {
        return sideTop;
    }

    public void setSideTop(int sideTop) {
        this.sideTop = sideTop;
    }


    public int getSideBottom() {
        return sideBottom;
    }

    public void setSideBottom(int sideBottom) {
        this.sideBottom = sideBottom;
    }

    public void reverse() {
        this.sideTop = sideBottom + sideTop;
        this.sideBottom = sideTop - sideBottom;
        this.sideTop = sideTop - sideBottom;
    }

    @Override
    public String toString() {
        return "[" + sideTop + " | " + sideBottom + "]";
    }

    public Side reverseSide(Side side) {
        return switch (side) {
            case TOP -> Side.BOTTOM;
            case LEFT -> Side.RIGHT;
            case RIGHT -> Side.LEFT;
            case BOTTOM -> Side.TOP;
        };
    }
}
