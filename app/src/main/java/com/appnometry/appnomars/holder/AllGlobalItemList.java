package com.appnometry.appnomars.holder;


import com.appnometry.appnomars.model.GlobalItemListModel;

import java.util.Vector;


public class AllGlobalItemList {

    public static Vector<GlobalItemListModel> globalItemListModels = new Vector<GlobalItemListModel>();


    public static Vector<GlobalItemListModel> getAllNewsFeedList() {
        return globalItemListModels;
    }

    public static void setAllNewsFeedList(Vector<GlobalItemListModel> globalItemListModels) {
        AllGlobalItemList.globalItemListModels = globalItemListModels;
    }

    public static GlobalItemListModel getNewsFeedList(int pos) {
        return globalItemListModels.elementAt(pos);
    }

    public static void setNewsFeedList(GlobalItemListModel globalItemListModels) {
        AllGlobalItemList.globalItemListModels.addElement(globalItemListModels);
    }

    public static void removeNewsFeedList() {
        AllGlobalItemList.globalItemListModels.removeAllElements();
    }

}
