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