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

package com.toedter.e4.ui.controls.javafx;

import javafx.scene.control.TabPane;

import com.toedter.e4.ui.controls.javafx.skin.CTabPaneSkin;

public class CTabPane extends TabPane {
	private boolean isMaximized;
	private boolean isMinimized;

	private final CTabPaneSkin skin;

	public CTabPane() {
		skin = new CTabPaneSkin(this);
		setSkin(skin);
	}

	public void setMinimizeVisible(boolean isMinimizeVisible) {
		skin.setMinimizeVisible(isMinimizeVisible);
	}

	public void setMaximizeVisible(boolean isMaximizeVisible) {
		skin.setMaximizeVisible(isMaximizeVisible);
	}

	public void setMaximized(boolean isMaximized) {
		this.isMaximized = isMaximized;
	}

	public void setMinimized(boolean isMinimized) {
		this.isMinimized = isMinimized;
	}

	public void setMaximizeHandler(Runnable handler) {
		skin.setMaximizeHandler(handler);
		skin.setMaximizeVisible(true);
	}

	public void setMinimizeHandler(Runnable handler) {
		skin.setMinimizeHandler(handler);
		skin.setMinimizeVisible(true);
	}

	public void setRestoreHandler(Runnable handler) {
		skin.setRestoreHandler(handler);
		skin.setMaximizeVisible(true);
	}
}
