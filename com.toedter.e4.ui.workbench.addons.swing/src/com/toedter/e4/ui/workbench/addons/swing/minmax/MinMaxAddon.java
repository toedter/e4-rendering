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

package com.toedter.e4.ui.workbench.addons.swing.minmax;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

import com.toedter.e4.ui.workbench.addons.minmax.GenericMinMaxAddon;
import com.toedter.e4.ui.workbench.addons.minmax.IMinMaxAddon;
import com.toedter.e4.ui.workbench.renderers.swing.controls.CTabbedPane;

@SuppressWarnings("restriction")
public class MinMaxAddon implements IMinMaxAddon {

	public MinMaxAddon() {
		System.out.println("Swing MinMaxAddon");
	}

	@Override
	public void setGenericMinMaxAddon(GenericMinMaxAddon genericMinMaxAddon) {
		System.out.println("Swing MinMaxAddon.setGenericMinMaxAddon()");
	}

	@Override
	public void setMaximizeHandler(MUIElement element, Runnable maximizeHandler) {
		if (element.getWidget() instanceof CTabbedPane) {
			((CTabbedPane) element.getWidget()).setMaximizeHandler(maximizeHandler);
		}
	}

	@Override
	public void setMinimizeHandler(MUIElement element, Runnable minimizeHandler) {
		if (element.getWidget() instanceof CTabbedPane) {
			((CTabbedPane) element.getWidget()).setMinimizeHandler(minimizeHandler);
		}
	}

	@Override
	public void setRestoreHandler(MUIElement element, Runnable restoreHandler) {
		if (element.getWidget() instanceof CTabbedPane) {
			((CTabbedPane) element.getWidget()).setRestoreHandler(restoreHandler);
		}
	}
}
