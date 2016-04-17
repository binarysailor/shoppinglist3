package net.binarysailor.shoppinglist.catalog.activities;

import android.content.Intent;

import net.binarysailor.shoppinglist.catalog.model.Category;
import net.binarysailor.shoppinglist.list.model.ProductSelection;

class IntentStorage {

    private static final String CATEGORY_KEY = "category";

    private final Intent intent;

    public IntentStorage(final Intent intent) {
        this.intent = intent;
    }

    void putCategory(final Category category) {
        intent.putExtra(CATEGORY_KEY, category);
    }

    Category getCategory() {
        return (Category) intent.getSerializableExtra(CATEGORY_KEY);
    }
}
