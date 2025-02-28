import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/*
 * メモリテスト用プログラム
 */
public class MemoryTest {

    public static void main(String[] args) {
        int iterations = 20;  // 試行回数を設定
        MemoryTest test = new MemoryTest();

        // メモリ使用量のトラッキング開始
        Runtime runtime = Runtime.getRuntime();

        // FileOutputStreamのメモリ使用量を計測
        System.out.println("FileOutputStream");
        for (int i = 0; i < iterations; i++) {
            try {
                OutputStream outputStream = new FileOutputStream("output" + i + ".pdf");
                test.readPdfAndWriteToStream("file.pdf", outputStream);
                outputStream.close();
            } catch (IOException e) {
                System.out.println("Error at iteration " + i + ": " + e.getMessage());
            }
        }

        // メモリ使用量を取得して表示
        long memoryUsedFileOutputStream = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("FileOutputStream - Total Memory used: " + memoryUsedFileOutputStream / (1024 * 1024) + " MB");

        // ByteArrayOutputStreamのメモリ使用量を計測
        System.out.println("ByteArrayOutputStream");
        for (int i = 0; i < iterations; i++) {
            try {
                ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
                test.readPdfAndWriteToStream("file.pdf", byteArrayStream);
                //close前にByteArrayOutputStreamをファイル出力
                try (FileOutputStream fos = new FileOutputStream("output_bytearray" + i + ".pdf")) {
                    byteArrayStream.writeTo(fos);
                }
                byteArrayStream.close();
            } catch (IOException e) {
                System.out.println("Error at iteration " + i + ": " + e.getMessage());
            }
        }

        // メモリ使用量を取得して表示
        long memoryUsedByteArray = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("ByteArrayOutputStream - Total Memory used: " + memoryUsedByteArray / (1024 * 1024) + " MB");

    }

    /*
     * ファイルを読み込みしてストリームへ書き込む
     * @String pdfFilePath ファイル名
     * @OutputStream out ストリーム
    */
    public void readPdfAndWriteToStream(String pdfFilePath, OutputStream out) {
        try (FileInputStream fis = new FileInputStream(new File(pdfFilePath))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
