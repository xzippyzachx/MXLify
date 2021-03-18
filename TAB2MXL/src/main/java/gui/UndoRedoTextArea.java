package gui;

import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

@SuppressWarnings("serial")
public class UndoRedoTextArea extends ColumnTextArea implements UndoableEditListener, FocusListener, KeyListener {

	private UndoManager manager; // Manager to track changes

	public UndoRedoTextArea() { // Empty text area
		this(new String());
	}

	public UndoRedoTextArea(String text) { // Text area with starting text
		super();
		getDocument().addUndoableEditListener(this);
		this.addKeyListener(this);
		this.addFocusListener(this);
	}

	private void createUndoMananger() { // Create manager - new manager, and set limit to 25
		manager = new UndoManager();
		manager.setLimit(25);
	}

	private void removeUndoMananger() { // Remove manager - end
		manager.end();
	}

	public void focusGained(FocusEvent fe) { // Create a manager when focus is on text area
		createUndoMananger();
	}

	public void focusLost(FocusEvent fe) { // Remove manager when focus is not on text area
		removeUndoMananger();
	}

	public void undoableEditHappened(UndoableEditEvent e) {
		manager.addEdit(e.getEdit()); // Add edits to history in manager
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		
		if ((e.getKeyCode() == KeyEvent.VK_Z) && (e.isControlDown())) { // Control-Z

			try {
				manager.undo();                                         // Try to undo text
			} catch (CannotUndoException cue) {                         // If cannot, audible beep
				Toolkit.getDefaultToolkit().beep();
			}
		}

		if ((e.getKeyCode() == KeyEvent.VK_Y) && (e.isControlDown())) { // Control-Y

			try {
				manager.redo();                                         // Try to redo text
			} catch (CannotRedoException cue) {                         // If cannot, audible beep
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	
}
