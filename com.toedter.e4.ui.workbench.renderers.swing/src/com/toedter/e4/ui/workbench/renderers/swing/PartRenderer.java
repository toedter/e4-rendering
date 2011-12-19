/*
 * Currently not used anymore....
 */
package com.toedter.e4.ui.workbench.renderers.swing;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class PartRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		JPanel pane = new JPanel(new BorderLayout());
		final MPart part = (MPart) element;

		// Create a context for this part
		IEclipseContext localContext = part.getContext();
		localContext.set(JPanel.class, pane);

		IContributionFactory contributionFactory = (IContributionFactory) localContext.get(IContributionFactory.class
				.getName());
		Object newPart = contributionFactory.create(part.getContributionURI(), localContext);
		part.setObject(newPart);

		element.setWidget(pane);
	}
}
