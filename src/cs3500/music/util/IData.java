package cs3500.music.util;

import java.util.ArrayList;

import cs3500.music.model.INote;

/**
 * This represents the data that will be taken from a model to be sent to the view.
 */
public interface IData {

  ArrayList<INote> getMusic();

  /**
   * Gets the current tempo of the model (used for the MIDI view; tempo = 0 on construction).
   * @return the tempo of the model.
   */
  int getTempo();

  /**
   * Finds the last beat in the music editor model (factor in start and duration to find last beat).
   * @return the last beat as an int.
   */
  int findLastBeat();

  /**
   * Finds the lowest note in the model.
   * @return the lowest note in the model.
   */
  INote findLowestNote();

  /**
   * Finds the highest note in the model.
   * @return the lowest note in the model.
   */
  INote findHighestNote();

  /**
   * Returns an array list (in order) of the range of notes in the music editor model.
   * @return an array list of all notes in the model.
   */
  ArrayList<INote> getScaleRange();

  /**
   * Returns if a note starts at the given beat for the given range number of the model.
   * @param beat is the beat to check if a note starts here.
   * @param rangeNumber is the index number of the getScaleRange of the model to use (which pitch).
   * @return true if a note starts here, false if not.
   */
  boolean noteStartsHuh(int beat, int rangeNumber);

  /**
   * Returns if a note continues at the given beat for the given range number of the model.
   * @param beat is the beat to check if a note continues here.
   * @param rangeNumber is the index number of the getScaleRange of the model to use (which pitch).
   * @return true if a note continues here, false if not.
   */
  boolean noteContinuesHuh(int beat, int rangeNumber);

}
