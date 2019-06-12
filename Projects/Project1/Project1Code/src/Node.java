/**This class provides the definition for a generic singly linked list node
 *
 * @author Yaniv Bronshtein
 *
 *
 * */
class Node<T> {

    private T data;
    private Node<T> next;

    /**
     * Constructs a node with no next pointer specified
     * @param data The information to be inserted into newly created node
     * */
    Node(T data)
    {
        this.data = data;
        this.next = null;
    }

    /**
     * Constructs a node with data, and next pointer specified
     * @param data The information to be inserted into newly created node
     * @param next Pointer to next node in the singly linked list
     * */
    Node(T data, Node<T> next)
    {
        this.data = data;
        this.next = next;
    }

    /**Setter method for data instance variable
     * @param data The information to be inserted into existing node */
    public void setData(T data)
    {
        this.data = data;
    }

    /**Getter method for data instance variable
     * @return data of existing node */
    public T getData()
    {
        return data;
    }

    /**Setter method for data instance variable
     * @param next Pointer to the next node in the singly linked list */
    public void setNext(Node<T> next)
    {
        this.next = next;
    }

    /**Getter method for next pointer
     * @return next pointer of existing node */
    public Node<T> getNext()
    {
        return next;
    }


}
