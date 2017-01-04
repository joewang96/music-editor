package cs3500.music.controller;

/**
 * This represents the interface for the different controllers supported by the Music Editor.
 * This interface details the minimum requirements that the controller must support.
 */
public interface IMusicEditorController {

  /**
   * Process a given string command and return status or error as a string
   * @param command the command given, including any parameters (e.g. "remove C#4 3 5")
   * @return status or error message
   */
  String processCommand(String command);

  /**
   * Start the program, i.e. give control to the controller
   */
  void run();
}