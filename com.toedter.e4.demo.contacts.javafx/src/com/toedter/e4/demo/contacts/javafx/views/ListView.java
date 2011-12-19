/*******************************************************************************
 * Copyright (c) 2009, 2011 Siemens AG and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Tödter - initial implementation
 ******************************************************************************/

package com.toedter.e4.demo.contacts.javafx.views;

import com.toedter.e4.demo.contacts.generic.model.Contact;
import com.toedter.e4.demo.contacts.generic.model.ContactsRepositoryFactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.MApplication;

@SuppressWarnings("restriction")
public class ListView {
	@Inject
	public ListView(BorderPane parent, final MApplication application) {
		TableView<Contact> table = new TableView<Contact>();
		TableColumn<Contact, String> firstNameCol = new TableColumn<Contact, String>("First Name");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));

		TableColumn<Contact, String> lastNameCol = new TableColumn<Contact, String>("Last Name");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
		parent.setCenter(table);

		table.getColumns().addAll(firstNameCol, lastNameCol);

		ObservableList<Contact> data = FXCollections.observableArrayList(ContactsRepositoryFactory
				.getContactsRepository().getAllContacts());
		table.setItems(data);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {

			public void changed(ObservableValue<? extends Contact> arg0, Contact arg1, Contact arg2) {
				application.getContext().set(Contact.class, arg2);
			}
		});

		// Hack to select Kai Toedter at startup
		int index = 0;
		for (Contact contact : data) {
			if ("Kai".equalsIgnoreCase(contact.getFirstName()) && "Tödter".equalsIgnoreCase(contact.getLastName())) {
				break;
			}
			index++;
		}
		table.getSelectionModel().clearAndSelect(index);
	}
}
