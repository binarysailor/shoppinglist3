package net.binarysailor.shoppinglist;

import net.binarysailor.shoppinglist.list.model.ProductSelection;

public class ApplicationSession {

    private static ApplicationSession currentSession = new ApplicationSession();

    private final ProductSelection productSelection = new ProductSelection();

    private ApplicationSession() {
    }

    public static ApplicationSession getCurrentSession() {
        return ApplicationSession.currentSession;
    }

    public ProductSelection getProductSelection() {
        return productSelection;
    }
}
