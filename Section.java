package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a portion of a StudyGuide
 */
public class Section extends AbsStudyGuide {
  private final Path parent;
  private final String openBracket = "[[";
  private final String closeBracket = "]]";
  private final String heading = "#";
  private final String tripleColon = ":::";
  BasicFileAttributes attrs;
  ArrayList<String> contents = new ArrayList<>();
  StudyGuide destination = new StudyGuide(this.output);
  ArrayList<String> questions = new ArrayList<>();
  private String title;
  private Scanner scan;

  /**
   * @param p      : File path to be read through
   * @param output the final destination for this study guide
   */
  public Section(Path p, String output) {
    super(output);
    parent = p;
    try {
      attrs = Files.readAttributes(p, BasicFileAttributes.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @param p        : the path from which this section was created
   * @param title    : Heading for a section of a study guide
   * @param contents : The information in this section
   * @param output   the final destination for this study guide
   */
  public Section(Path p, String title, ArrayList<String> contents, String output) {
    super(output);
    parent = p;
    try {
      attrs = Files.readAttributes(p, BasicFileAttributes.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.title = title;
    this.contents = contents;
  }

  /**
   * Reads through the given parent file, sections information based on heading
   */
  void readFile() {
    Section temp;
    String str;
    try {
      scan = new Scanner(parent);
    } catch (IOException e) {
      e.printStackTrace();
    }
    while (scan.hasNext()) {
      str = scan.nextLine();
      if (str.startsWith(heading) && contents.size() == 0) {
        this.title = str;
      } else if (str.startsWith(heading) && contents.size() > 0) {
        temp = new Section(this.parent, this.title, this.contents, this.output);
        temp.filter();
        destination.addSection(temp);
        this.contents = new ArrayList<>();
        this.title = str;
      } else if (!scan.hasNext() && contents.size() > 0) {
        contents.add(str);
        temp = new Section(this.parent, this.title, this.contents, this.output);
        temp.filter();
        destination.addSection(temp);
      } else if (str.contains(openBracket) && !str.endsWith(closeBracket)) {
        this.multiLine(str);
      } else {
        contents.add(str);
      }
    }
    destination.writeToFile();
  }

  /**
   * @param sec : Section to compare this one to
   * @return : true if the two are equivalent, false otherwise
   */
  boolean sameSection(Section sec) {
    boolean sameSize = (this.contents.size() == sec.contents.size());
    boolean sameTitle = this.title.equals(sec.title);
    boolean sameParent = this.parent.equals(sec.parent);
    boolean flag = false;
    if (sameSize) {
      for (int i = 0; i < this.contents.size(); i++) {
        if (!this.contents.get(i).equals(sec.contents.get(i))) {
          flag = false;
          break;
        } else {
          flag = this.contents.get(i).equals(sec.contents.get(i));
        }
      }
    }
    return sameSize && sameTitle && flag && sameParent;
  }

  /**
   * @param source : string to be exploded
   * @return produces a list of characters from the given string
   */
  ArrayList<Character> explode(String source) {
    ArrayList<Character> chars = new ArrayList<>();
    for (int i = 0; i < source.length(); i++) {
      chars.add(source.charAt(i));
    }
    return chars;
  }

  public String toString() {

    return this.title + " " + this.contents.size();
  }

  /**
   * Helper for read file; handles multiline text enclosed in brackets
   *
   * @param str : the beginning of a mutli line text of importance (enclosed in [[]])
   */
  void multiLine(String str) {
    StringBuilder builder = new StringBuilder();
    ArrayList<Character> chars;
    builder.append(str);
    while (!builder.toString().endsWith(closeBracket)) {
      if (scan.hasNext()) {
        chars = this.explode(scan.nextLine());
        for (Character c : chars) {
          builder.append(c);
        }
      } else {
        contents.add(builder.toString());
        break;
      }
    }
    contents.add(builder.toString());
  }

  /**
   * Removes unimportant information from this section
   */
  private void filter() {
    ArrayList<String> temp = new ArrayList<>(this.contents);
    for (String s : temp) {
      if (!s.startsWith(heading)) {
        if (!(s.startsWith(openBracket)) && !(s.endsWith(closeBracket))) {
          contents.remove(s);
        }
      }
    }
    this.filterQuestions();
  }

  /**
   * Separates questions from the rest of the important information in this section
   */
  private void filterQuestions() {
    ArrayList<String> temp = new ArrayList<>(this.contents);
    for (String s : temp) {
      if (s.contains(tripleColon)) {
        questions.add(s);
        contents.remove(s);
      }
    }
  }


  /**
   * Writes the contents of this section to the output path
   */
  void writeToFile() {
    title += "\n";
    try {
      Files.write(Path.of(output), title.getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      e.printStackTrace();
    }
    for (String s : contents) {
      s += "\n";
      try {
        Files.write(Path.of(output), s.getBytes(), StandardOpenOption.APPEND);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    for (String s : questions) {
      String q = s.substring(s.indexOf(openBracket) + 2, s.indexOf(tripleColon));
      String a = s.substring(s.indexOf(tripleColon) + 3, s.indexOf(closeBracket));
      String replace = "Question (H): " + q + "\n" + "Answer: " + a + "\n";
      try {
        Files.write(sessionPath.toPath(), replace.getBytes(), StandardOpenOption.APPEND);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
