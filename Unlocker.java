

public class Unlocker extends BitChanger implements BigIntCalc {

  public static String unlock(String[] byteNum, long q, String n) {
    String returnText = "";

    try {
   
      String[] byteNum2 = byteNum;
   

      for (int i = 0; i < byteNum2.length; i++) {
        byteNum2[i] = BigIntCalc.RSACalc(String.valueOf(byteNum2[i]), q, n);
       } 
   
      byte[] byteText3 = new byte[byteNum2.length];
   
      for (int i = 0; i < byteNum2.length; i++) {
        byteText3[i] = (byte)Integer.parseInt(byteNum2[i]);
      }
   
      String text3 = new String(byteText3, java.nio.charset.StandardCharsets.UTF_8);
   
      returnText = text3;
    } catch(Exception e) {

    }
    return returnText;

   }
   
   public static String unlock(String[] byteNum, long key) {
    String returnText = "";

    try {
   
      String[] byteNum2 = byteNum;
   

      for (int i = 0; i < byteNum2.length; i++) {
        byteNum2[i] = BigIntCalc.keyunlockCalc(String.valueOf(byteNum2[i]), key);
       } 
   
      byte[] byteText3 = new byte[byteNum2.length];
   
      for (int i = 0; i < byteNum2.length; i++) {
        byteText3[i] = (byte)Integer.parseInt(byteNum2[i]);
      }
   
      String text3 = new String(byteText3, java.nio.charset.StandardCharsets.UTF_8);
   
      returnText = text3;
    } catch (Exception e) {
      
    }
    return returnText;

   }
}
