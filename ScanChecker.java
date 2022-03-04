import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public interface ScanChecker {  
    static boolean checkInt(int min, int max, int num) {
        return (num >= min && num <= max);
    }

    static boolean checkFile(String fileName) {
        boolean judg = false;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get("./Files/" + fileName),
        StandardCharsets.UTF_8)) {
            judg = true;

        } catch(IOException e) {
            
        }
        return judg;
    }

    static boolean checkBig(String num) {
        boolean judg = false;
        BigInteger bigInt;

        try {
            bigInt = new BigInteger(num);
            judg = true;
        } catch(Exception e) {
            
        }


        return judg;
    }

}
