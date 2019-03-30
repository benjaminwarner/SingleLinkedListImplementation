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
		if (isEmpty()) {
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
		if (isEmpty()) {
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
			throw new IndexOutOfBoundsException();

		if (index == 0)
			addToFront(element);
		else if (index == size)
			addToRear(element);
		else {
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
	}

	@Override
	public void addAfter(T element, T target) {
		LinearNode<T> targetNode = null;
		LinearNode<T> current = head;
		LinearNode<T> newNode = new LinearNode<T>(element);

		for (int i = 0; i < size; ++i) {
			if (current.getElement().equals(target)) {
				targetNode = current;
				break;
			} else {
				current = current.getNext();
			}
		}

		if (targetNode == null)
			throw new NoSuchElementException();
		else if (targetNode == tail) {
			targetNode.setNext(newNode);
			tail = newNode;
		} else {
			newNode.setNext(targetNode.getNext());
			targetNode.setNext(newNode);
		}

		++size;
		++modCount;
	}

	@Override
	public T removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		T element = head.getElement();
		head = head.getNext();
		--size;
		++modCount;
		return element;
	}

	@Override
	public T removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();

		if (size == 1) {
			return removeFirst();
		} else {
			T element = tail.getElement();
			LinearNode<T> previous = head;
			for (int i = 0; i < size - 2; ++i)
				previous = previous.getNext();
			previous.setNext(null);
			tail = previous;
			--size;
			++modCount;
			return element;
		}
	}

	@Override
	public T remove(T element) {
		if (isEmpty())
			throw new NoSuchElementException();

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
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		T element;
		if (index == 0) {
			element = head.getElement();
			head = head.getNext();
		} else {
			LinearNode<T> previous = null;
			LinearNode<T> current = head;

			for (int i = 0; i < index; ++i) {
				previous = current;
				current = current.getNext();
			}
			element = current.getElement();
			previous.setNext(current.getNext());
		}

		--size;
		++modCount;
		return element;
	}

	@Override
	public void set(int index, T element) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		LinearNode<T> current = head;
		for (int i = 0; i < index; ++i)
			current = current.getNext();
		current.setElement(element);
	}

	@Override
	public T get(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		LinearNode<T> current = head;
		for (int i = 0; i < index; ++i) {
			current = current.getNext();
		}
		return current.getElement();
	}

	@Override
	public int indexOf(T element) {
		LinearNode<T> current = head;
		for (int i = 0; i < size; ++i) {
			if (current.getElement().equals(element))
				return i;
			current = current.getNext();
		}
		return -1;
	}

	@Override
	public T first() {
		if (isEmpty())
			throw new NoSuchElementException();
		return head.getElement();
	}

	@Override
	public T last() {
		if (isEmpty())
			throw new NoSuchElementException();
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		LinearNode<T> current = head;
		for (int i = 0; i < size; ++i) {
			if (current.getElement().equals(target))
				return true;
			current = current.getNext();
		}
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

	public String toString() {
		if (isEmpty())
			return "[]";
		String str = "[";
		LinearNode<T> current = head;
		for (int i = 0; i < size - 1; ++i) {
			str += current.getElement().toString() + ", ";
			current = current.getNext();
		}
		str += current.getElement().toString() + "]";
		return str;
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
		private boolean canRemove;

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
			canRemove = true;
			return element;
		}

		@Override
		public void remove() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			else if (!canRemove)
				throw new IllegalStateException();
			canRemove = false;
		}
	}
}
