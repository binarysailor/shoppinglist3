package net.binarysailor.shoppinglist.catalog.activities;

import net.binarysailor.shoppinglist.R;
import net.binarysailor.shoppinglist.list.model.ProductSelection;

class SingleCategoryUI extends AbstractCheckboxAndProductNameCatalogUI {

    public SingleCategoryUI(final ProductSelection productSelection) {
        super(productSelection);
    }

    @Override
    protected int getProductLayoutId() {
        return R.layout.product_in_list;
    }
}
