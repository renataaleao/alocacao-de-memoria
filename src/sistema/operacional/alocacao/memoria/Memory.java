package sistema.operacional.alocacao.memoria;

import java.util.ArrayList;
import java.util.List;

public class Memory {
	
	private final boolean space[];
    private Integer usedSpace;
    private List<Process> processList;

    public Memory(int size) {
        this.space = new boolean[size];
        this.processList = new ArrayList<>();
        this.usedSpace = 0;
    }

    public boolean[] getSpace() {
        return space;
    }

    public List<Process> getProcessList() {
        return processList;
    }

    public void addProcess(Process process) {
        this.processList.add(process);
        this.usedSpace += process.getState().equals(ProcessState.ATIVO) ? process.getAllocationSize() : 0;
        this.updateSpace(process.getInitPosition(), process.getAllocationSize(), true);
    }

    public void removeProcess(Process process) {
        this.processList.remove(process);
        this.usedSpace -= process.getAllocationSize();
        this.updateSpace(process.getInitPosition(), process.getAllocationSize(), false);
    }

    public Integer chekFreeSpace() {
        return this.space.length - usedSpace;
    }

    public Integer getUsedSpace() {
        return usedSpace;
    }

    private void updateSpace(Integer initPosition, Integer allocationSize, boolean value) {
        int allocationLimit = initPosition + allocationSize;

        for (int i = initPosition; i != allocationLimit; i++) {
            this.space[i] = value;
        }
    }
}
