package lists;

public class CircularDoublyLinkedList<T> {
    private int size;
    private Node<T> head, tail;

    public CircularDoublyLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public int size() {
        return this.size;
    }

    public void add(final T data) {
        this.size++;

        Node<T> newNode = new Node<>(data);

        if (this.head == null) {
            this.head = newNode;
        } else {
            this.tail.next = newNode;
            newNode.prev = this.tail;
        }

        this.tail = newNode;

        this.tail.next = this.head;
        this.head.prev = this.tail;
    }

    public void add(final T value, final int index) {
        if (index >= 0) {
            Node<T> newNode = new Node<>(value);

            if (index == 0) {
                this.size++;

                newNode.next = this.head;
                if (this.head != null) {
                    this.head.prev = newNode;
                }

                this.head = newNode;
                if (this.tail == null) {
                    this.tail = newNode;
                }

                this.tail.next = this.head;
                this.head.prev = this.tail;

            } else {
                Node<T> current = this.head;
                for (int i = 0; i < index - 1 && current != null; i++) {
                    current = current.next;
                }

                if (current != null) {
                    this.size++;

                    newNode.next = current.next;
                    newNode.prev = current;
                    current.next = newNode;

                    if (newNode.next != null) {
                        newNode.next.prev = newNode;
                    } else {
                        this.tail = newNode;
                    }

                    this.tail.next = this.head;
                    this.head.prev = this.tail;
                }
            }
        }
    }

    public void remove(final T value) {
        Node<T> current = this.head;

        while (current != null) {
            if (current.data == value) {
                this.size--;

                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    this.head = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    this.tail = current.prev;
                }

                this.tail.next = this.head;
                this.head.prev = this.tail;

                break;

            } else {
                current = current.next;
            }
        }
    }

    public void remove(final int index) {
        if (index >= 0) {
            Node<T> current = this.head;

            for (int i = 0; i < index && current != null; i++) {
                current = current.next;
            }

            if (current != null) {
                this.size--;

                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    this.head = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    this.tail = current.prev;
                }

                this.tail.next = this.head;
                this.head.prev = this.tail;
            }
        }
    }

    public int indexOf(final T value) {
        Node<T> current = this.head;
        int index = 0;

        while (current != null) {
            if (current.data == value) {
                return index;
            }

            current = current.next;
            index++;
        }

        return -1;
    }

    public int lastIndexOf(final T value) {
        Node<T> current = this.tail;
        int index = this.size - 1;

        while (current != null) {
            if (current.data == value) {
                return index;
            }

            current = current.prev;
            index--;
        }

        return -1;
    }

    public T get(final int index) {
        if (index >= 0) {
            Node<T> current = this.head;

            for (int i = 0; i < index && current != null; i++) {
                current = current.next;
            }

            if (current == null) {
                return null;
            }

            return current.data;
        }

        return null;
    }

    public T getFirst() {
        if (this.head != null) {
            return this.head.data;
        } else {
            return null;
        }
    }

    public T getLast() {
        if (this.tail != null) {
            return this.tail.data;
        } else {
            return null;
        }
    }
}