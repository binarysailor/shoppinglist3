package net.binarysailor.shoppinglist.list.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProductSelection implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Set<Integer> selectedIds = new HashSet<Integer>();

	public boolean isSelected(final int productId) {
		return selectedIds.contains(productId);
	}

	public void select(final int productId) {
		selectedIds.add(productId);
	}

	public void deselect(final int productId) {
		selectedIds.remove(productId);
	}

	public boolean toggle(final int productId) {
		if (isSelected(productId)) {
			deselect(productId);
		} else {
			select(productId);
		}

		return isSelected(productId);
	}

	public void add(final ProductSelection other) {
		selectedIds.addAll(other.selectedIds);
	}

	public void deselectAll() {
		selectedIds.clear();
	}

	public Collection<Integer> getProductIds() {
		return Collections.unmodifiableSet(selectedIds);
	}
}
