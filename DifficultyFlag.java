package cs3500.pa01;

/**
 * Represents a difficulty flag (i.e. hard or easy)
 */
public enum DifficultyFlag {
  /**
   * represents a hard(difficult) object
   */
  HARD,
  /**
   * represents an easy object
   */
  EASY;

  /**
   * @return a string representation of this difficulty flag
   */
  @Override
  public String toString() {
    if (this.equals(EASY)) {
      return "(E)";
    } else {
      return "(H)";
    }
  }

}
