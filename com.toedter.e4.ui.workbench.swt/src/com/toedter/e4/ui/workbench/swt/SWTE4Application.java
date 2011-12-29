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

package com.toedter.e4.ui.workbench.swt;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;
import org.eclipse.swt.widgets.Display;

import com.toedter.e4.ui.workbench.generic.GenericE4Application;

@SuppressWarnings("restriction")
public class SWTE4Application extends GenericE4Application {

	public SWTE4Application() {
		logger.debug("DEBUG SWTE4Application()");
		presentationEngineURI = "platform:/plugin/com.toedter.e4.ui.workbench.swt/"
				+ "com.toedter.e4.ui.workbench.swt.SWTPresentationEngine";
	}

	@Override
	protected void addToContext(IEclipseContext eclipseContext) {
		final Display display;
		if (eclipseContext.get(Display.class) != null) {
			display = eclipseContext.get(Display.class);
		} else {
			display = Display.getDefault();
			eclipseContext.set(Display.class, display);
		}

		PartRenderingEngine.initializeStyling(display, eclipseContext);
		logger.debug("SWTE4Application.createE4Workbench(): Styling inintialized");
	}
}