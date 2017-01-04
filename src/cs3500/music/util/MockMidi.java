package cs3500.music.util;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

/**
 * This represents a mock implementation of a MidiDevice.
 * This class is used for testing of the MIDI view.
 */
public class MockMidi implements MidiDevice {

  StringBuffer log;
  MockReceiver receiver;

  public MockMidi() {
    this.log = new StringBuffer();
    this.receiver = new MockReceiver(this.log);
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {
    // does nothing
  }

  @Override
  public void close() {
    // does nothing
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return this.receiver;
  }

  @Override
  public List<Receiver> getReceivers() {
    ArrayList<Receiver> result = new ArrayList<>();
    result.add(this.receiver);
    return result;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }

}
