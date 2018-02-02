public class ArrayDeque <T> {
    private T[] items;
    private int size;
    private int prev;
    private int next;
    private int len;

    public ArrayDeque () {
        items = (T[]) new Object[8];
        size = 0;
        prev = 1;
        next = 0;
        len = 8;
    }
    private void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        System.arraycopy(items, 0 , a, 0, size);
        items = a;
    }

    private int minusOne(int index) {
        int prev = index -1;
        if (prev < 0) {
            prev += len;
        }
        return prev;
    }

    private int plusOne(int index) {
        int next = index +1;
        if (next > len){
            next = next + (next % len);
        }
        return next;
    }

    public void addFirst(T item){
        if (this.size == items.length){
            resize(this.size * 2);
        }
        items[this.next] = item;
        this.next = minusOne(this.next);
        size = size + 1;
    }
    public void addLast(T item){
        if (this.size == items.length) {
            resize(this.size * 2);

        }
        items[this.prev] = item;
        this.prev = plusOne(this.prev);
        size = size + 1;
    }
    public boolean isEmpty() {
        if (this.size == 0){
            return true;
        }
        return false;
    }
    public int size () {
        return this.size;
    }
    public void printDeque() {
        for(int i = 0; i < this.size; i++) {
            System.out.print(this.get(i) + " ");
        }
    }
    public T removeFirst () {
        if (this.size == 0) {
            return null;
        }
        items initialvalue = this.get(0);
        this.next = plusOne(this.next);
        this.size = this.size - 1;

        return initialvalue;

    }
    public T removeLast () {
        if (this.size == 0) {
            return null;
        }
        items lastvalue = this.get(this.size-1);
        this.prev = minusOne(this.prev);
        this.size = this.size - 1;

        return lastvalue;
    }
    public T get(int index) {
        if(index < this.size) {
            return this.items[index];
        }
        else {
            return null;
        }
    }

}
