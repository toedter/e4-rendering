/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/
package com.toedter.e4.demo.contacts.generic.handlers;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

@SuppressWarnings("restriction")
public class SaveHandler {

	@CanExecute
	public boolean canExecute(EPartService partService) {
		MPart details = partService.findPart("part:detailsView");
		if (details != null) {
			return details.isDirty();
		}
		return true;
	}

	@Execute
	public void execute(IEclipseContext context, @Optional IStylingEngine engine, final EPartService partService)
			throws InvocationTargetException, InterruptedException {
		System.out.println("SaveHandler.execute()");
		final MPart details = partService.findPart("part:detailsView");
		final IEclipseContext pmContext = context.createChild();

		if (pmContext != null) {
			pmContext.dispose();
		}
	}

}
