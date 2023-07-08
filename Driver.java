package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * This is the main driver of this project.
 */
public class Driver {
  static Path inputPath;
  static String orderingFlag;
  static Path outputPath;

  /**
   * Project entry point
   *
   * @param args - 3 command line arguments:
   *             - relative or absolute folder path
   *             - ordering flag:
   *             - filename, created, modified
   *             - output folder path
   */
  public static void main(String[] args) throws IllegalArgumentException {
    if (args.length == 3) {
      inputPath = Path.of(args[0]);
      orderingFlag = args[1];
      outputPath = Path.of(args[2]);
      FolderVisitor fv = new FolderVisitor(inputPath, outputPath);
      try {
        Files.walkFileTree(inputPath, fv);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (args.length == 0) {
      InterfSessionModel model = new SessionModel();
      InterfSessionView view = new SessionView();
      Scanner scan = new Scanner(System.in);
      InterfStudySession session = new StudySession(model, view, scan);
      session.beginSession();
    } else {
      throw new IllegalArgumentException("Must have either 0 or 3 arguments");
    }
  }
}