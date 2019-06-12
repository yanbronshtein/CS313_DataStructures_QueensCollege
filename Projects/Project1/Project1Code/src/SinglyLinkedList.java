/**
 * The LinkedList Class will be a list of all the terms in the polynomial.
 * The class implements the LinkedListInterface
 * @author Yaniv Bronshtein
 * @version 1.0
 * */
class SinglyLinkedList<T> implements LinkedListInterface<T>
{
    /**size of list */
    private int size;
    /**pointer to first element of the list */
    private Node<T> head;
    /**pointer to last element of the list */
    private Node<T> tail;



    /**Constructs an empty singly linked list */
    SinglyLinkedList()
    {
        head = tail = null;
        size = 0;
    }

    /**Getter method for head pointer
     * @return pointer to first element of the list
     * */
    public Node<T> getHead()
    {
        return head;
    }

    /**Setter method for head pointer
     * @param head New head Pointer */
    public void setHead(Node<T> head)
    {
        this.head = head;
    }

    /**Getter method for tail pointer
     * @return pointer to last element of the list
     * */
    public Node<T> getTail() {
        return tail;
    }

    /**Setter method for tail pointer
     * @param tail New tail pointer */
    public void setTail(Node<T> tail)
    {
        this.tail = tail;
    }

    /**Setter method for the size parameter
     * @param size new Size of the list */
    public void setSize(int size)
    {
        this.size = size;
    }

    /**Determines if the list is empty
     * @return true if the list is empty and false if it contains at least 1 element */
    public boolean isEmpty()
    {
        return (size == 0);
    }

    /**This method returns the number of elements in the list
     * @return size */
    public int size()
    {
        return size;
    }



    /** This method adds a node to the front of the list
     * @param spot New node to be inserted */
    public void addHead(Node<T> spot)
    {
        if (this.isEmpty())
        {
            this.head = spot;
            this.tail = head;
        }
        else {
            spot.setNext(this.head);
            this.head = spot;
        }

        spot.setNext(head);
        head = spot;
        if (this.isEmpty())
            tail = spot;
        this.size++;
    }

    /** This method adds a node to the end of the list
     * @param spot New node to be inserted */
    public void addTail(Node<T> spot)
    {
        if (this.isEmpty())
        {
            head = spot;
            tail = head;
        }
        else {
            tail.setNext(spot);
            tail = spot;
        }
        this.size++;
    }


    /**
     * This method inserts a term into the correct location in a sorted linked list based on the order of the term
     * If the term has the same order as any of the terms in the linked list, the coefficients of the two terms are
     * added and stored in the existing node without altering the size of the list
     * @param spot The term to be inserted into the Singly Linked List
     *
     * */
    public void insert(Node<T> spot)
    {
        Term spotData = (Term) spot.getData();

        /*Case1: where list is empty*/
        if (this.isEmpty() )
        {
            this.addTail(spot);
            return;
        }

        /*Case 2a: List not empty. Compare to head node*/
        Term headData = (Term) this.getHead().getData();

        //New element has a greater order than that of the head node
        if (spotData.compareTo(headData) > 0)
            this.addHead(spot);
        //The two elements have the same order
        else if (spotData.compareTo(headData) == 0){
            headData.setCoefficent(spotData.getCoefficent() + headData.getCoefficent());
        }
        /*Case 2b: List not empty but term needs to be inserted in middle of list */
        else
        {

            Node<T> curr = this.getHead();
            while (curr.getNext() != null && ((Term) curr.getNext().getData()).compareTo(spotData) >= 0)
            {
                curr = curr.getNext();
            }

        /*
        At this point there are only 3 possible cases:
        a). The element has the same order as that of another element
        b). The end of the list has been reached
        c). The element should be inserted in between two nodes

        */
            Term currData = (Term) curr.getData();

            //Case a
            if (currData.compareTo(spotData) == 0)
            {
                currData.setCoefficent(spotData.getCoefficent() + currData.getCoefficent());
                return;
            }
            //Case b
            if (curr == tail)
            {
                curr.setNext(spot);
                tail = spot;
            }

            //Case c
            else{
                spot.setNext(curr.getNext());
                curr.setNext(spot);
            }

            size++;
        }

    }


    /**This method cleans a linked list by removing every term with a coefficient of 0
     * */
    public void clean()
    {
        if (this.isEmpty()) return;
        Node<T> prev = null;
        Node<T> curr = head;
        while (curr != null) {
            if (((Term)curr.getData()).getCoefficent() == 0.0)
            {

                // Found something to remove
                this.size--;
                if(prev == null)
                {
                    head = curr.getNext();
                } else {
                    prev.setNext(curr.getNext());
                }
            }
            // Maintain loop invariant: prev is the node before cur.
            if (curr.getNext() != head)
            {
                prev = curr;
            }
            curr = curr.getNext();
        }
    }



}






