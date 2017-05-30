// A stack-variant of a IList
public class Stack<T> implements ICollection<T> {
  
  Deque<T> stack;
  
  Stack() {
    this.stack = new Deque<T>();
  }

  @Override
  public T remove() {
    return this.stack.removeFromHead();
  }

  @Override
  public void add(T item) {
    this.stack.addAtHead(item);    
  }
  
  @Override
  public boolean isEmpty() {
    return this.stack.isEmpty();
  }

  public boolean contains(T item) {
    return this.stack.contains(item);
  }
}
