package com.toedter.e4.ui.workbench.addons.generic.minmax;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import com.toedter.e4.ui.workbench.addons.generic.MinMaxProcessor;
import com.toedter.e4.ui.workbench.generic.IPresentationEngine2;

@SuppressWarnings("restriction")
public class RestoreHandler {
	@Execute
	// public void restore(@Optional
	// @Named(GenericMinMaxAddon.ADDONS_MINMAX_TRIM_STACK_ID) String
	// trimStackId) {
	public void restore(MItem item, EModelService modelService, MWindow window, MApplication application) {
		String stackId = item.getContainerData();
		MUIElement stack = modelService.find(stackId, window);
		List<MAddon> addons = application.getAddons();

		for (MAddon addon : addons) {
			if (addon.getContributionURI().equals(MinMaxProcessor.GEN_MIN_MAX_CONTRIBUTION_URI)) {
				if (addon.getObject() instanceof GenericMinMaxAddon) {
					((GenericMinMaxAddon) addon.getObject()).restore(stack);
				}
			}
		}

		IPresentationEngine2 presentationEngine = (IPresentationEngine2) application.getContext().get(
				IPresentationEngine.class);
		MToolBar barToBeRemoved = null;
		MTrimBar currentTrimBar = null;
		if (window instanceof MTrimmedWindow) {
			MTrimmedWindow trimmedWindow = (MTrimmedWindow) window;
			List<MTrimBar> trimBars = trimmedWindow.getTrimBars();
			for (MTrimBar trimBar : trimBars) {
				currentTrimBar = trimBar;
				if (trimBar.getSide() == SideValue.LEFT || trimBar.getSide() == SideValue.RIGHT) {
					List<MTrimElement> children = trimBar.getChildren();
					for (MTrimElement child : children) {
						System.out.println(child);
						MToolBar bar = (MToolBar) child;
						for (MToolBarElement barElement : bar.getChildren()) {
							if (barElement == item) {
								barToBeRemoved = bar;
							}
						}

					}
				}
			}
		}
		if (barToBeRemoved != null) {
			currentTrimBar.getChildren().remove(barToBeRemoved);
			if (currentTrimBar.getChildren().size() == 0) {
				currentTrimBar.setVisible(false);
			}
			presentationEngine.refreshGui(currentTrimBar);
		}

		presentationEngine.refreshGui(window);
	}
}
