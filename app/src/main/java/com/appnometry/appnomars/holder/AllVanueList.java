package com.appnometry.appnomars.holder;


import com.appnometry.appnomars.model.VanueListModel;

import java.util.Vector;


public class AllVanueList {

    public static Vector<VanueListModel> vanueListModels = new Vector<VanueListModel>();


    public static Vector<VanueListModel> getAllVanueList() {
        return vanueListModels;
    }

    public static void setAllVanueList(Vector<VanueListModel> vanueListModels) {
        AllVanueList.vanueListModels = vanueListModels;
    }

    public static VanueListModel getVanueList(int pos) {
        return vanueListModels.elementAt(pos);
    }

    public static void setVanueList(VanueListModel vanueListModels) {
        AllVanueList.vanueListModels.addElement(vanueListModels);
    }

    public static void removeVanueList() {
        AllVanueList.vanueListModels.removeAllElements();
    }

}
