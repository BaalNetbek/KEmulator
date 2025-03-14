package emulator.ui.swt;

import emulator.UILocale;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;

final class Class36 extends SelectionAdapter {
	private final MemoryView aClass110_621;

	Class36(final MemoryView aClass110_621) {
		super();
		this.aClass110_621 = aClass110_621;
	}

	public final void widgetSelected(final SelectionEvent selectionEvent) {
		final FileDialog fileDialog;
		(fileDialog = new FileDialog(MemoryView.method632(this.aClass110_621), 8192)).setText(UILocale.get("MEMORY_VIEW_SAVE_TO_FILE", "Save to file"));
		fileDialog.setFilterExtensions(new String[]{"*.png"});
		final String open;
		if ((open = fileDialog.open()) != null && MemoryView.method640(this.aClass110_621) != null) {
			MemoryView.method640(this.aClass110_621).getImpl().saveToFile(open);
		}
	}
}
