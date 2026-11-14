class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class Solution {
    public boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node secondHalfHead = reverse(slow.next);
        
        Node p1 = head;
        Node p2 = secondHalfHead;
        boolean result = true;
        
        while (result && p2 != null) {
            if (p1.data != p2.data) {
                result = false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        slow.next = reverse(secondHalfHead);

        return result;
    }

    private Node reverse(Node head) {
        Node prev = null;
        Node curr = head;
        
        while (curr != null) {
            Node nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        
        return prev;
    }
}
