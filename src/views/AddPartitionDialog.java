package views;

import exceptions.EmptyPartitionNameException;
import exceptions.EmptyPartitionSizeException;
import exceptions.InvalidSizeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddPartitionDialog extends JDialog {

    private AddPartitionPanel addPartitionPanel;

    public AddPartitionDialog(ActionListener actionListener, boolean isEditing){
        setInfo();
        addPartitionPanel = new AddPartitionPanel(actionListener, isEditing);
        add(addPartitionPanel, BorderLayout.CENTER);
    }

    private void setInfo(){
        setSize(400, 300);
        setModal(true);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
    }

    public String getPartitionName() throws EmptyPartitionNameException {
        return addPartitionPanel.getPartitionName();
    }

    public long getPartitionSize() throws EmptyPartitionSizeException, InvalidSizeException {
        return addPartitionPanel.getPartitionSize();
    }

    public void setInitialInfo(String name, long size){
        addPartitionPanel.setInitialInfo(name, size);
    }
}