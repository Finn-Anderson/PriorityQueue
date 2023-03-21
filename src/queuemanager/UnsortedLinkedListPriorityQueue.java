package queuemanager;

/**
 * Implementation of the PriorityQueue ADT using an unsorted linked list for storage.
 *
 * @param <T> The type of things being stored.
 */
public class UnsortedLinkedListPriorityQueue<T> implements PriorityQueue<T> {

    class Node<T> {
        /**
         * Where the data is actually stored.
         */
        PriorityItem<T> data;

        /**
         * Pointer to the next node.
         */
        Node<T> next;

        Node(PriorityItem<T> d) {
            this.data = d;
            next = null;
        }
    }

    /**
     * Head node of list.
     */
    private Node<T> first;

    public UnsortedLinkedListPriorityQueue() {
        first = null;
    }

    @Override
    public T head() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            Node<T> head = first;
            for (Node<T> node = first.next; node != null; node = node.next) {
                if (head.data.getPriority() < node.data.getPriority()) {
                    head = node;
                }
            }
            return head.data.getItem();
        }
    }

    @Override
    public void add(T item, int priority) {
        PriorityItem<T> data = new PriorityItem<>(item, priority);

        if (first == null) {
            first = new Node(data);
        } else {
            /* Adds data to end of list */
            Node<T> last = null;

            for (Node<T> node = first; node != null; node = node.next) {
                last = node;
            }

            Node<T> input = new Node(data);

            last.next = input;
        }
    }

    @Override
    public void remove() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            Node<T> head = first;
            Node<T> last = null;

            for (Node<T> node = first; node.next != null; node = node.next) {
                if (head.data.getPriority() < node.next.data.getPriority()) {
                    last = node;
                    head = node.next;
                }
            }

            if (last != null) {
                last.next = head.next;
            } else {
                first = head.next;
            }
        }
    }

    @Override
    public boolean isEmpty() { return first == null; }

    @Override
    public String toString() {
        String result = "[";
        for (Node<T> node = first; node != null; node = node.next) {
            if (node != first) {
                result = result + ", ";
            }
            result = result + node.data;
        }
        result = result + "]";
        return result;
    }
}
