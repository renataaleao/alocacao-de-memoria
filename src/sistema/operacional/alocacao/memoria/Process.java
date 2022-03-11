package sistema.operacional.alocacao.memoria;

public class Process {
	
	private Long id;
    private Integer initPosition;
    private ProcessState state;
    private Integer allocationSize;

    public Process(Long id, Integer allocationSize) {
        this.id = id;
        this.allocationSize = allocationSize;
        this.state = ProcessState.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInitPosition() {
        return initPosition;
    }

    public void setInitPosition(Integer initPosition) {
        this.initPosition = initPosition;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public Integer getAllocationSize() {
        return allocationSize;
    }

    public void setAllocationSize(Integer allocationSize) {
        this.allocationSize = allocationSize;
    }
}
