import lists.CircularDoublyLinkedList;
import lists.DoublyLinkedList;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(1);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        final CircularDoublyLinkedList<Integer> circularList = new CircularDoublyLinkedList<>();
        circularList.add(1);
        circularList.add(4);
        circularList.add(8);
        circularList.add(8);

        for (int i = 0; i < circularList.size(); i++) {
            System.out.println(circularList.get(i));
        }

    }

}