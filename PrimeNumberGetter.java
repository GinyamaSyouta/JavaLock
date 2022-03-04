import java.util.Arrays;
import java.util.Random;

public class PrimeNumberGetter {

  private static int[] primeList;
  private static int length;

  static {
    int j = 0; //PrimeListの添え字
    length = 0; //PrimeListの要素数

    for (int i = 2; i <= 1000; i++) {
      if (primeJudg(i)) {
        length++;
      }
    }

    primeList = new int[length];

    for (int i = 2; i <= 1000; i++) {
      if (primeJudg(i)) {
        primeList[j] = i;
        j++;
      }
    }

  }
  
  static boolean primeJudg(int n) {
    boolean judg = true;

    for (int i = 2; i < (n / 2) + 1; i++) {
      if (n % i == 0) {
        judg = false;
      }
    }

    return judg;
  }

  static int[] primeNumGet() { //ランダムに素数(1~500)を返却
    Random rand = new Random();
    int[] returnList = new int[2];

    while(true) {
      returnList[0] = primeList[rand.nextInt(length)];
      returnList[1] = primeList[rand.nextInt(length)];

      if (returnList[0] * returnList[1] > 256) {
        break;
      }
    }

    return returnList;
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(primeList));
    System.out.println(Arrays.toString(primeNumGet()));
  }

}
