package net.binarysailor.shoppinglist.catalog.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.binarysailor.shoppinglist.ApplicationSession;
import net.binarysailor.shoppinglist.R;
import net.binarysailor.shoppinglist.catalog.model.Category;
import net.binarysailor.shoppinglist.catalog.model.Product;
import net.binarysailor.shoppinglist.list.model.ProductSelection;

public class CatalogAsGridActivity extends Activity implements CatalogListener, CatalogSetup {

    private ProductSelection productSelection;
    private CatalogUI catalogUI;
    private CatalogGridFragment catalogGrid;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.productSelection = ApplicationSession.getCurrentSession().getProductSelection();
        this.catalogUI =  new CatalogAsGridUI(productSelection);

        setContentView(R.layout.catalog_as_grid);

        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        catalogGrid = new CatalogGridFragment();
        catalogGrid.setCatalogSetup(this);
        fragmentTransaction.replace(R.id.categories, catalogGrid);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // repaint the grid in case eg. product selection has changed
        catalogGrid.notifyDataSetChanged();
    }

    @Override
    public void onCategoryHeaderClicked(final View categoryView, final Category category) {
        final Intent intent = new Intent(this, SingleCategoryActivity.class);

        final IntentStorage storage = new IntentStorage(intent);
        storage.putCategory(category);

        startActivity(intent);
    }

    @Override
    public void onProductClicked(final View productView, final Product product) {
        productSelection.toggle(product.getId());
    }

    @Override
    public CatalogListener getCatalogListener() {
        return this;
    }

    @Override
    public CatalogUI getCatalogUI() {
        return catalogUI;
    }
}
