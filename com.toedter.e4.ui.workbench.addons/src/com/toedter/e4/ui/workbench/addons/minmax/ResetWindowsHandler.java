package com.toedter.e4.ui.workbench.addons.minmax;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

@SuppressWarnings("restriction")
public class ResetWindowsHandler {
	@Execute
	public void resetWindows(MApplication application, GenericMinMaxAddon minMaxAddon) {
		if (minMaxAddon instanceof GenericMinMaxAddon) {
			for (MWindow window : application.getChildren()) {
				((GenericMinMaxAddon) minMaxAddon).resetWindows(window);
			}
		}
	}
}
