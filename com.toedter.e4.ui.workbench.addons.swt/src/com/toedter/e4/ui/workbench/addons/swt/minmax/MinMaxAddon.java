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

package com.toedter.e4.ui.workbench.addons.swt.minmax;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;

import com.toedter.e4.ui.workbench.addons.minmax.GenericMinMaxAddon;
import com.toedter.e4.ui.workbench.addons.minmax.IMinMaxAddon;

@SuppressWarnings("restriction")
public class MinMaxAddon implements IMinMaxAddon {

	public MinMaxAddon() {
		System.out.println("SWT MinMaxAddon");
	}

	@Override
	public void setGenericMinMaxAddon(GenericMinMaxAddon genericMinMaxAddon) {
		System.out.println("SWT MinMaxAddon.setGenericMinMaxAddon()");
	}

	@Override
	public void setMaximizedHandler(MUIElement element, final Runnable maximizedHandler) {
		System.out.println("SWT MinMaxAddon.setMaximizedHandler(): " + element);
		if (element.getWidget() instanceof CTabFolder) {

			((CTabFolder) element.getWidget()).addCTabFolder2Listener(new CTabFolder2Listener() {

				@Override
				public void showList(CTabFolderEvent event) {
					System.out
							.println("MinMaxAddon.setMaximizedHandler(...).new CTabFolder2Listener() {...}.showList()");
				}

				@Override
				public void restore(CTabFolderEvent event) {
					System.out
							.println("MinMaxAddon.setMaximizedHandler(...).new CTabFolder2Listener() {...}.restore()");
				}

				@Override
				public void minimize(CTabFolderEvent event) {
					System.out
							.println("MinMaxAddon.setMaximizedHandler(...).new CTabFolder2Listener() {...}.minimize()");
				}

				@Override
				public void maximize(CTabFolderEvent event) {
					maximizedHandler.run();
				}

				@Override
				public void close(CTabFolderEvent event) {
					System.out.println("MinMaxAddon.setMaximizedHandler(...).new CTabFolder2Listener() {...}.close()");
				}
			});
		}
	}
}
