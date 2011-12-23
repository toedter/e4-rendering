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

package com.toedter.e4.ui.workbench.javafx;

import com.toedter.e4.ui.workbench.generic.GenericE4Application;

public class JavaFXE4Application extends GenericE4Application {
	public JavaFXE4Application() {
		presentationEngineURI = "platform:/plugin/com.toedter.e4.ui.workbench.javafx/"
				+ "com.toedter.e4.ui.workbench.javafx.JavaFXPresentationEngine";
	}
}