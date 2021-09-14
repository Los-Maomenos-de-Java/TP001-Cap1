package parque;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class generadorTicketTest {

    @Test
    public void testSiHomeroRespondeTodoQueSi() throws IOException {
        List<String> file1 = Files.readAllLines(Paths.get("archivos/archivosTests/HomeroSi.txt"));
        List<String> file2 = Files.readAllLines(Paths.get("archivos/archivosTests/HomeroSimpsonSalida.txt"));

        assertEquals(file1.size(), file2.size() - 2);

        for (int i = 0; i < file1.size(); i++) {
            System.out.println("Comparing line: " + i);
            assertEquals(file1.get(i), file2.get(i + 2));
        }
    }

    @Test
    public void testSiHomeroRespondeTodoQueNo() throws IOException {
        List<String> file1 = Files.readAllLines(Paths.get("archivos/archivosTests/HomeroNo.txt"));
        List<String> file2 = Files.readAllLines(Paths.get("archivos/archivosTests/HomeroSimpsonSalidaNo.txt"));

        assertEquals(file1.size(), file2.size() - 2);

        for (int i = 0; i < file1.size(); i++) {
            System.out.println("Comparing line: " + i);
            assertEquals(file1.get(i), file2.get(i + 2));
        }
    }
}
