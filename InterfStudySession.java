package cs3500.pa01;

/**
 * Represents a study session
 */
public interface InterfStudySession {
  /**
   * Prints a welcome message, prompts the user for an sr file
   */
  void beginSession();

  /**
   * Advances the study session based on user input
   */
  void advanceSession();
}

