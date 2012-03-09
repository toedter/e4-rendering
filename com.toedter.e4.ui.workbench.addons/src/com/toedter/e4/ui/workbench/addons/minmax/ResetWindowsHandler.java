package com.toedter.e4.ui.workbench.addons.minmax;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

import com.toedter.e4.ui.workbench.addons.MinMaxProcessor;

@SuppressWarnings("restriction")
public class ResetWindowsHandler {
	@Execute
	public void resetWindows(MApplication application) {
		List<MAddon> addons = application.getAddons();

		// Prevent multiple copies
		for (MAddon addon : addons) {
			System.out.println(addon.getContributionURI());
			if (addon.getContributionURI().equals(MinMaxProcessor.GEN_MIN_MAX_CONTRIBUTION_URI)) {
				if (addon.getObject() instanceof GenericMinMaxAddon) {
					for (MWindow window : application.getChildren()) {
						((GenericMinMaxAddon) addon.getObject()).resetWindows(window);
					}
				}
			}
		}

	}
}
