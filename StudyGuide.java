package cs3500.pa01;

import java.util.ArrayList;

/**
 * Takes a summarized list of class notes and organizes them into a study guide file
 * to be used to prepare for an exam
 */
public class StudyGuide extends AbsStudyGuide {
  ArrayList<Section> contents = new ArrayList<>();

  /**
   * Initializes the output path to the one provided in Driver.main(String[] args)
   *
   * @param output the file where this study guide will be written onto
   */
  public StudyGuide(String output) {
    super(output);
  }

  /**
   * Appends the given section to the contents of this study guide
   *
   * @param sec new section to be added to the contents of this study guide
   */
  void addSection(Section sec) {
    contents.add(sec);
  }

  /**
   * Writes the contents of this study guide to the output path
   */
  void writeToFile() {
    for (Section sec : contents) {
      sec.writeToFile();
    }
  }
}
