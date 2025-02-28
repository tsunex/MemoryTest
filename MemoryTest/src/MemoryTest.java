import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/*
 * �������e�X�g�p�v���O����
 */
public class MemoryTest {

    public static void main(String[] args) {
        int iterations = 20;  // ���s�񐔂�ݒ�
        MemoryTest test = new MemoryTest();

        // �������g�p�ʂ̃g���b�L���O�J�n
        Runtime runtime = Runtime.getRuntime();

        // FileOutputStream�̃������g�p�ʂ��v��
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

        // �������g�p�ʂ��擾���ĕ\��
        long memoryUsedFileOutputStream = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("FileOutputStream - Total Memory used: " + memoryUsedFileOutputStream / (1024 * 1024) + " MB");

        // ByteArrayOutputStream�̃������g�p�ʂ��v��
        System.out.println("ByteArrayOutputStream");
        for (int i = 0; i < iterations; i++) {
            try {
                ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
                test.readPdfAndWriteToStream("file.pdf", byteArrayStream);
                //close�O��ByteArrayOutputStream���t�@�C���o��
                try (FileOutputStream fos = new FileOutputStream("output_bytearray" + i + ".pdf")) {
                    byteArrayStream.writeTo(fos);
                }
                byteArrayStream.close();
            } catch (IOException e) {
                System.out.println("Error at iteration " + i + ": " + e.getMessage());
            }
        }

        // �������g�p�ʂ��擾���ĕ\��
        long memoryUsedByteArray = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("ByteArrayOutputStream - Total Memory used: " + memoryUsedByteArray / (1024 * 1024) + " MB");

    }

    /*
     * �t�@�C����ǂݍ��݂��ăX�g���[���֏�������
     * @String pdfFilePath �t�@�C����
     * @OutputStream out �X�g���[��
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
