import java.util.Arrays;

/**
 * The wrapper class for our hashes
 * 
 * @author Chloe Kelly
 * @author Lydia Ye
 */
public class Hash {
  // +--------+-----------------------------------------------------------
  // | Fields |
  // +--------+
  /**
   * The byte array for the hash
   */
  byte[] data;

  // +-------------+------------------------------------------------------
  // | Constructor |
  // +-------------+
  /**
   * Creates a new Hash
   */
  Hash(byte[] _data){
    this.data = _data;
  } //Hash(byte[])

  // +---------+----------------------------------------------------------
  // | Methods |
  // +---------+
  /**
   * Returns the byte array of data
   */
  public byte[] getData(){
    return this.data;
  } //getData()

  /**
   * Checks if the Hash is valid based on the criteria that its first three indices contain zeroes
   */
  public boolean isValid(){
    for(int i = 0; i < 3; i++){
      if (Byte.toUnsignedInt(this.data[i]) != 0){
        return false;
      }//if
    }//for
    return true;
  }//isValid()

  /**
   * Returns the string representation of the hash as a string of hexadecimal digits, 2 digits per byte
   */
  public String toString(){
    String result = "";
    for(byte b : this.data){
      result += String.format("%02X", Byte.toUnsignedInt(b));
    }//for
    return result;
  }//toString()

  /**
   * Returns true if this hash is structurally equal to the argument
   */
  public boolean equals(Object other){
    Hash o;
    if(other instanceof Hash){
      o = (Hash) other; //cast parameter as a Hash
    } else {
      return false;
    }//if/else
    return Arrays.equals(this.data, o.data); //compare and return
  } //equals()
}// class Hash(byte[])