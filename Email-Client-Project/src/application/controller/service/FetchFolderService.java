package application.controller.service;

import java.util.Arrays;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

import application.model.EmailTreeItem;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchFolderService extends Service<Void> {

	private EmailTreeItem<String> folderRoot;
	private Store store;
	private List<Folder> folderList;

	public FetchFolderService(Store store, EmailTreeItem<String> folderRoot, List<Folder> folderList) {
		this.store = store;
		this.folderRoot = folderRoot;
		this.folderList = folderList;// create shallow copy of EmailManager filed of folderList
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				setUpFolder();
				return null;
			}
		};
	}

	public void setUpFolder() {

		try {
			Folder[] folders = store.getDefaultFolder().list();
			handleFolder(folders);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void handleFolder(Folder[] folders) {

		// for (Folder folder : folders) {
		Arrays.stream(folders).forEach(folder -> {
			folderList.add(folder);
			EmailTreeItem<String> tree = new EmailTreeItem<String>(folder.getName());// do not use EmailTreeItem here
																						// otherwise treeItem will not
																						// be display in treeView
			tree.setExpanded(true);
			fetchMessageFolder(folder, tree);
			addMessageListenerToFolder(folder, folderRoot);
			folderRoot.getChildren().add(tree);

			try {
				if (folder.getType() == folder.HOLDS_FOLDERS) {
					Folder[] subFolder = folder.list();
					handleFolder(subFolder);
				}
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private void addMessageListenerToFolder(Folder folder, EmailTreeItem<String> emailTree) {

		folder.addMessageCountListener(new MessageCountListener() {

			@Override
			public void messagesRemoved(MessageCountEvent arg0) {

				System.out.println("messaged removed");
			}

			@Override
			public void messagesAdded(MessageCountEvent e) {
//				System.out.println("message added");

				for (int i = 0; i < e.getMessages().length; i++) {

					try {
						Message message = folder.getMessage(folder.getMessageCount() - 1);
						//emailTree.addEmailToTop();
					} catch (MessagingException me) {
						me.printStackTrace();
					}
				}
			}
		});
	}

	private void fetchMessageFolder(Folder folder, EmailTreeItem<String> tree) {

		Service fetchMessageService = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {

				return new Task<Void>() {

					@Override
					protected Void call() throws Exception {

						if (folder.getType() != folder.HOLDS_FOLDERS) {

							int folderSize = folder.getMessageCount();
							folder.open(Folder.READ_WRITE);

							for (int i = folderSize; i >= 0; i--) {

								tree.addEmail(folder.getMessage(i));

//								System.out.println(folder.getMessage(i).getSubject());
							}
						}
						return null;
					}

				};
			}

		};
		fetchMessageService.start();
	}

}
