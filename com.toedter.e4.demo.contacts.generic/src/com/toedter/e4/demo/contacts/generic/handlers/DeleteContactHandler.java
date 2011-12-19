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

package com.toedter.e4.demo.contacts.generic.handlers;

import com.toedter.e4.demo.contacts.generic.model.Contact;
import com.toedter.e4.demo.contacts.generic.model.ContactsRepositoryFactory;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.MContext;

@SuppressWarnings("restriction")
public class DeleteContactHandler {
	@CanExecute
	boolean canExecute(MContext context) {
		if (context == null) {
			return false;
		}
		Contact contact = (Contact) context.getContext().get(Contact.class);
		return contact != null;
	}

	@Execute
	void execute(@Optional final Contact contact) {
		System.out.println("DeleteContactHandler.execute(): " + contact);
		if (contact != null) {
			ContactsRepositoryFactory.getContactsRepository().removeContact(contact);
		}
	}
}
