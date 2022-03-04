import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextExeportNeo {

  /**
   * メイン.
   * @param args  実行引数
   */
  public static void exeport(String fileName, String text) {
    //fileNameファイルをtextが書き込まれている状態で生成する

    try (BufferedWriter writer =
        Files.newBufferedWriter(Paths.get("./Files/" + fileName),
        StandardCharsets.UTF_8)) {

      writer.append(text);

    } catch (IOException e) {
      System.out.println(e);
    }

  }

}