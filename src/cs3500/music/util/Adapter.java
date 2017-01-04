package cs3500.music.util;

import java.util.ArrayList;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.INote;

/**
 * Represents the implementation of IData which will take in a model and send its data to the View.
 */
public class Adapter implements IData {

  private IMusicEditorModel model;

  /**
   * The constructor for the Data class. It takes in the model whose data will be accessed.
   * @param model is the model to be used and who's data will be sent to the view eventually.
   */
  public Adapter(IMusicEditorModel model) {
    this.model = model;
  }

  @Override
  public ArrayList<INote> getMusic() {
    return this.model.getMusic();
  }

  @Override
  public int getTempo() {
    return this.model.getTempo();
  }

  @Override
  public int findLastBeat() {
    return this.model.findLastBeat();
  }

  @Override
  public INote findLowestNote() {
    return this.model.findLowestNote();
  }

  @Override
  public INote findHighestNote() {
    return this.model.findHighestNote();
  }

  @Override
  public ArrayList<INote> getScaleRange() {
    return this.model.getScaleRange();
  }

  @Override
  public boolean noteStartsHuh(int beat, int rangeNumber) {
    return this.model.noteStartsHuh(beat, rangeNumber);
  }

  @Override
  public boolean noteContinuesHuh(int beat, int rangeNumber) {
    return this.model.noteContinuesHuh(beat, rangeNumber);
  }

}
