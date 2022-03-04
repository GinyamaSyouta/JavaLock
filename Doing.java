import java.util.InputMismatchException;
import java.util.Scanner;

public class Doing implements ScanChecker {

  private static final String Message = "入力が適切ではありません。";

  private static final String Message2 = "ファイルの暗号化ならFilesフォルダ内のファイル名を入力してください(拡張子含む)\n実行中断する場合は \"end\"\nと入力：";

  private static final String Message3 = "指定された名前のファイルが存在しません。";
  //上のメッセージはよくつかうメッセージ

  private static final boolean Lock = true; //公開鍵暗号方式

  private static final boolean Sign = false; //デジタル署名


  static int scannerIntIn(Scanner stdIn, int min, int max, String message) {
    // int入力用
    int num = 0;
    boolean judge = false;

    while(true) {

      try {
        System.out.print(message);
        num = Integer.parseInt(stdIn.nextLine());

        judge = true;
      } catch(InputMismatchException e) {
        //stdIn.nextLine();
        System.out.println("整数ではありません。");

      } catch (Exception e) {
        //stdIn.nextLine();
        System.out.println("整数ではありません。");
      }

      if (ScanChecker.checkInt(min, max, num) && judge) {
        break;
      } else {
        System.out.println(Message);
      }

    }
    return num;
  } 

  static long scannerLongIn(Scanner stdIn, String message) {
    // long入力用
    long num = 0;

    while(true) {

      try {
        System.out.print(message);
        num = Long.valueOf(stdIn.nextLine());
        break;
      } catch(NumberFormatException e) {
        System.out.println("整数ではありません。");

      }

    }
    return num;
  } 

  static String scannerBigIn(Scanner stdIn, String message) {
    // bigIntの材料となる数字列入力用
    String num = "";

    while(true) {

      try {
        System.out.print(message);
        num = stdIn.nextLine();
        if (ScanChecker.checkBig(num)) {
          break;
        } else {
          System.out.println(Message);
        }

      } catch (Exception e) {

      }

    }
    return num;
  } 

  static String scannerFileNameIn(Scanner stdIn) {
    //ファイル名入力用
    String fileName;

    while(true) {

      System.out.print(Message2);
      fileName = stdIn.nextLine();

      if (fileName.equals("\"end\"")) {
        System.out.println("終了します。");
        System.exit(0); //強制終了
      } else if (fileName.equals("\"key\"")) {
        fileName = "../__KEY__.txt"; //key(共通鍵)へのパス
      }

      if (ScanChecker.checkFile(fileName)) {
        break;
      } else {
        System.out.println(Message3);
      }

    }

    return fileName;
  }

  static void lock(boolean judg) { //公開鍵暗号化用メソッド
    String fileName;

    try(Scanner stdIn = new Scanner(System.in)) {

      if (judg) {
        System.out.println("\nこのデバイスの共通鍵を暗号化&署名なら\"key\"");
      }

      while(true) {
        fileName = scannerFileNameIn(stdIn);
        if (judg || (!judg && !fileName.equals("../__KEY__.txt"))) {
          break;
        }
      }

      String text = TextReadNeo.lockReading(fileName);

      long key;

      if (judg) {
        key = scannerLongIn(stdIn, "受信者の公開鍵Pを入力："); //暗号化用公開鍵P
      } else {
        key = scannerLongIn(stdIn, "送信者の秘密鍵Qを入力："); //署名用秘密鍵Q
      }
      
      String n;

      if (judg) {
        n = scannerBigIn(stdIn, "受信者の公開鍵Nを入力："); //割る数N(受信者の)
      } else {
        n = scannerBigIn(stdIn, "送信者の公開鍵Nを入力："); //割る数N(署名者の)
      }

      String lockedText = Locker.lock(text, key, n); //暗号化済

      if (fileName.equals("../__KEY__.txt")) { //共通鍵の暗号化&署名
        String signLockedText = "";
        long q = scannerLongIn(stdIn, "送信者の秘密鍵Qを入力：");
        String n2 = scannerBigIn(stdIn, "送信者の公開鍵Nを入力：");

        signLockedText = Locker.lock(lockedText, q, n2);
        fileName = "__KEY__.txt"; // ../を消すことでパスの矛盾を防ぐ

        TextExeportNeo.exeport("(RSA暗号化済&署名済)" + fileName, signLockedText);

      } else {
        if (judg) { //その他のファイル暗号化
          TextExeportNeo.exeport("(RSA暗号化済)" + fileName, lockedText);
        } else {
          TextExeportNeo.exeport("(署名済)" + fileName, lockedText);
        }
      }

    }
    
  }

  static void lock() { //共通鍵暗号化用メソッド
    String fileName;

    try(Scanner stdIn = new Scanner(System.in)) {

      fileName = scannerFileNameIn(stdIn);

      String text = TextReadNeo.lockReading(fileName);

      long key = 0;

      try {
        key = Long.valueOf(TextReadNeo.lockReading("../__KEY__.txt"));

      } catch (Exception e) {
        System.out.println("共通鍵を正しく登録できていますか？\n未登録または不適切な値の可能性があります。");
      }

      String lockedText = Locker.lock(text, key); //暗号化済

      TextExeportNeo.exeport("(暗号化済)" + fileName, lockedText);

    }
    
  }

