package SupportClasses;

import javax.swing.table.DefaultTableModel;

public class TableFactory {

    public DefaultTableModel getTableModel(String table){

        DefaultTableModel model;

        switch(table)
        {
            case "MainView" : model = new CatalogTableModel();
                break;
            case "CartView" : model = new CartTableModel();
                break;
            default : model = new DefaultTableModel();
                break;
        }

        return model;
    }
}
