package com.toedter.e4.ui.workbench.addons.minmax;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

@SuppressWarnings("restriction")
public interface IMinMaxAddon {
	void setGenericMinMaxAddon(GenericMinMaxAddon genericMinMaxAddon);

	void setMaximizedHandler(MUIElement element, Runnable maximizedHandler);
}
