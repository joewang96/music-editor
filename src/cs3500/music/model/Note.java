package cs3500.music.model;

/**
 * This represents a single note.
 */
public class Note implements INote {

  // this represents the letter of the note
  private NotePitch pitch;

  // this represents the octave of the note
  private int octave;

  // this represents the length or duration of the note, in terms of beats
  protected int duration;

  // tells the note what beat to start on
  private int start;

  // tells the note which instrument it will be played as
  private int instrument;

  // tells the note the volume that it will be played at
  private int volume;

  /**
   * The constructor for a note.
   * @param pitch is the pitch (NotePitch).
   * @param octave is the octave (int).
   * @param start is the beat to start on.
   * @param duration is how many beats it lasts.
   */
  public Note(NotePitch pitch, int octave, int start, int duration, int instrument, int volume) {
    this.pitch = pitch;
    this.octave = octave;
    if (start < 0) { // start must be 0 or after, can't have negative beats
      throw new IllegalArgumentException("Start must be >= 0");
    }
    else {
      this.start = start;
    }

    this.duration = duration;
    this.instrument = instrument;
    this.volume = volume;
  }

  @Override
  public boolean isSame(INote aNote) {
    return (this.noteToValue() == aNote.noteToValue()) && this.start == aNote.getStart()
            && this.duration == aNote.getDuration();
  }

  @Override
  public boolean samePOS(INote aNote) {
    return this.noteToValue() == aNote.noteToValue();
  }

  @Override
  public NotePitch getPitch() {
    return this.pitch;
  }

  @Override
  public int getOctave() {
    return this.octave;
  }

  @Override
  public int getStart() {
    return this.start;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

  @Override
  public int getInstrument() {
    return this.instrument;
  }

  @Override
  public int getVolume() {
    return this.volume;
  }

  @Override
  public void setPitch(NotePitch pitch) {
    this.pitch = pitch;
  }

  @Override
  public void setOctave(int octave) {
    this.octave = octave;
  }

  @Override
  public void setStart(int start) {
    if (start < 0) { // start must be 0 or after, can't have negative beats
      throw new IllegalArgumentException("Start must be >= 0");
    }
    else {
      this.start = start;
    }
  }

  @Override
  public void setDuration(int duration) {
    if (duration < 1) {
      throw new IllegalArgumentException("Duration must be greater than 0");
    }
    else {
      this.duration = duration;
    }
  }

  @Override
  public void setInstrument(int instrument) {
    this.instrument = instrument;
  }

  @Override
  public void setVolume(int volume) {
    this.volume = volume;
  }

  @Override
  public int pitchValue() {
    switch (this.pitch) {
      case C:
        return 1;
      case CSharp:
        return 2;
      case D:
        return 3;
      case DSharp:
        return 4;
      case E:
        return 5;
      case F:
        return 6;
      case FSharp:
        return 7;
      case G:
        return 8;
      case GSharp:
        return 9;
      case A:
        return 10;
      case ASharp:
        return 11;
      case B:
        return 12;
      default:
        throw new IllegalArgumentException("Pitch not valid");
    }
  }

  @Override
  public int noteToValue() {
    return (this.octave * 12) + this.pitchValue();
  }

  /**
   * Returns a string value of the note based on pitch, "sharpness", and octave.
   * @return the string representation of the note.
   */
  @Override
  public String noteToString() {
    String result = "";
    switch (this.pitch) {
      case E:
        result = result + "E";
        break;
      case F:
        result = result + "F";
        break;
      case FSharp:
        result = result + "F#";
        break;
      case G:
        result = result + "G";
        break;
      case GSharp:
        result = result + "G#";
        break;
      case A:
        result = result + "A";
        break;
      case ASharp:
        result = result + "A#";
        break;
      case B:
        result = result + "B";
        break;
      case C:
        result = result + "C";
        break;
      case CSharp:
        result = result + "C#";
        break;
      case D:
        result = result + "D";
        break;
      case DSharp:
        result = result + "D#";
        break;
      default:
        throw new IllegalArgumentException("Pitch not valid");
    }

    result = result + Integer.toString(this.octave);

    // now to add the leading spaces
    if (result.length() == 2) {
      result = "  " + result + " ";
    }
    else if (result.length() == 3) {
      result = " " + result + " ";
    }
    else if (result.length() == 4) {
      result = result + " ";
    }
    else if (result.length() == 5) {
      // nothing changes (no extra spacing)
    }
    else {
      throw new IllegalArgumentException("Note octave is too high for this representation");
    }
    return result;
  }

  @Override
  public NotePitch valueToPitch(int i) {
    switch (i) {
      case 1:
        return NotePitch.C;
      case 2:
        return NotePitch.CSharp;
      case 3:
        return NotePitch.D;
      case 4:
        return NotePitch.DSharp;
      case 5:
        return NotePitch.E;
      case 6:
        return NotePitch.F;
      case 7:
        return NotePitch.FSharp;
      case 8:
        return NotePitch.G;
      case 9:
        return NotePitch.GSharp;
      case 10:
        return NotePitch.A;
      case 11:
        return NotePitch.ASharp;
      case 12:
        return NotePitch.B;
      default:
        throw new IllegalArgumentException("Value is not between 1 and 12");
    }
  }

  @Override
  public INote nextNote() {
    int newOct;
    NotePitch newPitch;

    if (this.pitchValue() == 12) {
      newOct = this.octave + 1;
      newPitch = this.valueToPitch(1);
    }

    else {
      newOct = this.octave;
      newPitch = this.valueToPitch(this.pitchValue() + 1);
    }
    return new Note(newPitch, newOct, this.start, this.duration, this.instrument, this.volume);
  }
}
