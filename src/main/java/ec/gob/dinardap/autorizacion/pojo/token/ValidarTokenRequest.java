package ec.gob.dinardap.autorizacion.pojo.token;

public class ValidarTokenRequest {

	private String identifier;
	private String tokenType;
	
	public ValidarTokenRequest(String identifier, String tokenType) {
		super();
		this.identifier = identifier;
		this.tokenType = tokenType;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
}
