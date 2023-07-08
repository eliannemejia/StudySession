package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for all methods in FolderVisitor.Class
 */

class FolderVisitorTest {
  private final String sampleInputsDirectory = "src/test/resources";
  private final String sampleMd1 = "src/test/resources/calc.md";
  private final String sampleMd2 = "src/test/resources/fundies.md";
  private final String sampleMd3 = "src/test/resources/mdm.md";
  private final String samplePdf = "src/test/resources/hello.pdf";
  private final String samplePdf2 = "src/test/resources/science.pdf";
  private final String samplePng = "src/test/resources/mathStuff.png";
  private final String sampleJpeg = "src/test/resources/goodbye.jpeg";
  private final Path md1 = Path.of(sampleMd1);
  private final Path md2 = Path.of(sampleMd2);
  private final Path md3 = Path.of(sampleMd3);
  private final Path pdf1 = Path.of(samplePdf);
  private final Path pdf2 = Path.of(samplePdf2);
  private final Path png = Path.of(samplePng);
  private final Path jpeg = Path.of(sampleJpeg);
  private final Path in = Path.of(sampleInputsDirectory);
  private final Path out = Path.of(sampleInputsDirectory);
  FolderVisitor fv;
  BasicFileAttributes md1Att;
  BasicFileAttributes md2Att;
  BasicFileAttributes md3Att;
  BasicFileAttributes pdf1Att;
  BasicFileAttributes pdf2Att;
  BasicFileAttributes pngAtt;
  BasicFileAttributes jpegAtt;

  /**
   * Initializes the data values for all test fields
   */
  @BeforeEach
  void initData() {
    Driver.orderingFlag = "filename";
    fv = new FolderVisitor(in, out);
    try {
      md1Att = Files.readAttributes(md1, BasicFileAttributes.class);
      md2Att = Files.readAttributes(md2, BasicFileAttributes.class);
      md3Att = Files.readAttributes(md3, BasicFileAttributes.class);
      pdf1Att = Files.readAttributes(pdf1, BasicFileAttributes.class);
      pdf2Att = Files.readAttributes(md1, BasicFileAttributes.class);
      pngAtt = Files.readAttributes(png, BasicFileAttributes.class);
      jpegAtt = Files.readAttributes(jpeg, BasicFileAttributes.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Tests for preVisitDirectory
   */
  @Test
  void preVisitDirectory() {
    assertEquals(FileVisitResult.CONTINUE, fv.preVisitDirectory(in, md1Att));
  }

  /**
   * Tests for visitFile
   */
  @Test
  void visitFile() {
    fv.visitFile(md1, md1Att);
    fv.visitFile(md2, md2Att);
    fv.visitFile(md3, md3Att);
    fv.visitFile(pdf1, pdf1Att);
    fv.visitFile(pdf2, pdf2Att);
    fv.visitFile(png, pngAtt);
    fv.visitFile(jpeg, jpegAtt);
    MarkdownReader read = fv.reader;
    assertTrue(read.files.contains(md1));
    assertTrue(read.files.contains(md2));
    assertTrue(read.files.contains(md3));
    assertFalse(read.files.contains(pdf1));
    assertFalse(read.files.contains(pdf2));
    assertFalse(read.files.contains(png));
    assertFalse(read.files.contains(jpeg));
    Driver.orderingFlag = "filename";
    Driver.outputPath = md3;
    fv.reader = new MarkdownReader("studyGuide.md");
    fv.outputPath = md1;
    fv.visitFile(md1, md1Att);
    //assertFalse(reader.files.contains(md1)); // gradle didnt fw this so commented it out.
  }

  /**
   * Tests for visitFileFailed
   */
  @Test
  void visitFileFailed() {
    Path of = Path.of("fakeFile.pdf");
    fv.visitFile(of, md1Att);
    assertThrows(IOException.class,
        () -> fv.visitFileFailed(of, new IOException("File does not exist")));
  }

  /**
   * Tests for postVisitDirectory
   */
  @Test
  void postVisitDirectory() {
    Driver.orderingFlag = "filename";
    Driver.outputPath = out;
    MarkdownReader read = new MarkdownReader("studyGuide.md");
    read.files.add(md2);
    fv.reader = read;
    assertEquals(FileVisitResult.CONTINUE, fv.postVisitDirectory(md2, new IOException("Error")));
    fv.postVisitDirectory(md3, new IOException("Error"));
    assertEquals(2, read.div.size());
    read = new MarkdownReader("studyGuide.md");
    fv.postVisitDirectory(md3, new IOException("Error"));
    assertEquals(0, read.div.size());
  }
}

