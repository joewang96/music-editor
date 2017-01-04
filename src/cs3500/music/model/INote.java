package cs3500.music.model;

/**
 * This is the interface which represents what a Note could be.
 */
public interface INote {

  /**
   * This is the letter the note has and can range from E tp D.
   */
  enum NotePitch {
    /**
     * These notes are the possible musical notes (E to D) and all with sharps but E and B.
     */
    E, F, FSharp, G, GSharp, A, ASharp, B, C, CSharp, D, DSharp;
  }

  /**
   * Determines if a given note is the same as this note.
   * @param aNote is the Note to test if it is the same.
   * @return returns if the given note is the same as this note.
   */
  boolean isSame(INote aNote);

  /**
   * Determines if the note has the same pitch, octave, and sharp values.
   * @param aNote is the note to compare to.
   * @return true if they do have the same pitch, octave, and sharp values.
   */
  boolean samePOS(INote aNote);

  /**
   * Returns the pitch of a given note (used for editing existing notes and comparing).
   * @return returns the pitch as a NotePitch.
   */
  NotePitch getPitch();

  /**
   * Returns the octave of a given note (used for editing existing notes and comparing).
   * @return returns the octave as an int.
   */
  int getOctave();

  /**
   * Returns the start beat of a given note (used for playing consecutively).
   * @return returns the start beat as an int.
   */
  int getStart();

  /**
   * Returns the length of a note (used for playing consecutively).
   * @return the length of a note (duration) as an int.
   */
  int getDuration();

  /**
   * Sets the note's pitch to the given one (used in editing existing notes).
   * @param pitch is the pitch in which to change to.
   */
  void setPitch(NotePitch pitch);

  /**
   * Sets the note's octave to the given one (used in editing existing notes).
   * @param octave is the octave in which to change to.
   */
  void setOctave(int octave);

  /**
   * Modifies the start beat of a given note (used for playing consecutively).
   * @param start is the beat in which to start on.
   */
  void setStart(int start);

  /**
   * Sets the note's duration to the given one (used in editing existing notes).
   * @param duration is the length in which to change to.
   */
  void setDuration(int duration);

  /**
   * Returns a value based on the pitch (higher pitches are given a higher value).
   * @return the value assigned to a pitch.
   */
  int pitchValue();

  /**
   * Assigns a numerical value to the note (based on pitch and octave) to help sort notes.
   * @return the value used to assign which notes are higher and lower.
   */
  int noteToValue();

  /**
   * Gives a string representation (if applicable) to the note.
   * @return the string representation of the note.
   */
  String noteToString();

  /**
   * Returns a note pitch given its numerical value.
   * @param i is the integer to use.
   * @return the pitch given its numerical form.
   */
  NotePitch valueToPitch(int i);

  /**
   * Returns the note that is one higher than the current note with the same start and duration.
   * @return a note that is the next in the sequence.
   */
  INote nextNote();

  /**
   * Gets the instrument for the note (used in the MIDI view).
   * @return the instrument, denoted as an integer value.
   */
  int getInstrument();

  /**
   * Sets the instrument for the note (used in the MIDI view) according to the given integer.
   * @param instrument is the instrument that will be played by the note (integer value).
   */
  void setInstrument(int instrument);

  /**
   * Sets the volume the note will play at (used for the MIDI view only).
   * @return an integer value denoting the volume the note will be played at.
   */
  int getVolume();

  /**
   * Sets the volume that the note will be played at (used only in the MIDI view).
   * @param volume is the integer value of the volume that will be used when the note is played.
   */
  void setVolume(int volume);
}
