package dominoes;

import dominoes.models.*;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    public Domino[][] board;
    private Deck deck;
    public boolean firstDominoPlaced;

    private List<Player> players;
    private int currentPlayer = 0;

    public GameEngine(int numPlayers) {
        board = new Domino[28][28];
        deck = new Deck();
        players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            var p = new Player(String.format("Игрок %d", i + 1));
            for (int j = 0; j < 6; j++) {
                p.addDomino(deck.popDomino());
            }
            players.add(p);
        }
    }

    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }

    public Player getCurrentPlayer() {
        if (players.isEmpty())
            return null;
        return players.get(currentPlayer);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayer;
    }

    public int getNumPlayers() {
        return players.size();
    }

    public void removePlayer() {
        players.remove(currentPlayer);
        if(currentPlayer >= players.size()) {
            // Early loop
            currentPlayer = 0;
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void pickDomino() {
        var domino = deck.popDomino();
        if (domino != null) {
            getCurrentPlayer().addDomino(domino);
        }
    }

    public int getDeckSize() {
        return deck.getDeck().size();
    }


    public boolean placeDomino(Domino domino, int x, int y) {
        if(!firstDominoPlaced) {
            board[y][x] = domino;
            firstDominoPlaced = true;
            return true;
        }

        // TOP
        if (y<27) {
            int v = checkCompatibility(board[y+1][x], domino, Domino.Side.TOP);
            if (v!=0) {
                domino.setSide(v>0? Domino.Side.BOTTOM: Domino.Side.TOP);
                board[y][x] = domino;
                return true;
            }
        }

        // BOTTOM
        if (y>0) {
            int v = checkCompatibility(board[y-1][x], domino, Domino.Side.BOTTOM);
            if (v!=0) {
                domino.setSide(v>0? Domino.Side.TOP: Domino.Side.BOTTOM);
                board[y][x] = domino;
                return true;
            }
        }

        // RIGHT
        if (x<27) {
            int v = checkCompatibility(board[y][x+1], domino, Domino.Side.LEFT);
            if (v!=0) {
                domino.setSide(v>0? Domino.Side.RIGHT: Domino.Side.LEFT);
                board[y][x] = domino;
                return true;
            }
        }

        // LEFT
        if (x>0) {
            int v = checkCompatibility(board[y][x-1], domino, Domino.Side.RIGHT);
            if (v!=0) {
                domino.setSide(v>0? Domino.Side.LEFT: Domino.Side.RIGHT);
                board[y][x] = domino;
                return true;
            }
        }

        return false;
    }

    public int checkCompatibility(Domino srcd, Domino newd, Domino.Side side) {
        if (srcd==null)
            return 0;
        // sig()
        int sval;
        if(srcd.getSide() == side) {
            // T-B (T=T)
            sval = srcd.getSideTop();
        } else if(srcd.reverseSide(srcd.getSide()) == side) {
            // T-B (B=B)
            sval = srcd.getSideBottom();
        } else {
            if (srcd.getSideTop()!=srcd.getSideBottom())
                return 0;
            sval = srcd.getSideTop();
        }

        if (sval==newd.getSideTop())
            return 1;
        else if (sval==newd.getSideBottom())
            return -1;
        else
            return 0;
    }
}
