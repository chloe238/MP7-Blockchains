import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;

/**
 * The block that contains transaction data
 * 
 * @author Chloe Kelly
 * @author Lydia Ye
 */
public class Block {
  // +--------+-----------------------------------------------------------
  // | Fields |
  // +--------+
  /**
   * Block number
   */
  int blockNum;

  /**
   * Transaction amount
   */
  int amount;

  /**
   * The previous block's hash
   */
  Hash prevHash;

  /**
   * This block's nonce
   */
  long nonce;

  /**
   * This block's hash
   */
  Hash thisHash;

  // +--------------+-----------------------------------------------------
  // | Constructors |
  // +--------------+
  /**
   * Creates a new block and generates a nonce
   */
  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException{
    this.blockNum = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = calculateNonce();
  } //Block(int, int, Hash)

  /**
   * Creates a new block with a given nonce
   */
  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    this.blockNum = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;
    this.thisHash = new Hash(calculateHash(num, amount, prevHash, nonce));
  }//Block(int, int, Hash, long)

  // +----------------+---------------------------------------------------
  // | Public Methods |
  // +----------------+
  /**
   * Returns the block number of this block
   */
  public int getNum() {
    return this.blockNum;
  } //getNum()

  /**
   * Returns the transaction amount of this block
   */
  public int getAmount() {
    return this.amount;
  } //getAmount()

  /**
   * Returns the nonce of this block
   */
  public long getNonce() {
    return this.nonce;
  } //getNonce()

  /**
   * Returns the previous hash of this block
   */
  public Hash getPrevHash(){
    return this.prevHash;
  } //getPrevHash()

  /**
   * Returns the hash of this block
   */
  public Hash getHash() {
    return this.thisHash;
  } //getHash()

  /**
   * Returns the string representation of this block
   */
  public String toString() {
    return "Block " + blockNum + " (Amount: " + amount + ", Nonce: " + nonce + ", prevHash: " + prevHash + ", Hash: " + thisHash + ")";
  } //toString()

  // +-----------------+--------------------------------------------------
  // | Private Methods |
  // +-----------------+
  /**
   * Generates a hash based on the given block number, amount, previous hash if given, and nonce
   * @throws NoSuchAlgorithmException
   */
  private byte[] calculateHash(Integer num, Integer amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("sha-256");
    md.reset();
    md.update(ByteBuffer.allocate(Integer.BYTES).putInt(num).array());
    md.update(ByteBuffer.allocate(Integer.BYTES).putInt(amount).array());

    if (prevHash != null) {
      md.update(ByteBuffer.allocate(prevHash.getData().length).put(prevHash.getData()).array());
    }
    md.update(ByteBuffer.allocate(Long.BYTES).putLong(nonce).array());
    byte[] hash = md.digest();
    return hash;
  } // calculateHash(int, int, Hash, long)

  /**
   * Generates a nonce and sets this current block's hash to the hash given by the valid nonce
   * @throws NoSuchAlgorithmException
   */
  private long calculateNonce() throws NoSuchAlgorithmException{
    byte[] data;
    long test = -1;
    Hash hash;

    // Loop through all possible nonce values until the valid one is found
    do {  
      test++;    
      data = calculateHash(this.blockNum, this.amount, this.prevHash, test);
      hash = new Hash(data); 
    } while(!hash.isValid());

    this.thisHash = hash;
    // return the valid nonce
    return test;
  } // calculateNonce()
} // Block
