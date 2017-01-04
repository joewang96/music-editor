package cs3500.music.util;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.INote;

/**
 * The builder class which implements the CompositionBuilder interface given to us.
 * The builder is used in conjunction with the MusicReader to add notes from a file.
 */
public class Builder implements CompositionBuilder<IMusicEditorModel> {

  private IMusicEditorModel model;

  /**
   * The constructor for our Builder class. The model is automatically created using a blank one.
   */
  public Builder() {
    this.model = new MusicEditorModel();
  }

  /**
   * Returns the built model.
   * @return the model to be used.
   */
  public IMusicEditorModel build() {
    return this.model;
  }

  /**
   * Sets the tempo to be used for the midi view.
   * @param tempo The speed, in microseconds per beat.
   * @return the composition builder after it is edited.
   */
  public CompositionBuilder<IMusicEditorModel> setTempo(int tempo) {
    this.model.setTempo(tempo);
    return this;
  }

  /**
   * Adds notes to the current composition.
   * @param start The start time of the note, in beats.
   * @param end The end time of the note, in beats.
   * @param instrument The instrument number (to be interpreted by MIDI).
   * @param pitch The pitch (in the range [0, 127], where 60 represents C4, the middle-C on piano).
   * @param volume The volume (in the range [0, 127]).
   * @return itself after all changes are made and note is added.
   */
  public CompositionBuilder<IMusicEditorModel> addNote(int start, int end, int instrument,
                                                       int pitch, int volume) {
    // my C4 note = 49, translated MIDI values to mine
    int tempPO = pitch - 11;
    int myOct = tempPO / 12;
    int myPitch = tempPO % 12;
    if (myPitch == 0) {
      myPitch = 12;
    }

    INote placeholder = new Note(INote.NotePitch.A, 1, 1, 2, 3, 4);
    INote temp = new Note(placeholder.valueToPitch(myPitch), myOct, start, end - start,
            instrument, volume);
    this.model.addNote(temp);

    return this;
  }

}
