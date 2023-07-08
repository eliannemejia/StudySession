package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a Study Session model
 */
public class SessionModel implements InterfSessionModel {
  boolean sessionOver;
  private ArrayList<Question> questionBank;
  private ArrayList<Question> sessionQuestions;
  private Scanner scan;
  private int markedHard = 0;
  private int markedEasy = 0;
  private int questionsAnswered = 0;
  Path studySource;
  Random rand;

  /**
   * initializes an ongoing study session
   */
  public SessionModel() {
    sessionOver = false;
    rand = new Random();
  }

  /**
   * @param seed the seed for the random object in this session
   *             Convenience constructor for testing
   */
  public SessionModel(int seed) {
    sessionOver = false;
    rand = new Random(seed);
  }

  /**
   * @return list of questions for this study session
   */
  @Override
  public ArrayList<Question> genBank() {
    ArrayList<Question> quesBank = new ArrayList<>();
    while (scan.hasNext()) {
      String line = scan.nextLine();
      Question q;
      String question;
      String answer;
      String flag;
      if (line.startsWith("Question")) {
        question = line.substring(line.indexOf(")") + 2);
        flag = line.substring(line.indexOf("("), line.indexOf(")") + 1);
        answer = scan.nextLine();
        q = new Question(question, answer, flag);
        quesBank.add(q);
      }
    }
    return quesBank;
  }

  /**
   * @param num the number of questions for this session
   */
  @Override
  public void getSeshQuests(int num) {
    ArrayList<Question> hard = this.getHard();
    ArrayList<Question> easy = this.getEasy();
    ArrayList<Question> seshQuests = new ArrayList<>();
    int hardCount = hard.size();
    int idx;
    if (num <= hardCount) {
      while (seshQuests.size() < num) {
        idx = rand.nextInt(hard.size());
        seshQuests.add(hard.get(idx));
        hard.remove(idx);
      }
    } else {
      while (seshQuests.size() < hardCount) {
        idx = rand.nextInt(hard.size());
        seshQuests.add(hard.get(idx));
        hard.remove(idx);
      }
      while (seshQuests.size() < num) {
        idx = rand.nextInt(easy.size());
        seshQuests.add(easy.get(idx));
        easy.remove(idx);
      }
    }
    sessionQuestions = seshQuests;
  }

  /**
   * @return determines if there are more questions in this session
   */
  public boolean hasNextQuestion() {
    return sessionQuestions.size() > 0;
  }

  /**
   * @return the next question in this study session
   */
  public Question selectQuestion() {
    Question q = sessionQuestions.get(0);
    sessionQuestions.remove(0);
    questionsAnswered++;
    return q;
  }

  /**
   * @param q    the index of the
   * @param flag the new difficulty flag for this question
   */
  @Override
  public void updateDiffFlag(Question q, DifficultyFlag flag) {
    int idx = questionBank.indexOf(q);
    questionBank.get(idx).setDifficulty(flag);
    if (flag.equals(DifficultyFlag.EASY)) {
      markedEasy++;
    }
    if (flag.equals(DifficultyFlag.HARD)) {
      markedHard++;
    }
  }

  /**
   * @param source the path to obtain the question bank from
   * @return true if the given file exists
   */
  @Override
  public boolean setSource(Path source) {
    studySource = source;
    try {
      scan = new Scanner(source);
    } catch (IOException e) {
      e.printStackTrace();
    }
    questionBank = this.genBank();
    return source.toFile().exists();
  }

  /**
   * Ends this study session
   */
  @Override
  public void endSession() {
    sessionOver = true;
    updateSource();
  }

  /**
   * @return determines whether this session is still ongoing
   */
  public boolean sessionOver() {
    return sessionOver;
  }

  /**
   * @return all the hard questions in the bank
   */
  @Override
  public ArrayList<Question> getHard() {
    ArrayList<Question> hard = new ArrayList<>();
    for (Question q : this.questionBank) {
      if (q.isHard()) {
        hard.add(q);
      }
    }
    return hard;
  }

  /**
   * @return all the easy questions in the bank
   */
  @Override
  public ArrayList<Question> getEasy() {
    ArrayList<Question> easy = new ArrayList<>();
    for (Question q : this.questionBank) {
      if (!q.isHard()) {
        easy.add(q);
      }
    }
    return easy;
  }

  /**
   * Writes the updated questions to the study source
   */
  public void updateSource() {
    try {
      Files.newOutputStream(studySource, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    for (Question q : questionBank) {
      try {
        String str = q.toString() + System.lineSeparator();
        Files.write(studySource, str.getBytes(), StandardOpenOption.APPEND);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * @return the ending stats for this study session
   */
  public ArrayList<String> endingStats() {
    ArrayList<String> endingStats = new ArrayList<>();
    endingStats.add("Questions Answered: " + questionsAnswered);
    endingStats.add("Marked as Easy: " + markedEasy);
    endingStats.add("Marked as Hard: " + markedHard);
    endingStats.add("Total Easy: " + getEasy().size());
    endingStats.add("Total Hard: " + getHard().size());
    return endingStats;
  }
}
