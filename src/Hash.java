public class Hash {

  byte[] data;

  Hash(byte[] _data){
    this.data = _data;
  }

  public byte[] getData(){
    return this.data;
  } 

  public boolean isValid(){
    for(int i = 0; i < 3; i++){
      if (Byte.toUnsignedInt(this.data[i]) != 0){
        return false;
      }
    }
    return true;
  }

  public String toString(){
    String result = "";
    for(byte b : this.data){
      result += String.format("%02X", Byte.toUnsignedInt(b));
    }
    return result;
  }

  public boolean equals(Object other){
    Hash o;
    if(other instanceof Hash){
      o = (Hash) other;
    } else {
      return false;
    }
    return this.data == o.data;
  }
}