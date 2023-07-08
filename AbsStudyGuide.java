package cs3500.pa01;

import java.io.File;
import java.io.IOException;

/**
 * Abstract class to represent the output path for a study guide
 * and its sections
 */
public abstract class AbsStudyGuide {
  /**
   * a string representation of the output path for this study guide
   */
  protected String output;
  /**
   * represents the location of the newly formed q and a file
   */
  protected File sessionPath;


  /**
   * @param output the output path for this study guide
   */
  public AbsStudyGuide(String output) {
    this.output = output; //Driver.outputPath;
    String session = output.substring(0, output.indexOf(".md")) + ".sr";
    sessionPath = new File(session);
    try {
      sessionPath.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
