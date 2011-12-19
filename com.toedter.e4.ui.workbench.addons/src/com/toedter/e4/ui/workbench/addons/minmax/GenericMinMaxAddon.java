package com.toedter.e4.ui.workbench.addons.minmax;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@SuppressWarnings("restriction")
public class GenericMinMaxAddon {
	private final IMinMaxAddon uiMinMaxAddon;
	private final IEventBroker eventBroker;

	@Inject
	public GenericMinMaxAddon(IMinMaxAddon uiMinMaxAddon, IEventBroker eventBroker) {
		System.out.println("Generic GenericMinMaxAddon()");
		this.uiMinMaxAddon = uiMinMaxAddon;
		this.eventBroker = eventBroker;
		this.uiMinMaxAddon.setGenericMinMaxAddon(this);
	}

	@PostConstruct
	void hookListeners() {
		String topic = UIEvents.buildTopic(UIEvents.UIElement.TOPIC, UIEvents.UIElement.WIDGET);
		eventBroker.subscribe(topic, widgetListener);
	}

	private final EventHandler widgetListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			final MUIElement element = (MUIElement) event.getProperty(EventTags.ELEMENT);
			if (!(element instanceof MPartStack) && !(element instanceof MArea)) {
				return;
			}
			final Runnable maximizeRunnable = new Runnable() {

				@Override
				public void run() {
					maximize(element);
				}
			};
			uiMinMaxAddon.setMaximizedHandler(element, maximizeRunnable);
		}

		private void maximize(MUIElement element) {
			System.out.println("Maximized " + element);

			MWindow window = getWindowFor(element);
			System.out.println(window);
			System.out.println(window.getChildren());

			if (window != null) {
				window.getChildren().remove(0);
			}
		}

		private void minimize(MUIElement element) {
			System.out.println("Minimized " + element);
		}

		private MWindow getWindowFor(MUIElement element) {
			MUIElement parent = element.getParent();
			while (parent != null && !(parent instanceof MWindow)) {
				parent = parent.getParent();
			}
			return (MWindow) parent;
		}

	};

}
