package net.binarysailor.shoppinglist.catalog.activities;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.binarysailor.shoppinglist.R;
import net.binarysailor.shoppinglist.catalog.model.Category;

import java.util.List;

class CategoryListAdapter extends BaseAdapter {

    private static class CategoryHeaderOnClickListener implements View.OnClickListener {

        private final @Nullable CatalogListener catalogListener;

        CategoryHeaderOnClickListener(final @Nullable CatalogListener catalogListener) {
            this.catalogListener = catalogListener;
        }

        @Override
        public void onClick(final View v) {
            View cell = (View)v.getParent();
            final Category category = (Category) cell.getTag();
            if (catalogListener != null) {
                catalogListener.onCategoryHeaderClicked(v, category);
            }
        }
    }

    private final Context context;
    private final @NonNull CatalogSetup catalogSetup;
    private final List<Category> categories;
    private final CategoryHeaderOnClickListener categoryHeaderOnclickListener;

    public CategoryListAdapter(final Context context, final @NonNull CatalogSetup catalogSetup, final List<Category> categories) {
        this.context = context;
        this.catalogSetup = catalogSetup;
        this.categories = categories;
        this.categoryHeaderOnclickListener = new CategoryHeaderOnClickListener(catalogSetup.getCatalogListener());
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ((Category)getItem(i)).getId();
    }

    @Override
    public View getView(final int i, final View view, final ViewGroup viewGroup) {
        if (i >= categories.size()) {
            return null;
        }

        final Category category = categories.get(i);

        final View result;
        if (view != null) {
            result = view;
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            result = inflater.inflate(R.layout.category_cell, viewGroup, false);
        }
        result.setBackgroundResource(R.drawable.cell_bckgrnd);
        //result.setBackgroundColor(Color.argb(150, 200, 200, 255));
        result.setTag(category);

        final TextView categoryHeader = (TextView) result.findViewById(R.id.category_header);
        categoryHeader.setText(category.getName());
        categoryHeader.setOnClickListener(categoryHeaderOnclickListener);

        //final TextView debugTextView = (TextView) result.findViewById(R.id.category_header_debug);
        //debugTextView.setText(formatTextDimensions(categoryHeader));

        final ListView categoryProducts = (ListView) result.findViewById(R.id.category_products);
        categoryProducts.setAdapter(new CategoryProductListAdapter(context, catalogSetup, category));

        return result;
    }

    private String formatTextDimensions(TextView textView) {
        CharSequence text = textView.getText();
        Rect bounds = new Rect();
        textView.getPaint().getTextBounds(text.toString(), 0, text.length(), bounds);
        return bounds.width() + " x " + bounds.height();
    }
}
