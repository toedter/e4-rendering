package com.toedter.e4.ui.workbench.addons.javafx.minmax;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

import com.toedter.e4.ui.controls.javafx.CTabPane;
import com.toedter.e4.ui.workbench.addons.minmax.GenericMinMaxAddon;
import com.toedter.e4.ui.workbench.addons.minmax.IMinMaxAddon;

@SuppressWarnings("restriction")
public class MinMaxAddon implements IMinMaxAddon {

	public MinMaxAddon() {
		System.out.println("JavaFX MinMaxAddon");
	}

	@Override
	public void setGenericMinMaxAddon(GenericMinMaxAddon genericMinMaxAddon) {
		System.out.println("MinMaxAddon.setGenericMinMaxAddon()");
	}

	@Override
	public void setMaximizedHandler(MUIElement element, Runnable maximizedHandler) {
		if (element.getWidget() instanceof CTabPane) {
			((CTabPane) element.getWidget()).setMaximizedHandler(maximizedHandler);
		}
	}
}
