package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests for all methods in Question.Class
 */
class QuestionTest {
  Question capitol;
  Question states;
  Question circle;

  @BeforeEach
  void initData() {
    capitol = new Question("What is the capitol of Pennsylvania?",
        "Answer: Harrisburg", "(H)");
    states = new Question("How many states are in the U.S?",
        "Answer: 50", "(E)");
    circle = new Question("What is the formula for area of a circle?",
        "Answer: pi r squared", "(H)");
  }

  /**
   * tests for set difficulty
   */
  @Test
  void setDifficulty() {
    capitol.setDifficulty(DifficultyFlag.EASY);
    assertFalse(capitol.isHard());
    states.setDifficulty(DifficultyFlag.HARD);
    assertTrue(states.isHard());
    circle.setDifficulty(DifficultyFlag.HARD);
    assertTrue(circle.isHard());
  }

  /**
   * tests for get answer
   */
  @Test
  void getAnswer() {
    assertEquals("Answer: Harrisburg", capitol.getAnswer());
    assertEquals("Answer: 50", states.getAnswer());
    assertEquals("Answer: pi r squared", circle.getAnswer());
  }

  /**
   * tests for get question
   */
  @Test
  void getQuestion() {
    assertEquals("What is the capitol of Pennsylvania?", capitol.getQuestion());
    assertEquals("How many states are in the U.S?", states.getQuestion());
    assertEquals("What is the formula for area of a circle?", circle.getQuestion());
  }

  /**
   * tests for is hard
   */
  @Test
  void isHard() {
    assertTrue(capitol.isHard());
    assertFalse(states.isHard());
    assertTrue(circle.isHard());
  }

  /**
   * tests for to string
   */
  @Test
  void testToString() {
    assertEquals("Question (H):What is the capitol of Pennsylvania?\n"
        + "Answer: Harrisburg", capitol.toString());
    assertEquals("Question (E):How many states are in the U.S?\n"
        + "Answer: 50", states.toString());
  }
}