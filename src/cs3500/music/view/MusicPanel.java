package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import cs3500.music.model.INote;

import javax.swing.JPanel;

/**
 * This panel represents the notes that we will display on our GUI View implementation.
 */
public class MusicPanel extends JPanel {
  private ArrayList<INote> notes; // the notes we will be displaying
  private ArrayList<INote> range; // the range for the scale range along side
  private int highestPitch;
  private int lastBeat; // last beat in the music
  protected static int boxPixel = 20; // use this to draw scale
  private int currentBeat;

  /**
   * This is our implementation of JPanel for the MusicGuiView.
   * This will be able to take in all of the data from the view and draw it accordingly.
   */
  public MusicPanel() {
    super();
    notes = new ArrayList<>();
    this.setBackground(new Color(212, 212, 212));
  }

  /**
   * Sets the notes for storage.
   * @param notes the notes to take in.
   */
  public void setNotes(ArrayList<INote> notes) {
    this.notes = notes;
  }

  /**
   * Sets the range for storage.
   * @param range the range to use.
   */
  public void setRange(ArrayList<INote> range) {
    this.range = range;
  }

  /**
   * Sets the highest pitch value to calculate side range bar.
   * @param i is the number of the pitch to take in.
   */
  public void setHighestPitch(int i) {
    this.highestPitch = i;
  }

  /**
   * Sets the final beat for storage.
   * @param i is the final beat number.
   */
  public void setLastBeat(int i) {
    this.lastBeat = i;
  }

  /**
   * Sets the current beat.
   * @param i is the current beat to use.
   */
  public void setCurrentBeat(int i) {
    this.currentBeat = i;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);  // this is calling the super constructor, always necessary

    g.setFont(new Font("Helvetica", Font.PLAIN, 12)); // sets the font for the entire panel

    // this draws the beats along the top of the panel
    for (int i = 0; i < lastBeat; i += 4) {
      g.drawString(i + "", 50 + (i / 4 * (boxPixel * 4)), 20);
    }

    // this draws the pitch range along the left side of the panel
    for (int i = this.range.size() - 1; i >= 0; i--) {
      INote temp = this.range.get(i);
      int y = ((this.highestPitch - temp.noteToValue()) * boxPixel) + 45;
      g.drawString(temp.noteToString() , 10,  y);
    }

    // now we draw the notes!!!
    for (int i = 0; i < notes.size(); i++) {
      // drawing the entire length of the note (will draw start on top)
      g.setColor(new Color(122, 255, 251));
      INote temp = this.notes.get(i);

      int width = boxPixel * temp.getDuration();
      int y = ((this.highestPitch - temp.noteToValue() - 1) * boxPixel) + 50;

      g.fillRect(temp.getStart() * boxPixel + 50, y, width, boxPixel);

      // now to draw the start note on top of it
      g.setColor(new Color(255, 153, 207));
      g.fillRect(temp.getStart() * boxPixel + 50, y, boxPixel, boxPixel);
    }

    // draws the grids
    for (int i = 0; i < lastBeat / 4; i++) {
      for (int j = 0; j < this.range.size(); j++) {
        g.setColor(Color.BLACK);
        g.drawRect(50 + (i * boxPixel * 4), 30 + (j * boxPixel), (boxPixel * 4), boxPixel);
      }
    }

    //Draws the line showing what is being played
    g.setColor(Color.red);
    int offset = 50;
    g.drawLine(currentBeat + offset, 30, currentBeat + offset, 30 + this.range.size() * boxPixel);
  }


}
