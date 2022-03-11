package sistema.operacional.alocacao.memoria;

import java.util.Random;

public class ProcessGenerator {
	
	public static final int ALCANCE_MIN = 10;
    public static final int ALCANCE_MAX = 50;

    private ProcessGenerator() {
    }

    private static final Random RANDOM = new Random();

    public static Process build() {
        Integer allocationSize = RANDOM.nextInt((ALCANCE_MAX - ALCANCE_MIN) + 1) + ALCANCE_MIN;

        return new Process(System.currentTimeMillis(), allocationSize);
    }
}
