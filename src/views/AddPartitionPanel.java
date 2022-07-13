package views;

import exceptions.EmptyPartitionNameException;
import exceptions.EmptyPartitionSizeException;
import exceptions.InvalidSizeException;
import presenters.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddPartitionPanel extends MyGridPanel{

    private static final String TITLE = "Nueva Particion";
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final String TXT_PARTITION_NAME_LB = "Nombre: ";
    private static final Font FONT_PARTITION_NAME_LB = new Font("Arial", Font.PLAIN, 12);
    private static final String TXT_PARTITION_SIZE_LB = "Tamaño: ";
    private static final Color WHITE_COLOR = Color.decode("#FDFEFE");
    private JTextField partitionNameTxt;
    private JTextField partitionSizeTxt;
    private JButton addBtn;

    public AddPartitionPanel(ActionListener actionListener, boolean isEditing){
        setBackground(WHITE_COLOR);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        initComponents(actionListener, isEditing);
    }

    private void initComponents(ActionListener actionListener, boolean isEditing){
        initTitle();
        initPartitionNameTxt();
        initPartitionSizeTxt();
        if (isEditing){
            initButtons(actionListener, Events.ACCEPT_EDIT_PARTITION.toString(), Events.CANCEL_EDIT_PARTITION.toString(),
                    isEditing);
        }else{
            initButtons(actionListener, Events.ACCEPT_ADD_PARTITION.toString(), Events.CANCEL_ADD_PARTITION.toString(),
                    isEditing);
        }
    }

    private void initTitle(){
        JLabel titleLb = createLb(TITLE, TITLE_FONT);
        titleLb.setHorizontalTextPosition(SwingConstants.CENTER);
        addComponent(new JLabel(" "), 0,0,12, 0.05);
        addComponent(titleLb, 4,1,4,0.1);
        addComponent(new JLabel(" "), 0,2,12, 0.05);
    }

    private void initPartitionNameTxt(){
        JLabel partitionNameLb = createLb(TXT_PARTITION_NAME_LB, FONT_PARTITION_NAME_LB);
        addComponent(partitionNameLb, 2,3,1,0.1);
        partitionNameTxt = new JTextField();
        addComponent(partitionNameTxt, 4, 3, 6, 0.1);
        addComponent(new JLabel(" "), 0,4,12, 0.05);
    }

    private void initPartitionSizeTxt(){
        JLabel partitionSizeLb = createLb(TXT_PARTITION_SIZE_LB, FONT_PARTITION_NAME_LB);
        addComponent(partitionSizeLb, 2,5,1,0.1);
        partitionSizeTxt = new JTextField();
        addComponent(partitionSizeTxt, 4, 5, 6, 0.1);
        addComponent(new JLabel(" "), 0,6,12, 0.05);
    }

    private void initButtons(ActionListener listener, String acceptEvent, String cancelEvent, boolean isEditing){
        String addBtnTxt = isEditing ? "Editar" : "Agregar";
        addBtn = createBtn(addBtnTxt, Color.decode("#00D48B"), listener, acceptEvent);
        addComponent(addBtn, 3, 7, 2, 0.1);
        JButton cancelBtn = createBtn("Cancelar", Color.decode("#FA512D"), listener, cancelEvent);
        addComponent(cancelBtn, 7, 7, 2, 0.1);
        addComponent(new JLabel(" "), 0, 8, 12, 0.05);
    }

    private JLabel createLb(String txt, Font font){
        JLabel lb = new JLabel(txt);
        lb.setFont(font);
        return lb;
    }

    private JButton createBtn(String txt, Color color, ActionListener listener, String command){
        JButton btn = new JButton(txt);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.addActionListener(listener);
        btn.setActionCommand(command);
        return btn;
    }

    public String getPartitionName() throws EmptyPartitionNameException {
        if(!partitionNameTxt.getText().isEmpty()){
            return partitionNameTxt.getText();
        }else{
            throw new EmptyPartitionNameException();
        }
    }

    public long getPartitionSize() throws EmptyPartitionSizeException, InvalidSizeException {
        String size = partitionSizeTxt.getText();
        if(!size.isEmpty()){
            boolean isNumber = size.chars().allMatch(Character::isDigit);
            if(isNumber){
                return Long.parseLong(size);
            }else{
                throw new InvalidSizeException();
            }
        }else {
            throw new EmptyPartitionSizeException();
        }
    }

    public void setInitialInfo(String name, long size){
        partitionNameTxt.setText(name);
        partitionSizeTxt.setText(String.valueOf(size));
        addBtn.setName(name);
    }
}
