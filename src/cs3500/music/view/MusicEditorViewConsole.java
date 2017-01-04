package cs3500.music.view;

import java.awt.event.ActionListener;
import java.io.IOException;

import cs3500.music.model.INote;
import cs3500.music.util.IData;

/**
 * This represents the console view implementation for the Music Editor project.
 * The information is displayed through the console as strings.
 */
public class MusicEditorViewConsole implements IMusicEditorView {

  private IData adapter;
  private Appendable ap;

  public MusicEditorViewConsole() {
    // sets the default appendable to that of the console
    this.ap = System.out;
  }

  public MusicEditorViewConsole(Appendable ap) {
    // sets appendable to the input - this constructor is used just for testing
    this.ap = ap;
  }

  @Override
  public void setAdapter(IData adapter) {
    this.adapter = adapter;
  }

  @Override
  public IData getAdapter() {
    return this.adapter;
  }

  @Override
  public void display() {
    // this.renderState();
    // will need to append this to the appendable
    try {
      this.ap.append(this.renderState());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void refresh() {
    /* Will finish this when we implement user inputs for the next hw assignment */
  }

  @Override
  public void setExecuteListener(ActionListener actionEvent) {
    // does nothing
  }

  @Override
  public String sendCommand() {
    /* Will finish this when we implement user inputs for the next hw assignment */
    return "";
  }

  /* The string renderState from model which is moved to this view (remember to remove from model)*/

  /**
   * Creates the pitch heading based off of the lowest and highest notes in the model.
   * @return the string representation of the pitch headings.
   */
  private String renderPitchHeading() {
    String result = "  ";
    INote temp = this.adapter.findLowestNote();
    while (!temp.samePOS(this.adapter.findHighestNote())) {
      result = result + temp.noteToString();
      temp = temp.nextNote();
    }
    result = result + this.adapter.findHighestNote().noteToString();
    //adds the highest value cause it gets cut off
    return result;
  }

  /**
   * Returns how many leading spaces need to go before the given beat (right-justified).
   * @param currentBeat is the current beat.
   * @return a string consisting of however many leading spaces we need for the beat column.
   */
  private String leadingBeatSpace(int currentBeat) {
    String result = ""; // the output string
    String curBeat = Integer.toString(currentBeat); // makes the current beat a string
    String maxBeat = Integer.toString(this.adapter.findLastBeat()); // converts max beat to string
    for (int i = 0; i < (maxBeat.length() - curBeat.length()); i++) {
      result = result + " ";
    }
    return result;
  }

  private String renderLine(int currentBeat) {
    String result = "";
    result = result + this.leadingBeatSpace(currentBeat) + currentBeat; // does the beat number
    for (int i = 0; i < this.adapter.getScaleRange().size(); i++) {
      if (this.adapter.noteStartsHuh(currentBeat, i)) {
        result = result + this.addX();
      }
      else if (this.adapter.noteContinuesHuh(currentBeat, i)) {
        result = result + this.addLine();
      }
      else {
        result = result + this.addEmpty();
      }
    }
    return result;
  }

  /**
   * Returns an X to signify a note starts here.
   * @return an X to show a beat starts there.
   */
  private String addX() {
    return "  X  ";
  }

  /**
   * Returns a vertical line to signify a note continues there.
   * @return a string used for showing a beat is continuing.
   */
  private String addLine() {
    return "  |  ";
  }

  /**
   * Returns 5 empty spaces to signify no note is here (there's a rest).
   * @return a string used for showing a rest.
   */
  private String addEmpty() {
    return "     ";
  }

  /**
   * Renders the model into a string representation based off of what notes there are currently.
   * @return the string representation of the model.
   */
  private String renderState() {
    String result = "";
    if (this.adapter.getMusic().size() < 1) {
      return "Nothing to display, no music in model yet";
    }
    result = result + this.renderPitchHeading() + "\n";
    for (int i = 0; i < this.adapter.findLastBeat(); i++) {
      if (i == this.adapter.findLastBeat() - 1) {
        result = result + this.renderLine(i);
      }
      else {
        result = result + this.renderLine(i) + "\n";
      }
    }
    return result;
  }

}
