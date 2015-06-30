package com.appnometry.appnomars.holder;


import com.appnometry.appnomars.model.ShoppingCartListModel;

import java.util.Vector;


public class AllShoppingCartList {

    public static Vector<ShoppingCartListModel> shoppingCartListModels = new Vector<ShoppingCartListModel>();


    public static Vector<ShoppingCartListModel> getAllShoppingList() {
        return shoppingCartListModels;
    }

    public static void setAllShoppingList(Vector<ShoppingCartListModel> shoppingCartListModels) {
        AllShoppingCartList.shoppingCartListModels = shoppingCartListModels;
    }

    public static ShoppingCartListModel getShoppingList(int pos) {
        return shoppingCartListModels.elementAt(pos);
    }

    public static void setShoppingList(ShoppingCartListModel shoppingCartListModels) {
        AllShoppingCartList.shoppingCartListModels.addElement(shoppingCartListModels);
    }

    public static void removeShoppingList() {
        AllShoppingCartList.shoppingCartListModels.removeAllElements();
    }

}
