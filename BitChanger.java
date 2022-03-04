public class BitChanger {

  public static String stringToBit(String text) { 
    String returnText = "";
   
    byte[] byteText2 = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
      //テキストをバイト型に
   
    String[] byteNum2 = new String[byteText2.length];//数値変換用
   
    for (int i = 0; i < byteText2.length; i++) {
      byteNum2[i] = String.valueOf(Byte.toUnsignedInt(byteText2[i]));//byteら数字に変換
    }

    for (int i = 0; i < byteText2.length; i++) {
      byteNum2[i] = byteNum2[i];//変換用
    }
   
   
    for (int i = 0; i < byteNum2.length; i++) {
      returnText = returnText + byteNum2[i] + "\n";
    }

    returnText = returnText.substring(0, (returnText.length() - 1));
    
    return returnText;

  }

  public static String bitToString(String[] byteNum) {
    String returnText = "";

    try {
   
      String[] byteNum2 = byteNum;
   
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
