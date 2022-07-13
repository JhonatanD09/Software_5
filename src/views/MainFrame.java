package views;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import models.MyProcess;
import models.Partition;


public class MainFrame extends JFrame{

    private static final String TITLE = "Software 5";
    
    private MainPanel mainPanel;
    private ActionListener actionListener;
    
    public MainFrame(ActionListener actionListener){
        this.actionListener = actionListener;
        setUndecorated(true);
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        initMainPanel(actionListener);
    }
    
    public void updateProcesses(ArrayList<MyProcess> processQueue){
        mainPanel.updateProcesses(processQueue);
    }
    
    public void updatePartitions(ArrayList<Partition> partitions){
        mainPanel.updatePartitions(partitions);
    }
    
    public void initMainPanel(ActionListener actionListener){
        mainPanel = new MainPanel(actionListener);
        add(mainPanel);
    }
    
    public void initReportsPanel(ArrayList<Partition> partitions,  ArrayList<MyProcess> processes, ArrayList<MyProcess> processesErrors,
                                ArrayList<MyProcess> processesTermined, ArrayList<Partition> partitionsTermined){
        mainPanel.initReportsPanel(partitions, processes, processesErrors, processesTermined, partitionsTermined);
    }
    
    public void newSimulation(){
        getContentPane().remove(mainPanel);
        mainPanel = new MainPanel(actionListener);
        add(mainPanel);
        getContentPane().revalidate();
    }
}
