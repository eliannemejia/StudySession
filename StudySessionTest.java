package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for all methods in StudySession.Class
 */
class StudySessionTest {
  StudySession session;
  SessionModel model;
  SessionView view;
  Path mockInput;
  Scanner scan;

  /**
   * initial values for all testing fields
   */
  @BeforeEach
  void initData() {
    model = new SessionModel();
    view = new SessionView();
    mockInput = Path.of("mockInput.txt");
    try {
      Files.newOutputStream(mockInput, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * tests for begin session
   */
  @Test
  void beginSession() {
    try {
      Files.write(mockInput, "sampleSr.sr\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "9\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "5\n".getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      FileReader reader = new FileReader("mockInput.txt");
      scan = new Scanner(reader);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    session = new StudySession(model, view, scan);
    session.beginSession();
    assertTrue(model.hasNextQuestion());
  }

  /**
   * tests for advance session
   */
  @Test
  void advanceSession() {
    try {
      Files.write(mockInput, "sampleSr.sr\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "9\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      FileReader reader = new FileReader("mockInput.txt");
      scan = new Scanner(reader);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    session = new StudySession(model, view, scan);
    session.beginSession();
    assertFalse(model.hasNextQuestion());
    initData();
    try {
      Files.write(mockInput, "sampleSr.sr\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "3\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "1\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "5\n".getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      FileReader reader = new FileReader("mockInput.txt");
      scan = new Scanner(reader);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    session = new StudySession(model, view, scan);
    session.beginSession();
    assertTrue(model.getHard().size() < 9);
    initData();
    try {
      Files.write(mockInput, "allEasy.sr\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "3\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "2\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "5\n".getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      FileReader reader = new FileReader("mockInput.txt");
      scan = new Scanner(reader);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    session = new StudySession(model, view, scan);
    session.beginSession();
    assertTrue(model.getEasy().size() < 9);
    initData();
    try {
      Files.write(mockInput, "sampleSr.sr\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "3\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "3\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "5\n".getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      FileReader reader = new FileReader("mockInput.txt");
      scan = new Scanner(reader);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    session = new StudySession(model, view, scan);
    session.beginSession();
  }

  /**
   * Tests for gen questions
   */
  @Test
  void genQuestions() {
    try {
      Files.write(mockInput, "sampleSr.sr\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "4\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "2\n".getBytes(), StandardOpenOption.APPEND);
      Files.write(mockInput, "5\n".getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      FileReader reader = new FileReader("mockInput.txt");
      scan = new Scanner(reader);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    session = new StudySession(model, view, scan);
    session.beginSession();
    assertFalse(session.genQuestions(13));
    assertTrue(session.genQuestions(5));
  }
}