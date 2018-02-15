package SupportClasses;

import javax.swing.table.DefaultTableModel;

public class CartTableModel extends DefaultTableModel{

    public CartTableModel(){
        super(null,
                new String[]{
                    "Immagine", "Titolo", "Prezzo", "Autore", "Genere"
                });
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }


    @Override
    public boolean isCellEditable(int row, int column) {
        if(column==5)
            return true;
        return false;
    }


}
