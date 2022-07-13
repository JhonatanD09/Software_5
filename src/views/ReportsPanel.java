package views;

import models.Partition;
import presenters.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import models.Manager;
import models.MyProcess;

public class ReportsPanel extends JPanel {

    private static final String[] COLUMNS = {"Nombre", "Tiempo", "Tamaño", "Bloqueo"};
     private static final String[] TERMINED_COLUMNS = {"Nombre", "Tiempo", "Tamaño", "Bloqueo"};
    private static final String[] OVER_SIZE_COLUMNS = {"Nombre", "Descripcion"};
    private static final String NEW_SIMULATION_BTN_TXT = "Nueva simulacion";
    private static final Color BLUE_COLOR = Color.decode("#2980B9");

    public ReportsPanel(ActionListener listener, ArrayList<Partition> partitions, ArrayList<MyProcess> processes,  
            ArrayList<MyProcess> processesErrors, ArrayList<MyProcess> processesTermined, 
            ArrayList<Partition> partitionsTermined){
        setLayout(new BorderLayout());
        setBackground(Color.decode("#FDFEFE"));
        initTitle();
        addReports(partitions, processes, processesErrors, processesTermined, partitionsTermined);
        initNewSimulationBtn(listener);
    }

    public void addReports(ArrayList<Partition> partitions, ArrayList<MyProcess> processes, ArrayList<MyProcess> processesErrors, 
            ArrayList<MyProcess> processesTermined, ArrayList<Partition> partitionsTermined){
        JTabbedPane reports = new JTabbedPane();
        reports.setFont(new Font("Arial", Font.BOLD, 18));
        for(Partition partition : partitions){
            PartitionReportsPanel partitionReportsPanel = new PartitionReportsPanel(partition.getReadyProccess(),
                    partition.getProcessDespachados(), partition.getExecuting(),
                    partition.getProcessExpired(), partition.getProcessToLocked(),
                    partition.getProcessLocked(), partition.getProcessWakeUp(),
                    partition.getProcessTerminated(), partition.getOverSize());
            reports.add(partitionReportsPanel, partition.getName());
        }
        TablePanel terminedProcessesTable = new TablePanel(Manager.processProcessTermiedInfo(processesTermined), TERMINED_COLUMNS);
        reports.add("Terminacion tiempo procesos", terminedProcessesTable);
        TablePanel terminedPartitionsTable = new TablePanel(Manager.processPartitionsTermiedInfo(partitionsTermined), TERMINED_COLUMNS);
        reports.add("Terminacion tiempo particiones", terminedPartitionsTable);
        TablePanel reportProcessesPanel = new TablePanel(Partition.processInfo(processes), COLUMNS);
        reports.add("Listos", reportProcessesPanel);
        TablePanel reportOverSizePanel = new TablePanel(Partition.processOverSizeInfo(processesErrors), OVER_SIZE_COLUMNS);
        reports.add("Tamaño exedido", reportOverSizePanel);
        add(reports, BorderLayout.CENTER);
    }
    

    private void initNewSimulationBtn(ActionListener listener){
        JButton newSimulationBtn = new JButton(NEW_SIMULATION_BTN_TXT);
        newSimulationBtn.setFont(new Font("Arial", Font.BOLD, 20));
        newSimulationBtn.setForeground(Color.WHITE);
        newSimulationBtn.setBackground(BLUE_COLOR);
        newSimulationBtn.addActionListener(listener);
        newSimulationBtn.setActionCommand(Events.NEW_SIMULATION.toString());
        add(newSimulationBtn, BorderLayout.SOUTH);
    }

    private void initTitle(){
        JLabel titleLb = new JLabel("REPORTES");
        titleLb.setFont(new Font("Arial", Font.BOLD, 16));
        titleLb.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLb, BorderLayout.NORTH);
    }
}
