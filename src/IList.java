
/* IList --
 * Methods:
 *  pop() -> T; get next on the IList
 *  push(T item); add this item to the IList
 *  length() -> int; how many left?
 *  isEmpty() -> boolean; is this IList empty? 
 */
public interface IList<T> {

  // Get the next item from this IList and remove it from the IList
  // () -> T
  T pop();
  
  // Add an item to the IList
  // T -> ()
  void push(T item);

  // What is the current length of the IList?
  // () -> int
  int length();

  // Is this IList empty?
  // () -> Boolean
  boolean isEmpty();
  
  // Contains?
  // T -> Boolean
  boolean contains(T item);
  
  
}