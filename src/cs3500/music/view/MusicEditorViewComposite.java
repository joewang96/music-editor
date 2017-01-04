package cs3500.music.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import cs3500.music.util.IData;

/**
 * This represents the composite view for a music editor which takes both gui and midi views.
 * This will allow for user input to edit the notes on screen and allows for real-time playback.
 */
public class MusicEditorViewComposite extends JFrame implements IMusicEditorViewGUI, KeyListener {

  private MusicEditorViewGUI gui;
  private MusicEditorViewMIDISeq midi;
  private JPanel displayPanel;
  private JPanel userPanel;
  private JButton executeButton;
  private JTextField inputField;
  private JLabel action;
  private JScrollPane scrollFrame;
  private int screenRangeLow;
  private int screenRangeHigh;

  /**
   * This is the constructor for the composite view for the music editor.
   * @param gui is the gui view to take in.
   * @param midi is the midi view to take in.
   */
  public MusicEditorViewComposite(MusicEditorViewGUI gui, MusicEditorViewMIDISeq midi) {
    super();
    this.gui = gui;
    this.midi = midi;
    this.displayPanel = this.gui.getPanel();
    this.setSize(1200, 400);
    this.setTitle("Music Editor");
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    // modifying the layout of the panel
    this.setLayout(new BorderLayout());

    // this is a placeholder for the initial dimension, it is recalculated when adapter is added
    this.displayPanel.setPreferredSize(new Dimension(1200, 400));
    //this.add(displayPanel, BorderLayout.CENTER);

    // scrolling
    scrollFrame = new JScrollPane(displayPanel);
    displayPanel.setAutoscrolls(true);
    scrollFrame.setPreferredSize(new Dimension(800, 375));
    // changes scroll speeds to make them easier for the user to make use of
    scrollFrame.getVerticalScrollBar().setUnitIncrement(16);
    scrollFrame.getHorizontalScrollBar().setUnitIncrement(16);

    this.add(scrollFrame, BorderLayout.CENTER);

    // user stuff (buttons, text field)
    this.userPanel = new JPanel();
    this.executeButton = new JButton("Execute");

    this.inputField = new JTextField(15);

    action = new JLabel("Make an Edit");

    this.userPanel.add(action);
    this.userPanel.add(inputField);
    this.userPanel.add(executeButton);

    this.add(this.userPanel, BorderLayout.SOUTH);

    this.addKeyListener(this);

    this.pack();

    this.screenRangeLow = 0;
    this.screenRangeHigh = 0;

  }

  @Override
  public IData getAdapter() {
    return this.gui.getAdapter();
  }

  @Override
  public void setAdapter(IData adapter) {
    this.gui.setAdapter(adapter);
    this.midi.setAdapter(adapter);
  }

  @Override
  public void display() {
    // this is for the gui to set up stuff, can't use display cause that will make it visible
    this.gui.refresh();
    this.setFocusable(true);
    this.setVisible(true);
    this.midi.display();
    this.updateScreenRange();
    timer.start();
  }

  /**
   * Updates the screen range based on viewport.
   */
  private void updateScreenRange() {
    this.screenRangeLow = this.scrollFrame.getHorizontalScrollBar().getValue();
    this.screenRangeHigh = this.scrollFrame.getHorizontalScrollBar().getWidth()
            + this.scrollFrame.getHorizontalScrollBar().getValue();
  }

  ActionListener action2 = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      int micro = midi.getCurrentBeat();
      long tempo = midi.getTempo();
      gui.setCurrentBeat(micro, tempo);
      gui.refresh();

      screenRangeLow = scrollFrame.getHorizontalScrollBar().getValue();
      screenRangeHigh = scrollFrame.getHorizontalScrollBar().getWidth()
              + scrollFrame.getHorizontalScrollBar().getValue();

      if ( ((midi.getCurrentBeat() * 4 / tempo)  > screenRangeHigh) && midi.isPlaying()) {
        scrollFrame.getHorizontalScrollBar().setValue(screenRangeHigh);
        int temp = screenRangeHigh;
        screenRangeLow = temp;
        screenRangeHigh = temp + scrollFrame.getHorizontalScrollBar().getWidth();
      }

      if ( ((midi.getCurrentBeat() * 4 / tempo)  < screenRangeLow) && midi.isPlaying()) {
        scrollFrame.getHorizontalScrollBar().setValue(screenRangeLow
                - scrollFrame.getHorizontalScrollBar().getValue());
        int temp = screenRangeLow;
        screenRangeLow = temp - scrollFrame.getHorizontalScrollBar().getWidth();
        screenRangeHigh = temp;
      }
    }
  };

  private int interval = 50;
  private Timer timer = new Timer(interval, action2);

  @Override
  public void refresh() {
    this.gui.refresh();
    this.midi.refresh();
  }

  @Override
  public String sendCommand() {
    String command = this.inputField.getText();
    this.inputField.setText("");
    return command;
  }

  @Override
  public void setExecuteListener(ActionListener actionEvent) {
    executeButton.addActionListener(actionEvent);
  }

  @Override
  public void showError(String error) {
    action.setText(error);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == KeyEvent.VK_SPACE) {
      if (!midi.isPlaying()) {
        try {
          this.midi.play();
        } catch (InvalidMidiDataException e1) {
          e1.printStackTrace();
        }
      }
      else {
        try {
          this.midi.pause();
        } catch (InvalidMidiDataException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        scrollFrame.getHorizontalScrollBar().setValue(
                scrollFrame.getHorizontalScrollBar().getValue() - 30);
        break;
      case KeyEvent.VK_RIGHT:
        scrollFrame.getHorizontalScrollBar().setValue(
                scrollFrame.getHorizontalScrollBar().getValue() + 30);
        break;
      case KeyEvent.VK_UP:
        scrollFrame.getVerticalScrollBar().setValue(
                scrollFrame.getVerticalScrollBar().getValue() - 30);
        break;
      case KeyEvent.VK_DOWN:
        scrollFrame.getVerticalScrollBar().setValue(
                scrollFrame.getVerticalScrollBar().getValue() + 30);
        break;
      default:
        // nothing happens
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    JScrollBar h;
    if (e.getKeyCode() == KeyEvent.VK_E) {
      h = scrollFrame.getHorizontalScrollBar();
      h.setValue(h.getMaximum());
      this.midi.setCurrentBeat(this.getAdapter().findLastBeat() * this.getAdapter().getTempo());
    }
    else if (e.getKeyCode() == KeyEvent.VK_S) {
      h = scrollFrame.getHorizontalScrollBar();
      h.setValue(h.getMinimum());
      this.midi.setCurrentBeat(0);
      this.midi.refresh();
    }
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

}
