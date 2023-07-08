package cs3500.pa01;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * Represents a spaced repetition study session
 */
public class StudySession implements InterfStudySession {
  InterfSessionModel model;
  InterfSessionView view;
  Scanner scan;
  Question currentQ;

  /**
   * @param m the model for this study session
   * @param v the view for this study session
   */
  public StudySession(InterfSessionModel m, InterfSessionView v, Scanner s) {
    model = m;
    view = v;
    scan = s;
  }

  /**
   * Begins this study session
   */
  @Override
  public void beginSession() {
    view.displayWelcome();
    String input = scan.nextLine();
    Path inputPath = Path.of(input);
    if (input.endsWith(".sr") && inputPath.toFile().exists()) {
      model.setSource(inputPath);
      view.displayMessage("How many questions would you like to study today?");
      while (!genQuestions(scan.nextInt())) {
        genQuestions(scan.nextInt());
      }
      while (!model.sessionOver()) {
        advanceSession();
      }
    } else {
      view.displayMessage("Please provide a valid .sr file");
    }
  }

  /**
   * @param num the number of questions for this session
   */
  public boolean genQuestions(int num) {
    int total = model.getHard().size() + model.getEasy().size();
    if (num <= total) {
      model.getSeshQuests(num);
      currentQ = model.selectQuestion();
      view.displayMessage("Question:" + currentQ.getQuestion());
      view.displayOptions();
      return true;
    } else {
      view.displayMessage("Sorry, your file only has " + total + " questions :(");
      view.displayMessage("Please choose a number less than " + total);
      return false;
    }
  }

  /**
   * Advances the study session based on user input
   */
  @Override
  public void advanceSession() {
    int selection = scan.nextInt();
    if (selection == 1) {
      model.updateDiffFlag(currentQ, DifficultyFlag.EASY);
      view.displayMessage("Great job!");
      view.displayOptions();
    } else if (selection == 2) {
      model.updateDiffFlag(currentQ, DifficultyFlag.HARD);
      view.displayMessage("It's good to keep studying!");
      view.displayOptions();
    } else if (selection == 3) {
      view.displayMessage(currentQ.getAnswer());
      view.displayOptions();
    } else if (selection == 4) {
      if (model.hasNextQuestion()) {
        currentQ = model.selectQuestion();
        view.displayMessage("Question:" + currentQ.getQuestion());
        view.displayOptions();
      } else {
        view.displayMessage("No more questions :(");
        model.endSession();
        view.displayFarewell(model.endingStats());
      }
    } else if (selection == 5) {
      model.endSession();
      view.displayFarewell(model.endingStats());
    }
  }
}
