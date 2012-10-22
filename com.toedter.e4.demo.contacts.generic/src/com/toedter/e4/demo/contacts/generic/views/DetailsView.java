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

package com.toedter.e4.demo.contacts.generic.views;

import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;

public class DetailsView {

	@Inject
	protected MDirtyable dirtyable;

	public DetailsView() {
		System.out.println("TODO: Please provide a UI toolkit specific DetailsView implementetion.");
	}
}
