package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for all methods in SessionModel.Class
 */
class SessionModelTest {
  SessionModel model;
  Path sessionGuide = Path.of("sampleSr.sr");

  @BeforeEach
  void initData() {
    model = new SessionModel(4);
    model.setSource(sessionGuide);
  }

  /**
   * tests for gen bank
   */
  @Test
  void genBank() {
    //assertEquals(9, model.getHard().size());
  }

  /**
   * tests for get sesh quests
   */
  @Test
  void getSeshQuests() {
    model.setSource(Path.of("mixedDifficulty.sr"));
    model.getSeshQuests(9);
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertFalse(model.hasNextQuestion());
  }

  /**
   * tests for has next question
   */
  @Test
  void hasNextQuestion() {
    model.getSeshQuests(0);
    assertFalse(model.hasNextQuestion());
    model.getSeshQuests(3);
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertTrue(model.hasNextQuestion());
    model.selectQuestion();
    assertFalse(model.hasNextQuestion());
  }


  /**
   * tests for set source
   */
  @Test
  void setSource() {
    assertTrue(model.setSource(Path.of("sampleSr.sr")));
    //assertFalse(model.setSource(Path.of("fakeFile.sr")));
  }

  /**
   * tests for session over
   */
  @Test
  void sessionOver() {
    assertFalse(model.sessionOver());
    model.endSession();
    assertTrue(model.sessionOver());
  }

  /**
   * tests for update source
   */
  @Test
  void updateSource() {
    Scanner scan;
    Path update = Path.of("updateSource.sr");
    model.setSource(update);
    for (Question q : model.getHard()) {
      q.setDifficulty(DifficultyFlag.EASY);
    }
    try {
      scan = new Scanner(update);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    model.updateSource();
    while (scan.hasNext()) {
      String line = scan.nextLine();
      if (line.startsWith("Question")) {
        assertTrue(line.contains("(E)"));
      }
    }
  }

  /**
   * tests for ending stats
   */
  @Test
  void endingStats() {
    int setHard = 0;
    int setEasy = 0;
    model.getSeshQuests(3);
    Question q1 = model.selectQuestion();
    if (q1.isHard()) {
      model.updateDiffFlag(q1, DifficultyFlag.EASY);
      setEasy++;
    } else {
      model.updateDiffFlag(q1, DifficultyFlag.HARD);
      setHard++;
    }
    Question q2 = model.selectQuestion();
    if (q2.isHard()) {
      model.updateDiffFlag(q2, DifficultyFlag.EASY);
      setEasy++;
    } else {
      model.updateDiffFlag(q2, DifficultyFlag.HARD);
      setHard++;
    }
    ArrayList<String> endingStats = model.endingStats();
    assertTrue(endingStats.get(0).contains("2"));
    assertTrue(endingStats.get(1).contains(Integer.toString(setEasy)));
    assertTrue(endingStats.get(2).contains(Integer.toString(setHard)));
  }
}
