package cs3500.pa01;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Given a list of markdown file paths, generates individual sections for a study guides
 */
public class MarkdownReader {
  String outputPath;
  ArrayList<Section> div = new ArrayList<>();
  ArrayList<Path> files = new ArrayList<>();
  Comparator<Path> comp;
  String orderFlag;
  final String created = "created";
  final String modified = "modified";
  final String fileName = "filename";

  /**
   * Constructor; initializes orderFlag and comp
   *
   * @param outputPath the location where a study guide will be written to
   */
  public MarkdownReader(String outputPath) {
    this.outputPath = outputPath;
    this.orderFlag = Driver.orderingFlag;
    if (orderFlag.equalsIgnoreCase(created)) {
      comp = new CreatedComp();
    } else if (orderFlag.equalsIgnoreCase(modified)) {
      comp = new ModifiedComp();
    } else {
      this.orderFlag = fileName;
    }
  }

  /**
   * Sorts the contents of this study guide by the order flag
   */
  void sort() {
    if (this.orderFlag.equalsIgnoreCase(created)) {
      files.sort(new CreatedComp());
    }
    if (this.orderFlag.equalsIgnoreCase(modified)) {
      files.sort(new ModifiedComp());
    }
    if (this.orderFlag.equalsIgnoreCase(fileName)) {
      Collections.sort(files);
    }
  }

  /**
   * Appends the given path to the list of files to be read through
   *
   * @param p : new path to be added to mdFiles
   */
  void addPath(Path p) {
    files.add(p);
  }

  /**
   * Creates a study guide section for every md file in mdFiles, then adds it to this.div
   */
  void section() {
    Section sec;
    this.sort();
    for (Path path : files) {
      sec = new Section(path, outputPath);
      div.add(sec);
    }
    this.readFiles();
  }

  /**
   * Reads through every mdFile in div
   */
  private void readFiles() {
    for (Section sec : div) {
      sec.readFile();
    }
  }
}
