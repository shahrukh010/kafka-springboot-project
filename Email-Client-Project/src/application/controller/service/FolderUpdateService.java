package application.controller.service;

import java.util.List;

import javax.mail.Folder;
import javax.mail.MessagingException;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FolderUpdateService<V> extends Service {

	private List<Folder> folderList;

	public FolderUpdateService(List<Folder> folderList) {

		this.folderList = folderList;
	}

	@Override
	protected Task createTask() {

		return new Task<V>() {

			@Override
			protected V call() throws Exception {

				for (;;) {

					try {
						Thread.sleep(5000);
						for (Folder folder : folderList) {

							if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()) {

								folder.getMessageCount();
							}
						}
					} catch (MessagingException e) {

					}
				}

				//return null;
			}
		};
	}

}
