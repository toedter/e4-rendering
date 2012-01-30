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

package com.toedter.e4.demo.contacts.swt.processors;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;

@SuppressWarnings("restriction")
public class AddonProcessor {
	@Execute
	public void process(MApplication app) {
		MAddon bindingServiceAddon = ApplicationFactoryImpl.eINSTANCE.createAddon();
		bindingServiceAddon.setElementId("org.eclipse.e4.ui.bindings.service"); //$NON-NLS-1$
		bindingServiceAddon
				.setContributionURI("bundleclass://org.eclipse.e4.ui.bindings/org.eclipse.e4.ui.bindings.BindingServiceAddon"); //$NON-NLS-1$
		app.getAddons().add(bindingServiceAddon);

		MAddon bindingProcessingAddon = ApplicationFactoryImpl.eINSTANCE.createAddon();
		bindingProcessingAddon.setElementId("org.eclipse.e4.ui.workbench.bindings.model"); //$NON-NLS-1$
		bindingProcessingAddon
				.setContributionURI("bundleclass://org.eclipse.e4.ui.workbench.swt/org.eclipse.e4.ui.workbench.swt.util.BindingProcessingAddon"); //$NON-NLS-1$
		app.getAddons().add(bindingProcessingAddon);
	}
}
