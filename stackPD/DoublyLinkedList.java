package stackPD;

import java.util.ArrayList;

public class DoublyLinkedList<E> {
	private Node<E> head;
	private Node<E> tail;
	private int size;
	
	public DoublyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public DoublyLinkedList<E> addFirst(E e) {
		Node<E> newest = new Node<E>(e);
		newest.setNext(head);
		newest.setPrev(null);
		if(this.isEmpty())
			tail = newest;
		else
			head.setPrev(newest);
		head = newest;
		size++;
		
		return this;
	}
	
	public DoublyLinkedList<E> addLast(E e) {
		Node<E> newest = new Node<E>(e);
		newest.setNext(null);
		newest.setPrev(tail);
		if(this.isEmpty())
			head = newest;
		else
			tail.setNext(newest);
		tail = newest;
		size++;
		
		return this;
	}
	
	public E getFirst() {
		return head.getElement();
	}
	
	public E removeFirst() {
		E temp = null;
		
		if(!this.isEmpty())
		{
			temp = head.getElement();
			if (size != 1)
			{
				head = head.getNext();
				head.setPrev(null);
			}
			size--;
			
			return temp;
		}
		else
			return null;
	}
	
	public E removeLast() {
		E temp = null;
		
		if(!this.isEmpty())
		{
			temp = tail.getElement();
			tail = tail.getPrev();
			tail.setNext(null);
			size--;
			
			return temp;
		}
		else
			return null;
			
	}
	
	public ArrayList<E> toArrayFromFirst() {
		ArrayList<E> list = new ArrayList<E>();
		Node<E> listNode = head;
		
		while (listNode != null)
		{
			list.add(listNode.getElement());
			listNode = listNode.getNext();
		}
		
		return list;
	}
	
	public ArrayList<E> toArrayFromLast() {
		ArrayList<E> list = new ArrayList<E>();
		Node<E> listNode = tail;
		
		while (listNode != null)
		{
			list.add(listNode.getElement());
			listNode = listNode.getPrev();
		}
		
		return list;
	}
	
	public DoublyLinkedList<E> clone() {
		DoublyLinkedList<E> listCopy = new DoublyLinkedList<E>();
		Node<E> listNode = head;
		
		while (listNode != null)
		{
			listCopy.addLast(listNode.getElement());
			listNode = listNode.getNext();
		}
		
		return listCopy;
	}
}

class Node<E> {
	private Node<E> prev;
	private Node<E> next;
	private E element;
	
	public Node(E e) {
		element = e;
	}

	public Node<E> getPrev() {
		return prev;
	}

	public void setPrev(Node<E> prev) {
		this.prev = prev;
	}

	public Node<E> getNext() {
		return next;
	}

	public void setNext(Node<E> next) {
		this.next = next;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}
}