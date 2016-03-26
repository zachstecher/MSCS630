/**
 * @file: aescipher.java
 * @author Zach Stecher
 * @Class: MSCS630 - Security Algorithms and Protocols
 * @Assignment: Labs 2 & 3
 * @Due Date: February 23rd, 2016; March 29th, 2016
 * 
 * This file contains the methods to generate AES cipher round
 * keys given a secret key as a hex value String.
 * 
 * This file also contains the methods to encrypt a 16-byte
 * plaintext using a given secret key, and produce a ciphertext.
 *
 */

package aescipher;

public class aescipher {
  
  static String[][] K = new String[4][4];
  static String[][] W = new String[4][44];
  public String[][] pTextHex = new String[4][4];
  public String[][] cTextHex = new String[4][4];
  /*
   * Initialization of variables and matrices
   */
  //public static String keyHex = "5468617473206D79204B756E67204675";
  /*
   * This is the s-box initialization as a matrix
   */
  static char s[][] = 
    {
       {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
       {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
       {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
       {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
       {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
       {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
       {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
       {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
       {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
       {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
       {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
       {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
       {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
       {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
       {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
       {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}
    };
  
  /*
   * Initialize the rcon box as a matrix
   */
  static char rcon[] = {
      0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 
      0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 
      0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 
      0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 
      0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 
      0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 
      0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 
      0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 
      0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 
      0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 
      0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 
      0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 
      0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 
      0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 
      0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 
      0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
  };

  
  /*
   * Method: aesRoundKeys
   * 
   * Parameters:
   * 
   * Input
   *    keyHex - the 16-byte secret key in hex format
   * 
   * Method for generating the table W to be used as the round keys.
   * Copies K into the first 4 columns, then follows the key schedule to
   * fill the rest of the table.
   */
  
  public void aesRoundKeys(String keyHex){
    initialRoundKeys(keyHex);
    for (int i = 4; i < 44; i++){
       
      if ((i%4) == 0){ // if the column IS divisible by 4...
        System.out.println("");
          String[] Wnew = new String[4];
          for(int k = 0; k < 4; k++){   // Copy the previous column into a temporary storage area
            Wnew[k] = W[k][i-1];
          }
        
          String temp = Wnew[0];    // Store the first index in an outside value
          for (int l = 0; l < 3; l++){  // Rotate all indexes left by 1 spot
            Wnew[l] = Wnew[l+1];
          }
          Wnew[3] = temp;   // Place the first index at the end, completing the left rotation
          
        
          for(int m = 0; m < 4; m++){   // Perform the S-box substitution plus the rcon XOR
            Wnew[m] = aesSbox(Wnew[m]);
            if (m == 0){                // Only perform the rcon XOR on the first byte of Wnew
              Wnew[m] = aesRcon(Wnew[m], i);
            }
            W[m][i] = roundXOR(Wnew[m], W[m][i-4]);
           System.out.print(W[m][i]);
          }
        
      }
      else{  // once we pass the first 4 columns, if the column is not divisible by 4...
        for (int j = 0; j < 4; j++){
            W[j][i] = roundXOR(W[j][i-1], W[j][i-4]);
            System.out.print(W[j][i]);
        }
      }
    }
  }
  
  
  /*
   * Method: aesSbox
   * 
   * Parameters:
   * 
   * Input
   *    inHex - The single byte hex value to be used in the S-box lookup
   * 
   * Output
   *    result - The corresponding single byte hex value gotten from the S-box
   *    
   *    
   * This method takes a hex pair string argument and performs the S-box lookup
   * by splitting the pair into 2 separate characters, converting them to their
   * decimal value, and matching the corresponding pair on the table.
   */
  
  private String aesSbox(String inHex){
    int slookup1 = hex2decimal(inHex.substring(0, 1));
    int slookup2 = hex2decimal(inHex.substring(1, inHex.length()));
    String result = Integer.toHexString(s[slookup1][slookup2]);
    if(result.length() < 2){    // Clunky way to temporarily fix the 0 trimming problem
      result = "0" + result;
    }
    return result;
  }
  
  /*
   * Method: aesRcon
   * 
   * Parameters:
   * 
   * Input
   *    s - The single byte hex value to be XORed with the rcon value
   *    round - the numerical round value to be used in determining which rcon value to use
   *    
   * Output
   *    result - The single byte hex value that results from XORing s with the rcon value
   *    
   * This method handles the rcon lookup and XOR for AES round key generation.
   * 
   */
  
  private String aesRcon(String s, int round){
    int rlookup = (int) Math.floor(round/4);
    String rlookupXOR = Integer.toHexString(rcon[rlookup]);
    String result = Integer.toHexString((hex2decimal(s) ^ hex2decimal(rlookupXOR)));  // Couldn't get this to work with roundXOR method...
    if(result.length() < 2){    // Clunky way to temporarily fix the 0 trimming problem
      result = "0" + result;
    }
    return result;
  }
  
  /*
   * Method: initialRoundKeys
   * 
   * Parameters:
   * 
   * Input
   *    s - the 16 byte secret key
   *     
   * Method for inputting the secret key into matrix K.
   * Pass the key string in as an argument, split the String into pairs
   * and insert each pair into an index of K.
   */
  private void initialRoundKeys(String s){
    int counter = 0;
    for(int i = 0; i < 4; i++){
      for (int j = 0; j < 4; j++){
        K[j][i] = s.substring(counter, (counter + 2));
        W[j][i] = K[j][i];
        counter = counter + 2;
      }
    }
  }
  
  /*
   * Method: roundXOR
   * 
   * Parameters:
   * 
   * Input
   *    s - The first single byte hex value to be XORed
   *    t - The second single byte hex value to be XORed
   *    
   * Output
   *    result - The resulting single byte hex value derived from
   *    XORing s and t.
   *    
   * This method handles the XORing of two single byte values by splitting them in half,
   * converting them to decimal format and then performing the XOR, then converting them
   * back to a String.
   * 
   */
  
  private String roundXOR (String s, String t){
    int a = hex2decimal(s.substring(0, 1));
    int b = hex2decimal(s.substring(1, s.length()));
    int c = hex2decimal(t.substring(0, 1));
    int d = hex2decimal(t.substring(1, t.length()));
    
    String e = Integer.toHexString(a^c);
    String f = Integer.toHexString(b^d);
    String result = e + f;
    return result;
  }
    
  /*
   * hex2decimal
   *
   * This method takes a hexadecimal string value s and transforms it into 
   * its corresponding decimal value val.
   *
   * Parameters
   *    s - The String we are converting to decimal
   *    
   * Returns
   *    val - The decimal representation of the String "s"
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
 
    
 /*
  * The following methods perform the encryption for the AES algorithm...   
  */
    
    /*
     * Method aes
     * 
     * Parameters:
     * 
     * Input
     *    pTextHex - The plaintext we want to encrypt (in Hex form)
     *    keyHex - The key we are using to encrypt the plaintext
     *    
     * Output
     *    cTextHex - The ciphertext we get from performing aes.
     *    
     * This method takes a plaintext block of 4x4 and combines it with the 128-bit
     * key to encrypt a message and provide us with a ciphertext.
     */
    
    public String[][] aes(String[][] pTextHex, String[][] keyHex){
      
      String[][] cTextHex = new String[4][4];
      int roundCount = 0;
      
      // Perform the initial Add Key
      cTextHex = aesStateXOR(pTextHex, keyHex);
      
      // Start the 10 rounds of encryption...
      for(int i = 0; i < 10; i++){
        cTextHex = aesNibbleSub(cTextHex);
        cTextHex = aesShiftRow(cTextHex);
        if (i != 9){  // Skip mixColumn in the last round...
          cTextHex = aesMixColumn(cTextHex);
        }
        cTextHex = aesStateXOR(cTextHex, keyHex);
      }    
      return cTextHex;
    }
    
    /*
     * Method: aesStateXOR
     * Parameters:
     * 
     * Input
     *    sHex - The state matrix that we are encrypting
     *    keyHex - The key that we are XORing with the state matrix
     *    
     * Output
     *    outStateHex - The state matrix after it has been XORed with the key
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
     * Method: aesNibbleSub
     * 
     * Parameters:
     * 
     * Input
     *    inStateHex - The state matrix on which we are performing the substitutions
     *    
     * Output
     *    outStateHex - The state matrix after the substitutions
     * 
     * This method takes a 4x4 matrix input(the "state") and performs the
     * Sbox substitution, outputting the result.
     */
    
    public String[][] aesNibbleSub(String[][] inStateHex){
      String[][] outStateHex = new String[4][4];
      for(int i = 0; i < 4; i++){
        for (int j = 0; j < 4; j++){
          outStateHex[i][j] = aesSbox(inStateHex[i][j]);
        }
      }
      return outStateHex;
    }
    
    /*
     * Method: aesShiftRow
     * 
     * Parameters
     * 
     * Input 
     *    inStateHex - the state matrix we are performing the shiftRow on
     * 
     * Output 
     *    outStateHex - The state matrix after the shiftRow is performed
     * 
     * This method takes a 4x4 matrix input(the "state") and shifts each
     * row to the left in increasing increment (0, 1, 2, 3).
     */
    
    public String[][] aesShiftRow(String[][] inStateHex){
      int counter = 0;
      String[][] outStateHex = new String[4][4];
      String[] tempVector = new String[4];
      
      for (int i = 0; i < 4; i++){
        //copy into new vector for manipulation
        for (int j = 0; j < 4; j++){
          tempVector[j] = inStateHex[i][j];
        }
        // using the counter as a row marker, perform a left shift
        // that many times...
        for(int k = 0; k < counter; k++){
          String temp = tempVector[0];
          tempVector[0] = tempVector[1];
          tempVector[1] = tempVector[2];
          tempVector[2] = tempVector[3];
          tempVector[3] = temp;
        }
        // Copy the result into outStateHex
        for (int n = 0; n < 4; n++){
          outStateHex[i][n] = tempVector[n];
        }
        counter++;
      }
      return outStateHex;
    }
    
    public String[][] aesMixColumn(String[][] inStateHex){
      String[][] outStateHex = new String[4][4];
      
      return outStateHex;
    }
    
}
