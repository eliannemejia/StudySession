package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for all methods in ModifiedComp.Class
 */
class ModifiedCompTest {
  private final String sampleMd3 = "src/test/resources/mdm.md";
  private final Path md3 = Path.of(sampleMd3);
  File file = new File("fileName.txt");
  File other;
  Path fileP;
  Path otherP;
  FileWriter fileWriter;

  /**
   * Initial values for test fields
   */
  @BeforeEach
  void initData() {
    file = new File("fileName.txt");
    other = new File("otherName.txt");
    fileP = file.toPath();
    otherP = other.toPath();

    try {
      file.createNewFile();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    try {
      other.createNewFile();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    try {
      fileWriter = new FileWriter(file);
      fileWriter.write("sample text");
      fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests for compare()
   */
  @Test
  void compare() {
    ModifiedComp comp = new ModifiedComp();
    assertTrue(comp.compare(md3, fileP) < 0);
    assertTrue(comp.compare(fileP, md3) > 0);
    assertThrows(RuntimeException.class, () -> comp.compare(fileP, Path.of("fakefile.pdf")));
  }
}