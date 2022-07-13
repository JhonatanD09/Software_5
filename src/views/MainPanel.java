package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import models.MyProcess;
import models.Partition;
import presenters.Events;


public class MainPanel extends JPanel{

    public static final String EXIT_BTN_TXT = "Salir";
    private static final Color WHITE_COLOR = Color.decode("#FDFEFE");
    public static final Color RED_COLOR = Color.decode("#34495E");
    private ActionListener actionListener;
    private ProcessesPanel processesPanel;
    private PartitionsPanel partitionsPanel;
    private JPanel centerPanel;
    private JPanel startSimulationPanel;
    
    public MainPanel(ActionListener actionListener){
        this.actionListener = actionListener;
        setLayout(new BorderLayout());
        centerPanel = new JPanel(new GridLayout(1, 2));
        setBackground(WHITE_COLOR);
        initExitBtn();
        initCenterPanel();
        initStartSimulationPanel();
    }
    
    private void initExitBtn(){
        MyGridPanel exitBtnPanel = new MyGridPanel();
        exitBtnPanel.setBackground(RED_COLOR);
        JButton exitBtn = createBtn(EXIT_BTN_TXT, Color.decode("#DE1D2C"), actionListener, Events.EXIT.toString());
        exitBtnPanel.addComponentWithInsets(exitBtn, 11, 1, 1, 0.1, new Insets(5,0,5,0));
        add(exitBtnPanel, BorderLayout.NORTH);
    }
    
    private void initCenterPanel(){
        processesPanel = new ProcessesPanel(actionListener);
        centerPanel.add(processesPanel);
        partitionsPanel = new PartitionsPanel(actionListener);
        centerPanel.add(partitionsPanel);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void initStartSimulationPanel(){
        startSimulationPanel = new JPanel(new BorderLayout());
        JButton startSimulationBtn = createBtn("Iniciar Simulacion", Color.decode("#2980B9"),
                actionListener, Events.INIT_SIMULATION.toString());
        startSimulationPanel.setBackground(Color.decode("#FDFEFE"));
        startSimulationPanel.add(startSimulationBtn, BorderLayout.CENTER);
        add(startSimulationPanel, BorderLayout.SOUTH);
    }
    
    private JButton createBtn(String txt, Color color, ActionListener listener, String command){
        JButton btn = new JButton(txt);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.addActionListener(listener);
        btn.setActionCommand(command);
        return btn;
    }
    
    public void initReportsPanel(ArrayList<Partition> partitions,  ArrayList<MyProcess> processes, 
                                ArrayList<MyProcess> processesErrors, ArrayList<MyProcess> processesTermined,
                                ArrayList<Partition> partitionsTermined){
        centerPanel.removeAll();
        remove(startSimulationPanel);
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.add(partitionsPanel);
        leftPanel.add(processesPanel);
        partitionsPanel.disableBtn();
        centerPanel.add(leftPanel);
        ReportsPanel reportsPanel = new ReportsPanel(actionListener, partitions, processes, processesErrors, processesTermined,
                                                     partitionsTermined);
        centerPanel.add(reportsPanel);
        add(centerPanel);
        updateUI();
    }
    
    public void updateProcesses(ArrayList<MyProcess> processQueue){
        processesPanel.updateProcesses(processQueue);
    }
    
    public void updatePartitions(ArrayList<Partition> partitions){
        partitionsPanel.updatePartitions(partitions);
    }
}
