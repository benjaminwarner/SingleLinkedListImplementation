import java.util.Iterator;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 *
 * @author Benjamin Warner
 *
 * @param <T> type to store
 */

public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private LinearNode<T> head, tail;
	private int size;
	private int modCount;

	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = null;
		tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		if (size == 0) {
			head = tail = new LinearNode<T>(element);
		} else {
			LinearNode<T> currentHead = head;
			head = new LinearNode<T>(element);
			head.setNext(currentHead);
		}
		++size;
		++modCount;
	}

	@Override
	public void addToRear(T element) {
		if (size == 0) {
			head = tail = new LinearNode<T>(element);
		} else {
			LinearNode<T> currentTail = tail;
			tail = new LinearNode<T>(element);
			currentTail.setNext(tail);
		}
		++size;
		++modCount;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void add(int index, T element) {
		if (index > size || index < 0)
			throw new NoSuchElementException();

		LinearNode<T> current = head;
		LinearNode<T> previous = null;
		LinearNode<T> newNode = new LinearNode<T>(element);

		for (int i = 0; i < index; ++i) {
			previous = current;
			current = current.getNext();
		}

		newNode.setNext(current);
		if (previous != null)
			previous.setNext(newNode);
		++size;
		++modCount;
	}

	@Override
	public void addAfter(T element, T target) {
		// TODO
	}

	@Override
	public T removeFirst() {
		// TODO
		return null;
	}

	@Override
	public T removeLast() {
		// TODO
		return null;
	}

	@Override
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		boolean found = false;
		LinearNode<T> previous = null;
		LinearNode<T> current = head;

		while (current != null && !found) {
			if (element.equals(current.getElement()))
				found = true;
			else {
				previous = current;
				current = current.getNext();
			}
		}

		if (!found)
			throw new NoSuchElementException();

		if (size() == 1)
			head = tail = null;
		else if (current == head)
			head = current.getNext();
		else if (current == tail) {
			tail = previous;
			tail.setNext(null);
		} else
			previous.setNext(current.getNext());

		--size;
		++modCount;
		return current.getElement();
	}

	@Override
	public T remove(int index) {
		// TODO
		return null;
	}

	@Override
	public void set(int index, T element) {
		// TODO
	}

	@Override
	public T get(int index) {
		// TODO
		return null;
	}

	@Override
	public int indexOf(T element) {
		// TODO
		return -1;
	}

	@Override
	public T first() {
		// TODO
		return null;
	}

	@Override
	public T last() {
		return null;
	}

	@Override
	public boolean contains(T target) {
		// TODO
		return false;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public int size() {
		return this.size;
	}

	private LinearNode<T> find(T element) {
		return null;
	}

	private LinearNode<T> findNodeAt(int index) {
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private LinearNode<T> nextNode;
		private int iterModCount;

		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			return nextNode != null;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			T element = nextNode.getElement();
			nextNode = nextNode.getNext();
			return element;
		}

		@Override
		public void remove() {
			// TODO
		}
	}
}
