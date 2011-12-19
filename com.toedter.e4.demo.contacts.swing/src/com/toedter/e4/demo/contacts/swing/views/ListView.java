package com.toedter.e4.demo.contacts.swing.views;

import com.toedter.e4.demo.contacts.generic.model.Contact;
import com.toedter.e4.demo.contacts.generic.model.ContactsRepositoryFactory;
import java.awt.BorderLayout;
import javax.inject.Inject;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.e4.ui.model.application.MApplication;

@SuppressWarnings("restriction")
public class ListView {
	@Inject
	public ListView(JPanel parent, final MApplication application) {
		final String[] columnNames = { "First Name", "Last Name" };
		final IObservableList allContacts = ContactsRepositoryFactory.getContactsRepository().getAllContacts();

		@SuppressWarnings("serial")
		AbstractTableModel tableModel = new AbstractTableModel() {

			@Override
			public String getColumnName(int column) {
				return columnNames[column];
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Contact contact = (Contact) allContacts.get(rowIndex);
				if (columnIndex == 0) {
					return contact.getFirstName();
				} else if (columnIndex == 1) {
					return contact.getLastName();
				}
				return "?";
			}

			@Override
			public int getRowCount() {
				return allContacts.size();
			}

			@Override
			public int getColumnCount() {
				return columnNames.length;
			}
		};

		JTable table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		parent.add(scrollPane, BorderLayout.CENTER);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = ((DefaultListSelectionModel) e.getSource()).getAnchorSelectionIndex();
				Contact contact = (Contact) allContacts.get(index);
				if (!e.getValueIsAdjusting()) {
					application.getContext().set(Contact.class, contact);
				}
			}
		});

		// Hack to select Kai Toedter at startup
		int index = 0;
		for (int i = 0; i < allContacts.size(); i++) {
			Contact contact = (Contact) allContacts.get(i);

			if ("Kai".equalsIgnoreCase(contact.getFirstName()) && "Tödter".equalsIgnoreCase(contact.getLastName())) {
				break;
			}
			index++;
		}
		table.getSelectionModel().setAnchorSelectionIndex(index);

	}
}
