import java.util.HashMap;

class LRUCache {
    class ListNode {
        int key, val;
        ListNode next=null, prev=null;
        ListNode(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    ListNode head, last;
    HashMap<Integer, ListNode> map;
    int capacity, size;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new ListNode(0,0);
        last = new ListNode(0,0);
        last.prev = head;
        head.next = last;
        size = 0;
    }
    private void deleteNode(ListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addNode(ListNode node) {
        node.next = head.next;
        node.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    public int get(int key) {
        if(map.containsKey(key)) {
            ListNode node = map.get(key);
            deleteNode(node);
            addNode(node);
            return node.val;
        }
        return -1;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)) {
            ListNode node = map.get(key);
            deleteNode(node);
            node.val = value;
            addNode(node);
            map.put(key, node);
        } else {
            ListNode node = new ListNode(key, value);
            map.put(key, node);
            if(size < capacity) {
                addNode(node);
                size++;
            } else {
                ListNode remove = last.prev;
                deleteNode(remove);
                map.remove(remove.key);
                addNode(node);
            }
        }
    }

    public static void main(String a[]) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
    }
}