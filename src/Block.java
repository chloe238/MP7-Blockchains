import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;

public class Block {
  /* Fields */
  int blockNum;
  int amount;
  Hash prevHash;
  long nonce;
  Hash thisHash;

  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException{
    this.blockNum = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = calculateNonce();
  }

  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    this.blockNum = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;
    this.thisHash = new Hash(calculateHash(num, amount, prevHash, nonce));
  }

  public int getNum() {
    return this.blockNum;
  }

  public int getAmount() {
    return this.amount;
  }

  public long getNonce() {
    return this.nonce;
  }

  public Hash getPrevHash(){
    return this.prevHash;
  }

  public Hash getHash() {
    return this.thisHash;
  }

  public String toString() {
    return "Block " + blockNum + " (Amount: " + amount + ", Nonce: " + nonce + ", prevHash: " + prevHash + ", Hash: " + thisHash + " )";
  }

  public static byte[] calculateHash(Integer num, Integer amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
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
  } // calculateHash(String)

  private long calculateNonce() throws NoSuchAlgorithmException{
    byte[] data;
    long test = -1;
    Hash hash;
    do {  
      test++;    
      data = calculateHash(this.blockNum, this.amount, this.prevHash, test);
      hash = new Hash(data); 
    } while(!hash.isValid());
    this.thisHash = hash;
    return test;
  }
} // Block
