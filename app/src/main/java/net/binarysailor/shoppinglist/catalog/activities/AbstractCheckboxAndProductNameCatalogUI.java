package net.binarysailor.shoppinglist.catalog.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.binarysailor.shoppinglist.R;
import net.binarysailor.shoppinglist.catalog.model.Product;
import net.binarysailor.shoppinglist.list.model.ProductSelection;

abstract class AbstractCheckboxAndProductNameCatalogUI implements CatalogUI {

    private final ProductSelection productSelection;

    protected AbstractCheckboxAndProductNameCatalogUI(final ProductSelection productSelection) {
        this.productSelection = productSelection;
    }

    @Override
    public View createProductView(final Context context, final ViewGroup parent, final Product product) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View productView = inflater.inflate(getProductLayoutId(), parent, false);

        getProductNameView(productView).setText(product.getName());

        return productView;
    }

    @Override
    public void styleProductView(final View productView) {
        final Product product = getProductFromProductView(productView);
        if (productSelection.isSelected(product.getId())) {
            activateStatusImage(productView);
        } else {
            deactivateStatusImage(productView);
        }
    }

    protected void deactivateStatusImage(final View productView) {
        final ImageView statusImageView = getStatusImageView(productView);
        statusImageView.setVisibility(View.INVISIBLE);
    }

    protected void activateStatusImage(final View productView) {
        final ImageView statusImageView = getStatusImageView(productView);
        statusImageView.setVisibility(View.VISIBLE);
    }

    private TextView getProductNameView(final View productView) {
        return (TextView) productView.findViewById(getProductNameViewId());
    }

    private ImageView getStatusImageView(final View productView) {
        return (ImageView) productView.findViewById(getStatusImageViewId());
    }

    protected Product getProductFromProductView(final View productView) {
        return (Product) productView.getTag();
    }

    protected int getProductNameViewId() {
        return R.id.product_name;
    }

    protected int getStatusImageViewId() {
        return R.id.status_image;
    }

    abstract protected int getProductLayoutId();
}
