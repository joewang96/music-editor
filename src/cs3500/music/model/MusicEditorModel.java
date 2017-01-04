package cs3500.music.model;

import java.util.ArrayList;

/**
 * The model for the Music Editor used for HW05.
 */
public class MusicEditorModel implements IMusicEditorModel {

  private ArrayList<INote> music;
  private int finalBeat;
  private INote lowestNote;
  private INote highestNote;
  private ArrayList<INote> range;
  private int tempo;

  /**
   * The constructor for the music editor if you have nothing in it.
   */
  public MusicEditorModel() {
    this.music = new ArrayList<>();
    this.tempo = 0;
  }

  /**
   * The constructor for the music editor if you want to give music to start.
   * @param music is the collection of notes in the music editor.
   */
  public MusicEditorModel(ArrayList<INote> music) {
    this.music = music;
    this.update();
  }

  /**
   * Updates the model to depict the true lowest octave, highest octave, and last beat.
   * This is used when creating the model and whenever something is added or removed.
   */
  private void update() {
    this.finalBeat = this.findLastBeat();
    this.lowestNote = this.findLowestNote();
    this.highestNote = this.findHighestNote();
    this.range = this.getScaleRange();
  }

  @Override
  public ArrayList<INote> getMusic() {
    return this.music;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public void setTempo(int tempo) {
    if (tempo >= 0) {
      this.tempo = tempo;
    }
    else {
      throw new IllegalArgumentException("Tempo must be greater than or equal to 0");
    }
  }

  @Override
  public int findLastBeat() {
    if (this.music.size() == 0) { // throws exception if no music is in there yet
      throw new IllegalArgumentException("No music yet, add to use this feature");
    }
    int max = this.music.get(0).getStart() + this.music.get(0).getDuration();
    for (int i = 1; i < this.music.size(); i++) {
      if (this.music.get(i).getStart() + this.music.get(i).getDuration() > max) {
        max = this.music.get(i).getStart() + this.music.get(i).getDuration();
      }
    }
    return max;
  }

  @Override
  public INote findLowestNote() {
    if (this.music.size() == 0) { // throws exception if no music is in there yet
      throw new IllegalArgumentException("No music yet, add to use this feature");
    }
    INote min = this.music.get(0);
    for (int i = 1; i < this.music.size(); i++) {
      if (this.music.get(i).noteToValue() < min.noteToValue()) {
        min = this.music.get(i);
      }
    }
    return min;
  }

  @Override
  public INote findHighestNote() {
    if (this.music.size() == 0) { // throws exception if no music is in there yet
      throw new IllegalArgumentException("No music yet, add to use this feature");
    }
    INote max = this.music.get(0);
    for (int i = 1; i < this.music.size(); i++) {
      if (this.music.get(i).noteToValue() > max.noteToValue()) {
        max = this.music.get(i);
      }
    }
    return max;
  }

  @Override
  public void addNote(INote note) {
    this.music.add(note);
    this.update();
  }

  @Override
  public void removeNote(INote note) {
    for (int i = 0; i < this.music.size(); i++) {
      if (this.music.get(i).isSame(note)) {
        this.music.remove(i);
        return;
      }
    }
    this.update();
  }

  @Override
  public void addMusicDuring(ArrayList<INote> music) {
    this.music.addAll(music);
    this.update();
  }

  @Override
  public void addMusicAfter(ArrayList<INote> music2) {
    int last = this.findLastBeat() + 1;
    ArrayList<INote> temp = music2;
    // first to update the music being added
    for (int i = 0; i < temp.size(); i++) {
      temp.get(i).setStart(temp.get(i).getStart() + last);
    }
    // now to add the music
    this.music.addAll(temp);
    this.update();
  }


  @Override
  public boolean noteStartsHuh(int beat, int rangeNumber) {
    for (int i = 0; i < this.music.size(); i++) {
      if (this.music.get(i).samePOS(this.range.get(rangeNumber))
              && this.music.get(i).getStart() == beat) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean noteContinuesHuh(int beat, int rangeNumber) {
    for (int i = 0; i < this.music.size(); i++) {
      if (this.music.get(i).samePOS(this.range.get(rangeNumber))
              && beat > this.music.get(i).getStart()
              && beat <= (this.music.get(i).getStart() + this.music.get(i).getDuration())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ArrayList<INote> getScaleRange() {
    ArrayList<INote> result = new ArrayList<>();
    INote currentNote = this.lowestNote;
    while (!currentNote.samePOS(this.highestNote)) {
      result.add(currentNote);
      currentNote = currentNote.nextNote();
    }
    result.add(this.highestNote); // adds the highest note cause it gets cut off
    return result;
  }

  /* ------------------------------------------------------------------------------------------ */
  /* ------------------------------------------------------------------------------------------ */
  /* ------ Stuff that was used in the previous hw but moved to view (need to delete) --------- */

  /**
   * Creates the pitch heading based off of the lowest and highest notes in the model.
   * @return the string representation of the pitch headings.
   */
  private String renderPitchHeading() {
    String result = "  ";
    INote temp = this.lowestNote;
    while (!temp.samePOS(highestNote)) {
      result = result + temp.noteToString();
      temp = temp.nextNote();
    }
    result = result + this.highestNote.noteToString();//adds the highest value cause it gets cut off
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
    String maxBeat = Integer.toString(this.finalBeat); // converts max beat to string
    for (int i = 0; i < (maxBeat.length() - curBeat.length()); i++) {
      result = result + " ";
    }
    return result;
  }


  private String renderLine(int currentBeat) {
    String result = "";
    result = result + this.leadingBeatSpace(currentBeat) + currentBeat; // does the beat number
    for (int i = 0; i < this.range.size(); i++) {
      if (this.noteStartsHuh(currentBeat, i)) {
        result = result + this.addX();
      }
      else if (this.noteContinuesHuh(currentBeat, i)) {
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
  protected String renderState() {
    String result = "";
    if (this.music.size() < 1) {
      return "Nothing to display, no music in model yet";
    }
    result = result + this.renderPitchHeading() + "\n";
    for (int i = 0; i < this.finalBeat; i++) {
      if (i == this.finalBeat - 1) {
        result = result + this.renderLine(i);
      }
      else {
        result = result + this.renderLine(i) + "\n";
      }
    }
    return result;
  }
}