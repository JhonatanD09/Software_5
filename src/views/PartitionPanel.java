package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import models.Partition;
import presenters.Events;


public class PartitionPanel extends MyGridPanel{
    
    private static final String LB_NAME_TXT = "Nombre: ";
    private static final String LB_SIZE_TXT = "Tamaño: ";
    private static final String EDIT_BTN_TXT = "Editar";
    private static final String DELETE_BTN_TXT = "Eliminar";
    private JButton editBtn;
    private JButton deleteButton;

    public PartitionPanel(Partition process ,ActionListener listener){
        setBackground(Color.decode("#FDFEFE"));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        initComponents(process, listener);
    }

    private void initComponents(Partition partition, ActionListener listener){
        addComponent(new JLabel(" "), 0, 0, 12, 0.2);
        JLabel lbName = new JLabel(LB_NAME_TXT + partition.getName());
        lbName.setFont(new Font("Arial", Font.BOLD, 16));
        addComponent(lbName, 1, 1, 1, 1);

        JLabel lbSize = new JLabel(LB_SIZE_TXT + partition.getSize());
        lbSize.setFont(new Font("Arial", Font.BOLD, 16));
        addComponent(lbSize, 3,1,1,1);

        initButtons(listener, partition.getName());

        addComponent(new JLabel(" "), 0, 2, 12, 0.2);
    }

    private void initButtons(ActionListener listener, String partitionName){
        String info = partitionName;
        editBtn = createBtn(EDIT_BTN_TXT, Color.BLUE, listener, Events.EDIT_PARTITION.toString(), info);
        addComponent(editBtn, 8,1,1,0.5);
        deleteButton = createBtn(DELETE_BTN_TXT, Color.RED, listener, Events.DELETE_PARTITION.toString(), info);
        addComponent(deleteButton, 10, 1,1,0.5);
    }

    private JButton createBtn(String txt, Color color, ActionListener listener, String command, String info){
        JButton btn = new JButton(txt);
        btn.setName(info);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.addActionListener(listener);
        btn.setActionCommand(command);
        return btn;
    }
    
    public void disableBtns(){
        editBtn.setEnabled(false);
        deleteButton.setEnabled(false);
    }
}