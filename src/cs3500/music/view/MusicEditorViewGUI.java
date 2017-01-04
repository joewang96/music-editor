package cs3500.music.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;


import cs3500.music.util.IData;

/**
 * This represents the GUI view implementation for the Music Editor project.
 * The information is displayed through Frames and Panels made using Java Swing.
 */
public class MusicEditorViewGUI extends JFrame implements IMusicEditorViewGUI {

  private IData adapter;
  private final MusicPanel displayPanel;

  /**
   * The constructor for the GUI view implementation of the View interface.
   */
  public MusicEditorViewGUI() {
    super();
    this.setSize(800, 400);
    this.setTitle("Music Editor");
    this.displayPanel = new MusicPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);

    // modifying the layout of the panel
    this.setLayout(new BorderLayout());

    // this is a placeholder for the initial dimension, it is recalculated when adapter is added
    this.displayPanel.setPreferredSize(new Dimension(800, 375));
    //this.add(displayPanel, BorderLayout.CENTER);

    // scrolling
    JScrollPane scrollFrame = new JScrollPane(displayPanel);
    displayPanel.setAutoscrolls(true);
    scrollFrame.setPreferredSize(new Dimension(800, 375));
    // changes scroll speeds to make them easier for the user to make use of
    scrollFrame.getVerticalScrollBar().setUnitIncrement(16);
    scrollFrame.getHorizontalScrollBar().setUnitIncrement(16);
    this.add(scrollFrame);

    this.pack();
  }

  @Override
  public void setAdapter(IData adapter) {
    this.adapter = adapter;
    this.displayPanel.setPreferredSize(new Dimension((this.adapter.findLastBeat() + 1)
            * displayPanel.boxPixel + 60, this.adapter.getScaleRange().size()
            * displayPanel.boxPixel + 45));
  }

  @Override
  public IData getAdapter() {
    return this.adapter;
  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.setNotes();
    this.setRange();
    this.setHighest();
    this.setLast();
    this.displayPanel.repaint();
  }

  @Override
  public String sendCommand() {
    /* nothing yet, this will be used when we add user controls and inputs */
    return "";
  }

  @Override
  public void setExecuteListener(ActionListener actionEvent) {
    // does nothing for this instance of the view
  }

  @Override
  public void showError(String error) {
    // does nothing for this one
  }

  /**
   * Allows the GUI view to pass in the list of notes to the JPanel implementation (MusicPanel).
   */
  protected void setNotes() {
    this.displayPanel.setNotes(this.adapter.getMusic());
  }

  /**
   * Allows the GUI view to pass in the the scale range to the JPanel implementation (MusicPanel).
   */
  protected void setRange() {
    this.displayPanel.setRange(this.adapter.getScaleRange());
  }

  /**
   * Allows the GUI view to pass in the highest note to the JPanel implementation (MusicPanel).
   */
  protected void setHighest() {
    this.displayPanel.setHighestPitch(this.adapter.findHighestNote().noteToValue());
  }

  /**
   * Allows the GUI view to pass in the final beat of the model to the JPanel implementation.
   */
  protected void setLast() {
    this.displayPanel.setLastBeat(this.adapter.findLastBeat());
  }

  public JPanel getPanel() {
    return this.displayPanel;
  }

  /**
   * Makes the display set its current beat according to where the song is at.
   * The current beat being sent is modified to express the pixel location to allow for easier use.
   * @param i is the current beat in microseconds.
   * @param tempo is the temp of the song in MSPQ.
   */
  void setCurrentBeat(int i, long tempo) {

    int currentX;

    if (tempo != 0) {
      currentX = (int)( (i / tempo) * 4);
    }
    else {
      currentX = 0;
    }

    this.displayPanel.setCurrentBeat(currentX);
  }

  @Override
  public void resetFocus() {
    // does nothing right now
  }

}
