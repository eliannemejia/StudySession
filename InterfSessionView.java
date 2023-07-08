package cs3500.pa01;

import java.util.ArrayList;

/**
 * Represents the view for a session
 */
public interface InterfSessionView {
  /**
   * Welcomes the user
   */
  void displayWelcome();

  /**
   * Displays the options for advancing the study session
   */
  void displayOptions();

  /**
   * @param message message to be printed
   *                Prints the given message
   */
  void displayMessage(String message);

  /**
   * @param endingStats the stats to be printed
   *                    for this study session
   */
  void displayFarewell(ArrayList<String> endingStats);
}
