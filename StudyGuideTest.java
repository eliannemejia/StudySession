package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for all methods in StudyGuide.Class
 */
class StudyGuideTest {
  private final String sampleMd3 = "src/test/resources/mdm.md";
  private final Path mdm = Path.of(sampleMd3);
  Section mdmSec;
  Section mdmSec1;
  Section mdmSec2;
  ArrayList<String> sec1Contents;
  ArrayList<String> sec2Contents;
  StudyGuide study;
  File sampFile;

  /**
   * Initial values for test fields
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
    sampFile = new File("sample.md");
    if (sampFile.exists()) {
      sampFile.delete();
    }

  }

  /**
   * Tests for addSection()
   */
  @Test
  void addSection() {
    study.addSection(mdmSec1);
    study.addSection(mdmSec2);
    assertEquals(2, study.contents.size());
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
        "- [[check assumptions apply]]\n", "- [[sometimes valid fryuiguhojpoirdedrftiugyo"
            + "hiuphugftdre7s7dr8ftoydr- figuyohygtr587t98ytr589t680y7t6r5]]\n",
        "- [[symmetrical bell curve]]\n"));
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
}