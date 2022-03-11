package sistema.operacional.alocacao.memoria;

public class AllocationAlgorithms {
	
	private static int lastAllocationNextFit = 0;

    private AllocationAlgorithms() {
    }

    // [0,0,0,0,0,1,1,1,0,0,0,0,1,0,1,0,1,0,0,0,1,1,1,1]
    public static boolean firstFit(boolean space[], Process process) {
        int partitionCount = 0;

        for (int i = 0; i < space.length; i++) {
            if (!space[i]) {
                partitionCount++;
                if (partitionCount == process.getAllocationSize()) {
                    process.setInitPosition(i - process.getAllocationSize() + 1);
                    return true;
                }
            } else {
                partitionCount = 0;
            }
        }
        return false;
    }

    // [0,0,0,0,0,1,1,1,0,0,0,0,1,0,1,0,1,0,0,0,1,1,1,1]
    public static boolean bestFit(boolean space[], Process process) {
        int partitionCount = 0;
        int idealSize = process.getAllocationSize();
        boolean hasSpace = false;

        for (int i = 0; i < space.length; i++) {
            if (!space[i] && partitionCount < process.getAllocationSize()) {
                partitionCount++;
            } else {
                if (partitionCount <= idealSize) { // 5 < 1; 4 < 5; 1 < 4; 1 < 1; 3 < 1
                    idealSize = partitionCount; // 1; 4; 1;
                    process.setInitPosition(i - process.getAllocationSize());
                    hasSpace = true;
                }
                partitionCount = 0;
            }
        }

        return hasSpace;
    }

    // [0,0,0,0,0,1,1,1,0,0,0,0,1,0,1,0,1,0,0,0,1,1,1,1]
    public static boolean worstFit(boolean space[], Process process) {
        int partitionCount = 0;
        boolean hasSpace = false;

        for (int i = 0; i < space.length; i++) {
            if (!space[i] && i<space.length-1) {
                partitionCount++;
            } else {
                if (partitionCount > process.getAllocationSize()) { // 5 > 1; 4 > 5
                    process.setInitPosition(i-partitionCount);
                    hasSpace = true;
                }
                partitionCount = 0;
            }
        }

        return hasSpace;
    }

    // [0,0,0,0,0,1,1,1,0,0,0,0,1,0,1,0,1,0,0,0,1,1,1,1]
    public static boolean nextFit(boolean space[], Process process) {
        int partitionCount = 0;

        for (int i = lastAllocationNextFit; i < space.length; i++) {
            if (!space[i]) {
                partitionCount++;
                if (partitionCount == process.getAllocationSize()) { // 1 = 1; 3 == 3
                    process.setInitPosition(i - process.getAllocationSize() + 1); // 0; 3
                    lastAllocationNextFit = i + 1; // 1; 4;
                    return true;
                }
            } else {
                partitionCount = 0;
            }
        }

        return false;
    }
}
