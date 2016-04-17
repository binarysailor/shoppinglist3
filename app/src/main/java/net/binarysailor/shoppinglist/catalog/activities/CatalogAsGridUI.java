package net.binarysailor.shoppinglist.catalog.activities;

import android.view.View;
import android.widget.ImageView;

import net.binarysailor.shoppinglist.R;
import net.binarysailor.shoppinglist.catalog.model.Product;
import net.binarysailor.shoppinglist.list.model.ProductSelection;

class CatalogAsGridUI extends AbstractCheckboxAndProductNameCatalogUI {

    CatalogAsGridUI(final ProductSelection productSelection) {
        super(productSelection);
    }

    @Override
    protected int getProductLayoutId() {
        return R.layout.product_in_category_cell;
    }
}
