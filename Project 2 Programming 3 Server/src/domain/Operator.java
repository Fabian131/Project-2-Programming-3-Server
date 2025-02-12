package domain;

public class Operator {
	
	private int idDB; 
	private String identification;
	private String name;
	private String surnames;
	private String password;
	private int numberOfEmergenciesAttended;
	private transient boolean isAvailable; 
	
	public Operator() {}

	public Operator(String identification, String name,String surnames, String password) {// para registrar
		this.identification = identification;
		this.name = name;
		this.surnames = surnames;
		this.password = password;
		this.numberOfEmergenciesAttended = 0;
		this.isAvailable = true;
	}
	
	public Operator(String identification, String password) {//para login
		this.identification = identification;
		this.password = password;
	}
	
	public Operator(int idDB, String identification, String name, String surnames, String password,
			int numberOfEmergenciesAttended, boolean isAvailable) {
		this.idDB = idDB;
		this.identification = identification;
		this.name = name;
		this.surnames = surnames;
		this.password = password;
		this.numberOfEmergenciesAttended = numberOfEmergenciesAttended;
		this.isAvailable = isAvailable;
	}

	public int getIdDB() {
		return idDB;
	}

	public void setIdDB(int idDB) {
		this.idDB = idDB;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumberOfEmergenciesAttended() {
		return numberOfEmergenciesAttended;
	}

	public void setNumberOfEmergenciesAttended(int numberOfEmergenciesAttended) {
		this.numberOfEmergenciesAttended = numberOfEmergenciesAttended;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Operator [idDB=" + idDB + ", identification=" + identification + ", name=" + name + ", surnames="
				+ surnames + ", password=" + password + ", numberOfEmergenciesAttended=" + numberOfEmergenciesAttended
				+ ", isAvailable=" + isAvailable + "]";
	}

	

}
