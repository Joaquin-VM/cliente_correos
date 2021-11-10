// ListStack class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void push( x )         --> Insert x
// void pop( )            --> Remove most recently inserted item
// AnyType top( )         --> Return most recently inserted item
// AnyType topAndPop( )   --> Return and remove most recent item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// top, pop, or topAndPop on empty stack

/**
 * List-based implementation of the stack.
 *
 * @author Mark Allen Weiss
 */
public class ListStack<AnyType> implements Stack<AnyType> {

  private ListNode<AnyType> topOfStack;
  private int size;

  /**
   * Construct the stack.
   */
  public ListStack() {
    topOfStack = null;
    size = 0;
  }

  /**
   * Test if the stack is logically empty.
   *
   * @return true if empty, false otherwise.
   */
  public boolean isEmpty() {
    return topOfStack == null;
  }

  /**
   * Make the stack logically empty.
   */
  public void makeEmpty() {
    topOfStack = null;
    size = 0;
  }

  /**
   * Insert a new item into the stack.
   *
   * @param x the item to insert.
   */
  public void push(AnyType x) {
    topOfStack = new ListNode<>(x, topOfStack);
    ++size;
  }

  /**
   * Remove the most recently inserted item from the stack.
   *
   * @throws Exception if the stack is empty.
   */
  public void pop() throws Exception {
    if (isEmpty()) {
      throw new Exception("ListStack pop");
    }
    topOfStack = topOfStack.next;
    --size;
  }

  /**
   * Get the most recently inserted item in the stack. Does not alter the stack.
   *
   * @return the most recently inserted item in the stack.
   * @throws Exception if the stack is empty.
   */
  public AnyType top() throws Exception {
    if (isEmpty()) {
      throw new Exception("ListStack top");
    }
    return topOfStack.element;
  }

  /**
   * Return and remove the most recently inserted item from the stack.
   *
   * @return the most recently inserted item in the stack.
   * @throws Exception if the stack is empty.
   */
  public AnyType topAndPop() throws Exception {
    if (isEmpty()) {
      throw new Exception("ListStack topAndPop");
    }

    AnyType topItem = topOfStack.element;
    topOfStack = topOfStack.next;
    --size;
    return topItem;
  }

  public int size() {
    return size;
  }

}