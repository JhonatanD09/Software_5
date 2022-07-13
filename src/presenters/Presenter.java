package presenters;

import exceptions.EmptyPartitionNameException;
import exceptions.EmptyPartitionSizeException;
import exceptions.EmptyProcessNameException;
import exceptions.EmptyProcessSizeException;
import exceptions.EmptyProcessTimeException;
import exceptions.InvalidSizeException;
import exceptions.InvalidTimeException;
import exceptions.RepeatedNameException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import models.Manager;
import models.MyProcess;
import models.Partition;
import views.AddPartitionDialog;
import views.AddProcessDialog;
import views.MainFrame;


public class Presenter implements ActionListener{

    private Manager manager;
    private MainFrame mainFrame;
    private AddPartitionDialog addPartitionDialog;
    private AddProcessDialog addProcessDialog;
    private AddPartitionDialog editPartitionDialog;
    private AddProcessDialog editProcessDialog;
    
    public Presenter(){
        manager = new Manager();
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Events.valueOf(e.getActionCommand())) {
            case ADD_PARTITION:
                manageAddPartitionAction();
                break;
            case CANCEL_ADD_PARTITION:
                manageCancelAddPartitionAction();
                break;
            case ACCEPT_ADD_PARTITION:
                manageAcceptAddPartitionAction();
                break;
            case DELETE_PARTITION:
                manageDeletePartitionAction(e);
                break;
            case EDIT_PARTITION:
                manageEditPartitionAction(e);
                break;
            case CANCEL_EDIT_PARTITION:
                manageCancelEditPartitionAction();
                break;
            case ACCEPT_EDIT_PARTITION:
                manageAcceptEditPartition(e);
                break;
            case ADD_PROCESS:
                manageAddProcessAction();  
                break;
            case CANCEL_ADD_PROCESS:
                manageCancelAddProcessAction();
                break;
            case ACCEPT_ADD_PROCESS:
                manageAcceptAddProcessAction();
                break;
            case DELETE_PROCESS:
                manageDeleteProccessAction(e);
                break;
            case EDIT_PROCESS:
                manageEditProcessAction(e);
                break;
            case CANCEL_EDIT_PROCESS:
                manageCancelEditProcessAction();
                break;
            case ACCEPT_EDIT_PROCESS:
                manageAcceptEditProcessAction(e);
                break;
            case INIT_SIMULATION:
                manageInitSimulationAction();
                break;
            case NEW_SIMULATION:
                manageNewSimulationAction();
                break;
            case EXIT:
                manageExitEvent();
                break;
        }
    }

    private void manageAddProcessAction() {
        addProcessDialog = new AddProcessDialog(this, false);
        addProcessDialog.setVisible(true);
    }
    
    private void manageCancelAddProcessAction() {
        addProcessDialog.dispose();
    }
    
    private void manageAcceptAddPartitionAction() {
        try {
            String name = addPartitionDialog.getPartitionName();
            manager.verifyPartitionName(name);
            long size = addPartitionDialog.getPartitionSize();
            manager.addPartition(name, size);
            mainFrame.updatePartitions(manager.getPartitions());
            addPartitionDialog.dispose();
        } catch (EmptyPartitionNameException | RepeatedNameException | InvalidSizeException |
                EmptyPartitionSizeException ex) {
           JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manageAddPartitionAction() {
        addPartitionDialog = new AddPartitionDialog(this, false);
        addPartitionDialog.setVisible(true);
    }

    private void manageCancelAddPartitionAction() {
        addPartitionDialog.dispose();
    }
    
    private void manageDeletePartitionAction(ActionEvent e) {
        String partitionName = ((JButton) e.getSource()).getName();
        if(manager.deletePartition(partitionName)){
            mainFrame.updatePartitions(manager.getPartitions());      
            JOptionPane.showMessageDialog(mainFrame, "Particion eliminada con exito", "Eliminar",
                                            JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(mainFrame, "No se ha podido eliminar la particion",
                    "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void manageEditPartitionAction(ActionEvent e) {
        String partitionName = ((JButton) e.getSource()).getName();
        Partition partition = manager.searchPartition(partitionName);
        editPartitionDialog = new AddPartitionDialog(this, true);
        editPartitionDialog.setInitialInfo(partition.getName(), partition.getSize());
        editPartitionDialog.setVisible(true);
    }
    
    private void manageCancelEditPartitionAction() {
        editPartitionDialog.dispose();
    }
    
    
    private void manageAcceptEditPartition(ActionEvent e) {
         try {
            String actualName = ((JButton) e.getSource()).getName();
            if(!actualName.equals(editPartitionDialog.getPartitionName())){
                manager.verifyPartitionName(editPartitionDialog.getPartitionName());
            }
            manager.editPartition(actualName, editPartitionDialog.getPartitionName(),
                                        editPartitionDialog.getPartitionSize());
            editPartitionDialog.dispose();
            mainFrame.updatePartitions(manager.getPartitions());
        } catch (EmptyPartitionNameException | EmptyPartitionSizeException | RepeatedNameException |
                 InvalidSizeException ex) {
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void manageAcceptAddProcessAction() {
         try {
            String processName = addProcessDialog.getProcessName();
            manager.verifyProcessName(processName);
            long time = addProcessDialog.getProcessTime();
            long size = addProcessDialog.getProcessSize();
            boolean isBlocked = addProcessDialog.getIsBlocked();
            manager.addProcess(new MyProcess(processName, time, size, isBlocked));
            mainFrame.updateProcesses(manager.getProcesses());
            addProcessDialog.dispose();
        } catch (EmptyProcessNameException | EmptyProcessTimeException | EmptyProcessSizeException |
                 RepeatedNameException | InvalidTimeException | InvalidSizeException ex) {
             System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manageDeleteProccessAction(ActionEvent e) {
        String processName = ((JButton) e.getSource()).getName();
        if(manager.deleteProccess(processName)){
            mainFrame.updateProcesses(manager.getProcesses());
            JOptionPane.showMessageDialog(mainFrame, "Proceso eliminado con exito", "Eliminar",
                                            JOptionPane.INFORMATION_MESSAGE);
        }else{
           JOptionPane.showMessageDialog(mainFrame, "No se ha podido eliminar el proceso",
                    "ERROR!!!", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    private void manageEditProcessAction(ActionEvent e) {
        String processName = ((JButton) e.getSource()).getName();
        MyProcess process = manager.search(processName);
        editProcessDialog = new AddProcessDialog(this, true);
        editProcessDialog.setInitialInfo(process.getName(), String.valueOf(process.getTime()), 
                                            String.valueOf(process.getSize()), process.isLocked());
        editProcessDialog.setVisible(true);
    }
    
    private void manageCancelEditProcessAction() {
       editProcessDialog.dispose();
    }
    
    private void manageAcceptEditProcessAction(ActionEvent e) {
       try{
           String actualName = ((JButton) e.getSource()).getName();
           System.out.println(actualName);
           if(!actualName.equals(editProcessDialog.getProcessName())){
               manager.verifyProcessName(editProcessDialog.getProcessName());
           }
           manager.editProcess(actualName, editProcessDialog.getProcessName(), editProcessDialog.getProcessTime(), 
                                editProcessDialog.getProcessSize(), editProcessDialog.getIsBlocked());
           mainFrame.updateProcesses(manager.getProcesses());
       }catch(EmptyProcessNameException | RepeatedNameException | EmptyProcessSizeException | 
               EmptyProcessTimeException | InvalidSizeException | InvalidTimeException ex){
           JOptionPane.showMessageDialog(mainFrame, ex.getMessage());
       } 
    }  

    private void manageInitSimulationAction() {
         if(!manager.getPartitions().isEmpty()){
            manager.initSimulation();
            ArrayList<Partition> partitionsTermined = manager.getPartitions();
            Collections.sort(partitionsTermined);
            mainFrame.initReportsPanel(manager.getPartitions(), manager.getProcesses(), manager.getProcessesErrors(), manager.getProcessesTermined(),
                    partitionsTermined);
        }else{
            JOptionPane.showMessageDialog(mainFrame, "Debe haber almenos una particion para poder iniciar la simulacion",
                                            "ALERTA", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void manageNewSimulationAction() {
       manager = new Manager();
       mainFrame.newSimulation();
    }
    
    private void manageExitEvent() {
        System.exit(0);
    } 
}
