import java.util.Scanner;
import java.util.Arrays;

/**
 * 
 * MSCS630
 * Professor Pablo Rivas
 * 
 * @author Zach Stecher
 *
 * This class houses the main method for the ClassLab.
 */
public class driver {
  public static void main(String[] args){
    
    ClassLab hexBlock = new ClassLab();
    Scanner input = new Scanner(System.in);
    String m = input.nextLine();
    while (input.hasNext()){
      m = m + input.next();
    }
    System.out.println(m);
    System.out.println(hexBlock.stringToHex(m));
    
    String blockBreak = hexBlock.stringToHex(m);
    
    String blocks[][] = hexBlock.blockSize(blockBreak, 4);
    System.out.println(Arrays.deepToString(blocks));
  }
}
