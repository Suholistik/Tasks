package dominoes.models;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Domino> dominoInHand;

    private String name;


    public Player(String name) {
        this.dominoInHand = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Domino> getDominoInHand() {
        return dominoInHand;
    }
    public List<Domino> setDominoInHand() {
        return dominoInHand;
    }

    public Domino getDomino(int index) {
        return dominoInHand.get(index);
    }

    public void removeDomino(int index) {
        dominoInHand.remove(index);
    }

    public void addDomino(Domino domino){
        this.dominoInHand.add(domino);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("У игрока:");

        for (Domino d: dominoInHand) {
            str.append(" ").append(d.toString()).append(" ");
        }
        return str.toString();
    }
}
