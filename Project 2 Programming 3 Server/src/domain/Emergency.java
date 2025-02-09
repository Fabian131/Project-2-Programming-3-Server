package domain;

public class Emergency {
	
	private int id; 
	private String idUser; //usuario que reporte la emergencia
	private String typeEmergency; 
	private String ubication; 
	private String description;
	
	public Emergency(String idUser, String typeEmergency, String ubication, String description) {
		super();
		this.id = 0; 
		this.idUser = idUser;
		this.typeEmergency = typeEmergency;
		this.ubication = ubication;
		this.description = description;
	}

	public int getId() {
		return id; 
	}
	
	public void setId(int id) {
		this.id = id; 
	}
	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getTypeEmergency() {
		return typeEmergency;
	}

	public void setTypeEmergency(String typeEmergency) {
		this.typeEmergency = typeEmergency;
	}

	public String getUbication() {
		return ubication;
	}

	public void setUbication(String ubication) {
		this.ubication = ubication;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return idUser + typeEmergency + ubication
				+ description;
	} 

}