  static void unlock(boolean judg) { //公開鍵暗号解読用メソッド
    String fileName;
    boolean keyJudge = false;

    try(Scanner stdIn = new Scanner(System.in)) {

      //-----------------------------------------------------
      if (judg) {
        System.out.println("\nFailesフォルダ内の相手デバイスの共通鍵を復号&検証なら\"key\"");
      }

      while(true) {
        fileName = scannerFileNameIn(stdIn);
        if (judg || (!judg && !fileName.equals("../__KEY__.txt"))) {
          break;
        }
      }
      
      if (fileName.equals("../__KEY__.txt")) {
        keyJudge = true;
        
        while(true) {
          System.out.println("\n※以下\"key\" は入力不可能");
          fileName = scannerFileNameIn(stdIn);
          if (!fileName.equals("../__KEY__.txt")) {
            break;
          }
        }
      }
      //-----------------------------------------------------
      //fileName = scannerFileNameIn(stdIn);
      
      String[] text = TextReadNeo.unlockReading(fileName);

      long key;

      if (judg && !keyJudge) {
        key = scannerLongIn(stdIn, "\n受信者の秘密鍵Qを入力："); //暗号解読用秘密鍵Q
      } else {
        key = scannerLongIn(stdIn, "\n送信者の公開鍵Pを入力："); //署名検証用公開鍵P
      }

      String n;

      if (judg && !keyJudge) {
        n = scannerBigIn(stdIn, "\n受信者の公開鍵Nを入力："); //割る数N(受信者の)
      } else {
        n = scannerBigIn(stdIn, "\n送信者の公開鍵Nを入力："); //割る数N(署名者の)
      }

      String unlockedText = Unlocker.unlock(text, key, n);

      if (keyJudge) { //復号も同時に

        fileName = "(復号・検証済)" + fileName;
        TextExeportNeo.exeport(fileName, unlockedText);

        long key2;
        key2 = scannerLongIn(stdIn, "\n受信者の秘密鍵Qを入力："); //暗号解読用秘密鍵
  
        String n2;
        n2 = scannerBigIn(stdIn, "\n受信者の公開鍵Nを入力："); //割る数N(受信者の)

        String[] text2;
        text2 = TextReadNeo.unlockReading(fileName);

        String unlockedText2 = Unlocker.unlock(text2, key2, n2);

        TextExeportNeo.exeport(fileName, unlockedText2);

      } else { //通常時

        if (judg) {
          TextExeportNeo.exeport("(RSA復号済)" + fileName, unlockedText);
        } else {
          TextExeportNeo.exeport("(検証済)" + fileName, unlockedText);
        }

      }


    }

  }

  static void unlock() { //共通鍵暗号解読用メソッド
    String fileName;

    try(Scanner stdIn = new Scanner(System.in)) {

      fileName = scannerFileNameIn(stdIn);
      
      String[] text = TextReadNeo.unlockReading(fileName);

      long key;

      key = Long.valueOf(TextReadNeo.lockReading("../__KEY__.txt"));

      String unlockedText = Unlocker.unlock(text, key);

      TextExeportNeo.exeport("(復号済)" + fileName, unlockedText);

    }

  }

  static void keyGet() {
    int[] primeList;
    int breakCount = 0;
    System.out.println("\nーーーーーーーーーーーーーーーーーーーーーーーー");

    while(true) {
      primeList = PrimeNumberGetter.primeNumGet();

      if (KeyGetter.keyGet(String.valueOf(primeList[0]), String.valueOf(primeList[1]))) {
        System.out.println();
        breakCount++;
      }

      if (breakCount == 3) {
        break;
      }

    }

    System.out.println("ーーーーーーーーーーーーーーーーーーーーーーーー");
    System.out.println("\nこのような候補が見つかりました。");

  }

  static void keyRegister() {
    try (Scanner stdIn = new Scanner(System.in)) {
      int select = scannerIntIn(stdIn, 0, 1, "\nランダムに共通鍵を生成するなら0を\n自分で共通鍵の値を入力する場合は1を\n入力してください：");

      String key = "0";

      if (select == 0) {
        key = KeyGetter.KeyGet();
        
      } else {

        key = String.valueOf(Math.abs(scannerLongIn(stdIn, "\n共通鍵の値を入力(正の整数のみ、最大値はおよそ922京まで)：")));

        if (Long.valueOf(key) < 100000) {
          System.out.println("\n※桁数が少ない値は推奨できません。");
        } 
      }

      
      TextExeportNeo.exeport("../__KEY__.txt", key);
    }

  }

  public static void main(String[] args) {
    int select = 0;
    try(Scanner stdIn = new Scanner(System.in)) {

      select = scannerIntIn(stdIn, 0, 1, "\n共通鍵暗号方式なら0を\n公開鍵暗号方式なら1を\n入力してください。：");

      if (select == 0) {

        select = scannerIntIn(stdIn, 0, 5, "\n実行中断なら0を\n暗号化なら1を\n暗号解読なら2を\n共通鍵の管理なら3を\n入力してください：");

        switch (select) {
          case 0: System.out.println("\n終了します。"); System.exit(0);//強制終了
          case 1: lock(); break; //暗号化
          case 2: unlock(); break; //復号
          case 3: keyRegister(); break; //鍵生成
        }


      } else {

        select = scannerIntIn(stdIn, 0, 5, "\n実行中断なら0を\n暗号化なら1を\n署名付与なら2を\n暗号解読なら3を\n署名検証なら4を\n鍵の生成なら5を\n入力してください：");

        switch (select) {
          case 0: System.out.println("\n終了します。"); System.exit(0);//強制終了
          case 1: lock(Lock); break; //ファイル暗号化
          case 2: lock(Sign); break; //署名付与
          case 3: unlock(Lock); break; //暗号化ファイル復号
          case 4: unlock(Sign); break; //署名検証
          case 5: keyGet(); break; //鍵生成
        }

      }

    }


  }
  
}
