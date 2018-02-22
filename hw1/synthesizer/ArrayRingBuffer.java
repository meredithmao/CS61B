// package <package name>;
package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     * @param capacity
     */
    public ArrayRingBuffer(int capacity) {

        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[this.capacity];

    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last = last + 1;

        if (last == capacity) {
            last = 0;
        }

    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {

        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T value = rb[first];
        rb[first] = null;
        first = first + 1;
        fillCount -= 1;
        if (first == capacity) {
            first = 0;
        }
        return value;
    }

    /**
     * Return oldest item, but don't remove it.
     */

    public T peek() {

        return rb[first];
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterate();
    }
    private class Iterate implements Iterator<T> {
        private int index;
        Iterate() {
            index = 0;
        }
        public boolean hasNext() {
            return (index != (capacity - 1));
        }

        public T next() {
            T nextIteration = rb[index];
            index += 1;
            return nextIteration;
        }
    }
}
