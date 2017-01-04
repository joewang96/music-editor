package cs3500.music.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.util.Adapter;
import cs3500.music.util.Builder;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.IData;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicEditorView;

/**
 * This is an example of a basic controller to be used for the MusicEditor.
 */
public class BasicMusicController implements IMusicEditorController {
  final IMusicEditorModel model;
  IMusicEditorView view;

  /**
   * Constructs a basic controller that we will be using for this assignment.
   * Note that we will be making a new, concrete Controller implementation and interface next hw.
   * @param text is the File that we want to read from which will create our model with reader.
   * @param view is the view which we want to display our composition on (passed in from factory).
   */
  public BasicMusicController(File text, IMusicEditorView view) {
    MusicReader temp = new MusicReader();
    CompositionBuilder<IMusicEditorModel> builder = new Builder();
    Readable fr = null;
    try {
      fr = new FileReader(text);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    this.model = temp.parseFile(fr, builder);
    this.view = view;

    // now to set the adapter so the view has the data it needs
    IData adapter = new Adapter(model);
    this.view.setAdapter(adapter);
  }

  @Override
  public String processCommand(String command) {
    return null;
  }

  /**
   * Tells the controller to display the view based on the model that was built from the text file.
   */
  public void run() {
    this.view.refresh();
    this.view.display();
  }
}
