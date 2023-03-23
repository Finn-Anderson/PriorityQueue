package queuemanager;

/**
 * Implementation of the PriorityQueue ADT using a heap for storage.
 *
 * @param <T> The type of things being stored.
 */
public class HeapPriorityQueue<T> implements PriorityQueue<T> {

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
    public HeapPriorityQueue(int size) {
        storage = new Object[size];
        capacity = size;
        tailIndex = -1;
    }

    private int parent(int i) { return (i - 1) / 2; }

    private int leftChild(int i) { return (2 * i) + 1; }

    private int rightChild(int i) { return (2 * i) + 2; }

    private void swap(int parentPos, int childPos) {
        PriorityItem<T> temp = (PriorityItem<T>) storage[parentPos];
        storage[parentPos] = storage[childPos];
        storage[childPos] = temp;
    }

    private void maxHeapify(int i) {
        int parentPriority = ((PriorityItem<T>) storage[i]).getPriority();
        PriorityItem<T> leftChild = ((PriorityItem<T>) storage[leftChild(i)]);
        PriorityItem<T> rightChild = ((PriorityItem<T>) storage[rightChild(i)]);

        if (leftChild != null) {
            if (rightChild != null) {
                if (parentPriority < leftChild.getPriority() || parentPriority < rightChild.getPriority()) {
                    if (leftChild.getPriority() > rightChild.getPriority()) {
                        swap(i, leftChild(i));
                        maxHeapify(leftChild(i));
                    } else {
                        swap(i, rightChild(i));
                        maxHeapify(rightChild(i));
                    }
                }
            } else {
                if (parentPriority < leftChild.getPriority()) {
                    swap(i, leftChild(i));
                    maxHeapify(leftChild(i));
                }
            }
        }
    }

    @Override
    public void add(T item, int priority) throws QueueOverflowException {
        tailIndex = tailIndex + 1;
        if (tailIndex >= capacity) {
            /* No resizing implemented, but that would be a good enhancement. */
            tailIndex = tailIndex - 1;
            throw new QueueOverflowException();
        } else {
            int i = tailIndex;

            storage[i] = new PriorityItem<>(item, priority);

            while (((PriorityItem<T>) storage[i]).getPriority() > ((PriorityItem<T>) storage[parent(i)]).getPriority()) {
                swap(parent(i), i);
                i = parent(i);
            }
        }
    }

    @Override
    public T head() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            return ((PriorityItem<T>) storage[0]).getItem();
        }
    }

    @Override
    public void remove() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            int i = tailIndex;

            storage[0] = storage[i];
            storage[i] = null;

            tailIndex = tailIndex - 1;

            if (tailIndex > 0) {
                maxHeapify(0);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return tailIndex < 0;
    }

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i <= (tailIndex / 2) && i < (capacity / 2); i++) {
            if (i > 0 && storage[i + 1] != null) {
                result = result + ", ";
            }
            result = result + storage[i] + " -> " + storage[leftChild(i)] + " : " + storage[rightChild(i)];
        }
        result = result + "]";
        return result;
    }
}
