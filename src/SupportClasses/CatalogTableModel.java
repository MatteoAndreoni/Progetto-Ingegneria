package SupportClasses;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class CatalogTableModel extends DefaultTableModel {

    public CatalogTableModel()
    {
        super(null, new String[]{"Immagine", "Titolo", "Prezzo", "Autore", "Genere"});
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

}
