package net.binarysailor.shoppinglist.catalog.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.binarysailor.shoppinglist.catalog.model.Category;
import net.binarysailor.shoppinglist.catalog.model.Product;
import net.binarysailor.shoppinglist.common.DatabaseUtils;
import net.binarysailor.shoppinglist.list.model.ProductSelection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

public class CatalogDAO {
	private static String[] CATEGORY_COLUMNS = { CatalogContract.Category.ID, CatalogContract.Category.NAME,
			CatalogContract.Category.SORT_ORDER };

	private static String[] PRODUCT_COLUMNS = { CatalogContract.Product.ID, CatalogContract.Product.NAME,
			CatalogContract.Product.CATEGORY_ID };

	private static List<Category> categories;
	private static Map<Integer, Product> productsById;

	private Context context;

	public CatalogDAO(Context context) {
		this.context = context;
	}

	public List<Category> getCategories() {
		assertCategoriesLoaded();
		return CatalogDAO.categories;
	}

	public void createCategory(final Category editedCategory) {
		assertCategoriesLoaded();
		Integer categoryId = (Integer) DatabaseUtils.executeInTransaction(context, new CatalogDatabaseOperations.InsertCategory(
				editedCategory));
		editedCategory.setId(categoryId);
		CatalogDAO.categories.add(editedCategory);
	}

	public Category getCategoryById(int id) {
		assertCategoriesLoaded();
		for (Category c : getCategories()) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	public void updateCategory(final Category editedCategory) {
		for (Category c : getCategories()) {
			if (c.getId() == editedCategory.getId()) {
				DatabaseUtils.executeInTransaction(context, new CatalogDatabaseOperations.UpdateCategory(editedCategory));
				c.setName(editedCategory.getName());
				break;
			}
		}
	}

	public void deleteCategory(final int id) {
		Iterator<Category> c = getCategories().iterator();
		while (c.hasNext()) {
			Category category = c.next();
			if (category.getId() == id) {
				DatabaseUtils.executeInTransaction(context, new CatalogDatabaseOperations.DeleteCategory(id));
				c.remove();
			}
		}
	}

	public void createProduct(final Product editedProduct) {
		Integer productId = (Integer) DatabaseUtils.executeInTransaction(context,
				new CatalogDatabaseOperations.InsertProduct(editedProduct));
		editedProduct.setId(productId);
		if (editedProduct.getCategory() != null) {
			Category category = getCategoryById(editedProduct.getCategory().getId());
			category.addProduct(editedProduct);
		}
		CatalogDAO.productsById.put(productId, editedProduct);
	}

	public Product getProductById(int id) {
		assertCategoriesLoaded();
		return CatalogDAO.productsById.get(id);
	}

	public Collection<Product> getProducts(ProductSelection ps) {
		Collection<Product> result = new LinkedList<Product>();
		for (Integer id : ps.getProductIds()) {
			result.add(getProductById(id));
		}
		return result;
	}

	public void updateProduct(final Product editedProduct) {
		assertCategoriesLoaded();
		Product product = CatalogDAO.productsById.get(editedProduct.getId());
		if (product != null) {
			DatabaseUtils.executeInTransaction(context, new CatalogDatabaseOperations.UpdateProduct(editedProduct));
			product.setName(editedProduct.getName());
		}
	}

	public void deleteProduct(final int id) {
		Product product = getProductById(id);
		if (product != null) {
			product.getCategory().removeProduct(product);
			CatalogDAO.productsById.remove(id);
			DatabaseUtils.executeInTransaction(context, new CatalogDatabaseOperations.DeleteProduct(id));
		}
	}

	public Collection<Product> getAllProducts() {
		assertCategoriesLoaded();
		return CatalogDAO.productsById.values();
	}

	private void assertCategoriesLoaded() {
		if (CatalogDAO.categories == null) {
			loadCategories();
		}
	}

	@SuppressLint("UseSparseArrays")
	private void loadCategories() {
		SQLiteDatabase db = DatabaseUtils.getReadableDatabase(context);
		Cursor cursor = db.query(CatalogContract.Category.TABLE_NAME, CATEGORY_COLUMNS, CatalogContract.Category.ACTIVE + " = 1", null,
				null, null, CatalogContract.Category.SORT_ORDER);
		List<Category> categories = new LinkedList<Category>();
		SparseArray<Category> categoriesById = new SparseArray<Category>();
		boolean dataAvailable = cursor.moveToFirst();
		while (dataAvailable) {
			Category category = EntityFactory.createCategory(cursor);
			categories.add(category);
			categoriesById.put(category.getId(), category);
			dataAvailable = cursor.moveToNext();
		}
		cursor.close();
		CatalogDAO.productsById = new HashMap<Integer, Product>();
		cursor = db.query(CatalogContract.Product.TABLE_NAME, PRODUCT_COLUMNS, CatalogContract.Product.ACTIVE + " = 1", null, null, null,
				CatalogContract.Product.CATEGORY_ID + "," + CatalogContract.Product.SORT_ORDER);
		dataAvailable = cursor.moveToFirst();
		while (dataAvailable) {
			Product product = EntityFactory.createProduct(cursor);
			if (product.getCategory() != null) {
				Category c = categoriesById.get(product.getCategory().getId());
				if (c != null) {
					c.addProduct(product);
				}
			}
			CatalogDAO.productsById.put(product.getId(), product);
			dataAvailable = cursor.moveToNext();
		}
		cursor.close();
		db.close();
		CatalogDAO.categories = categories;
	}
}
