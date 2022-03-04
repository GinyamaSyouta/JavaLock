public class Locker extends BitChanger implements BigIntCalc {
  
  public static String lock(String text, long p, String n) { //公開鍵用
    String returnText = "";

    try {
   
      byte[] byteText2 = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
      //テキストをバイト型に
   
      String[] byteNum2 = new String[byteText2.length];//数値変換用
   
      for (int i = 0; i < byteText2.length; i++) {
        byteNum2[i] = String.valueOf(Byte.toUnsignedInt(byteText2[i]));//byteから数字に変換
      }

      for (int i = 0; i < byteText2.length; i++) {
        byteNum2[i] = BigIntCalc.RSACalc(byteNum2[i], p, n);//変換(暗号化)
      }
   
   
      for (int i = 0; i < byteNum2.length; i++) {
        returnText = returnText + byteNum2[i] + "\n";
      }

      returnText = returnText.substring(0, (returnText.length() - 1));
    } catch (Exception e) {

    }
    return returnText;

   }

  public static String lock(String text, long key) { //共通鍵用
    String returnText = "";
   
    byte[] byteText2 = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
      //テキストをバイト型に
   
    String[] byteNum2 = new String[byteText2.length];//数値変換用
   
    for (int i = 0; i < byteText2.length; i++) {
      byteNum2[i] = String.valueOf(Byte.toUnsignedInt(byteText2[i]));//byteら数字に変換
    }

    for (int i = 0; i < byteText2.length; i++) {
      byteNum2[i] = BigIntCalc.keyLockCalc(byteNum2[i], key);//変換(暗号化)
    }
   
   
    for (int i = 0; i < byteNum2.length; i++) {
      returnText = returnText + byteNum2[i] + "\n";
    }

    returnText = returnText.substring(0, (returnText.length() - 1));
    
    return returnText;

   }

}
