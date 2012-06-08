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

import java.util.HashMap;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;

import com.toedter.e4.ui.workbench.addons.generic.minmax.GenericMinMaxAddon;
import com.toedter.e4.ui.workbench.addons.generic.minmax.IMinMaxAddon;

@SuppressWarnings("restriction")
public class MinMaxAddon implements IMinMaxAddon {

	private static class MyCTabFolder2Listener implements CTabFolder2Listener {
		public Runnable maximizedHandler;
		public Runnable minimizedHandler;
		public Runnable restoreHandler;

		@Override
		public void showList(CTabFolderEvent event) {
			System.out.println("SWT MinMaxAddon: showList()");
		}

		@Override
		public void restore(CTabFolderEvent event) {
			restoreHandler.run();
			CTabFolder cTabFolder = (CTabFolder) event.getSource();
			cTabFolder.setMaximizeVisible(true);
			cTabFolder.setMaximized(false);
			cTabFolder.layout();
		}

		@Override
		public void minimize(CTabFolderEvent event) {
			minimizedHandler.run();
			CTabFolder cTabFolder = (CTabFolder) event.getSource();
			cTabFolder.setMaximizeVisible(true);
			cTabFolder.setMaximized(false);
			cTabFolder.layout();
		}

		@Override
		public void maximize(CTabFolderEvent event) {
			maximizedHandler.run();
			CTabFolder cTabFolder = (CTabFolder) event.getSource();
			cTabFolder.setMaximizeVisible(true);
			cTabFolder.setMaximized(true);
			cTabFolder.layout();
		}

		@Override
		public void close(CTabFolderEvent event) {
			System.out.println("SWT MinMaxAddon: close()");
		}
	};

	private final HashMap<CTabFolder, MyCTabFolder2Listener> tabFolderMap;

	public MinMaxAddon() {
		System.out.println("SWT MinMaxAddon");
		tabFolderMap = new HashMap<CTabFolder, MyCTabFolder2Listener>();
	}

	@Override
	public void setGenericMinMaxAddon(GenericMinMaxAddon genericMinMaxAddon) {
		System.out.println("SWT MinMaxAddon.setGenericMinMaxAddon()");
	}

	@Override
	public void setMaximizeHandler(MUIElement element, final Runnable maximizedHandler) {
		if (element.getWidget() instanceof CTabFolder) {
			MyCTabFolder2Listener cTabFolder2Listener = getTabFolder2Listener(element);
			cTabFolder2Listener.maximizedHandler = maximizedHandler;
			CTabFolder cTabFolder = (CTabFolder) element.getWidget();
			cTabFolder.setMaximizeVisible(true);
		}
	}

	private MyCTabFolder2Listener getTabFolder2Listener(MUIElement element) {
		CTabFolder cTabFolder = (CTabFolder) element.getWidget();
		MyCTabFolder2Listener cTabFolder2Listener = tabFolderMap.get(cTabFolder);
		if (cTabFolder2Listener == null) {
			cTabFolder2Listener = new MyCTabFolder2Listener();
			cTabFolder.addCTabFolder2Listener(cTabFolder2Listener);
			tabFolderMap.put(cTabFolder, cTabFolder2Listener);
		}
		return cTabFolder2Listener;
	}

	@Override
	public void setMinimizeHandler(MUIElement element, Runnable minimizedHandler) {
		if (element.getWidget() instanceof CTabFolder) {
			MyCTabFolder2Listener cTabFolder2Listener = getTabFolder2Listener(element);
			cTabFolder2Listener.minimizedHandler = minimizedHandler;
			CTabFolder cTabFolder = (CTabFolder) element.getWidget();
			cTabFolder.setMinimizeVisible(true);
		}
	}

	@Override
	public void setRestoreHandler(MUIElement element, Runnable restoreHandler) {
		if (element.getWidget() instanceof CTabFolder) {
			MyCTabFolder2Listener cTabFolder2Listener = getTabFolder2Listener(element);
			cTabFolder2Listener.restoreHandler = restoreHandler;
		}
	}

	private void setCTabFolderButtons(CTabFolder cTabFolder, MUIElement element, boolean hideButtons) {
		if (hideButtons) {
			cTabFolder.setMinimizeVisible(false);
			cTabFolder.setMaximizeVisible(false);
		} else {
			if (element.getTags().contains(GenericMinMaxAddon.MINIMIZED)) {
				cTabFolder.setMinimizeVisible(false);
				cTabFolder.setMaximizeVisible(true);
				cTabFolder.setMaximized(true);
			} else if (element.getTags().contains(GenericMinMaxAddon.MAXIMIZED)) {
				cTabFolder.setMinimizeVisible(true);
				cTabFolder.setMaximizeVisible(true);
				cTabFolder.setMaximized(true);
			} else {
				cTabFolder.setMinimizeVisible(true);
				cTabFolder.setMaximizeVisible(true);
				cTabFolder.setMinimized(false);
				cTabFolder.setMaximized(false);
				cTabFolder.layout();
			}
		}
	}

}
