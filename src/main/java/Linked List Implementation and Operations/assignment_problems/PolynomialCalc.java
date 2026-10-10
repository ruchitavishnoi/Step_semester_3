class Node {
    int coeff;
    int exp;
    Node next;

    Node(int coeff, int exp) {
        this.coeff = coeff;
        this.exp = exp;
        this.next = null;
    }
}

public class PolynomialCalc {
    public static Node addPolynomials(Node p, Node q) {
        Node dummy = new Node(0, 0);
        Node tail = dummy;

        while (p != null && q != null) {
            if (p.exp > q.exp) {
                tail.next = new Node(p.coeff, p.exp);
                p = p.next;
                tail = tail.next;
            } else if (q.exp > p.exp) {
                tail.next = new Node(q.coeff, q.exp);
                q = q.next;
                tail = tail.next;
            } else {
                int sumCoeff = p.coeff + q.coeff;
                if (sumCoeff != 0) {
                    tail.next = new Node(sumCoeff, p.exp);
                    tail = tail.next;
                }
                p = p.next;
                q = q.next;
            }
        }

        while (p != null) {
            tail.next = new Node(p.coeff, p.exp);
            p = p.next;
            tail = tail.next;
        }

        while (q != null) {
            tail.next = new Node(q.coeff, q.exp);
            q = q.next;
            tail = tail.next;
        }

        return dummy.next;
    }

    public static String toStandardForm(Node head) {
        if (head == null) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        Node curr = head;
        boolean firstTerm = true;

        while (curr != null) {
            int c = curr.coeff;
            int e = curr.exp;

            if (firstTerm) {
                if (c < 0) {
                    sb.append("-");
                }
            } else {
                if (c > 0) {
                    sb.append(" + ");
                } else {
                    sb.append(" - ");
                }
            }

            int absC = Math.abs(c);
            if (e == 0) {
                sb.append(absC);
            } else {
                if (absC != 1) {
                    sb.append(absC);
                }
                sb.append("x");
                if (e > 1) {
                    sb.append("^").append(e);
                }
            }

            firstTerm = false;
            curr = curr.next;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Node p1 = new Node(5, 3);
        p1.next = new Node(4, 2);
        p1.next.next = new Node(2, 0);

        Node q1 = new Node(5, 2);
        q1.next = new Node(-3, 1);
        q1.next.next = new Node(1, 0);

        Node res1 = addPolynomials(p1, q1);
        System.out.println(toStandardForm(res1));

        Node p2 = new Node(3, 2);
        p2.next = new Node(1, 0);

        Node q2 = new Node(-3, 2);
        q2.next = new Node(4, 1);

        Node res2 = addPolynomials(p2, q2);
        System.out.println(toStandardForm(res2));
    }
}
