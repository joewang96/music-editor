package cs3500.music.view;

import java.awt.event.ActionListener;

import cs3500.music.util.IData;

/**
 * This represents the interface for the different views supported by the Music Editor.
 * This interface details the minimum requirements that the view must support.
 */
public interface IMusicEditorView {

  /**
   * Sets the adapter that will be used for the view.
   * (must have this so we can create an empty view at first w/ factory then give it data).
   * @param adapter is the adapter that we will give it which will be the data from the model.
   */
  void setAdapter(IData adapter);

  /**
   * Gets the adapter from the view.
   * @return the adapter as an IData object.
   */
  IData getAdapter();

  /**
   * Sets the display so that the view plays.
   */
  void display();

  /**
   * Refreshes the view so that the display that the view is showing is up to date with the model.
   */
  void refresh();

  /**
   * Provide the view with an action listener for
   * the button that should cause the program to
   * process a command. This is so that when the button
   * is pressed, control goes to the action listener
   * @param actionEvent is the action event from the button from the view.
   */
  void setExecuteListener(ActionListener actionEvent);

  /**
   * Sends a command as a string to the controller.
   * @return returns a string which tells the controller what to do.
   */
  String sendCommand();

}
