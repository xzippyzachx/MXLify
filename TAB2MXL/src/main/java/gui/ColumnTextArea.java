package gui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.InputMap;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotUndoException;

public class ColumnTextArea extends JTextArea{

    private static final long serialVersionUID = 1L;
    
    Object[] actionKeys = new Object[2];

    public ColumnTextArea(){
        super();
        this.setCaret(new ColumnTextArea.ColumnSelectionCaret());

        //Disable ALT key presses when the textarea is focused
        this.getInputMap(javax.swing.JComponent.WHEN_FOCUSED).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ALT, 0, true), "ALTKEY");
        this.getActionMap().put("ALTKEY", new javax.swing.AbstractAction() {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
            }
        });
    }

    @Override
    public java.lang.String getSelectedText(){
        javax.swing.text.Highlighter.Highlight[] selections = this.getHighlighter().getHighlights();
        String text = "";
        int cnt = selections.length;
        if(cnt == 0){
            text = super.getSelectedText();
        }else{
            for (int i=0; i<cnt; i++) {
                int start = selections[i].getStartOffset();
                int end = selections[i].getEndOffset();
                java.lang.String selectedText = "";
                try {
                    selectedText = this.getDocument().getText(start,end-start);
                } catch (javax.swing.text.BadLocationException e) {
                    e.printStackTrace();
                }
                if(i == 0){
                    text = selectedText;
                }else{
                    text += '\n' + selectedText;
                }
            }
        }
        return(text);
    }
    
    @Override
    public void copy(){
        java.awt.datatransfer.StringSelection ss = new java.awt.datatransfer.StringSelection(this.getSelectedText());
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,ss);        
    }
    
    
    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) { // Backspace/delete
			javax.swing.text.Highlighter.Highlight[] selections = this.getHighlighter().getHighlights();
			int cnt = selections.length;
	        if(cnt == 0){
	            
	        }else{
	            for (int i=0; i<cnt; i++) {
	                int start = selections[i].getStartOffset();
	                int end = selections[i].getEndOffset();
	                replaceRange("", start, end);
	            }
	        } 
		}
    }
    
    public void keyReleased(KeyEvent e) {
    	if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) { // Backspace/delete
    		enableKeys(getInputMap());
		}    	
    }
    
    private void disableKeys(InputMap inputMap) {
        String[] keys = {"BACK_SPACE", "DELETE"};
        int i = 0;
        for (String key : keys) {
        	actionKeys[i] = inputMap.get(KeyStroke.getKeyStroke(key));
            inputMap.put(KeyStroke.getKeyStroke(key), "none");
            i++;
        }
    }
    
    private void enableKeys(InputMap inputMap) {
    	String[] keys = {"BACK_SPACE", "DELETE"};
    	int i = 0;
        for (String key : keys) {        	
            inputMap.put(KeyStroke.getKeyStroke(key), actionKeys[i]);
            i++;
        }        
    }
            
    class ColumnSelectionCaret extends javax.swing.text.DefaultCaret {
        private static final long serialVersionUID = 1L;
        java.awt.Point lastPoint = new java.awt.Point(0,0);
        boolean altDown = false;
        boolean moving = false;

        @Override
        public void mouseMoved(java.awt.event.MouseEvent e) {
            super.mouseMoved(e);
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            super.mouseClicked(e);
            if(e.getClickCount()==1){
                this.getComponent().getHighlighter().removeAllHighlights();
                enableKeys(getInputMap());
            }
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e){
            super.mousePressed(e);
            this.lastPoint = new java.awt.Point(e.getX(),e.getY());
            this.altDown = e.isAltDown();
            if(this.altDown)
            	disableKeys(getInputMap());
        }

        @Override
        protected void moveCaret(java.awt.event.MouseEvent e) {
            if(!this.moving){
                this.getComponent().getHighlighter().removeAllHighlights();               
                this.moving = true;
            }
            if(this.altDown){
                java.awt.Point pt = new java.awt.Point(e.getX(), e.getY());
                javax.swing.text.Position.Bias[] biasRet = new javax.swing.text.Position.Bias[1];
                int pos = getComponent().getUI().viewToModel2D(getComponent(), pt, biasRet);
                if(biasRet[0] == null)
                    biasRet[0] = javax.swing.text.Position.Bias.Forward;
                if (pos >= 0) {
                    this.setDot(pos);
                    java.awt.Point start = new java.awt.Point(Math.min(this.lastPoint.x,pt.x), Math.min(this.lastPoint.y,pt.y));
                    java.awt.Point end = new java.awt.Point(Math.max(this.lastPoint.x,pt.x), Math.max(this.lastPoint.y,pt.y));
                    this.customHighlight(start,end);
                }
            }else{
                super.moveCaret(e);
            }
        }
        @Override
        public void mouseReleased(java.awt.event.MouseEvent e){
            this.moving = false;
            super.mouseReleased(e);
        }

        protected void customHighlight(java.awt.Point start, java.awt.Point end) {
            javax.swing.text.JTextComponent comp = this.getComponent();
            javax.swing.text.Position.Bias[] biasRet = new javax.swing.text.Position.Bias[1];

            comp.getHighlighter().removeAllHighlights();
            int y = start.y;
            int firstX = start.x;
            int lastX = end.x;

            int pos1 = comp.getUI().viewToModel2D(comp, new java.awt.Point(firstX,y), biasRet);
            int pos2 = comp.getUI().viewToModel2D(comp, new java.awt.Point(lastX,y), biasRet);
            try {
                comp.getHighlighter().addHighlight(pos1, pos2, this.getSelectionPainter());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            y++;
            while (y<end.y) {
                int pos1new = comp.getUI().viewToModel2D(comp, new java.awt.Point(firstX,y), biasRet);
                int pos2new = comp.getUI().viewToModel2D(comp, new java.awt.Point(lastX,y), biasRet);
                if (pos1!=pos1new)  {
                    pos1=pos1new;
                    pos2=pos2new;
                    try {
                        comp.getHighlighter().addHighlight(pos1, pos2, this.getSelectionPainter());
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                y++;
            }
        }
    }

}
