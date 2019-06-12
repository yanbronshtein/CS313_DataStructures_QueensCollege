
/**
 * This interface defines the methods to be implemented by the singly linked list class
 * @author Alex Chen
 * @version 1.0
 * */


interface LinkedListInterface<T>
{
    boolean isEmpty();
    int size();
    void insert(Node<T> spot);

}