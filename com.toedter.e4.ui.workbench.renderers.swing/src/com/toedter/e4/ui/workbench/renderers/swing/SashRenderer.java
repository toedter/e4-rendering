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

package com.toedter.e4.ui.workbench.renderers.swing;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.inject.Inject;
import javax.swing.JSplitPane;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class SashRenderer extends GenericRenderer {

	private EventHandler sashOrientationHandler;
	private EventHandler sashWeightHandler;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MPartSashContainer)) {
			return;
		}
		final MPartSashContainer partSashContainer = (MPartSashContainer) element;
		JSplitPane splitPane = new JSplitPane();

		if (partSashContainer.isHorizontal()) {
			splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		} else {
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		element.setWidget(splitPane);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> element) {
		if (element.getChildren().size() == 2) {
			JSplitPane splitPane = (JSplitPane) element.getWidget();
			int visibleChildrenCount = 0;
			if (element.getChildren().get(0).isVisible()) {
				splitPane.setLeftComponent((Component) element.getChildren().get(0).getWidget());
				visibleChildrenCount++;
			}
			if (element.getChildren().get(1).isVisible()) {
				splitPane.setRightComponent((Component) element.getChildren().get(1).getWidget());
				visibleChildrenCount++;
			}

			// TODO This is not a good position to hook the controller logic
			// but hookControllerLogic() is invoked before processContents()...
			String dividerPos = element.getContainerData();
			if (visibleChildrenCount == 2) {
				if (dividerPos != null) {
					splitPane.setDividerLocation(Integer.parseInt(dividerPos));
				}
			}

		} else {
			System.err.println("A sash has to have 2 children");
		}
	}

	@Override
	public void hookControllerLogic(MUIElement element) {
		if (element instanceof MPartSashContainer) {
			final MPartSashContainer partSashContainer = (MPartSashContainer) element;

			JSplitPane splitPane = (JSplitPane) partSashContainer.getWidget();
			splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					partSashContainer.setContainerData(((Integer) event.getNewValue()).toString());
				}
			});
		}
	}

	@Inject
	void postConstruct(IEventBroker eventBroker) {
		sashOrientationHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				// Ensure that this event is for a MPartSashContainer
				MUIElement element = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
				if (element.getRenderer() != SashRenderer.this) {
					return;
				}
				int orientation;
				if (((MPartSashContainer) element).isHorizontal()) {
					orientation = JSplitPane.HORIZONTAL_SPLIT;
				} else {
					orientation = JSplitPane.VERTICAL_SPLIT;
				}
				((JSplitPane) element.getWidget()).setOrientation(orientation);
			}
		};

		eventBroker.subscribe(UIEvents.GenericTile.TOPIC_HORIZONTAL, sashOrientationHandler);

		sashWeightHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				// Ensure that this event is for a MPartSashContainer
				MUIElement element = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
				if (element.getRenderer() != SashRenderer.this) {
					return;
				}

				String dividerPos = element.getContainerData();
				JSplitPane splitPane = ((JSplitPane) element.getWidget());
				if (dividerPos != null && dividerPos != ((Integer) splitPane.getDividerLocation()).toString()) {
					splitPane.setDividerLocation(Float.parseFloat(dividerPos));
				}
			}
		};

		eventBroker.subscribe(UIEvents.UIElement.TOPIC_CONTAINERDATA, sashWeightHandler);
	}
}
