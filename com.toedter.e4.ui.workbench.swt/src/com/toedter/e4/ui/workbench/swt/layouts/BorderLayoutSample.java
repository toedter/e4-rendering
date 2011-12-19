package com.toedter.e4.ui.workbench.swt.layouts;

/******************************************************************************
 * Copyright (c) 1998, 2004 Jackwind Li Guojie
 * All right reserved. 
 * 
 * Created on Jan 30, 2004 11:52:21 PM by JACK
 * $Id$
 * 
 * visit: http://www.asprise.com/swt
 *****************************************************************************/

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/******************************************************************************
 * Copyright (c) 1998, 2004 Jackwind Li Guojie All right reserved.
 * 
 * Created on Jan 31, 2004 1:05:58 AM by JACK $Id$
 * 
 * visit: http://www.asprise.com/swt
 *****************************************************************************/
public class BorderLayoutSample {
	Display display = new Display();
	Shell shell = new Shell(display);

	public BorderLayoutSample() {
		shell.setLayout(new BorderLayout());

		Button buttonWest = new Button(shell, SWT.PUSH);
		buttonWest.setText("West");
		buttonWest.setLayoutData(new BorderLayout.BorderData(BorderLayout.WEST));

		Button buttonEast = new Button(shell, SWT.PUSH);
		buttonEast.setText("East");
		buttonEast.setLayoutData(new BorderLayout.BorderData(BorderLayout.EAST));

		Button buttonNorth = new Button(shell, SWT.PUSH);
		buttonNorth.setText("North");
		buttonNorth.setLayoutData(new BorderLayout.BorderData(BorderLayout.NORTH));

		Button buttonSouth = new Button(shell, SWT.PUSH);
		buttonSouth.setText("South");
		buttonSouth.setLayoutData(new BorderLayout.BorderData(BorderLayout.SOUTH));

		Text text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setText("Center");
		text.setLayoutData(new BorderLayout.BorderData(BorderLayout.CENTER));

		shell.pack();
		shell.open();
		// textUser.forceFocus();

		// Set up the event loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				// If no more entries in event queue
				display.sleep();
			}
		}

		display.dispose();
	}

	public static void main(String[] args) {
		new BorderLayoutSample();
	}
}