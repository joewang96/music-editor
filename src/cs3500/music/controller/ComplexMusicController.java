package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.INote;
import cs3500.music.model.Note;
import cs3500.music.util.Adapter;
import cs3500.music.util.Builder;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.IData;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicEditorViewGUI;

/**
 * This is an example of a basic controller to be used for the MusicEditor.
 */
public class ComplexMusicController implements IMusicEditorController, ActionListener {
  final IMusicEditorModel model;
  IMusicEditorViewGUI view;

  /**
   * This is the constructor for the Complex Controller. This will run the composite view.
   * @param text the text file to get music from.
   * @param view is the view to use (in this case the composite view).
   */
  public ComplexMusicController(File text, IMusicEditorViewGUI view) {
    MusicReader temp = new MusicReader();
    CompositionBuilder<IMusicEditorModel> builder = new Builder();
    Readable fr = null;
    try {
      fr = new FileReader(text);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    this.model = temp.parseFile(fr, builder);
    this.view = view;

    // now to set the adapter so the view has the data it needs
    IData adapter = new Adapter(model);
    this.view.setAdapter(adapter);
  }

  //String note, int start, int duration, int instrument, int volume
  @Override
  public String processCommand(String command) {
    StringBuilder output = new StringBuilder();
    Scanner s = new Scanner(command);

    while (s.hasNext()) {
      String in = s.next();
      String note = "";
      int start = 0;
      int duration = 0;
      int instrument = 0;
      int volume = 0;

      try {
        note = s.next();
        start = s.nextInt();
        duration = s.nextInt();
        instrument = s.nextInt();
        volume = s.nextInt();
      } catch (Exception e) {
        output.append("This note is not properly formed");
        return output.toString();
      }


      try {
        INote temp = this.setNote(note, start, duration, instrument, volume);
        switch (in) {
          case "add":

            this.model.addNote(temp);
            output.append(note + " has been added ");


            break;
          case "remove":
            int originalSize = this.model.getMusic().size();

            this.model.removeNote(temp);

            if (this.model.getMusic().size() != originalSize) {
              output.append(note + " has been removed ");
            }
            else {
              throw new IllegalArgumentException("That note is not in this piece");
            }


            break;
          default:
            throw new IllegalArgumentException("The command you have entered does not exist");
        }
      } catch (IllegalArgumentException e) {
        output.append(e.getMessage());
      }
    }
    return output.toString();
  }

  /**
   * Creates a note based on the following information.
   * @param note string representation of the pitch and octave
   * @param start start of the note
   * @param duration duration of the note
   * @param instrument instrument of the note
   * @param volume volume of the note
   * @return an INote that corresponds to the inputted info
   */
  private INote setNote(String note, int start, int duration, int instrument, int volume) {
    INote temp = new Note(INote.NotePitch.A, 1, 1, 1, 1, 1);

    int oct = 0;
    String pit = "x";
    INote.NotePitch pitch = INote.NotePitch.A;

    if (isValidNote(note)) {
      if (note.charAt(1) == '#') {
        oct = Integer.parseInt(note.substring(2));
        pit = note.substring(0, 2);
      } else {
        oct = Integer.parseInt(note.substring(1));
        pit = note.substring(0, 1);
      }
    }

    switch (pit) {
      case "A":
        pitch = INote.NotePitch.A;
        break;
      case "A#":
        pitch = INote.NotePitch.ASharp;
        break;
      case "B":
        pitch = INote.NotePitch.B;
        break;
      case "C":
        pitch = INote.NotePitch.C;
        break;
      case "C#":
        pitch = INote.NotePitch.CSharp;
        break;
      case "D":
        pitch = INote.NotePitch.D;
        break;
      case "D#":
        pitch = INote.NotePitch.DSharp;
        break;
      case "E":
        pitch = INote.NotePitch.E;
        break;
      case "F":
        pitch = INote.NotePitch.F;
        break;
      case "F#":
        pitch = INote.NotePitch.FSharp;
        break;
      case "G":
        pitch = INote.NotePitch.G;
        break;
      case "G#":
        pitch = INote.NotePitch.GSharp;
        break;
      default:
        throw new IllegalArgumentException("This is not a real note value");
    }

    temp.setPitch(pitch);
    temp.setOctave(oct);
    temp.setStart(start);
    temp.setDuration(duration);
    temp.setInstrument(instrument);
    temp.setVolume(volume);

    return temp;
  }

  /**
   * Checks if a string is a valid representation of a note.
   * @param pitch the string of the note.
   * @return true if that string can corespond to a valid pitch and octave.
   */
  private boolean isValidNote(String pitch) {
    if (pitch.equals("")) {
      return false;
    }
    switch (pitch.substring(0, 1)) {
      case "A":
        break;
      case "B":
        break;
      case "C":
        break;
      case "D":
        break;
      case "E":
        break;
      case "F":
        break;
      case "G":
        break;
      default:
        return false;
    }

    if (pitch.charAt(1) == '#') {
      String str = pitch.substring(2);
      return str.matches("^-?\\d+$");
    }
    else {
      String str = pitch.substring(1);
      return str.matches("^-?\\d+$");
    }
  }

  @Override
  public void run() {
    this.view.setExecuteListener(this);
    this.view.refresh();
    this.view.display();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = view.sendCommand();
    String status;

    try {
      status = processCommand(command);
      if (this.view instanceof IMusicEditorViewGUI) {
        IMusicEditorViewGUI temp = this.view;
        temp.showError(status);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    //refresh the view using the adapter
    IData newData = new Adapter(this.model);
    this.view.setAdapter(newData);
    this.view.refresh();
    this.view.display();
    this.view.resetFocus();
  }
}
