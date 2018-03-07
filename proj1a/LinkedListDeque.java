public class LinkedListDeque<T> implements Deque<T> {
    private Node sentinel;
    private int size;
    private class Node {
        private Node prev;
        private T item;
        private Node next;

        Node(Node p, T i, Node n) {
            this.prev = p;
            this.item = i;
            this.next = n;
        }
    }
    public LinkedListDeque() {
        sentinel = new Node(sentinel, null, sentinel);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
    public void addFirst(T item) {
        if (this.isEmpty()) {
            this.sentinel.next = new Node(this.sentinel, item, this.sentinel);
            this.sentinel.prev = this.sentinel.next;
        } else {
            this.sentinel.next.prev = new Node(this.sentinel, item, this.sentinel.next);
            this.sentinel.next = this.sentinel.next.prev;
        }
        this.size = this.size + 1;
    }
    public void addLast(T item) {
        /*keep moving through the nodes */
        Node old = this.sentinel.prev;
        Node p = new Node(old, item, this.sentinel);
        old.next = p;
        this.sentinel.prev = p;
        this.size = this.size + 1;
    }
    public boolean isEmpty() {
        return (this.sentinel == this.sentinel.next);
    }
    public int size() {
        return this.size;
    }
    public void printDeque() {
        Node p = this.sentinel.next;
        int count = 0;
        while (count < this.size) {
            System.out.print(p.item.toString() + " ");
            p = p.next;
            count += 1;
        }
    }
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        /* to figure out the first item */
        Node firstitem = this.sentinel.next;
        T itemvalue = firstitem.item;
        this.size = this.size - 1;
        /* move the second node to become the first node */
        this.sentinel.next = firstitem.next;
        firstitem.next.prev = this.sentinel;
        return itemvalue;
    }
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        Node oldNode = this.sentinel.prev;
        T itemvalue = oldNode.item;
        this.size = this.size - 1;
        oldNode.prev.next = this.sentinel;
        this.sentinel.prev = oldNode.prev;
        return itemvalue;
    }
    public T get(int index) {
        Node p = this.sentinel.next;
        if (index > this.size) {
            return null;
        }
        while (index > 0) {
            p = p.next;
            index = index - 1;
        }
        return p.item;
    }
    private T recursiveHelper(int index, Node p) {
        if (index == 0) {
            return p.item;
        } else {
            return recursiveHelper(index - 1, p.next);
        }
    }
    public T getRecursive(int index) {
        return recursiveHelper(index, this.sentinel.next);
    }
}
