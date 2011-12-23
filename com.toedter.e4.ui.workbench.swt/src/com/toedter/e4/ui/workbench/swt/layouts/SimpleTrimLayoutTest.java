package com.toedter.e4.ui.workbench.swt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SimpleTrimLayoutTest {
	Display display = new Display();
	Shell shell = new Shell(display);

	public SimpleTrimLayoutTest() {
		shell.setLayout(new SimpleTrimLayout());

		Button topButton = new Button(shell, SWT.PUSH);
		topButton.setText("TOP");
		topButton.setLayoutData(SimpleTrimLayout.TOP);

		Button leftButton = new Button(shell, SWT.PUSH);
		leftButton.setText("Left");
		leftButton.setLayoutData(SimpleTrimLayout.LEFT);

		Button rightButton = new Button(shell, SWT.PUSH);
		rightButton.setText("Right");
		rightButton.setLayoutData(SimpleTrimLayout.RIGHT);

		Button bottomButton = new Button(shell, SWT.PUSH);
		bottomButton.setText("Bottom");
		bottomButton.setLayoutData(SimpleTrimLayout.BOTTOM);

		Text text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setText("Center");
		text.setLayoutData(SimpleTrimLayout.CENTER);

		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}

	public static void main(String[] args) {
		new SimpleTrimLayoutTest();
	}
}