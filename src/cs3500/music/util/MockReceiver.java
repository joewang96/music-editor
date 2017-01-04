package cs3500.music.util;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * This class represents a mock implementation of the Receiver class.
 * This is used for testing the MIDI view.
 */
public class MockReceiver implements Receiver {

  StringBuffer log;

  MockReceiver(StringBuffer log) {
    this.log = log;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage m = new ShortMessage();
    if (message instanceof ShortMessage) {
      m = (ShortMessage) message;

      this.log.append("" + m.getCommand() + "-" + m.getData1() + "-" + m.getData2()
              + "-" + m.getChannel() + "\n");
    }
    else {
      this.log.append(message + "");
    }
  }

  @Override
  public void close() {
    this.log.append("Song Ended");

  }

  /**
   * A getter for the log of this receiver.
   * @return the StringBuffer log
   */
  public StringBuffer getLog() {
    return this.log;
  }
}
