package tab2mxl;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

public class DragDropListener implements DropTargetListener{
	@Override
	public void drop(DropTargetDropEvent event) {

		event.acceptDrop(DnDConstants.ACTION_COPY); // Accept copy drops

		Transferable transferable = event.getTransferable(); // Get the transfer which can provide the dropped item data

		DataFlavor[] flavors = transferable.getTransferDataFlavors(); // Get the data formats of the dropped item

		for (DataFlavor flavor : flavors) { // Loop through the flavors

			try {

				// If the drop items are files
				if (flavor.isFlavorJavaFileListType()) {

					// Get all of the dropped files
					List files = (List) transferable.getTransferData(flavor);

					// Loop them through
					for (Object file : files) {
						FileDropPanel.dropFilePath = ((File) (file)).getPath();

						// Print out the file path
						System.out.println("File path is '" + ((File) (file)).getPath() + "'.");

						
						// ****** TO DO ******  Read data in file and send to Parser
						
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
