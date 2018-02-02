public class LinkedListDeque <T> {

	public class Node {
		public Node prev;
		public T item;
		public Node next;

		public Node(Node p, T i, Node n) {
			this.prev = p;
			this.item = i;
			this.next = n;

		}
	}
	/* The first item if it exists is at sentinel.next*/
	private Node sentinel;
	private int size;

	public LinkedListDeque () {
		sentinel = new Node(this.sentinel, null, this.sentinel);
		size = 0;
	}

	public void addFirst (T item) {
		//p.next = this.sentinel;
		Node p = new Node (this.sentinel, item, this.sentinel.next);
		if (this.isEmpty()) {
			this.sentinel.prev = p;
			p.next = this.sentinel;
		}
		this.sentinel.next = p;
		this.size = this.size + 1;
	}

	public void addLast(T item) {
		this.size = this.size + 1;
	/*keep moving through the nodes */
		Node oldNode = this.sentinel.prev;

		Node newNode = new Node (oldNode, item, this.sentinel);
		oldNode.next = newNode;
		this.sentinel.prev = newNode;

	}

	public boolean isEmpty() {
		if (this.size == 0){
			return true;
		}
		else {
			return false;
		}
	}

	public int size () {
		return this.size;
	}

	public void printDeque () {
		Node p = this.sentinel.next;

		int count = 0;
		while (count < this.size) {
			System.out.print(p.item.toString() + " ");
			p = p.next;
			count += 1;
		}

	}

	public T removeFirst() {
		if(this.isEmpty()){
			return null;
		}
		else {
			this.size = this.size - 1;
			/* to figure out the first item */
			Node firstitem = this.sentinel.next;
			T itemvalue = firstitem.item;
			/* move the second node to become the first node */
			this.sentinel.next = firstitem.next;
			firstitem.next.prev = this.sentinel;

			return itemvalue;
		}
	}

	public T removeLast() {
		if (this.isEmpty()){
			return null;
		}
		else {
			this.size = this.size - 1;
			Node oldNode = this.sentinel.prev;
			T itemvalue = oldNode.item;
			/* remove the last item of the deque by repointing where the previous sentinel directs to */
			this.sentinel.prev = oldNode.prev;
			oldNode.prev.next = this.sentinel;

			return itemvalue;

		}

	}

	public T get(int index) {
		Node p = this.sentinel;

		if (index > this.size) {
			return null;
		}
		else if (this.isEmpty()) {
			return null;
		}
		else {
			while (index > 0) {
				p.next = p.next.next;
				index = index - 1;

			}
			return p.item;
		}
	}
	private T RecursiveHelper (int index, Node p) {
		if (index == 0) {
			return p.item;
		}
		else {
			return RecursiveHelper(index - 1, p.next);
		}
	}
	public T getRecursive (int index) {
		if (index > this.size) {
			return null;
		}
		else if (this.isEmpty()) {
			return null;
		}
		else {
			return RecursiveHelper(index, this.sentinel.next);
		}
	}
}