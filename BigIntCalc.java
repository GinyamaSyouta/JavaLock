import java.math.BigInteger;

public interface BigIntCalc {

    static String RSACalc(String num, long key, String n) {
        String s = "";
        try {
            // numのkey乗をnで割ったあまりを返却 ( (num ** key) mod n )
            BigInteger bigInt = new BigInteger("1"); // 整数Nの0乗なので1
            BigInteger bigInt1 = new BigInteger(num);
            BigInteger bigInt2 = new BigInteger(n);

            for (long i = 0; i < key; i++) {
                bigInt = bigInt.multiply(bigInt1); //bigInt <- numのkey乗
            }

            s = String.valueOf(bigInt.mod(bigInt2));

        } catch (NumberFormatException e) {
            System.out.println("適切に復号・暗号化されているか確認してください。");
        }
        return s;
        //このインタフェース内での処理がRSAのアルゴリズムにおける最重要部分
        //暗号化(署名)及び復号(検証)の処理そのものになる

    }

    static String keyLockCalc(String num, long key) {
        int keyN = 0; //各桁の数字の合計を2乗した数を格納する

        String strKey = String.valueOf(key);

        for (int i = 0; i < strKey.length(); i++) {
            keyN += Integer.parseInt(String.valueOf(strKey.charAt(i)));
        }

        keyN *= keyN; //2乗

        BigInteger bigIntKeyN = new BigInteger(String.valueOf(keyN));
        BigInteger bigIntNum = new BigInteger(String.valueOf(num));
        BigInteger bigIntKey = new BigInteger(String.valueOf(key));

        BigInteger returnBigInt = bigIntNum.multiply(bigIntKey.multiply(bigIntKeyN));

        return String.valueOf(returnBigInt);
        // (平文 * 鍵 * keyN)を文字列にして返却 
    }

    static String keyunlockCalc(String num, long key) {
        int keyN = 0; //各桁の数字の合計を2乗した数を格納する

        String strKey = String.valueOf(key);

        for (int i = 0; i < strKey.length(); i++) {
            keyN += Integer.parseInt(String.valueOf(strKey.charAt(i)));
        }

        keyN *= keyN; //2乗

        BigInteger bigIntKeyN = new BigInteger(String.valueOf(keyN));
        BigInteger bigIntNum = new BigInteger(String.valueOf(num));
        BigInteger bigIntKey = new BigInteger(String.valueOf(key));

        BigInteger returnBigInt = bigIntNum.divide(bigIntKey);
        returnBigInt = returnBigInt.divide(bigIntKeyN);

        return String.valueOf(returnBigInt); 
        // (平文 / 鍵 / keyN)を文字列にして返却 

    }

}
