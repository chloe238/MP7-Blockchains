import java.security.NoSuchAlgorithmException;
import java.io.PrintWriter;

/**
 * The linked list of blocks we will use to track transactions
 * 
 * @author Chloe Kelly
 * @author Lydia Ye
 * @author Samuel A. Rebelsky (node inner class)
 */

public class BlockChain{
  // +--------+-----------------------------------------------------------
  // | Fields |
  // +--------+
  
  /**
   * Pointer to the first block in the chain
   */
  Node first;

  /**
   * Pointer to the last block in the chain
   */
  Node last;
  
  // +--------------+-----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Creates a new BlockChain
   */
  public BlockChain(int initial) throws NoSuchAlgorithmException{
    this.first = new Node(new Block(0, initial, null), null);
    this.last = this.first;
  }// BlockChain(int)
  
  // +---------+----------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Creates a new candiate block with the given amount to be added to the chain
   * @throws NoSuchAlgorithmException
   */
  public Block mine(int amount) throws NoSuchAlgorithmException {  
    Block result = new Block(this.last.data.getNum() + 1, amount, this.last.data.getHash());    
    //Create node space
    Node block = new Node(result, null);
    //Add to the end of the chain
    this.last.next = block;
    this.last = this.last.next;
    return result;
  } // mine(int)

  /**
   * Returns the size of the blockchain
   */
  public int getSize(){
    return this.last.data.getNum() + 1;
  } // getSize()

  /**
   * Adds given block to the end of the chain
   * @throws IllegalArgumentException if the block is not valid 
   */
  public void append(Block blk) throws IllegalArgumentException{
    if(!blk.thisHash.isValid()){
      throw new IllegalArgumentException();
    } //if
    Node block = new Node(blk, null);
    this.last.next = block;
    this.last = this.last.next;
  } //append(Block)

  /**
   * Removes the last block in the chain, returning true if complete.
   * Returns false and does nothing if there is only one block in the chain
   * 
   */
  public boolean removeLast(){
    if(!this.first.equals(this.last)){
      Node tmp = this.first;
      while(!tmp.next.equals(this.last)){
        tmp = tmp.next;
        //find the block before the last one
      }//while
      this.last = tmp;
      //remove the last block
      this.last.next = null;
      return true;
    }//if
    return false;
  } //removeLast()

  /**
   * Returns the hash of the last block in the chain.
   */
  public Hash getHash(){
    return this.last.data.getHash();
  } //getHash()

  /**
   * Returns true if the blockchain transactions are legal
   */
  public boolean isValidBlockChain(){
    Node tmp = this.first;
    int sum = 0;
    while(tmp != null){
      sum += tmp.data.amount;
      if (sum > this.first.data.getAmount() || sum < 0){
        return false;
      }//if
      tmp = tmp.next;
    }//while
    return true;
  }// isValidBlockChain()

  /**
   * Prints Alexis' and Blake's balances
   */
  public void printBalances(){
    PrintWriter pen = new PrintWriter(System.out, true);
    Node tmp = this.first.next;
    int alexis = this.first.data.amount;
    int blake = 0;
    while(tmp != null){
      alexis += tmp.data.amount;
      blake -= tmp.data.amount;
      tmp = tmp.next;
    } //while
    pen.println("Alexis: " + alexis + ", Blake: " + blake);
  }// printBalances

  /**
   * Returns the string representation of the blockchain
   */
  public String toString(){
    String res = "";
    Node tmp = this.first;
    while(tmp != null){
      res += tmp.data.toString() + "\n";
      tmp = tmp.next;
    } //while
    return res;
  } // toString();

  class Node {
    // +--------+-----------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The data stored in the node.
     */
    Block data;

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
    public Node(Block data, Node next)
    {
      this.data = data;
      this.next = next;
    } // Node(T, Node)
  } // class Node

}
