
/** 
 * An interface definition for the Stack data type
 * Every method in this interface must run in O(1) time
*/
public interface StackIntf<E> {  
    /**
     * Tests if this stack is empty.
     * @return true if and only if this stack contains no items; false otherwise.
     */
    public boolean empty();

    /**
     * Pushes an item onto the top of this stack.
     * @param e the element to be added to the stack or null if the items could not be added
     * @return the added element.
     */
    public E push(E e);

    /**
     * Looks at the object at the top of this stack without removing it from the stack.
     * @return the item at the top of the stack or null if the stack is empty
     */
    public E peek();

    /**
     * Removes the object at the top of this stack 
     * and returns that object as the value of this function.
     * @return The removed object or null if the stack is empty
     */
    public E pop();

    /**
     * Returns the number of items in the stack
     * @return The number of items in the stack.
     */
    public int size();
}