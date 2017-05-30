import tester.Tester;

/* Deque - Double-Ended Queue
 * Methods:
 *  addAtHead(T item) - Add the given item at the start of this Deque
 *  removeFromHead() -> T - Remove the first item in this Deque, and return it
 *  popIgnore() - Remove the first item in this Deque, DO NOT RETURN IT
 *  addAtTail(T item) - Add the given item at the end of this Deque
 *  removeFromTail() -> T - Remove the last item from this Deque, and return it
 *  pullIgnore() - Remove the last item from this Deque, DO NOT RETURN IT
 *  size() -> int - How big is this Deque?
 *  isEmpty() -> boolean - Is this Deque empty?
 */
class Deque<T> {

  ANode<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> toUse) {
    this.header = toUse;
  }

  // EFFECTS: Add item to the top of the deque
  public void addAtHead(T item) {
    this.header.pushNext(item);
  }

  // EFFECTS: Remove item from the top of the deque, return it
  public T removeFromHead() {
    return this.header.popNext();
  }

  // EFFECTS: Remove item from the top, ignore its value
  public void popIgnore() {
    this.header.removeNext();
  }

  // EFFECTS: Add item to the bottom of the deque
  public void addAtTail(T item) {
    this.header.pushPrev(item);
  }

  // EFFECTS: Remove item from the bottom of the deque, return it
  public T removeFromTail() {
    return this.header.popPrev();
  }

  // EFFECTS: Remove item from the bottom of the deque, ignore its value
  public void pullIgnore() {
    this.header.removePrev();
  }

  // How many nodes does this deque have? (ignoring sentinal)
  public int size() {
    return this.header.next.count();
  }
  
  // Is this an empty Deque?
  public boolean isEmpty() {
    return this.header == this.header.next;
  }
  
  public boolean contains(T item) {
    return this.header.contains(item);
  }

}


abstract class ANode<T> {

  ANode<T> next;
  ANode<T> prev;

  // EFFECTS: Add the given item, as a new node, next to this node
  void pushNext(T item) {
    new Node<T>(item, this.next, this);
  }

  // EFFECTS: Add the given item, as a new node, previous to this node
  void pushPrev(T item) {
    new Node<T>(item, this, this.prev);
  }

  // EFFECTS: Remove the element next to this node
  // Throws RuntimeException on empty deque
  void removeNext() {
    this.next.getData();
    this.next = this.next.next;
    this.next.prev = this;
  }

  // Return the next elements data
  // EFFECTS: Remove the element next to this node
  // Throws RuntimeException on empty deque
  T popNext() {
    T data = this.next.getData();
    this.removeNext();
    return data;
  }

  // EFFECTS: Remove the element previous to this node
  // Throws RuntimeException on empty deque
  void removePrev() {
    this.prev.getData();
    this.prev = this.prev.prev;
    this.prev.next = this;
  }

  // Return the previous elements data
  // EFFECTS: Remove the element previous to this node
  // Throws RuntimeException on empty deque
  T popPrev() {
    T data = this.prev.getData();
    this.removePrev();
    return data;
  }

  // Return this.data or throw an exception if sentinel
  T getData() {
    throw new RuntimeException("Sentinel");
  }

  // Count the number of elements in this list
  int count() {
    return 0;
  }
  
  boolean contains(T item) {
    return false;
  }
}


class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  Sentinel(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }
}


class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    this.next = null;
    this.prev = null;
    this.data = data;
  }

  // EFFECTS: prev of first Node; and next of second Node
  Node(T data, ANode<T> next, ANode<T> prev) {
    if (next == null || prev == null) {
      throw new IllegalArgumentException("Null passed to Node constructor");
    }

    this.next = next;
    this.prev = prev;
    this.data = data;

    next.prev = this;
    prev.next = this;
  }

  // Return this.data or throw an exception if sentinal
  @Override
  T getData() {
    return this.data;
  }

  // Count the number of elements in this deque
  @Override
  int count() {
    return 1 + this.next.count();
  }
  
  boolean contains(T item) {
    return this.data == item || this.next.contains(item);
  }
}

class ExamplesDeque {

  Deque<String> deque1 = new Deque<String>();
  Deque<String> deque2 = new Deque<String>();
  Deque<String> deque3 = new Deque<String>();

  Deque<Integer> dequeInt2 = new Deque<Integer>();
  Deque<Integer> dequeInt3 = new Deque<Integer>();

  void reset() {
    deque1 = new Deque<String>();

    deque2 = new Deque<String>();

    new Node<String>("def", deque2.header,
        new Node<String>("cde", deque2.header, new Node<String>("bcd", deque2.header,
            new Node<String>("abc", deque2.header, deque2.header))));

    deque3 = new Deque<String>();
    new Node<String>("bob", deque3.header,
        new Node<String>("john", deque3.header, new Node<String>("steve", deque3.header,
            new Node<String>("ben", deque3.header, deque3.header))));

    dequeInt2 = new Deque<Integer>();

    new Node<Integer>(4, dequeInt2.header,
        new Node<Integer>(3, dequeInt2.header, new Node<Integer>(2, dequeInt2.header,
            new Node<Integer>(1, dequeInt2.header, dequeInt2.header))));

    dequeInt3 = new Deque<Integer>();

    new Node<Integer>(5, dequeInt3.header,
        new Node<Integer>(7, dequeInt3.header, new Node<Integer>(3, dequeInt3.header,
            new Node<Integer>(6, dequeInt3.header, dequeInt3.header))));
  }

  Void testThis(Tester t) {
    this.reset();
    return null;
  }

  Void testSize(Tester t) {
    this.reset();
    t.checkExpect(deque1.size(), 0);
    t.checkExpect(deque2.size(), 4);
    return null;
  }

  Void testAddAtHead(Tester t) {
    this.reset();
    deque1.addAtHead("a");
    t.checkExpect(deque1.header.next.getData(), "a");
    dequeInt3.addAtHead(4);
    t.checkExpect(dequeInt3.header.next.getData(), 4);
    return null;
  }

  Void testAddAtTail(Tester t) {
    this.reset();
    deque1.addAtTail("a");
    t.checkExpect(deque1.header.prev.getData(), "a");
    dequeInt3.addAtTail(4);
    t.checkExpect(dequeInt3.header.prev.getData(), 4);
    return null;
  }

  Void testRemoveFromHead(Tester t) {
    this.reset();

    t.checkExpect(deque2.removeFromHead(), "abc");
    t.checkExpect(dequeInt2.removeFromHead(), 1);
    return null;
  }

  Void testRemoveFromTail(Tester t) {
    this.reset();

    t.checkExpect(deque2.removeFromTail(), "def");
    t.checkExpect(dequeInt2.removeFromTail(), 4);
    return null;
  }

}
