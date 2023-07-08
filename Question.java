package cs3500.pa01;

/**
 * Represents a question in Q and A format
 */
public class Question {
  private final String question;
  private final String answer;
  private DifficultyFlag diffFlag;

  /**
   * @param q    the question
   * @param a    the answer to the question
   * @param flag the difficulty flag for this question
   */
  public Question(String q, String a, String flag) {
    question = q;
    answer = a;
    if (flag.equals("(H)")) {
      diffFlag = DifficultyFlag.HARD;
    }
    if (flag.equals("(E)")) {
      diffFlag = DifficultyFlag.EASY;
    }
  }

  /**
   * @param flag new difficulty flag
   *             sets the difficulty flag of this question to the given flag
   */
  void setDifficulty(DifficultyFlag flag) {
    this.diffFlag = flag;
  }

  /**
   * @return the answer to this question
   */
  String getAnswer() {
    return this.answer;
  }

  /**
   * @return the question for this question
   */
  String getQuestion() {
    return this.question;
  }

  /**
   * @return is this question hard or not
   */
  boolean isHard() {
    return this.diffFlag.equals(DifficultyFlag.HARD);
  }

  /**
   * @return a string representation of this question
   */
  @Override
  public String toString() {
    return "Question " + diffFlag.toString() + ":"
        + question + "\n" + answer;
  }

}
