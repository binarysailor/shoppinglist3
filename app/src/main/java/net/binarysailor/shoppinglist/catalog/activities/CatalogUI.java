package net.binarysailor.shoppinglist.catalog.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import net.binarysailor.shoppinglist.catalog.model.Product;

public interface CatalogUI {
    View createProductView(Context context, ViewGroup parent, Product product);
    void styleProductView(View productView);
}
