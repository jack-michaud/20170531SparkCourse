// A queue-variant of a IList
public class Queue<T> implements ICollection<T> {

  Deque<T> queue;
  
  Queue() {
    this.queue = new Deque<T>();
  }

  @Override
  public T remove() {
    return this.queue.removeFromHead();
  }

  @Override
  public void add(T item) {
    this.queue.addAtTail(item);    
  }
  
  @Override
  public boolean isEmpty() {
    return this.queue.isEmpty();
  }
  
//  @Override
//  public int length() {
//    return this.queue.size();
//  }
  
  public boolean contains(T item) {
    return this.queue.contains(item);
  }

}