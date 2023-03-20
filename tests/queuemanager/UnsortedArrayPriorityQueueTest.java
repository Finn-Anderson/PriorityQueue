package queuemanager;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnsortedArrayPriorityQueueTest {

    @Test
    public void testHead() throws QueueOverflowException, QueueUnderflowException {
        PriorityQueue<Person> qTest = new UnsortedArrayPriorityQueue<>(8);

        assertThrows("Should throw error if empty", QueueUnderflowException.class, qTest::head);

        Person person1 = new Person("Rodger");
        Person person2 = new Person("Finn");

        qTest.add(person1, 2);
        qTest.add(person2, 1);

        assertEquals("Should return Rodger", person1, qTest.head());
    }

    @Test
    public void testAdd() throws QueueOverflowException {
        PriorityQueue<Person> qTest = new UnsortedArrayPriorityQueue<>(8);

        Person[] people = {new Person("Rodger"), new Person("Finn"), new Person("Fred")};
        int[] priorities = {0, 100, -1};

        String target = "[(Rodger, 0), (Finn, 100), (Fred, -1)]";

        for (int i = 0; i < people.length; i++) {
            qTest.add(people[i], priorities[i]);
        }

        assertEquals("Should return in this order: Rodger, Finn, Fred", target, qTest.toString());

        for (int i = people.length; i < 8; i++) {
            qTest.add(new Person("Harry" + i), i);
        }

        assertThrows("Should throw error if full", QueueOverflowException.class, () -> qTest.add(new Person("Error"), 3));
    }

    @Test
    public void testRemove() throws QueueOverflowException, QueueUnderflowException {
        PriorityQueue<Person> qTest = new UnsortedArrayPriorityQueue<>(8);

        assertThrows("Should throw error if empty", QueueUnderflowException.class, qTest::remove);

        Person[] people = {new Person("Rodger"), new Person("Finn"), new Person("Fred")};
        int[] priorities = {0, 100, -1};

        String target = "[(Finn, 100), (Fred, -1)]";

        for (int i = 0; i < people.length; i++) {
            qTest.add(people[i], priorities[i]);
        }

        qTest.remove();

        assertEquals("Should return in this order: Finn, Fred", target, qTest.toString());
    }

    @Test
    public void testIsEmpty() throws QueueOverflowException {
        PriorityQueue<Person> qTest = new UnsortedArrayPriorityQueue<>(8);

        assertTrue("Should return true", qTest.isEmpty());

        qTest.add(new Person("Rodger"), 0);

        assertFalse("Should return false", qTest.isEmpty());
    }
}