package cs3500.pa01;

import java.util.ArrayList;

/**
 * Repressents the view for a study session
 */
public class SessionView implements InterfSessionView {

  /**
   * Welcomes the user
   */
  @Override
  public void displayWelcome() {
    System.out.println("Welcome to your study session!");
    System.out.println("Please provide the .sr from which you'd like to study");
  }

  /**
   * Displays the options for advancing the study session
   */
  @Override
  public void displayOptions() {
    System.out.println("1) Mark Easy");
    System.out.println("2) Mark Hard");
    System.out.println("3) See Answer");
    System.out.println("4) Next Question");
    System.out.println("5) End Session");
  }

  /**
   * @param message message to be printed
   *                Prints the given message
   */
  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * Prints the ending stats for this study session
   */
  @Override
  public void displayFarewell(ArrayList<String> endingStats) {
    System.out.println("Great work today!");
    for (String s : endingStats) {
      System.out.println(s);
    }
    System.out.println("Goodbye!");
  }
}
