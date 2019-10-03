package project3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BET {
	/************************* nested node class *************************/
	private class BinaryNode<E> {
		private BinaryNode<String> parent;
		private BinaryNode<String> leftChild;
		private BinaryNode<String> rightChild;
		private E element;

		public BinaryNode(BinaryNode<String> p, BinaryNode<String> r, BinaryNode<String> l, E e) {
			parent = p;
			leftChild = l;
			rightChild = r;
			element = e;

		}

		public E getElement() {
			return element;
		}

		public BinaryNode<String> getParent() {
			return parent;
		}

		public BinaryNode<String> getLeft() {
			return leftChild;
		}

		public BinaryNode<String> getRight() {
			return rightChild;
		}

		public boolean isRoot() {
			if (this.getParent() == null)
				;

			return true;
		}

	}

	/************************* end of nested *****************************/
	Stack<BinaryNode<String>> stackOp = new Stack<>(); // operator stack
	Stack<BinaryNode<String>> stackNum = new Stack<>(); // numbers or letters etc
	
	Queue<String> queNum = new LinkedList<>();
	Stack<String> part2Stack = new Stack<>();
	int size = 0;
	int leaf = 0;
	BinaryNode<String> root;

	public BET() {

	}

	public BET(String expr, char mode) {
		if (mode == 'i') {
			buildFromInfix(expr);
		} else if (mode == 'p') {
			buildFromPostfix(expr);
		} else {
			System.out.println("Invalid mode.");
		}
	}

	public boolean buildFromPostfix(String postfix) {
		if (isEmpty()) {
			makeEmpty(root);
		}
		String[] splitArr = postfix.split(" ");
		for (int i = 0; i < splitArr.length; i++) {
			if (isOperator(splitArr[i])) {
				BinaryNode<String> node = new BinaryNode<>(null, stackOp.pop(), stackOp.pop(), splitArr[i]);
				node.getLeft().parent = node;
				node.getRight().parent = node;
				stackOp.push(node);
				root = node;
			} else {
				BinaryNode<String> node = new BinaryNode<>(null, null, null, splitArr[i]);
				stackOp.push(node);
			}
		}
		return true;
	}

	public boolean buildFromInfix(String infix) {
		
		String[] splitArr = infix.split(" ");
		for (int i = 0; i < splitArr.length; i++) {
			System.out.println(queNum.toString());
			if (isOperator(splitArr[i])) {
				if(!part2Stack.isEmpty()) {
					while (hasHigherPrec(splitArr[i]) && !part2Stack.peek().contentEquals("(")) {
						queNum.add(part2Stack.pop());
						if (part2Stack.isEmpty()) {
						break;
						}
					}
				}
				part2Stack.push(splitArr[i]);
			} else if (isOpenP(splitArr[i])) {
				part2Stack.push(splitArr[i]);
			} else if (isCloseP(splitArr[i])) {
				while (!isOpenP(part2Stack.peek())) {
					queNum.add(part2Stack.pop());
				}
				if (isOpenP(part2Stack.peek())) {
					part2Stack.pop();
				}
			} else {
				queNum.add(splitArr[i]);
			}
		}
		while (!part2Stack.isEmpty()) {
			queNum.add(part2Stack.pop());
		}
		
		return true;
	}

	public void printInfixExpression() {

		printInfixExpression(root);
		System.out.println();
	}

	public void printPostfixExpression() {
		printPostfixExpression(root);
		System.out.println();
	}

	public int size() {
		size = 0;
		return size(root);
	}

	public boolean isEmpty() {
		return size != 0;
	}

	public int leafNodes() {
		return leafNodes(root);
	}

	private void printInfixExpression(BinaryNode n) {

		if (n.getLeft() != null) {
			System.out.print("( ");
			printInfixExpression(n.getLeft());
		}
		System.out.print(n.getElement() + " ");
		if (n.getRight() != null) {
			printInfixExpression(n.getRight());
			System.out.print(") ");

		}
	}

	private void makeEmpty(BinaryNode t) {
		root = null;

	}

	private void printPostfixExpression(BinaryNode n) {
		if (n.getLeft() != null) {
			printPostfixExpression(n.getLeft());
		}
		if (n.getRight() != null) {
			printPostfixExpression(n.getRight());
		}
		System.out.print(n.getElement() + " ");
	}

	private int size(BinaryNode t) {
		if (t == null) {
			return 0;
		}
		if (t.getLeft() != null) {
			size(t.getLeft());
		}
		if (t.getRight() != null) {
			size(t.getRight());
		}
		return (size += 1);
	}

	private int leafNodes(BinaryNode t) {
		if (t.getLeft() != null) {
			leafNodes(t.getLeft());
		}
		if (t.getRight() != null) {
			leafNodes(t.getRight());
		}
		if (t.getLeft() == null && t.getRight() == null)
			leaf++;
		return leaf;
	}

	private boolean isOperator(String s) {
		switch (s) {
		case "*":
		case "/":
		case "+":
		case "-":
			return true;
		default:
			return false;
		}
	}

	private boolean isOpenP(String s) {
		return s.equals("(");
	}

	private boolean isCloseP(String s) {
		return s.equals(")");
	}

	private boolean isP(String s) {
		return s.equals("(") || s.equals(")");
	}

	private boolean hasHigherPrec(String s) { // (higher or equivalent)
		if (s.equals("/") && stackOp.peek().element.equals("+")) {
			return false;
		} else if (s.equals("*") && part2Stack.peek().equals("+")) {
			return false;
		} else if (s.equals("/") && part2Stack.peek().equals("-")) {
			return false;
		} else if (s.equals("*") && part2Stack.peek().equals("-")) {
			return false;
		} else
			return true;
	}

	private boolean hasEqualPrec(String s) {
		if (s.equals("/") && part2Stack.peek().equals("/")) {
			return true;
		} else if (s.equals("/") && part2Stack.peek().equals("*")) {
			return true;
		} else if (s.equals("*") && part2Stack.peek().equals("/")) {
			return true;
		} else if (s.equals("*") && part2Stack.peek().equals("*")) {
			return true;
		} else if (s.equals("-") && part2Stack.peek().equals("-")) {
			return true;
		} else if (s.equals("-") && part2Stack.peek().equals("+")) {
			return true;
		} else if (s.equals("+") && part2Stack.peek().equals("-")) {
			return true;
		} else if (s.equals("+") && part2Stack.peek().equals("+")) {
			return true;
		} else
			return false;
	}
}
