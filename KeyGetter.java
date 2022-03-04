import java.math.BigInteger;
import java.util.Random;

public class KeyGetter {
  static boolean keyGet(String p, String q) {

    boolean judg = false;
    BigInteger bigIntSmallP = new BigInteger(p);
    BigInteger bigIntSmallQ = new BigInteger(q);
    BigInteger bigIntN = bigIntSmallP.multiply(bigIntSmallQ); // p * q

    BigInteger bigIntPQ = (bigIntSmallP.subtract(new BigInteger("1"))).multiply((bigIntSmallQ.subtract(new BigInteger("1"))));
    // (p - 1) * (q - 1) = PQ (これを素因数分解して得られるのが鍵)

    bigIntPQ = bigIntPQ.add(new BigInteger("1")); // PQ + 1

    BigInteger i = bigIntPQ.divide(new BigInteger(String.valueOf("2"))); 
    // i = (P*Q / 2)

    while(!String.valueOf(i).equals("0")) { // i == 0?

      if (String.valueOf(bigIntPQ.mod(i)).equals("0") &&
      !(String.valueOf(i).equals("1")) &&
      !(String.valueOf(bigIntPQ).equals("1"))) {
        //if (PQ mod i == 0? && i != 1 && bigIntPQ != 1)

        System.out.println("\n(公開鍵P " + i + " : 公開鍵N " + bigIntN + " : 秘密鍵Q " + bigIntPQ.divide(i) + ")");

        judg = true;
      }

      i = i.subtract(new BigInteger(String.valueOf("1"))); // i--
    } // このループはi回繰り返す

    return judg;
    
  }

  static void test(int n) {

    for (int i = 1; i < (n / 2) + 1; i++) {
      if (n % i == 0) {
        System.out.println("nは" + i + "と" + (n / i) + "に分解可能");
      }
    }

  }

  static String KeyGet() {
    Random rand = new Random();

    String key = "0";

    long min = (long)Math.pow(10, 18);//18桁
    
    while(Long.valueOf(key) < min) {
      key = String.valueOf(Math.abs(rand.nextLong()));
    }

    return key;
  }
}