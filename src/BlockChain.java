public class BlockChain<T>{
  class Node {
    // +--------+-----------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The data stored in the node.
     */
    T data;

    /**
     * The next node in the list.  Set to null at the end of the list.
     */
    Node next;

    // +--------------+-----------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Create a new node with specified data and next.
     */
    public Node(T data, Node next)
    {
      this.data = data;
      this.next = next;
    } // Node(T, Node)
  } // class Node

}
