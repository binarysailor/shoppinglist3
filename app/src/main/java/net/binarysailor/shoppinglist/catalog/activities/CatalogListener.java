package net.binarysailor.shoppinglist.catalog.activities;

import android.view.View;

import net.binarysailor.shoppinglist.catalog.model.Category;
import net.binarysailor.shoppinglist.catalog.model.Product;

public interface CatalogListener {
    void onCategoryHeaderClicked(View categoryView, Category category);
    void onProductClicked(View productView, Product product);
}
