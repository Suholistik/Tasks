package dominoes.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {


    private List<Domino> deck;// Список доминошек в колоде

    public Deck() {
        deck = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            for (int k = i; k <= 6; k++) {
                Domino newDom = new Domino(i, k);
                deck.add(newDom);
            }
        }
        // shuffle list
        Collections.shuffle(deck);
    }

    public List<Domino> getDeck() {
        return deck;
    }

    public void setDeck(List<Domino> deck) {
        this.deck = deck;
    }

    public Domino popDomino() {
        if (!deck.isEmpty()) {
            return deck.remove(0);
        }
        return null;
    }
}
