class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class MergingCounters {
    public static Node mergeSorted(Node a, Node b) {
        Node dummy = new Node(0);
        Node tail = dummy;

        while (a != null && b != null) {
            if (a.data <= b.data) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }

        if (a != null) {
            tail.next = a;
        } else {
            tail.next = b;
        }

        return dummy.next;
    }

    public static void printList(Node head) {
        if (head == null) {
            System.out.println("(empty)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null) {
                sb.append(" -> ");
            }
            curr = curr.next;
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        Node a1 = new Node(3);
        a1.next = new Node(8);
        a1.next.next = new Node(15);

        Node b1 = new Node(5);
        b1.next = new Node(8);
        b1.next.next = new Node(12);
        b1.next.next.next = new Node(20);

        Node merged1 = mergeSorted(a1, b1);
        printList(merged1);

        Node a2 = null;
        Node b2 = new Node(4);

        Node merged2 = mergeSorted(a2, b2);
        printList(merged2);
    }
}
