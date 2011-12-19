/*******************************************************************************
 * Copyright (c) 2009 Siemens AG and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Tödter - initial implementation
 ******************************************************************************/

package com.toedter.e4.demo.contacts.generic.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

@SuppressWarnings("restriction")
public class ExitHandler {
	@Execute
	public void exit() {
		System.out.println("TODO: Please implement exit handler for specific UI toolkit.");
	}
}
