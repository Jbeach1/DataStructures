package project2;

//Wilson Joshua Beach N01109437 2/18/2019

public class LinkedArrayQueue<E> {
	// don't forget the member data
	public final int CAPACITY = 8; // hard code capacity
	int front;
	int back;
	int size;
	E[] data;
	SinglyLinkedList<E> sll; // make sll from singly linked list
	// the default constructor

	@SuppressWarnings("unchecked")
	public LinkedArrayQueue() {
		sll = new SinglyLinkedList<>(); // new instance of singly linked list
		data = (E[]) new Object[CAPACITY]; // data an object casted to generic array
		front = 0;
		back = 0;
		size = 0;
	}

	// return the number of elements in the queue
	public int size() {
		return size;
	}

	// return the number of arrays currently storing elements
	public int numArrays() {
		return sll.getSize(); // call for size of sll, which is the number of nodes
							  // which equals the # of arrays
	}

	// test if the queue is empty
	public boolean isEmpty() {
		return (size == 0);
	}

	// return the element at the front of the queue. return null if queue is empty
	public E first() {
		if (isEmpty()) {
			return null;
		}
		return sll.first()[front]; // return first node in the list at front index
	}

	// return the element at the back of the queue. return null if queue is empty
	public E last() {
		if (isEmpty()) {
			return null;
		}
		return sll.last()[back]; // return last node with back
	}

	// push e to the back of the queue.
	@SuppressWarnings("unchecked")
	public void enqueue(E e) {

		if (isEmpty()) { // if empty make new object
			data = (E[]) new Object[CAPACITY];
			sll.addLast(data); // add array to end of list
			back = 0; // reset back (should be zero)
		} else if (back == 7) { // if == 7 because after adding another we will be at capacity
			data = (E[]) new Object[CAPACITY];
			sll.addLast(data); // add our new array to end of queue
			back = 0; // reset back
		} else
			back++; //back moves because we added to queue
		data[back] = e; // set element to back's position
		size++; // increase size b/c we adding to queue

	}

	// pop and return the element at the front of the queue. return null if queue is
	// empty
	public E dequeue() {
		E saved; // save for later
		saved = sll.first()[front]; // set saved equal to first element
		sll.first()[front] = null; // assist garbage collection
		if (isEmpty()) {
			return null;
		}
		front++; // increase the front reference because we have dequeued the front
		size--; // decrease size
		if (front == CAPACITY) { // use capacity because we already incremented front
			sll.removeFirst(); // remove first node to get rid of empty array
			front = 0; // reset front
		}

		return saved;

	}

}
