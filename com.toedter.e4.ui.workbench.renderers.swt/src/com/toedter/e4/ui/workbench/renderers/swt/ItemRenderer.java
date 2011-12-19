package com.toedter.e4.ui.workbench.renderers.swt;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.commands.MParameter;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public abstract class ItemRenderer extends GenericRenderer {
	protected ParameterizedCommand generateParameterizedCommand(final MHandledItem item,
			final IEclipseContext lclContext) {
		ECommandService cmdService = (ECommandService) lclContext.get(ECommandService.class.getName());
		Map<String, Object> parameters = null;
		List<MParameter> modelParms = item.getParameters();
		if (modelParms != null && !modelParms.isEmpty()) {
			parameters = new HashMap<String, Object>();
			for (MParameter mParm : modelParms) {
				parameters.put(mParm.getName(), mParm.getValue());
			}
		}
		ParameterizedCommand cmd = cmdService.createCommand(item.getCommand().getElementId(), parameters);
		item.setWbCommand(cmd);
		return cmd;
	}

	protected Listener createParametrizedCommandEventHandler(final MHandledItem item) {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {

				final IEclipseContext eclipseContext = getContext(item);
				EHandlerService service = (EHandlerService) eclipseContext.get(EHandlerService.class.getName());
				ParameterizedCommand command = item.getWbCommand();
				if (command == null) {
					command = generateParameterizedCommand(item, eclipseContext);
				}
				if (command == null) {
					System.err.println("Failed to execute: " + item.getCommand());
					return;
				}
				eclipseContext.set(MItem.class.getName(), item);
				service.executeHandler(command);
				eclipseContext.remove(MItem.class.getName());
			}
		};
	}

	protected Listener createEventHandler(final MItem item) {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				final IEclipseContext eclipseContext = getContext(item);
				eclipseContext.set(MItem.class, item);
				if (item instanceof MDirectToolItem) {
					ContextInjectionFactory.invoke(((MDirectToolItem) item).getObject(), Execute.class, eclipseContext);
				} else if (item instanceof MDirectMenuItem) {
					ContextInjectionFactory.invoke(((MDirectMenuItem) item).getObject(), Execute.class, eclipseContext);
				}
				eclipseContext.remove(MItem.class);
			}
		};
	}

	public ImageDescriptor getImageDescriptor(MUILabel element) {
		String iconURI = element.getIconURI();
		if (iconURI != null && iconURI.length() > 0) {
			return imageDescriptorFromURI(URI.createURI(iconURI));
		}
		return null;
	}

	public ImageDescriptor imageDescriptorFromURI(URI iconPath) {
		try {
			return ImageDescriptor.createFromURL(new URL(iconPath.toString()));
		} catch (MalformedURLException e) {
			System.err.println("iconURI \"" + iconPath.toString() + "\" is invalid, no image will be shown");
			return null;
		}
	}

}
