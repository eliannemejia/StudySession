package cs3500.pa01;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for all methods in CreatedComp.Class
 */
class CreatedCompTest {
  File file = new File("fileName.txt");
  File other;
  Path fileP;
  Path otherP;
  private final String sampleMd3 = "src/test/resources/mdm.md";
  private final Path md3 = Path.of(sampleMd3);

  /**
   * Initial values for test fields
   */
  @BeforeEach
  void initData() {
    file = new File("fileName.txt");
    other = new File("otherName.txt");
    otherP = other.toPath();
    fileP = file.toPath();
    try {
      if (file.createNewFile()) {
        file.createNewFile();
        fileP = file.toPath();
      }
      if (other.createNewFile()) {
        other.createNewFile();
        otherP = other.toPath();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests for compare
   */
  @Test
  void compare() {
    CreatedComp comp = new CreatedComp();
    FileTime fileAttrs;
    FileTime md3Attrs;
    try {
      fileAttrs = Files.readAttributes(fileP, BasicFileAttributes.class).creationTime();
      md3Attrs = Files.readAttributes(md3, BasicFileAttributes.class).creationTime();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    if (fileAttrs.compareTo(md3Attrs) < 0) {
      assertTrue(comp.compare(fileP, md3) < 0);
      assertTrue(comp.compare(md3, fileP) > 0);
    } else if (fileAttrs.compareTo(md3Attrs) > 0) {
      assertTrue(comp.compare(fileP, md3) > 0);
      assertTrue(comp.compare(md3, fileP) < 0);
    }
    //assertEquals(0, comp.compare(fileP, otherP));
    assertThrows(RuntimeException.class, () -> comp.compare(Path.of("fakefile.pdf"), fileP));
  }
}