package cs3500.music.model;

import java.util.ArrayList;

/**
 * The interface to represent what a MusicEditorModel must have.
 */
public interface IMusicEditorModel {

  /**
   * Returns the notes that are in the composition of music in the model.
   * @return the list of notes that makes up the composition of music.
   */
  ArrayList<INote> getMusic();

  /**
   * Gets the current tempo of the model (used for the MIDI view; tempo = 0 on construction).
   * @return the tempo of the model.
   */
  int getTempo();

  /**
   * Sets the tempo of the model to whatever the passed in value is (must be > 0).
   * @param tempo is the tempo that the model will be set to.
   */
  void setTempo(int tempo);

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
   * Adds a given note to the musical piece.
   * @param note is the note to add.
   */
  void addNote(INote note);

  /**
   * Removes a given note from the musical piece.
   * @param note is the note to remove.
   */
  void removeNote(INote note);

  /**
   * Adds pre-existing music (as an ArrayList) to the entire collection (play at same time).
   * @param music is the musical piece that is to be played consecutively.
   */
  void addMusicDuring(ArrayList<INote> music);

  /**
   * Adds pre-existing music (as an ArrayList) to the entire collection AFTER the current music.
   * (i.e. when it is done playing).
   * @param music is the musical piece that is to be played consecutively.
   */
  void addMusicAfter(ArrayList<INote> music);

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
