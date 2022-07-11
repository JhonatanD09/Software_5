package models;

import exceptions.RepeatedNameException;

import java.util.ArrayList;

public class Manager {

	private ArrayList<Partition> partitions;
	private ArrayList<MyProcess> processes;
	private ArrayList<MyProcess> processesErrors;
	private Queue<MyProcess> processQueueReady;

	public Manager() {
		this.processQueueReady = new Queue<>();
		partitions = new ArrayList<>();
		this.processes = new ArrayList<MyProcess>();
		this.processesErrors = new ArrayList<>();
	}

	public void addPartition(String name, int size) {
		partitions.add(new Partition(name, size, this.processQueueReady, this.processes));
	}

	public boolean editPartition(String name, String newName, int size) {
		Partition partition = searchPartition(name);
		if (partition != null) {
			partition.setName(newName);
			partition.setSize(size);
			return true;
		} else {
			return false;
		}
	}

	public Partition searchPartition(String name) {
		for (Partition partition : partitions) {
			if (partition.getName().equalsIgnoreCase(name)) {
				return partition;
			}
		}
		return null;
	}

	public void verifyPartitionName(String partitionName) throws RepeatedNameException {
		for (Partition partition : partitions) {
			if (partition.getName().equalsIgnoreCase(partitionName)) {
				throw new RepeatedNameException(partitionName);
			}
		}
	}

	public void verifyProcessName(String processName) throws RepeatedNameException {
		for (Partition partition : partitions) {
			partition.verifyProcessName(processName);
		}
	}

	public boolean deletePartition(String name) {
		Partition partition = searchPartition(name);
		if (partition != null) {
			partitions.remove(partition);
			return true;
		} else {
			return false;
		}
	}

	public boolean addProcess(MyProcess myProcess) {
		if (search(myProcess.getName()) == null) {
			processes.add(
					new MyProcess(myProcess.getName(), myProcess.getTime(), myProcess.getSize(), myProcess.isLocked()));
			processQueueReady.push(myProcess);
			return true;
		}
		return false;
	}

	private MyProcess searchInList(String name, ArrayList<MyProcess> myProcesses) {
		for (MyProcess myProcess : myProcesses) {
			if (name.equals(myProcess.getName())) {
				return myProcess;
			}
		}
		return null;
	}

	public MyProcess search(String name) {
		for (MyProcess myProcess : processes) {
			if (myProcess.getName().equalsIgnoreCase(name)) {
				return myProcess;
			}
		}
		return null;
	}

	public void editProcess(String actualName, String name, int time, int size, boolean lockedStatus) {
		edit(search(actualName), name, time, size, lockedStatus);
		edit(searchInList(actualName, processes), name, time, size, lockedStatus);
	}

	private void edit(MyProcess myProcess, String name, int time, int size, boolean lockedStatus) {
		myProcess.setName(name);
		myProcess.updateTime(time);
		myProcess.setSize(size);
		myProcess.setLocked(lockedStatus);
	}

	public boolean deleteProccess(String name) {
		boolean isDelete = false;
		Node<MyProcess> temp = processQueueReady.peek();
		processes.remove(searchInList(name, processes));
		if (temp.getData().getName().equals(name)) {
			processQueueReady.pop();
			isDelete = true;
		} else {
			isDelete = deleteProcess(name, isDelete, temp);
		}
		return isDelete;
	}

	private boolean deleteProcess(String name, boolean isDelete, Node<MyProcess> temp) {
		while (temp.getNext() != null) {
			if (temp.getNext().getData().getName().equals(name)) {
				temp.setNext(temp.getNext().getNext());
				isDelete = true;
			} else {
				temp = temp.getNext();
			}
		}
		return isDelete;
	}

