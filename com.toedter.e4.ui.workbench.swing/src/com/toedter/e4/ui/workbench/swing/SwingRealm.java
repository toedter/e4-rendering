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

import org.eclipse.core.databinding.observable.Realm;

public class SwingRealm extends Realm {
	public static void createDefault() {
		setDefault(new SwingRealm());
	}

	@Override
	public boolean isCurrent() {
		return SwingUtilities.isEventDispatchThread();
	}
}