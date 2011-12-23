/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial API and implementation
 ******************************************************************************/

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