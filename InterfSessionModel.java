package cs3500.pa01;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Represents the model for a study session
 */
public interface InterfSessionModel {
  /**
   * @return list of questions for this study session
   */
  ArrayList<Question> genBank();

  /**
   * @param num the number of questions for this session
   */
  void getSeshQuests(int num);

  /**
   * @param q    the index of the
   * @param flag the new difficulty flag for this question
   */
  void updateDiffFlag(Question q, DifficultyFlag flag);

  /**
   * @param source the path to obtain the question bank from
   * @return true if the given file exists
   */
  boolean setSource(Path source);

  /**
   * Ends this study session
   */
  void endSession();

  /**
   * @return all the hard questions in the bank
   */
  ArrayList<Question> getHard();

  /**
   * @return all the easy questions in the bank
   */
  ArrayList<Question> getEasy();

  /**
   * @return the next question in this study session
   */
  Question selectQuestion();

  /**
   * determines whether this session is still ongoing
   */
  boolean sessionOver();

  /**
   * @return the ending stats for this study session
   */
  ArrayList<String> endingStats();

  /**
   * @return determines if there are more questions in this session
   */
  boolean hasNextQuestion();

  /**
   * Writes the updated questions to the study source
   */
  void updateSource();
}
