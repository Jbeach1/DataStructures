package project2;

//Wilson Joshua Beach N01109437 2/18/2019

//Note that <E extends Comparable>. Therefore you should use Comparable
//instead of Object while creating arrays and casting them to generic type.
//Also use compareTO() instead of < or > while comparing generic elements
public class MaxMinStack<E extends Comparable<E>> {

	public final int CAPACITY = 10;
	private E[] primary, min, max;
	private int top = -1; //useful to start at -1		

//the default constructor
	@SuppressWarnings("unchecked")
	public MaxMinStack() {
		primary = (E[]) new Comparable[CAPACITY];   //make the three arrays w/ size of capacity
		min = (E[]) new Comparable[CAPACITY];
		max = (E[]) new Comparable[CAPACITY];
	}

//another constructor which returns a stack of specified size
	@SuppressWarnings("unchecked")
	public MaxMinStack(int ary_size) {
		primary = (E[]) new Comparable[ary_size];  //same as default constructor with variable size
		min = (E[]) new Comparable[ary_size];
		max = (E[]) new Comparable[ary_size];
	}

//return the element on top of the stack without removing it. return null if stack is empty.
	public E top() { 
		if (isEmpty())
			return null;
		return primary[top];  //returns element at top of array
	}

//return the number of elements in the stack
	public int size() {
		return top + 1; // # of elements is index of array +1
	}

//test if the stack is empty
	public boolean isEmpty() {
		return (top == -1); //if the top == -1 then we have no elements
	}

//return the actual capacity of the stack(not the number of elements stored in it).
	public int capacity() {
		return primary.length; //all three arrays have same capacity 
	}

//return the maximum value stored in the stack. return null if stack is empty.
	public E maximum() {
		if (isEmpty()) {
			return null;
		}
		return max[top]; //gives max, actual work done in push/pop
	}

//return the minimum value stored in the stack. return null if stack is empty.
	public E minimum() {
		if (isEmpty()) {
			return null;
		}
		return min[top]; //give minimum which is top of minimum
	}

//push a new element onto the stack
	public void push(E e) throws IllegalStateException {
// needed so we do not exceed our capacity and get an error
		if (size() == primary.length) 
			throw new IllegalStateException("Cannot add more elements, stack is full.");
//if its empty we can put element onto all three stacks	
		if (isEmpty()) {
			primary[++top] = e;
			max[top] = e;
			min[top] = e;
//if its not we need to check using the built in compareTo
		} else {
			primary[++top] = e; //push new element onto next spot in primary

			if (e.compareTo(max[top - 1]) >= 0) {
				max[top] = e; //if e is > last element we push e onto max
			} else {
				max[top] = max[top - 1]; //if it is not we need push the old max again
			}
			if (e.compareTo(min[top - 1]) <= 0) {
				min[top] = e; //if the element is < current min then push e onto min
			} else {
				min[top] = min[top - 1]; //if it is not, push old min again to min
			}

		}
		
	}

//pop the element on top of the stack and return it. return null if stack is empty.
	public E pop() {
		if (isEmpty()) {
			return null; //return null if empty
		}
		E popped = primary[top]; //save for later
		primary[top] = null; //pop top by setting to null
		max[top] = null; //pop top of others as well
		min[top] = null;
		top--; //decrease top because we popped everybody
		return popped; //return saved popped
	}

}
