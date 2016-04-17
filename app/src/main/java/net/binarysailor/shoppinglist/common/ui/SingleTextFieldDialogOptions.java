package net.binarysailor.shoppinglist.common.ui;

public class SingleTextFieldDialogOptions {

	private String textFieldHint;
	private String dialogTitle;
	private SingleTextFieldDialogCallback callback;

	public SingleTextFieldDialogOptions(String dialogTitle, String textFieldHint, SingleTextFieldDialogCallback callback) {
		this.textFieldHint = textFieldHint;
		this.dialogTitle = dialogTitle;
		this.callback = callback;
	}

	public void onOK(String text) {
		callback.onOK(text);
	}

	public String getTextFieldHint() {
		return textFieldHint;
	}

	public String getDialogTitle() {
		return dialogTitle;
	}

}
