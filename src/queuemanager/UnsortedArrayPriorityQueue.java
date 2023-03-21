package queuemanager;

/**
 * Implementation of the PriorityQueue ADT using an unsorted array for storage.
 *
 * @param <T> The type of things being stored.
 */
public class UnsortedArrayPriorityQueue<T> implements PriorityQueue<T> {

    /**
     * Where the data is actually stored.
     */
    private final Object[] storage;

    /**
     * The size of the storage array.
     */
    private final int capacity;

    /**
     * The index of the last item stored.
     *
     * This is equal to the item count minus one.
     */
    private int tailIndex;

    /**
     * Create a new empty queue of the given size.
     *
     * @param size
     */
    public UnsortedArrayPriorityQueue(int size) {
        storage = new Object[size];
        capacity = size;
        tailIndex = -1;
    }
    @Override
    public void add(T item, int priority) throws QueueOverflowException {
        tailIndex = tailIndex + 1;
        if (tailIndex >= capacity) {
            /* No resizing implemented, but that would be a good enhancement. */
            tailIndex = tailIndex - 1;
            throw new QueueOverflowException();
        } else {
            /* Inserts the item at the end of the array */
            int i = tailIndex;
            storage[i] = new PriorityItem<>(item, priority);
        }
    }

    @Override
    public T head() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            /* Scan backwards looking for highest priority */
            int i = tailIndex;

            PriorityItem<T> highest = null;

            while (i > 0) {
                if (highest == null || ((PriorityItem<T>) storage[i]).getPriority() > highest.getPriority()) {
                    highest = ((PriorityItem<T>) storage[i]);
                }
                i = i - 1;
            }
            return ((PriorityItem<T>) highest).getItem();
        }
    }

    @Override
    public void remove() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            /* Scan backwards looking for highest priority and remove */
            int i = tailIndex;

            PriorityItem<T> highest = null;
            int highestIndex = 0;

            while (i > 0) {
                if (highest == null || ((PriorityItem<T>) storage[i]).getPriority() > highest.getPriority()) {
                    highest = ((PriorityItem<T>) storage[i]);
                    highestIndex = i;
                }
                i = i - 1;
            }

            for (int j = highestIndex; j < tailIndex; j++) {
                storage[j] = storage[j + 1];
            }

            tailIndex = tailIndex - 1;
        }
    }

    @Override
    public boolean isEmpty() {
        return tailIndex < 0;
    }

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i <= tailIndex; i++) {
            if (i > 0) {
                result = result + ", ";
            }
            result = result + storage[i];
        }
        result = result + "]";
        return result;
    }
}
