package cs3500.music.view;

import java.awt.event.ActionListener;

/**
 * This represents the interface for the different views supported by the Music Editor.
 * This interface details the minimum requirements that the view must support.
 */
public interface IMusicEditorViewGUI extends IMusicEditorView {

  /**
   * Refreshes the view so that the display that the view is showing is up to date with the model.
   */
  void refresh();

  @Override
  void setExecuteListener(ActionListener actionEvent);

  /**
   * Displays an error message if there is one.
   */
  void showError(String error);

  /**
   * Resets the focus of the frame to allow for key events.
   */
  void resetFocus();


}

