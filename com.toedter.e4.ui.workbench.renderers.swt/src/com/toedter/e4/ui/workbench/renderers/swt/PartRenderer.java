/*
 * Currently not used anymore....
 */
package com.toedter.e4.ui.workbench.renderers.swt;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class PartRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		System.out.println("PartRenderer.createWidget(): " + parent.getWidget());

		Composite parentWidget = (Composite) parent.getWidget();
		final MPart part = (MPart) element;
		Composite composite = new Composite(parentWidget, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		// Create a context for this part
		IEclipseContext localContext = part.getContext();
		localContext.set(Composite.class, composite);

		IContributionFactory contributionFactory = (IContributionFactory) localContext.get(IContributionFactory.class
				.getName());
		Object newPart = contributionFactory.create(part.getContributionURI(), localContext);
		part.setObject(newPart);

		element.setWidget(composite);
	}
}
