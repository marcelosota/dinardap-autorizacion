package ec.gob.dinardap.autorizacion.constante;

public enum SemillaEnum {
	SEMILLA_REMANENTE("ghs%6f.D");
	
	private String semilla;
	
	private SemillaEnum(String semilla) {
		this.semilla = semilla;
	}

	public String getSemilla() {
		return semilla;
	}
	
}
