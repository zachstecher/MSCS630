/**
 * file: aescipher.java
 * @author Zach Stecher
 * Class: MSCS630 - Security Algorithms and Protocols
 * Assignment: Lab 2
 * Due Date: February 23rd, 2016
 * 
 * This file passes the key from its .txt file into
 * the aescipher class to generate the round keys.
 *
 */

import java.util.Scanner;

public class driver {
  public static void main(String[] args){
    aescipher cipher = new aescipher();
    Scanner input = new Scanner(System.in);
    String theKey = input.nextLine();
    cipher.aesRoundKeys(theKey);
  }
}
