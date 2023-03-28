package stackPD;

public class LinkedStack<E> {
	private DoublyLinkedList<E> list;
	
	public LinkedStack()
	{
		list = new DoublyLinkedList<E>();
	}
	
	public void push(E e) {
		list.addFirst(e);
	}
	
	public E pop() {
		if (this.isEmpty())
			return null;
		else
			return list.removeFirst();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public E peek() {
		if (this.isEmpty())
			return null;
		else
			return list.getFirst();
	}
}