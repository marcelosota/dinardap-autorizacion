package ec.gob.dinardap.autorizacion.pojo.claim;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimResponse {

	@SerializedName("alias")
	@Expose
	private String alias;
	@SerializedName("canton")
	@Expose
	private String canton;
	@SerializedName("ciudad")
	@Expose
	private String ciudad;
	@SerializedName("country")
	@Expose
	private String country;
	@SerializedName("denomination")
	@Expose
	private String denomination;
	@SerializedName("domicilio")
	@Expose
	private String domicilio;
	@SerializedName("email")
	@Expose
	private String email;
	@SerializedName("firstname")
	@Expose
	private String firstname;
	@SerializedName("fullname")
	@Expose
	private String fullname;
	@SerializedName("institucion")
	@Expose
	private String institucion;
	@SerializedName("lastname")
	@Expose
	private String lastname;
	@SerializedName("mainStreetAddress")
	@Expose
	private String mainStreetAddress;
	@SerializedName("mobile")
	@Expose
	private String mobile;
	@SerializedName("pdf")
	@Expose
	private String pdf;
	@SerializedName("province")
	@Expose
	private String province;
	@SerializedName("role")
	@Expose
	private String role;
	@SerializedName("ruc")
	@Expose
	private String ruc;
	@SerializedName("secundaryStreetAddress")
	@Expose
	private String secundaryStreetAddress;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("telephone")
	@Expose
	private String telephone;
	@SerializedName("username")
	@Expose
	private String username;
	@SerializedName("userType")
	@Expose
	private String userType;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCanton() {
		return canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMainStreetAddress() {
		return mainStreetAddress;
	}

	public void setMainStreetAddress(String mainStreetAddress) {
		this.mainStreetAddress = mainStreetAddress;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getSecundaryStreetAddress() {
		return secundaryStreetAddress;
	}

	public void setSecundaryStreetAddress(String secundaryStreetAddress) {
		this.secundaryStreetAddress = secundaryStreetAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
