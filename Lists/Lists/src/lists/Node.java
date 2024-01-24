package lists;

public class Node<T> {
    protected final T data;
    protected Node<T> next, prev;

    public Node(final T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}