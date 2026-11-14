import java.util.HashMap;

class Node {
    int key;
    String value;
    Node prev;
    Node next;

    public Node(int key, String value) {
        this.key = key;
        this.value = value;
    }
}

class RecentCache {
    private int capacity;
    private HashMap<Integer, Node> map;
    private Node head;
    private Node tail;

    public RecentCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(-1, "");
        this.tail = new Node(-1, "");
        head.next = tail;
        tail.prev = head;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertToHead(Node node) {
        node.next = head.next;
        node.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    public String get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            removeNode(node);
            insertToHead(node);
            System.out.println("get(" + key + ") -> " + node.value);
            return node.value;
        }
        System.out.println("get(" + key + ") -> -1");
        return "-1";
    }

    public void put(int key, String page) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = page;
            removeNode(node);
            insertToHead(node);
            System.out.println("put(" + key + "," + page + ") updated");
        } else {
            if (map.size() == capacity) {
                Node lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);
                System.out.println("put(" + key + "," + page + ") evicted " + lru.key);
            } else {
                System.out.println("put(" + key + "," + page + ")");
            }
            Node newNode = new Node(key, page);
            insertToHead(newNode);
            map.put(key, newNode);
        }
    }

    public void printCache() {
        System.out.print("Cache (most recent first): ");
        Node curr = head.next;
        boolean first = true;
        while (curr != tail) {
            if (!first) {
                System.out.print(", ");
            }
            System.out.print(curr.key + "=" + curr.value);
            first = false;
            curr = curr.next;
        }
        System.out.println();
    }
}

class Solution {
    public static void main(String[] args) {
        RecentCache cache = new RecentCache(2);
        cache.put(1, "Home");
        cache.put(2, "Notes");
        cache.get(1);
        cache.put(3, "Quiz");
        cache.get(2);
        cache.put(1, "Home2");
        cache.put(4, "Mail");
        cache.get(3);
        cache.get(1);
        cache.printCache();
    }
}
