package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gui_panels.FileDropPanel;
import tab2mxl.Main;

public class DragDropListener implements DropTargetListener{
	@Override
	public void drop(DropTargetDropEvent event) {

		if(Main.isInPopUp)
			return;
		
		event.acceptDrop(DnDConstants.ACTION_COPY); // Accept copy drops

		Transferable transferable = event.getTransferable(); // Get the transfer which can provide the dropped item data

		DataFlavor[] flavors = transferable.getTransferDataFlavors(); // Get the data formats of the dropped item

		for (DataFlavor flavor : flavors) { // Loop through the flavors

			try {

				// If the drop items are files
				if (flavor.isFlavorJavaFileListType()) {

					// Get all of the dropped files
					List files = (List) transferable.getTransferData(flavor);

					// Loop through
					for (Object file : files) {										
						
						try {
							BufferedReader reader = new BufferedReader(new FileReader ((File) file));
						    String         line = null;
						    StringBuilder  stringBuilder = new StringBuilder();
						    String         ls = System.getProperty("line.separator");
					    
					        while((line = reader.readLine()) != null) {
					            stringBuilder.append(line);
					            stringBuilder.append(ls);
					        }
					        
					        String fileName = ((File) (file)).getName();
							String extension = "";

							int i = fileName.lastIndexOf('.');
							int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

							if (i > p)
							    extension = fileName.substring(i+1);
							
							System.out.println(extension);
							
							if(extension.equals(".txt") || extension.equals("txt"))
							{
								FileDropPanel.dropFilePath = ((File) (file)).getPath();
								Main.FileUploaded(stringBuilder.toString());
							}								
					        
					        reader.close();
					        
					    } catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		event.dropComplete(true); // Inform that the drop is complete
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
