package com.toedter.e4.ui.workbench.renderers.javafx;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class MenuBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MMenu)) {
			return;
		}

		if (parent != null && parent.getWidget() instanceof MenuBar) {
			String label = ((MUILabel) element).getLocalizedLabel();
			label = label.replaceAll("&", "_"); // JavaFX Mnemonics
			Menu menu = new Menu(label);
			menu.setMnemonicParsing(true);
			element.setWidget(menu);
			return;
		}

		// No parent means the main menu bar
		MenuBar menuBar = new MenuBar();
		element.setWidget(menuBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		if (container.getWidget() instanceof MenuBar) {
			MenuBar menuBar = (MenuBar) container.getWidget();
			for (MUIElement e : container.getChildren()) {
				menuBar.getMenus().add((Menu) e.getWidget());
			}
		} else if (container.getWidget() instanceof Menu) {
			Menu menu = (Menu) container.getWidget();
			for (MUIElement e : container.getChildren()) {
				menu.getItems().add((MenuItem) e.getWidget());
			}
		}
	}
}
