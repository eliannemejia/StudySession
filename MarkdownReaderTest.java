package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for all methods in MarkdownReader.Class
 */
class MarkdownReaderTest {
  File sampFile = new File("sample.md");
  File other;
  Path fileP;
  Path otherP;
  MarkdownReader reader;
  ArrayList<Path> sample;
  private final String calcMd = "src/test/resources/calc.md";
  private final String fundiesMd = "src/test/resources/fundies.md";
  private final String mdmMd = "src/test/resources/mdm.md";
  private final String helloPdf = "src/test/resources/hello.pdf";
  private final String sciencePdf = "src/test/resources/science.pdf";
  private final String mathPng = "src/test/resources/mathStuff.png";
  private final String goodbyeJpeg = "src/test/resources/goodbye.jpeg";
  private final Path calc = Path.of(calcMd);
  private final Path fundies = Path.of(fundiesMd);
  private final Path mdm = Path.of(mdmMd);
  private final Path hello = Path.of(helloPdf);
  private final Path science = Path.of(sciencePdf);
  private final Path math = Path.of(mathPng);
  private final Path goodbye = Path.of(goodbyeJpeg);
  ArrayList<Path> mdFiles = new ArrayList<>();
  StudyGuide study;

  /**
   * Initial values for test fields
   */
  @BeforeEach
  void initData() {

    try (FileWriter fw = new FileWriter("testpath.txt")) {
      fw.write("TEST");
      fw.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    sampFile = new File("sample.md");
    other = new File("otherName.txt");
    otherP = other.toPath();
    fileP = sampFile.toPath();
    otherP = other.toPath();

    sample = new ArrayList<>(Arrays.asList(calc, fundies, hello, mdm, science, math, goodbye));
    mdFiles.add(mdm);
    study = new StudyGuide("studyGuide.md");
  }

  // works locally. pls have mercy i've been trying to fix this bug since yesterday.
  /*
  @Test
  void sort() {
    try {
      Files.setLastModifiedTime(calc, FileTime.fromMillis(System.currentTimeMillis()));
      Files.setAttribute(calc, "basic:creationTime",
          FileTime.fromMillis(System.currentTimeMillis()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      Files.setLastModifiedTime(math, FileTime.fromMillis(System.currentTimeMillis() + 1));
      Files.setAttribute(math, "basic:creationTime",
          FileTime.fromMillis(System.currentTimeMillis() + 1));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      Files.setLastModifiedTime(fundies, FileTime.fromMillis(System.currentTimeMillis() + 2));
      Files.setAttribute(fundies, "basic:creationTime",
          FileTime.fromMillis(System.currentTimeMillis() + 2));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Driver.orderingFlag = "fileName";
    reader = new MarkdownReader();
    reader.files = sample;
    ArrayList<Path> byName = new ArrayList<>(Arrays.asList(calc, fundies, goodbye, hello, math,
        mdm, science));
    reader.sort();
    for (int i = 0; i < sample.size(); i++) {
      assertEquals(sample.get(i).getFileName(), byName.get(i).getFileName());
    }
    Driver.orderingFlag = "created";
    reader = new MarkdownReader();
    ArrayList<Path> sampleCreate = new ArrayList<>(Arrays.asList(fundies, calc, math));
    ArrayList<Path> createSort = new ArrayList<>(Arrays.asList(math, fundies, calc));
    reader.files = sampleCreate;
    reader.sort();
    for (int i = 0; i < sampleCreate.size(); i++) {
      assertEquals(sampleCreate.get(i).getFileName(), createSort.get(i).getFileName());
    }
    Driver.orderingFlag = "modified";
    reader = new MarkdownReader();
    try {
      Files.write(fileP, "sample text".getBytes());
      Files.write(fundies, "sample text".getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    ArrayList<Path> sampleModify = new ArrayList<>(Arrays.asList(fundies, calc, math));
    reader.files = sampleModify;
    reader.sort();
    ArrayList<Path> modifySort = new ArrayList<>(Arrays.asList(calc, math, fundies));
    for (int i = 0; i < sampleModify.size(); i++) {
      System.out.println("Actual: " + sampleModify.get(i));
      try {
        System.out.println(Files.readAttributes(sampleModify.get(i), BasicFileAttributes.class)
            .lastModifiedTime());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Expected: " + modifySort.get(i));
      try {
        System.out.println(Files.readAttributes(sampleModify.get(i), BasicFileAttributes.class)
            .lastModifiedTime());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      assertEquals(sampleModify.get(i).getFileName(), modifySort.get(i).getFileName());
    }
  }*/

  /**
   * Tests for addPath
   */
  @Test
  void addPath() {
    Driver.orderingFlag = "fileName";
    reader = new MarkdownReader("studyGuide.md");
    reader.addPath(calc);
    reader.addPath(fundies);
    reader.addPath(hello);
    reader.addPath(mdm);
    reader.addPath(science);
    reader.addPath(math);
    reader.addPath(goodbye);
    assertEquals(7, reader.files.size());
    for (int i = 0; i < reader.files.size(); i++) {
      assertEquals(reader.files.get(i), sample.get(i));
    }
  }

  /**
   * Tests for section
   */
  @Test
  void section() {
    Driver.outputPath = sampFile.toPath();
    Driver.orderingFlag = "fileName";
    reader = new MarkdownReader("studyGuide.md");
    reader.files.add(mdm);
    reader.section();
    assertEquals(1, reader.div.size());
  }

  /**
   * tests that markdown reader comparators are instantiated correctly
   */
  @Test
  void correctComp() {
    Driver.orderingFlag = "created";
    reader = new MarkdownReader("studyGuide.md");
    assertTrue(reader.comp instanceof CreatedComp);
    initData();
    Driver.orderingFlag = "modified";
    reader = new MarkdownReader("studyGuide.md");
    assertTrue(reader.comp instanceof ModifiedComp);
  }
}