	public void initSimulation() {
		int position = 0;
		while (!processQueueReady.isEmpty()) {
			MyProcess process = processQueueReady.peek().getData();
			int count = 0;
			boolean isExecuting = true;
			while (isExecuting) {
				if (process.getSize() <= partitions.get(position).getSize()) {
					partitions.get(position).valideSystemTimer(process);
					count = 0;
					position++;
					isExecuting = false;
				} else if (count == partitions.size()) {
					processesErrors.add(processQueueReady.pop());
					isExecuting = false;
				} else {
					partitions.get(position).addNotIngresed(
							new MyProcess(process.getName(), process.getTime(), process.getSize(), process.isLocked()));
					count++;
					position++;
				}
				if (position == partitions.size()) {
					position = 0;
				}
			}
		}
	}

	public ArrayList<Partition> getPartitions() {
		return partitions;
	}

	public void show() {
		System.out.println("Coloa general de listos");
		for (MyProcess p : processes) {
			System.out.println(p.getName() + " -- " + p.getTime() + " -- " + p.getSize());
		}
		System.out.println("----------------------------------------------------");
		System.out.println("Particiones iniciales");
		for (Partition p : partitions) {
			System.out.println(p.getName() + " -- " + p.getSize());
		}

		System.out.println("----------------------------------------------------");
		System.out.println("Reportes por particion");
		for (Partition p : partitions) {
			System.out.println("-----------------------" + p.getName() + "-----------------------------");
			System.out.println("Listos y despachados");
			for (MyProcess process : p.getReadyProccess()) {
				System.out.println(process.getName() + " -- " + process.getTime() + " -- " + process.getSize());
			}
			System.out.println("----------------------------------------------------");
			System.out.println("Ejecucion");
			for (MyProcess process : p.getExecuting()) {
				System.out.println(process.getName() + " -- " + process.getTime() + " -- " + process.getSize());
			}

			System.out.println("----------------------------------------------------");
			System.out.println("Expirados");
			for (MyProcess process : p.getProcessExpired()) {
				System.out.println(process.getName() + " -- " + process.getTime() + " -- " + process.getSize());
			}

			System.out.println("----------------------------------------------------");
			System.out.println("Bloqueo");
			for (MyProcess process : p.getProcessLocked()) {
				System.out.println(process.getName() + " -- " + process.getTime() + " -- " + process.getSize());
			}

			System.out.println("----------------------------------------------------");
			System.out.println("Termiandos");
			for (MyProcess process : p.getProcessTerminated()) {
				System.out.println(process.getName() + " -- " + process.getTime() + " -- " + process.getSize());
			}
			
			System.out.println("----------------------------------------------------");
			System.out.println("No ingreso por exceder tamanio");
			for (MyProcess process : p.getOverSize()) {
				System.out.println(process.getName() + " -- " + process.getTime() + " -- " + process.getSize());
			}
		}

		System.out.println("----------------------------------------------------");
		System.out.println("No procesados en ninguna particion");
		for (MyProcess p : processesErrors) {
			System.out.println(p.getName() + " -- " + p.getSize());
		}

	}
	
	/**
	 * 
	 * @return Lista general de procesos o cola general de listos
	 */
	public ArrayList<MyProcess> getProcesses() {
		return processes;
	}
	
	/**
	 * 
	 * 
	 * @return No entraron a ninguan particion por el tamanio
	 */
	public ArrayList<MyProcess> getProcessesErrors() {
		return processesErrors;
	}

	public static void main(String[] args) {
		Manager manager = new Manager();
		manager.addProcess(new MyProcess("P1", 20, 30, false));
		manager.addProcess(new MyProcess("P2", 15, 25, true));
		manager.addProcess(new MyProcess("P3", 22, 39, false));
		manager.addProcess(new MyProcess("P4", 11, 10, true));
		manager.addProcess(new MyProcess("P5", 15, 80, true));
		manager.addPartition("PAR1", 20);
		manager.addPartition("PAR2", 40);
		manager.addPartition("PAR3", 15);
		manager.addPartition("PAR4", 30);

		manager.initSimulation();

		manager.show();
	}
}
