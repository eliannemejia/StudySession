package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for all methods in Section.Class
 */
class SectionTest {
  private final String sampleMd3 = "src/test/resources/mdm.md";
  private final Path mdm = Path.of(sampleMd3);
  Section mdmSec;
  Section mdmSec1;
  Section mdmSec2;
  ArrayList<String> sec1Contents;
  ArrayList<String> sec2Contents;
  StudyGuide study;
  File sampFile = new File("sample.md");

  /**
   * Initial values for all test fields
   */
  @BeforeEach
  void initData() {
    mdmSec = new Section(mdm, "studyGuide.md");
    sec1Contents = new ArrayList<>(Arrays.asList("- [[matrix transform]]\n",
        "- [[vectors are matrices]]\n"));
    sec2Contents = new ArrayList<>(Arrays.asList("- [[check assumptions apply]]\n",
        "- [[sometimes valid fryuiguhojpoirdedrftiugyo"
            + "hiuphugftdre7s7dr8ftoydr- figuyohygtr587t98ytr589t680y7t6r5]]\n",
        "- [[symmetrical bell curve]]\n"));
    mdmSec1 = new Section(mdm, "# Matrices\n", sec1Contents, "studyGuide.md");
    mdmSec2 = new Section(mdm, "# Linear Regression\n", sec2Contents, "studyGuide.md");
    study = new StudyGuide("studyGuide.md");
    if (sampFile.exists()) {
      sampFile.delete();
    }

  }


  /**
   * Tests for readFile()
   */
  @Test
  void readFile() {
    StudyGuide study = new StudyGuide("studyGuide.md");
    Path mdm = Path.of("src/test/resources/studyGuide.md");
    mdmSec1.output = mdm.toString();
    mdmSec2.output = mdm.toString();
    mdmSec1.destination = study;
    mdmSec2.destination = study;
    //mdmSec.readFile();

  }


  /**
   * Tests for filter()
   */
  @Test
  void filter() {
    Driver.outputPath = sampFile.toPath();
    try {
      sampFile.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    mdmSec.output = sampFile.toString();
    study.output = sampFile.toString();
    mdmSec.readFile();
    mdmSec.destination = study;
    ArrayList<Section> expected = new ArrayList<>(Arrays.asList(mdmSec1, mdmSec2));
    System.out.println(study.contents.size());
    for (int i = 0; i < study.contents.size(); i++) {
      assertTrue(study.contents.get(i).sameSection(expected.get(i)));
    }
    mdmSec.contents = new ArrayList<>(Arrays.asList("- [[matrix transform]]\n",
        "remove this line", "- [[vectors are matrices]]\n"));
    mdmSec.readFile();
    assertFalse(mdmSec.contents.contains("remove this line"));
    mdmSec.contents = new ArrayList<>(Arrays.asList("- [[matrix transform]]\n",
        "[[remove this line", "- [[vectors are matrices]]\n"));
    mdmSec.readFile();
    assertFalse(mdmSec.contents.contains("[[remove this line"));
    mdmSec.contents = new ArrayList<>(Arrays.asList("- [[matrix transform]]\n",
        "remove this line]]", "- [[vectors are matrices]]\n"));
    mdmSec.readFile();
    assertFalse(mdmSec.contents.contains("remove this line]]"));
  }


  /**
   * Tests for writeToFile()
   */
  @Test
  void writeToFile() {
    try {
      sampFile.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Path sampPath = sampFile.toPath();
    mdmSec1.output = sampPath.toString();
    mdmSec2.output = sampPath.toString();
    study.addSection(mdmSec1);
    study.addSection(mdmSec2);
    study.writeToFile();
    final ArrayList<String> expected = new ArrayList<>(Arrays.asList("# Matrices\n",
        "- [[matrix transform]]\n",
        "- [[vectors are matrices]]\n", "# Linear Regression\n",
        "- [[check assumptions apply]]\n",
        "- [[sometimes valid fryuiguhojpoirdedrftiugyohiuphugftdre7s7dr8ftoydr- "
            + "figuyohygtr587t98ytr589t680y7t6r5]]\n", "- [[symmetrical bell curve]]\n"));
    Scanner scan;
    try {
      scan = new Scanner(sampPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    ArrayList<String> actual = new ArrayList<>();
    while (scan.hasNext()) {
      String sgLine = scan.nextLine();
      if (!sgLine.equals("")) {
        actual.add(sgLine + "\n");
      }
    }
    System.out.println(actual.size());
    assertEquals(actual.size(), expected.size());
    for (int i = 0; i < actual.size(); i++) {
      if (!actual.get(i).equals(expected.get(i))) {
        System.out.print("Actual: " + actual.get(i));
        System.out.print("Expected: " + expected.get(i));
      }
      assertEquals(actual.get(i), expected.get(i));
    }
  }

  /**
   * Tests for sameSection()
   */
  @Test
  void sameSection() {
    Section s1 = new Section(mdm, "Section One", sec1Contents, "studyGuide.md");
    Section s2 = new Section(mdm, "Section One", sec1Contents, "studyGuide.md");
    Section s3 = new Section(mdm, "Section Two", sec1Contents, "studyGuide.md");
    Section s4 = new Section(mdm, "Section One", sec2Contents, "studyGuide.md");
    assertTrue(s1.sameSection(s2));
    assertFalse(s1.sameSection(s3));
    assertFalse(s1.sameSection(s4));
    ArrayList<String> samp = new ArrayList<>(Arrays.asList("- [[matrix transform]]\n",
        "breaks loop"));
    Section s5 = new Section(mdm, "Section One", samp, "studyGuide.md");
    assertFalse(s1.sameSection(s5));
  }

  /**
   * Tests for toString
   */
  @Test
  void testString() {
    Section s1 = new Section(mdm, "Section One", sec1Contents, "studyGuide.md");
    Section s2 = new Section(mdm, "Section Two", sec2Contents, "studyGuide.md");
    assertEquals("Section One 2", s1.toString());
    assertEquals("Section Two 3", s2.toString());
  }
}

