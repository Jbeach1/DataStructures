package project2;
/*Wilson Joshua Beach N01109437 2/18/2019
 The following code is implemented by following the textbook:
 The necessary change for this project was to implement using generic array
 */
public class SinglyLinkedList<E> {
	private static class Node<E> {
		private E[] element;
		private Node<E> next;

		public Node(E[] e, Node<E> n) {
			element = e;
			next = n;
		}

		public E[] getElement() {
			return element;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> n) {
			next = n;
		}
		// nested node class end
	}

	private Node<E> head = null;
	private Node<E> tail = null;
	private int size = 0;

	// constructor
	public SinglyLinkedList() {
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E[] first() { //first is just element at the head
		if (isEmpty())
			return null;
		return head.getElement();
	}

	public E[] last() { //last is element at tail
		if (isEmpty()) 
			return null;
		return tail.getElement();
	}

	public void addFirst(E[] e) {
		head = new Node<>(e, head); //add elements at head
		if (size == 0) //if empty set tail = head
			tail = head;
		size++;
	}

	public void addLast(E[] e) {
		Node<E> newest = new Node<>(e, null); //last element needs to point to null
		if (isEmpty())
			head = newest;
		else
			tail.setNext(newest);
		tail = newest;
		size++;
	}

	public E[] removeFirst() {
		if (isEmpty())
			return null;
		E[] answer = head.getElement(); //save for later
		head = head.getNext(); //move head to next node
		size--;
		if (size == 0)
			tail = null;  
		return answer;
	}
}
