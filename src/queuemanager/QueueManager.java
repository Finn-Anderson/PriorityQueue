package queuemanager;

import java.util.Scanner;

/**
 * Simple text based driver program for the PriorityQueue ADT and its different
 * implementations.
 */
public class QueueManager {

    public static void main(String[] args) {
        PriorityQueue<Person> q;
        Scanner stdin = new Scanner(System.in);

        /* Welcome and prompt for implementation choices */
        System.out.println("Welcome to the Priority Queue manager.");
        System.out.println();
        System.out.println("Select priority queue implementation");
        System.out.println("SA for a sorted array");
        System.out.println("UA for an unsorted array");
        System.out.println("SL for a sorted linked list");
        System.out.println("UL for an unsorted linked list");
        System.out.println("H for a heap");
        System.out.println();
        System.out.print("Your choice > ");
        String input = stdin.nextLine();

        /* Select implementation version to use */
        switch (input.toLowerCase()) {
            case "sa":
                q = new SortedArrayPriorityQueue<>(8);
                System.out.println("Using a sorted array.");
                break;
            case "ua":
                q = new UnsortedArrayPriorityQueue<>(8);
                System.out.println("Using an unsorted array");
                break;
            case "sl":
                q = new SortedLinkedListPriorityQueue<>();
                System.out.println("Using a sorted linked list");
                break;
            case "ul":
            case "h":
            default:
                q = new SortedArrayPriorityQueue<>(8);
                System.out.println("Invalid choice, using sorted array.");
                break;
        }

        /* Usage instructions */
        System.out.println("Enter commands at the prompt.");
        System.out.println("A <name> <priority> adds a person to the queue.");
        System.out.println("H displays the name of the person at the head of the queue");
        System.out.println("R removes the person at the head of the queue");
        System.out.println("E checks if the queue is empty");
        System.out.println("P prints the whole queue");
        System.out.println("Q quits from the system");

        System.out.print("> ");
        input = stdin.nextLine();

        /* Main loop */
        while (!input.toLowerCase().equals("q")) {
            if (input.toLowerCase().charAt(0) == 'a') {

                /* Add an item to the queue */
                String name = "";
                int priority = 0;
                boolean valid = true;

                try {
                    name = input.substring(2, input.lastIndexOf(' '));
                    priority = Integer.parseInt(input.substring(input.lastIndexOf(' ') + 1));
                } catch(NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Invalid input. Missing valid name and/or priority.");
                    valid = false;
                }

                if (valid) {
                    Person person = new Person(name);
                    System.out.println("Adding " + person.getName() + " with priority " + priority);
                    try {
                        q.add(person, priority);
                    } catch (QueueOverflowException e) {
                        System.out.println("Add operation failed: " + e);
                    }
                }
            } else if (input.toLowerCase().charAt(0) == 'h') {

                /* Display the item at the head of the queue */
                try {
                    String name = q.head().getName();
                    System.out.println("The person at the head of the queue is " + name);
                } catch (QueueUnderflowException e) {
                    System.out.println("Can't get head of queue: " + e);
                }
            } else if (input.toLowerCase().charAt(0) == 'r') {

                /* Remove the item at the head of the queue */
                try {
                    String name = q.head().getName();
                    System.out.println("Removing " + name + " from the head of the queue");
                    q.remove();
                } catch (QueueUnderflowException e) {
                    System.out.println("Can't remove head of queue: " + e);
                }
            } else if (input.toLowerCase().charAt(0) == 'e') {

                /* Report if the queue is empty or not */
                if (q.isEmpty()) {
                    System.out.println("The queue is empty");
                } else {
                    System.out.println("The queue is NOT empty");
                }
            } else if (input.toLowerCase().charAt(0) == 'p') {

                /* Print out the entire queue (in no particular order) */
                System.out.println(q);
            }
            System.out.print("> ");
            input = stdin.nextLine();
        }
        System.out.println("Bye");
    }
}
