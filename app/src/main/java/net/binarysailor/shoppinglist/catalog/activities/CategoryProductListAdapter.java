package net.binarysailor.shoppinglist.catalog.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.binarysailor.shoppinglist.catalog.model.Category;
import net.binarysailor.shoppinglist.catalog.model.Product;

public class CategoryProductListAdapter extends BaseAdapter {

    private class ProductOnClickListener implements View.OnClickListener {

        private final @Nullable
        CatalogListener catalogListener;

        ProductOnClickListener(final @Nullable CatalogListener catalogListener) {
            this.catalogListener = catalogListener;
        }

        @Override
        public void onClick(final View v) {
            final Product product = (Product) v.getTag();
            if (catalogListener != null) {
                catalogListener.onProductClicked(v, product);
            }
            notifyDataSetChanged();
        }
    }

    private final Context context;
    private final Category category;
    private final Product[] products;
    private final CatalogUI catalogUI;
    private final ProductOnClickListener productOnClickListener;

    public CategoryProductListAdapter(final Context context, final @NonNull CatalogSetup catalogSetup, final Category category) {
        this.context = context;
        this.category = category;
        this.products = category.getProducts().toArray(new Product[0]);
        this.catalogUI = catalogSetup.getCatalogUI();
        this.productOnClickListener = new ProductOnClickListener(catalogSetup.getCatalogListener());
    }

    @Override
    public int getCount() {
        return products.length;
    }

    @Override
    public Object getItem(int position) {
        return products[position];
    }

    @Override
    public long getItemId(int position) {
        return products[position].getId();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final Product product = (Product)getItem(position);

        final View result;
        if (convertView != null) {
            result = convertView;
        } else {
            result = catalogUI.createProductView(context, parent, product);
        }
        result.setTag(product);

        catalogUI.styleProductView(result);

        result.setOnClickListener(productOnClickListener);

        return result;
    }
}
