public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextLast = 0;
        nextFirst = 7;

    }
    private void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        for (int i = 0; items[(nextFirst + 1 + i) % items.length] != null; i++) {
            a[i % a.length] = items[(i + nextFirst + 1) % items.length];
        }
        nextFirst = a.length - 1;
        nextLast = this.size;
        items = a;
    }

    public void addFirst(T item) {
        this.items[this.nextFirst] = item;
        size = size + 1;
        this.nextFirst = this.nextFirst - 1;
        if (this.nextFirst < 0) {
            this.nextFirst = items.length - 1;
        }
        if (this.size == items.length) {
            resize(this.size * 2);
        }
    }
    public void addLast(T item) {
        this.items[this.nextLast] = item;
        size = size + 1;
        this.nextLast = this.nextLast + 1;
        if (this.nextLast > this.size - 1) {
            this.nextLast = 0;
        }
        if (this.size == items.length) {
            resize(this.size * 2);
        }
    }
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }
    public int size() {
        return this.size;
    }
    public void printDeque() {
        for (int i = 0; i < this.size; i++) {
            System.out.print(items[i] + " ");
        }
    }
    public T get(int index) {
        return this.items[(index + (this.nextFirst + 1) % items.length)];
    }
    public T removeFirst() {
        if (this.size == 0) {
            return null;
        }
        this.nextFirst = (nextFirst + 1);
        T value = this.items[nextFirst];
        items[nextFirst] = null;
        this.size = this.size - 1;
        if ((double) this.size / (double) items.length < 0.3) {
            resize(items.length / 2);
        }
        if (nextFirst > items.length - 1) {
            this.nextFirst = 0;
        }
        return value;

    }
    public T removeLast() {
        if (this.size == 0) {
            return null;
        }
        this.nextLast = this.nextLast - 1;
        T lastvalue = this.items[nextLast];
        items[nextLast] = null;
        this.size = this.size - 1;
        if ((double) this.size / (double) items.length < 0.3) {
            resize(items.length / 2);
        }
        if (nextLast < 0) {
            this.nextLast = items.length - 1;
        }
        return lastvalue;
    }
}
