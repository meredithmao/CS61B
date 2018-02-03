public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextLast = 1;
        nextFirst = 0;

    }
    private void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        System.arraycopy(items, 0 ,a, 0, size);
        items = a;
    }

    private int minusOne(int index) {
        int nextLast = index-1;
        if (nextLast < 0) {
            nextLast += this.len;
        }
        return nextLast;
    }

    private int plusOne(int index) {
        int nextFirst = index+1;
        if (nextFirst > len) {
            nextFirst = nextFirst + (nextFirst % this.len);
        }
        return nextFirst;
    }

    public void addFirst(T item) {
        this.items[this.nextFirst] = item;
        this.nextFirst = minusOne(this.nextFirst);
        if (this.size == items.length) {
            resize(this.size * 2);
            this.nextFirst = items.length-1;
            this.nextLast = this.size;
        }
        size = size + 1;
    }
    public void addLast(T item) {
        this.items[this.nextLast] = item;
        this.nextLast = plusOne(this.nextLast);
        if (this.size == items.length) {
            resize(this.size * 2);
            this.nextFirst = items.length - 1;
            this.nextLast = this.size;
        }
        size = size + 1;
    }
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }
    public int size () {
        return this.size;
    }
    public void printDeque() {
        for(int i = 0; i < this.size; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
    public T get(int index) {
        if (index < this.size) {
            return this.items[index + this.nextFirst + 1];
        } else {
            return null;
        }
    }
    public T removeFirst () {
        if (this.size == 0) {
            return null;
        }
        items initialvalue = this.get(0);
        items[plusOne(this.nextFirst)] = null;
        this.nextFirst = plusOne(this.nextFirst);
        this.size = this.size - 1;

        return initialvalue;

    }
    public T removeLast () {
        if (this.size == 0) {
            return null;
        }
        items lastvalue = this.get(this.size-1);
        items[minusOne((this.nextLast))] = null;
        this.nextLast = minusOne(this.nextLast);
        this.size = this.size - 1;

        return lastvalue;
    }

}
