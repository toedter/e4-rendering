package com.toedter.e4.ui.workbench.addons;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;

@SuppressWarnings("restriction")
public class MinMaxProcessor {
	@Execute
	void addMinMaxAddon(MApplication app) {
		List<MAddon> addons = app.getAddons();

		// Prevent multiple copies
		for (MAddon addon : addons) {
			if (addon.getContributionURI().contains("com.toedter.e4.ui.workbench.addons.minmax.GenericMinMaxAddon")) {
				return;
			}
		}

		// Insert the addon into the system
		MAddon minMaxAddon = ApplicationFactoryImpl.eINSTANCE.createAddon();
		minMaxAddon.setElementId("GenericMinMaxAddon"); //$NON-NLS-1$
		minMaxAddon
				.setContributionURI("platform:/plugin/com.toedter.e4.ui.workbench.addons/com.toedter.e4.ui.workbench.addons.minmax.GenericMinMaxAddon"); //$NON-NLS-1$
		app.getAddons().add(minMaxAddon);
	}
}
