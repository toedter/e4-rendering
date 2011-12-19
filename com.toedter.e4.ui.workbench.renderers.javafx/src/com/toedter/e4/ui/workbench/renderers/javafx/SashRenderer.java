package com.toedter.e4.ui.workbench.renderers.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

import javax.inject.Inject;

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
		SplitPane splitPane = new SplitPane();

		Orientation orientation;
		if (partSashContainer.isHorizontal()) {
			orientation = Orientation.HORIZONTAL;
		} else {
			orientation = Orientation.VERTICAL;
		}
		splitPane.setOrientation(orientation);
		element.setWidget(splitPane);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> element) {
		if (element.getChildren().size() == 2) {
			SplitPane splitPane = (SplitPane) element.getWidget();
			int visibleChildrenCount = 0;
			if (element.getChildren().get(0).isVisible()) {
				splitPane.getItems().add((Node) element.getChildren().get(0).getWidget());
				visibleChildrenCount++;
			}
			if (element.getChildren().get(1).isVisible()) {
				splitPane.getItems().add((Node) element.getChildren().get(1).getWidget());
				visibleChildrenCount++;
			}

			// TODO This is not a good position to hook the controller logic
			// but hookControllerLogic() is invoked before processContents()...
			String dividerPos = element.getContainerData();
			if (visibleChildrenCount == 2) {
				if (dividerPos != null) {
					splitPane.setDividerPositions(Float.parseFloat(dividerPos));
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
			SplitPane splitPane = (SplitPane) partSashContainer.getWidget();
			splitPane.getDividers().get(0).positionProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					partSashContainer.setContainerData(newValue.toString());
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
				Orientation orientation;
				if (((MPartSashContainer) element).isHorizontal()) {
					orientation = Orientation.HORIZONTAL;
				} else {
					orientation = Orientation.VERTICAL;
				}
				((SplitPane) element.getWidget()).setOrientation(orientation);
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
				SplitPane splitPane = ((SplitPane) element.getWidget());
				if (dividerPos != null && dividerPos != ((Double) splitPane.getDividerPositions()[0]).toString()) {
					splitPane.setDividerPositions(Float.parseFloat(dividerPos));
				}
			}
		};

		eventBroker.subscribe(UIEvents.UIElement.TOPIC_CONTAINERDATA, sashWeightHandler);
	}
}
