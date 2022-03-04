import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
//
public class TextReadNeo {

  /**
   * メイン.
   * @param args  実行引数
   */
  public static String lockReading(String fileName) {
    String text = ""; //返却用

    try (BufferedReader reader =
        Files.newBufferedReader(Paths.get("./Files/" + fileName),
        StandardCharsets.UTF_8)) {

      String line;

      while ((line = reader.readLine()) != null) {
        text = text + (line + "\n");
      }

      text = text.substring(0, text.length() - 1);

    } catch (IOException e) {
      System.out.println(e);
    }

    return text;
  }

  public static String[] unlockReading(String fileName) {
    int arrayNum = 0;

    try (BufferedReader reader =
        Files.newBufferedReader(Paths.get("./Files/" + fileName),
        StandardCharsets.UTF_8)) {

      String line;

      while ((line = reader.readLine()) != null) {
        arrayNum++;
      }

    } catch (IOException e) {
      System.out.println(e);
    }

    String[] text = new String[arrayNum]; //返却用

    try (BufferedReader reader =
        Files.newBufferedReader(Paths.get("./Files/" + fileName),
        StandardCharsets.UTF_8)) {

      String line;
      
      int lineNum = 0;

      while ((line = reader.readLine()) != null) {
        text[lineNum] = line;
        lineNum++; 
      }

    } catch (IOException e) {
      System.out.println(e);
    } catch (NumberFormatException e) {
      System.out.println("\n適切に暗号化されたファイルではありません。\n");
    }

    return text;
  }
}