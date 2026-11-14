class Solution {
    public ListNode findIntersection(ListNode a, ListNode b) {
        int lenA = 0;
        int lenB = 0;
        ListNode currA = a;
        ListNode currB = b;
        
        while (currA != null) {
            lenA++;
            currA = currA.next;
        }
        
        while (currB != null) {
            lenB++;
            currB = currB.next;
        }
        
        currA = a;
        currB = b;
        
        while (lenA > lenB) {
            currA = currA.next;
            lenA--;
        }
        
        while (lenB > lenA) {
            currB = currB.next;
            lenB--;
        }
        
        while (currA != currB) {
            currA = currA.next;
            currB = currB.next;
        }
        
        return currA;
    }
}
