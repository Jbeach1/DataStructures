

public class CDList<E> implements Cloneable {
	// --------------nested node class------------
	private static class Node<E> {
		private E element;
		private Node<E> prev;
		private Node<E> next;

		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}

		public E getElement() {
			return element;
		}

		public Node<E> getPrev() {
			return prev;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setPrev(Node<E> p) {
			prev = p;
		}

		public void setNext(Node<E> n) {
			next = n;
		}

		public String toString() {
			return "(" + prev.getElement() + "," + element + "," + next.getElement() + ")";
		}
	}
	// ---------------end of nested node class------------

	// Member data
	private Node<E> tail = null;
	private int size = 0;

	// Member Methods
	public CDList() {
		// default constructor

	}

	public CDList(CDList<E> cdl) {
		// the copy constructor

		for (int i = 0; i < cdl.size(); i++) {    //for size list being cloned
			addFirst(cdl.tail.getElement());  // add to front
			cdl.rotateBackward(); // rotate to get all elements added
		}

	}

	public int size() {
		// returns the size of the list
		return size; //return size
	}

	public boolean isEmpty() {
		// checks if the list is empty
		if (size == 0) //simple check if is empty return t/f
			return true;
		return false;

	}

	public E first() {
		// return the first element of the list

		return tail.getNext().getElement(); //gets the next node after tail (first) returns element
	}

	public E last() {
		// return the last element of the list
		return tail.getElement(); // last is tail so return tail's element
	}

	public void rotate() {
		// rotate the first element to the back of the list
		if (size != 0) {  //if it has elements to rotate
			tail = tail.getNext();   //simply just move tail to "rotate"
		}
	}

	public void rotateBackward() {
		if (size != 0) {
			tail = tail.getPrev(); //do opposite of rotate.
		}
		// rotate the last element to the front of the list

	}

	public void addFirst(E e) {
		// add element e to the front of the list

		if (size == 0) { // in the case that the list was empty, make a list with tail and set to itself
			tail = new Node<>(e, null, null);
			tail.setPrev(tail);  
			tail.setNext(tail);
		} else {
			Node<E> first = new Node<>(e, tail, tail.getNext()); 
			tail.setNext(first);			//update tail's next to be the new first node
			tail.getNext().getNext().setPrev(first); //update node in front of new node's prev to first
		}
		size++; //update size regardless if was empty or not

	}

	public void addLast(E e) {
		addFirst(e); //can let add first do the work 
		tail = tail.getNext(); //just move tail to make add last
	}

	public E removeFirst() {
		// remove and return the element at the front of the list
		if (isEmpty()) //if it's empty no need to remove anything
			return null;
		E saved = first(); //save the element to return later
		tail.setNext(tail.getNext().getNext()); // tail's next becomes the node after first
		tail.getNext().setPrev(tail); // set the new "head's" previous to be tail
		size--;  //decrement size
		return saved;

	}

	private void remove(Node<E> node) {
		node.getPrev().setNext(node.getNext()); //helper for my remove duplicates method
		node.getNext().setPrev(node.getPrev()); //removes the element by getting rid of links to itself
	}

	public E removeLast() {
		// remove and return the element at the back of the list
		if (isEmpty()) //if empty then do nothing
			return null;
		E saved = last(); //save for returning later

		tail.getNext().setPrev(tail.getPrev()); //similar method to removeFirst but remove the tail
		tail = tail.getPrev(); //reassign tail to node before it (prev)
		tail.setNext(tail.getNext().getNext());
		size--; //decrement because we removed an element
		return saved;
	}

	public CDList<E> clone() {
		// clone and return the new list(shallow)
		CDList<E> copy = new CDList<E>(this); //call copy constructor
		return copy; //return copied list
	}

	public boolean equals(Object o) {
		// check if the current instance and o are from the same class, have the same
		// type

		if (o == null)
			return false; // if is null != equal

		if (getClass() != o.getClass())
			return false; // checks if same class

		@SuppressWarnings("unchecked")
		CDList<E> subject = (CDList<E>) o; // makes the test subject of type CDList since of same class

		if (size != subject.size)
			return false; // checks if size equal

		Node<E> walker = tail; // creates a walker for the tester
		Node<E> compare = subject.tail; // creates a walker for the "test-ie" or subject
/*
		for (int j = 0; j < size - 1; j++) { //use of two loops so it will compare each element to all other elements
			walker = compare.getNext(); // move walker to next element past compare for comparisons to be made
			while(walker != compare) { 
				if (walker.getElement().equals(compare.getElement())) { //if elements equal we need to remove
					if (walker == tail) { //if the walker is tail the last must be removed 
						removeLast(); //we call remove last so tail is updated to tail.getPrev()
					} else {
						remove(walker); //if walker is not tail then we remove using the private remove function
					}
				} walker = walker.getNext(); //walker moves along list to check against compare
				
			}compare = compare.getNext(); //in outer loop we then move onward to the next element needed to be compared
		} 
	}
 */
		for (int i = 0; i < size; i++) {
			if (!walker.getElement().equals(compare.getElement())) {// checks if elements are equal
				for (int j = 0; j < subject.size(); j++) {
					if (walker.getElement().equals(compare.getElement())) { //checks eq
						subject.rotate();
					}

				}
				return false;
			}
			walker = walker.getNext(); // set tester nodes equal to next nodes to check all nodes
			compare = compare.getNext(); // same as above but for subject
		}
		// and have the same sequence of elements despite
		// having possibly different starting points.
		return true;
	}

	public void attach(CDList cdl) {
		// insert cdl after the tail of the current list

		CDList<E> cdlClone = cdl.clone();  //clone the list enable to not change the original copy
		this.tail.getNext().setPrev(cdlClone.tail); //total of 4 changes are to be made here
		Node<E> temp = this.tail.getNext(); //temp variable so we don't loose tail.getNext()
		this.tail.setNext(cdlClone.tail.getNext()); //perform operations to move both lists new links
		cdlClone.tail.getNext().setPrev(this.tail);
		cdlClone.tail.setNext(temp);
		this.tail = cdlClone.tail; //update tail
		size += cdlClone.size; //update size for new list

	}

	public void removeDuplicates() {
		// remove all elements that happen more than once in the list. If the tail gets
		// //deleted, the element immediately before the tail will become the new tail.

		Node<E> walker = tail.getNext(); //create walker node
		Node<E> compare = tail; // node to compare
		
		for (int j = 0; j < size - 1; j++) { //use of two loops so it will compare each element to all other elements
			walker = compare.getNext(); // move walker to next element past compare for comparisons to be made
			while(walker != compare) { 
				if (walker.getElement().equals(compare.getElement())) { //if elements equal we need to remove
					if (walker == tail) { //if the walker is tail the last must be removed 
						removeLast(); //we call remove last so tail is updated to tail.getPrev()
					} else {
						remove(walker); //if walker is not tail then we remove using the private remove function
					}
				} walker = walker.getNext(); //walker moves along list to check against compare
				
			}compare = compare.getNext(); //in outer loop we then move onward to the next element needed to be compared
		} 
	}

	public void printList() {
		// prints all elements in the list
		if (size == 0)
			System.out.println("List is empty.");
		else {
			Node<E> walk = tail.getNext();

			while (!(walk.getNext().equals(tail.getNext()))) {  //super helpful debug tool! thank you.
				System.out.print(walk.toString() + ", ");
				walk = walk.getNext();
			}
			System.out.println(walk.toString());
		}
	}

}
