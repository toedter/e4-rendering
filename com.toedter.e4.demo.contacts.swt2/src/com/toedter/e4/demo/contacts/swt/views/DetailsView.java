/*******************************************************************************
 * Copyright (c) 2009, 2010 Siemens AG and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Tödter - initial implementation
 ******************************************************************************/

package com.toedter.e4.demo.contacts.swt.views;

import javax.inject.Inject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class DetailsView {
	@Inject
	public DetailsView(Composite parent) {
		System.out.println("DetailsView.DetailsView()");
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Details");
	}
}
