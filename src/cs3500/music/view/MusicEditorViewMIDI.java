package cs3500.music.view;

import java.awt.event.ActionListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import cs3500.music.model.INote;
import cs3500.music.util.IData;
import cs3500.music.util.MockMidi;

/**
 * This represents the MIDI view implementation for the Music Editor project.
 * The information is "viewed" auditory, playing the notes housed in the model.
 */
public class MusicEditorViewMIDI implements IMusicEditorView {

  private IData adapter;
  private MidiDevice synth; // set as MidiDevice to allow for testing to implement a mock MIDI
  private Receiver receiver;

  /**
   * The default constructor for the MIDI View implementation.
   * This is used for actually producing sound output, the below constructor is used for testing.
   */
  public MusicEditorViewMIDI() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * This is the constructor for the MusicEditorViewMIDI that is used just for testing.
   * It allows the user to use a MockMidi as the synthesizer for testing purposes.
   * @param s is just a parameter to allow for a different constructor for the MIDI view (for test).
   */
  public MusicEditorViewMIDI(String s) {
    try {
      this.synth = new MockMidi();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  @Override
  public IData getAdapter() {
    return this.adapter;
  }

  @Override
  public void setAdapter(IData adapter) {
    this.adapter = adapter;
  }

  @Override
  public void display() {
    try {
      this.playNote();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  /**
   * This passes in all of the MidiMessages to the receiver. We actually play them with display().
   * @throws InvalidMidiDataException this is the exception if the midi data is invalid.
   */
  public void playNote() throws InvalidMidiDataException {

    for (int i = 0; i < this.adapter.getMusic().size(); i++) {
      INote temp = this.adapter.getMusic().get(i);
      int inst = temp.getInstrument() % 16;

      MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, inst,
              temp.noteToValue() + 11, temp.getVolume());
      MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, inst,
              temp.noteToValue() + 11, temp.getVolume());

      this.receiver.send(start, temp.getStart() * this.adapter.getTempo());
      this.receiver.send(stop, (temp.getStart() + temp.getDuration()) * this.adapter.getTempo() );
    }

    try {
      Thread.sleep(this.adapter.findLastBeat() * this.adapter.getTempo() / 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  @Override
  public void refresh() {
    /* nothing yet, this will be used when we add user controls and inputs */
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
   * This returns the synthesizer for the view - this is used just for testing.
   * @return the synthesizer for the view.
   */
  MidiDevice getSynth() {
    return this.synth;
  }
}
