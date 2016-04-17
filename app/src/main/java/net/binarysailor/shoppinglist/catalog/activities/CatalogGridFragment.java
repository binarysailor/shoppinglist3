package net.binarysailor.shoppinglist.catalog.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import net.binarysailor.shoppinglist.R;
import net.binarysailor.shoppinglist.catalog.model.Category;
import net.binarysailor.shoppinglist.catalog.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CatalogGridFragment extends Fragment {

    private List<Category> categories;
    private CatalogSetup catalogSetup;
    private CategoryListAdapter categoryListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //categories = new CatalogDAO(getActivity()).getCategories();
        categories = dummyCategories();
    }

    public void setCatalogSetup(final @NonNull CatalogSetup catalogSetup) {
        this.catalogSetup = catalogSetup;
    }

    private List<Category> dummyCategories() {
        final int count = 15;
        Random random = new Random();
        final List<Category> result = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            final Category category = new Category();
            category.setId(i);
            category.setName("Kategoria " + i);

            int productCount = random.nextInt(10);
            for (int p  = 0; p < productCount; p++) {
                Product product = new Product();
                product.setId(100 * i + p);
                product.setName(String.format("Produkt %d - %d", i, p));
                category.addProduct(product);
            }
            result.add(category);
        }
        return result;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        if (catalogSetup == null) {
            throw new IllegalStateException("Please set a catalogSetup object before attaching the CatalogGridFragment");
        }
        categoryListAdapter = new CategoryListAdapter(getActivity(), catalogSetup, categories);

        View layout = inflater.inflate(R.layout.category_grid, container, false);
        GridView gridView = (GridView) layout.findViewById(R.id.category_grid);
        gridView.setNumColumns(2);
        gridView.setAdapter(categoryListAdapter);

        TextView textView = (TextView) layout.findViewById(R.id.debugText);
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        textView.setText(String.format("Width = %d, Height = %d", metrics.widthPixels, metrics.heightPixels));

        return layout;
    }

    public void notifyDataSetChanged() {
        categoryListAdapter.notifyDataSetChanged();
    }
}
