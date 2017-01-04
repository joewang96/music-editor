package cs3500.music.view;

/**
 * This class is the factory for creating different types of views for the music editor project.
 * Since we have multiple types of views we want the user to be able to choose which one to use.
 * Therefore we have this factory which returns a certain view based on the given ViewType enum.
 */
public class MusicEditorFactory {

  /**
   * This is the constructor for the MusicEditorFactory, which of course doesn't take in anything.
   */
  public MusicEditorFactory() { /* nothing here */ }

  /**
   * Creates an IMusicEditorView of the given type corresponding to the inputted view type.
   * @param type is the type of view which will be created by the factory.
   * @return a particular implementation of IMusicEditorView based on the given view type.
   */

  public static IMusicEditorView create(String type) {
    switch (type) {
      case "console":
        return new MusicEditorViewConsole();
      case "visual":
        return new MusicEditorViewGUI();
      case "midi":
        return new MusicEditorViewMIDI();
      case "composite":
        return new MusicEditorViewComposite(new MusicEditorViewGUI(), new MusicEditorViewMIDISeq());
      default:
        throw new IllegalArgumentException("View type is not valid");
    }
  }
}
