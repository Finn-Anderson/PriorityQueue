package queuemanager;

/**
 * Implementation of the PriorityQueue ADT using a sorted linked list for storage.
 *
 * @param <T> The type of things being stored.
 */
public class SortedLinkedListPriorityQueue<T> implements PriorityQueue<T> {

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
    private Node<T> head;

    public SortedLinkedListPriorityQueue() {
        head = null;
    }

    @Override
    public T head() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            return head.data.getItem();
        }
    }

    @Override
    public void add(T item, int priority) {
        PriorityItem<T> data = new PriorityItem<>(item, priority);

        if (head == null) {
            head = new Node(data);
        } else {
            Node<T> last = null;

            for (Node<T> node = head; node != null; node = node.next) {
                /* Scans forward looking for insertion point */
                if (node.data.getPriority() < data.getPriority()) {
                    Node<T> input = new Node(data);

                    input.next = node;

                    if (last != null) {
                        last.next = input;
                    } else {
                        head = input;
                    }

                    return;
                }
                last = node;

            }
            /* Adds to end of list if no suitable insertion point was found */
            Node<T> input = new Node(data);

            last.next = input;
        }
    }

    @Override
    public void remove() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            head = head.next;
        }
    }

    @Override
    public boolean isEmpty() { return head == null; }

    @Override
    public String toString() {
        String result = "[";
        for (Node<T> node = head; node != null; node = node.next) {
            if (node != head) {
                result = result + ", ";
            }
            result = result + node.data;
        }
        result = result + "]";
        return result;
    }
}
