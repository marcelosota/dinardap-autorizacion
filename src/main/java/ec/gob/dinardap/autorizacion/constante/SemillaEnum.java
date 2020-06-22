package ec.gob.dinardap.autorizacion.constante;

public enum SemillaEnum {
	SEMILLA_REMANENTE("ghs%6f.D"),
	SEMILLA_SEGURIDAD("C0l!bX!3$#V0l@Nd%");
	
	private String semilla;
	
	private SemillaEnum(String semilla) {
		this.semilla = semilla;
	}

	public String getSemilla() {
		return semilla;
	}
	
}
