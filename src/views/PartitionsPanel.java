package views;

import models.Partition;
import presenters.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PartitionsPanel extends MyGridPanel {

    private static final String TITLE = "Lista de Particiones";
    private static final String TXT_ADD_PARTITION_BTN = "Agregar Particion";
    private JPanel partitions;
    private JScrollPane scrollPane;
    private ActionListener listener;
    private JButton addPartitionBtn;
    private ArrayList<PartitionPanel> partitionPanels;

    public PartitionsPanel(ActionListener listener) {
        partitionPanels = new ArrayList<>();
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        setBackground(Color.decode("#FDFEFE"));
        initComponents(listener);
    }

    private void initComponents(ActionListener listener){
        scrollPane = new JScrollPane();
        this.listener = listener;
        addTitle();
        initProcessesPanel();
        initAddPartitionBtn();
    }


    public void addTitle(){
        JLabel titleLb = new JLabel(TITLE);
        titleLb.setOpaque(true);
        titleLb.setBackground(Color.decode("#16A085"));
        titleLb.setForeground(Color.WHITE);
        titleLb.setFont(new Font("Arial", Font.BOLD, 20));
        titleLb.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLb, 0, 0, 12, 0.03);
    }

    private void initProcessesPanel(){
        partitions = new JPanel(new GridLayout(1,1));
        partitions.setBackground(Color.decode("#FDFEFE"));
        for (int i = 0; i < 10; i++) {
            partitions.add(new JLabel(" "));
        }
        scrollPane.add(partitions);
        addComponent(partitions, 0, 1, 12, 0.8);
    }

    public void updatePartitions(ArrayList<Partition> partitionsList){
        removeAll();
        addTitle();
        partitions = new JPanel(new GridLayout(partitionsList.size(), 1));
        verifyRowsNumber(partitionsList);
        scrollPane = new JScrollPane(partitions);
        addComponent(scrollPane, 0, 1, 12, 0.8);
        initAddPartitionBtn();
        updateUI();
    }

    private void verifyRowsNumber(ArrayList<Partition> partitionsList){
        if(partitionsList.size() < 10){
            partitions = new JPanel(new GridLayout(10, 1, 5, 5));
            partitions.setBackground(Color.decode("#FDFEFE"));
            addPartitions(partitionsList);
            for (int i = 0; i < (8 - partitionsList.size()); i++) {
                partitions.add(new JLabel(" "));
            }
        }else{
            partitions = new JPanel(new GridLayout(partitionsList.size(), 1, 5,5));
            partitions.setBackground(Color.decode("#FDFEFE"));
            addPartitions(partitionsList);
        }
    }

    private void addPartitions(ArrayList<Partition> partitionsList){
        for(Partition partition : partitionsList){
            PartitionPanel partitionPanel = new PartitionPanel(partition, listener);
            partitions.add(partitionPanel);
            partitionPanels.add(partitionPanel);
        }
    }

    public void initAddPartitionBtn(){
        addPartitionBtn = new JButton(TXT_ADD_PARTITION_BTN);
        addPartitionBtn.setBackground(Color.decode("#2980B9"));
        addPartitionBtn.setForeground(Color.WHITE);
        addPartitionBtn.setFont(new Font("Arial", Font.BOLD, 16));
        addPartitionBtn.setPreferredSize(new Dimension(500, 40));
        addPartitionBtn.addActionListener(listener);
        addPartitionBtn.setActionCommand(Events.ADD_PARTITION.toString());
        addComponent(addPartitionBtn, 0, 3, 12, 0.01);
    }
    
    public void disableBtn(){
        addPartitionBtn.setEnabled(false);
        for(PartitionPanel partitionPanel : partitionPanels){
            partitionPanel.disableBtns();
        }
        updateUI();
    }
}
