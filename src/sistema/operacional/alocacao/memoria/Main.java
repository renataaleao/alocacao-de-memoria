package sistema.operacional.alocacao.memoria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static final Random RANDOM = new Random();
    private static final int delay = 0;   // delay de 0 seg.
    private static final int interval = 1000;  // intervalo de 1 seg.
    private static final int duration = 30; // process duration 100 seg
    private static final Timer timer = new Timer();
    private static final Memory memoria = new Memory(150); // passando o tamanho da memoria de 150
    private static final List<Process> unallocatedProcesses = new ArrayList<>();
    private static double averageSizeProcesses = 0;
    private static double averageMemoryOccupancy = 0;

    public static void main(String[] args) {
        timer.schedule(new TimerTask() {
            int timeCount = 0;

            public void run() {
                timeCount++;

                createAndLocateProcessesInMemory(1);
                System.out.println("Ocupração da memoria: " + memoria.getUsedSpace());

                // Finalizando a execucao
                if (timeCount == duration) {
                    timer.cancel();
                    exerciseAnswer();
                }
            }
        }, delay, interval);
    }

    public static void createAndLocateProcessesInMemory(int algorithm) {
        List<Process> processes = generateProcesses();

        chooseAllocationAlgorithm(algorithm, processes);

        removeProcessesInMemory();
    }

    private static void chooseAllocationAlgorithm(int algorithm, List<Process> processes) {
        switch (algorithm) {
            case 1:
                addProcessesInMemoryWithFirstFit(processes);
                break;
            case 2:
                addProcessesInMemoryWithBestFit(processes);
                break;
            case 3:
                addProcessesInMemoryWithWorstFit(processes);
                break;
            case 4:
                addProcessesInMemoryWithNextFit(processes);
                break;
            default:
                System.out.println("Opção inválida!");

        }
    }

    private static void removeProcessesInMemory() {
        // Removendo processos
        int mustRemove = RANDOM.nextInt(2) + 1;

        for (int i = 0; i < mustRemove; i++) {
            if(memoria.getProcessList().size() > 0) {
                int positionElementRemove = RANDOM.nextInt(memoria.getProcessList().size());
                Process process = memoria.getProcessList().get(positionElementRemove);
                memoria.removeProcess(process);
                System.out.printf("Removendo processo %s%n", process.getId());
            }
        }
    }

    private static List<Process> generateProcesses() {
        // Executado a cada 1 segundo
        Process process1 = ProcessGenerator.build();
        Process process2 = ProcessGenerator.build();
        averageSizeProcesses += process1.getAllocationSize() + process2.getAllocationSize();

        System.out.printf("Gerando processos %s e %s%n", process1.getId(), process2.getId());
        return Arrays.asList(process1, process2);
    }

    private static void addProcessesInMemoryWithFirstFit(List<Process> processes) {
        processes.forEach(process -> {
            if (process.getAllocationSize() > memoria.chekFreeSpace() || !AllocationAlgorithms.firstFit(memoria.getSpace(), process)) {
                process.setState(ProcessState.INATIVO);
                unallocatedProcesses.add(process);
                System.out.printf("Descartando processo %s%n", process.getId());
            } else {
                memoria.addProcess(process);
                averageMemoryOccupancy += process.getAllocationSize();
            }
        });
        // Calculando a taxa media da ocupacao da memoria por segundo
        averageMemoryOccupancy = averageMemoryOccupancy / 2;
    }

    private static void addProcessesInMemoryWithBestFit(List<Process> processes) {
        processes.forEach(process -> {
            if (process.getAllocationSize() > memoria.chekFreeSpace() || !AllocationAlgorithms.bestFit(memoria.getSpace(), process)) {
                process.setState(ProcessState.INATIVO);
                unallocatedProcesses.add(process);
                System.out.printf("Descartando processo %s%n", process.getId());
            } else {
                memoria.addProcess(process);
                averageMemoryOccupancy += process.getAllocationSize();
            }
        });
        // Calculando a taxa media da ocupacao da memoria por segundo
        averageMemoryOccupancy = averageMemoryOccupancy / 2;
    }

    private static void addProcessesInMemoryWithWorstFit(List<Process> processes) {
        processes.forEach(process -> {
            if (process.getAllocationSize() > memoria.chekFreeSpace() || !AllocationAlgorithms.worstFit(memoria.getSpace(), process)) {
                process.setState(ProcessState.INATIVO);
                unallocatedProcesses.add(process);
                System.out.printf("Descartando processo %s%n", process.getId());
            } else {
                memoria.addProcess(process);
                averageMemoryOccupancy += process.getAllocationSize();
            }
        });
        // Calculando a taxa media da ocupacao da memoria por segundo
        averageMemoryOccupancy = averageMemoryOccupancy / 2;
    }

    private static void addProcessesInMemoryWithNextFit(List<Process> processes) {
        processes.forEach(process -> {
            if (process.getAllocationSize() > memoria.chekFreeSpace() || !AllocationAlgorithms.nextFit(memoria.getSpace(), process)) {
                process.setState(ProcessState.INATIVO);
                unallocatedProcesses.add(process);
                System.out.printf("Descartando processo %s%n", process.getId());
            } else {
                memoria.addProcess(process);
                averageMemoryOccupancy += process.getAllocationSize();
            }
        });
        // Calculando a taxa media da ocupacao da memoria por segundo
        averageMemoryOccupancy = averageMemoryOccupancy / 2;
    }

    public static void exerciseAnswer() {
        double disposalFee = unallocatedProcesses.size();
        averageSizeProcesses = averageSizeProcesses / (memoria.getProcessList().size() + unallocatedProcesses.size());

        System.out.println("============== Respostas ==============");
        System.out.printf("Tamanho médio do processos: %s%n", averageSizeProcesses);
        System.out.printf("Ocupação media da memoria: %ss%n", averageMemoryOccupancy);
        System.out.printf("Taxa de descarte: %s%n", disposalFee / 100);
    }
}
