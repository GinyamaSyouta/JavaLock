import java.util.Scanner;

public class Tester {

  public static void main(String[] args) {
    int total = 0; //素数の数
    int goal = 0; //終了数
    int start = 0; //開始数

    Scanner stdIn = new Scanner(System.in);

    System.out.print("開始数は？：");
    start = stdIn.nextInt();
    if(start == 1) {
      start = 2; //1ではプログラムの都合上判定できないため
    }

    System.out.print("終了数は？：");
    goal = stdIn.nextInt();

    boolean judge;
    for (int i = start; i <= goal; i++) {
      judge = true;
      

      for (int j = 2; j < (i / 2) + 1; j ++) {
        if (i % j == 0) {
          judge = false; //割り切れる数がある(素数でない)
          break;
        }
      }

      if(judge) {
        total++; //素数なら合計に+1
      }
    }

    System.out.println(start + "から" + goal + "までの素数の数：" + total);
    stdIn.close();
  }
}
