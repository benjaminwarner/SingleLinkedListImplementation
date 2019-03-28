/**
 * LinearNode is a helper class that links an element to another one.
 *
 * @author Boise State Computer Science Department
 */

public class LinearNode<E> {
	private LinearNode<E> next;
	private E element;

	public LinearNode() {
		next = null;
		element = null;
	}

	public LinearNode(E element) {
		next = null;
		this.element = element;
	}

	public LinearNode<E> getNext() {
		return next;
	}

	public void setNext(LinearNode<E> node) {
		next = node;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}
}
