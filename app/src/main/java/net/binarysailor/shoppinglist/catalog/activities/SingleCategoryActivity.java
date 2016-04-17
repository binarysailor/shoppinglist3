package net.binarysailor.shoppinglist.catalog.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import net.binarysailor.shoppinglist.ApplicationSession;
import net.binarysailor.shoppinglist.R;
import net.binarysailor.shoppinglist.catalog.model.Category;
import net.binarysailor.shoppinglist.catalog.model.Product;
import net.binarysailor.shoppinglist.list.model.ProductSelection;

public class SingleCategoryActivity extends Activity implements CatalogSetup, CatalogListener {

    private ProductSelection productSelection;
    private CatalogUI catalogUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_category);

        final IntentStorage storage = new IntentStorage(getIntent());
        productSelection = ApplicationSession.getCurrentSession().getProductSelection();
        final Category category = storage.getCategory();

        catalogUI = new SingleCategoryUI(productSelection);

        TextView categoryName = (TextView) findViewById(R.id.category_header);
        categoryName.setText(category.getName());

        ListView products = (ListView) findViewById(R.id.category_products);
        products.setAdapter(new CategoryProductListAdapter(this, this, category));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SCA", "onStop called. This is where we'll be persisting the product selection");
    }

    @Override
    public CatalogListener getCatalogListener() {
        return this;
    }

    @Override
    public CatalogUI getCatalogUI() {
        return catalogUI;
    }

    @Override
    public void onCategoryHeaderClicked(final View categoryView, final Category category) {
    }

    @Override
    public void onProductClicked(final View productView, final Product product) {
        productSelection.toggle(product.getId());
    }
}
