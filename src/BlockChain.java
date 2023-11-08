import java.security.NoSuchAlgorithmException;
import java.io.PrintWriter;

public class BlockChain{
  
  // Fields -----------
  Node first;
  Node last;
  
  public BlockChain(int initial) throws NoSuchAlgorithmException{
    this.first = new Node(new Block(0, initial, null), null);
    this.last = this.first;
  }
  
  public Block mine(int amount) throws NoSuchAlgorithmException {  
    Block result = new Block(this.last.data.getNum() + 1, amount, this.last.data.getPrevHash());    
    Node block = new Node(result, null);
    this.last.next = block;
    this.last = this.last.next;
    return result;
  }

  public int getSize(){
    return this.last.data.getNum() + 1;
  }

  public void append(Block blk) throws IllegalArgumentException{
    if(!blk.thisHash.isValid()){
      throw new IllegalArgumentException();
    }
    Node block = new Node(blk, null);
    this.last.next = block;
    this.last = this.last.next;
  }

  public boolean removeLast(){
    if(!this.first.equals(this.last)){
      Node tmp = this.first;
      while(!tmp.next.equals(this.last)){
        tmp = tmp.next;
      }
      this.last = tmp;
      this.last.next = null;
      return true;
    }
    return false;
  }

  public Hash getHash(){
    return this.last.data.getHash();
  }

  public boolean isValidBlockChain(){
    Node tmp = this.first;
    int sum = 0;
    while(tmp != null){
      sum += tmp.data.amount;
      tmp = tmp.next;
    }
    return (sum > 0);
    //not correct. should probably compare each pair of transactions instead of total value
  }

  public void printBalances(){
    PrintWriter pen = new PrintWriter(System.out, true);
    Node tmp = this.first.next;
    int alexis = this.first.data.amount;
    int blake = 0;
    while(tmp != null){
      alexis += tmp.data.amount;
      blake -= tmp.data.amount;
      tmp = tmp.next;
    }
    pen.println("Alexis: " + alexis + ", Blake: " + blake);
  }

  public String toString(){
    String res = "";
    Node tmp = this.first;
    while(tmp != null){
      res += tmp.data.toString() + "\n";
      tmp = tmp.next;
    }
    return res;
  }

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
