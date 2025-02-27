import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class MemoryTest {

    public static void main(String[] args) {
        int iterations = 20;  // 試行回数を設定
        MemoryTest test = new MemoryTest();

        // メモリ使用量のトラッキング開始
        Runtime runtime = Runtime.getRuntime();

        // OutputStreamのメモリ使用量を計測
        System.out.println("OutputStream");
        for (int i = 0; i < iterations; i++) {
            try {
                OutputStream outputStream = new MockOutputStream();
                test.readPdfAndWriteToStream("file.pdf", outputStream);
                outputStream.close();
            } catch (IOException e) {
                System.out.println("Error at iteration " + i + ": " + e.getMessage());
            }
        }

        // メモリ使用量を取得して表示
        long memoryUsedOutputStream = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("OutputStream - Total Memory used: " + memoryUsedOutputStream / (1024 * 1024) + " MB");

        // ByteArrayOutputStreamのメモリ使用量を計測
        System.out.println("ByteArrayOutputStream");
        for (int i = 0; i < iterations; i++) {
            try {
                ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
                test.readPdfAndWriteToStream("file.pdf", byteArrayStream);
                byteArrayStream.close();
            } catch (IOException e) {
                System.out.println("Error at iteration " + i + ": " + e.getMessage());
            }
        }

        // メモリ使用量を取得して表示
        long memoryUsedByteArray = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("ByteArrayOutputStream - Total Memory used: " + memoryUsedByteArray / (1024 * 1024) + " MB");

    }

    public void readPdfAndWriteToStream(String pdfFilePath, OutputStream out) {
        try (PDDocument document = Loader.loadPDF(new File(pdfFilePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            out.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mock OutputStream
    static class MockOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            // 実際のストリームの代わりにダミーの実装
        }

        @Override
        public void close() throws IOException {
            // リソース解放のためのダミーの実装
        }
    }
}
