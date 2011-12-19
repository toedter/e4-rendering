package com.toedter.e4.ui.workbench.renderers.javafx;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarSeparator;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class ToolBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MToolBar)) {
			return;
		}

		Orientation orientation = getOrientation(element);
		if (orientation == Orientation.VERTICAL) {
			VBox toolBar = new VBox();
			element.setWidget(toolBar);
		}
		// Since we use a JavaFX ToolBar for the TrimBar, each e4 tool bar is
		// rendered as JavaFX HBox
		HBox toolBar = new HBox();
		element.setWidget(toolBar);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		Pane toolBar = (Pane) container.getWidget();
		for (MUIElement element : container.getChildren()) {
			if (element instanceof MHandledToolItem || element instanceof MDirectToolItem) {
				toolBar.getChildren().add((Button) element.getWidget());
			} else if (element instanceof MToolBarSeparator) {
				Separator separator = (Separator) element.getWidget();
				if (separator != null) {
					separator.setOrientation(getOrientation(container) == Orientation.VERTICAL ? Orientation.HORIZONTAL
							: Orientation.VERTICAL);
					toolBar.getChildren().add(separator);
				}
			}
		}
	}

	private Orientation getOrientation(final MUIElement element) {
		MUIElement theParent = element.getParent();
		if (theParent instanceof MTrimBar) {
			MTrimBar trimContainer = (MTrimBar) theParent;
			SideValue side = trimContainer.getSide();
			if (side.getValue() == SideValue.LEFT_VALUE || side.getValue() == SideValue.RIGHT_VALUE) {
				return Orientation.VERTICAL;
			}
		}
		return Orientation.HORIZONTAL;
	}

}
