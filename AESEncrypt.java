/**
 * file: AESEncrypt.java
 * @author Zach Stecher
 * Class: MSCS630 - Security Algorithms and Protocols
 * Assignment: Lab 3
 * Due Date: TBA
 * 
 * This file contains the methods to encrypt a plaintext message
 * using the AES algorithm.
 *
 */

public class AESEncrypt {
  
  public static void main(String[] args){

  }
  
  /*
 * Method: aesStateXor
 * Parameters:
 * 
 * Input - sHex, keyHex
 * Output - outStateHex
 *
 * This method takes the state input "sHex" and XORs it with
 * the current round's key "keyHex", and returns the result
 * "outStateHex".
 */

public String[][] aesStateXOR(String[][] sHex, String[][] keyHex){
  String[][] outStateHex = new String[4][4];
  for (int i = 0; i < 4; i++){
    for(int j = 0; j < 4; j++){
      outStateHex[i][j] = roundXOR(sHex[i][j], keyHex[i][j]);
    }
  }
  return outStateHex;
}

  /*
   * Method: NibbleXOR
   * 
   * Parameters:
   * Input - (n1Hex, n2Hex)
   * Output - outHex
   * 
   * This method takes the input pair of hex strings, performs the 
   * XOR addition, and returns the result 'outHex'.
   */  
   
  public static String NibbleXOR(String n1Hex, String n2Hex){
    
    // Split the first Nibble into 2 values and convert them to decimal...
    int a = hex2decimal(n1Hex.substring(0, 1));
    int b = hex2decimal(n1Hex.substring(1, n1Hex.length()));
    
    // Do the same for the second Nibble
    int c = hex2decimal(n2Hex.substring(0, 1));
    int d = hex2decimal(n2Hex.substring(1, n2Hex.length()));
    
    // Perform the XOR and write the result to outHex
    String outHex = (Integer.toHexString(a^c) + Integer.toHexString(b^d));
        
    return outHex;
  }
  
  
  
  /*
   * hex2decimal
   *
   * This method takes a hexadecimal string value s and transforms it into 
   * its corresponding decimal value val.
   *
   * Parameters: s
   * Returns: val
   */
  public static int hex2decimal(String s) {
    String digits = "0123456789ABCDEF";
    s = s.toUpperCase();
    int val = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      int d = digits.indexOf(c);
      val = 16*val + d;
    }
    return val;
  }
}
