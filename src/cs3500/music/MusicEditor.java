package cs3500.music;

import java.io.File;

import cs3500.music.controller.BasicMusicController;
import cs3500.music.controller.ComplexMusicController;
import cs3500.music.controller.IMusicEditorController;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.IMusicEditorViewGUI;
import cs3500.music.view.MusicEditorFactory;

/**
 * This class represents the MusicEditor as a whole (MVC), and holds the main method to run it.
 */
public class MusicEditor {

  /**
   * This is the main method which actually runs the music editor and displays the view.
   * @param args Are the name of the file which we will read from (.txt) and the view type.
   *             view type is one of: console, visual, or midi.
   */
  public static void main(String []args) {
    // variables for the command line arguments
    if (args.length < 2) {
      throw new IllegalArgumentException("File type or view type is missing!");
    }
    String fileName = args[0];
    String viewType = args[1];

    // initializes and constructs the factory so we can choose our view
    MusicEditorFactory factory = new MusicEditorFactory();

    // initializes, but doesn't construct the view until the controller gets which one the user
    // wants to make from the factory's create() method
    IMusicEditorView view = factory.create(viewType); // will need to change this to take in
    // a string from the command line

    File temp = new File("src/cs3500/music/" + fileName);

    // initializes and constructs our controller that we will use and launches the program using
    // the controller's go() method
    IMusicEditorController controller;
    if (viewType.equals("composite")) {
      controller = new ComplexMusicController(temp, (IMusicEditorViewGUI) view);
    }
    else {
      controller = new BasicMusicController(temp, view);
    }
    controller.run();
  }
}
