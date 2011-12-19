package com.toedter.e4.ui.workbench.swing;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.toedter.e4.ui.workbench.generic.GenericE4Application;

public class SwingE4Application extends GenericE4Application {
	public SwingE4Application() {
		System.out.println("SwingE4Application.SwingE4Application()");
		presentationEngineURI = "platform:/plugin/com.toedter.e4.ui.workbench.swing/"
				+ "com.toedter.e4.ui.workbench.swing.SwingPresentationEngine";

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				UIManager.installLookAndFeel("Napkin", "net.sourceforge.napkinlaf.NapkinLookAndFeel");
				SwingRealm.createDefault();
			}
		});
	}
}