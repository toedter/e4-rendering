package com.toedter.e4.ui.workbench.renderers.swt;

import javax.inject.Inject;

import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.jface.resource.DeviceResourceException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

@SuppressWarnings("restriction")
public class ToolItemRenderer extends ItemRenderer {
	@Inject
	IContributionFactory contributionFactory;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (element instanceof MHandledToolItem || element instanceof MDirectToolItem) {
			ToolItem toolItem = new ToolItem((ToolBar) parent.getWidget(), SWT.PUSH);

			MToolItem item = (MToolItem) element;
			if (item.getIconURI() != null) {

				LocalResourceManager m = new LocalResourceManager(JFaceResources.getResources());
				ImageDescriptor icon = getImageDescriptor(item);
				try {
					toolItem.setImage(icon == null ? null : m.createImage(icon));
				} catch (DeviceResourceException e) {
					icon = ImageDescriptor.getMissingImageDescriptor();
					toolItem.setImage(m.createImage(icon));
				}
			}
			if (item.getTooltip() != null) {
				toolItem.setToolTipText(item.getLocalizedTooltip());
			}
			element.setWidget(toolItem);
		}
	}

	@Override
	public void hookControllerLogic(MUIElement me) {
		// if (me instanceof MDirectToolItem) {
		// final MDirectToolItem item = (MDirectToolItem) me;
		// item.setObject(contributionFactory.create(item.getContributionURI(),
		// getContext(item)));
		//
		// JButton button = (JButton) item.getWidget();
		// button.addActionListener(createEventHandler(item));
		// } else if (me instanceof MHandledToolItem) {
		// final MHandledItem item = (MHandledToolItem) me;
		//
		// JButton button = (JButton) item.getWidget();
		// button.addActionListener(createParametrizedCommandEventHandler(item));
		// }
	}
}
