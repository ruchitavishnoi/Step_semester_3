class Node {
    String title;
    int day;
    Node next;

    public Node(String title, int day) {
        this.title = title;
        this.day = day;
        this.next = null;
    }
}

class Solution {
    public Node sortByDueDay(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node slow = head;
        Node fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node rightHalf = slow.next;
        slow.next = null;

        Node leftSorted = sortByDueDay(head);
        Node rightSorted = sortByDueDay(rightHalf);

        return merge(leftSorted, rightSorted);
    }
