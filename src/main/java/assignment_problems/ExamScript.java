class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class Solution {
    public Node reverseInGroups(Node head, int k) {
        if (head == null || k <= 1) {
            return head;
        }

        Node dummy = new Node(0);
        dummy.next = head;
        Node prevGroupTail = dummy;
        Node curr = head;

        while (curr != null) {
            Node groupStart = curr;
            int count = 0;
            while (curr != null && count < k) {
                curr = curr.next;
                count++;
            }

            if (count == k) {
                Node prev = null;
                Node current = groupStart;
                for (int i = 0; i < k; i++) {
                    Node nextNode = current.next;
                    current.next = prev;
                    prev = current;
                    current = nextNode;
                }

                prevGroupTail.next = prev;
                groupStart.next = curr;
                prevGroupTail = groupStart;
            } else {
                prevGroupTail.next = groupStart;
            }
        }

        return dummy.next;
    }
}
