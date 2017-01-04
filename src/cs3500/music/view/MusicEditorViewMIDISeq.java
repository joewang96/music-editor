package cs3500.music.view;

import java.awt.event.ActionListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cs3500.music.model.INote;
import cs3500.music.util.IData;

/**
 * This represents the MIDI view implementation for the Music Editor project.
 * The information is "viewed" auditory, playing the notes housed in the model.
 */
public class MusicEditorViewMIDISeq implements IMusicEditorView {

  private IData adapter;
  private Sequencer sequencer; // sequencer to allow for the composite view functionality

  /**
   * The default constructor for the MIDI View implementation.
   * This is used for actually producing sound output, the below constructor is used for testing.
   */
  public MusicEditorViewMIDISeq() {
    try {
      this.sequencer = MidiSystem.getSequencer();
      this.sequencer.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * This is the constructor for the MusicEditorViewMIDI that is used just for testing.
   * It allows the user to use a MockMidi as the synthesizer for testing purposes.
   * @param s is just a parameter to allow for a different constructor for the MIDI view (for test).
   */
  public MusicEditorViewMIDISeq(String s) {
    try {
      this.sequencer = MidiSystem.getSequencer();
      this.sequencer.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
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
    try {
      this.loadSequencer();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads the midi events into the sequencer for playback.
   * @throws InvalidMidiDataException is the exception it throws if the midi data isn't valid.
   */
  private void loadSequencer() throws InvalidMidiDataException {
    Sequence mySeq;
    mySeq = new Sequence(Sequence.PPQ, this.adapter.getTempo());

    Track t = mySeq.createTrack();

    for (int i = 0; i < this.adapter.getMusic().size(); i++) {
      INote temp = this.adapter.getMusic().get(i);
      int inst = temp.getInstrument() % 16;

      MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, inst,
              temp.noteToValue() + 11, temp.getVolume());
      MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, inst,
              temp.noteToValue() + 11, temp.getVolume());

      t.add(new MidiEvent(start, temp.getStart() * this.adapter.getTempo()));
      t.add(new MidiEvent(stop, (temp.getStart() + temp.getDuration()) * this.adapter.getTempo()));
    }
    sequencer.setSequence(mySeq);
  }

  /**
   * This passes in all of the MidiMessages to the receiver. We actually play them with display().
   * @throws InvalidMidiDataException this is the exception if the midi data is invalid.
   */
  void play() throws InvalidMidiDataException {
    sequencer.start();
    sequencer.setTempoInMPQ(this.adapter.getTempo() );
  }

  /**
   * This tells the Sequencer to stop playing the notes.
   * @throws InvalidMidiDataException this is the exception if the midi data is invalid.
   */
  void pause() throws InvalidMidiDataException {
    sequencer.stop();
  }

  /**
   * Checks if the sequencer is currently playing. This helps to not have to refresh if not playing.
   * @return true if it is playing, false if it is not.
   */
  boolean isPlaying() {
    return this.sequencer.isRunning();
  }

  @Override
  public void refresh() {
    try {
      this.loadSequencer();
      this.sequencer.setTempoInMPQ(this.adapter.getTempo());
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setExecuteListener(ActionListener actionEvent) {
    // does nothing
  }

  @Override
  public String sendCommand() {
    /* nothing yet, this will be used when we add user controls and inputs */
    return "";
  }

  /**
   * Gives the current beat of the sequencer, in microseconds.
   * @return the current beat.
   */
  int getCurrentBeat() {
    return (int) this.sequencer.getTickPosition() * 5;
  }

  /**
   * Sets the current beat of the sequence depending on the given position.
   * @param pos is the position to move to, expressed in microseconds.
   */
  void setCurrentBeat(long pos) {
    this.sequencer.setTickPosition(pos);
  }

  /**
   * Returns the tempo of the piece.
   * @return the tempo in microseconds per quarter notes.
   */
  long getTempo() {
    return this.adapter.getTempo();
  }

  /**
   * This returns the synthesizer for the view - this is used just for testing.
   * @return the synthesizer for the view.
   */
  /*MidiDevice getSynth() {
    return this.synth;
  }*/
}
