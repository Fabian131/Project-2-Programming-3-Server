package domain;

public class User {
	
	private int id; 
	private String identification; 
	private String name;
	private String surnames; 
	private String password; 
	
	
	public User() {}
	
	

	public User(int id, String identification, String name, String surnames, String password) {
		super();
		this.id = id;
		this.identification = identification;
		this.name = name;
		this.surnames = surnames;
		this.password = password;
	}
	
	public User( String identification, String name, String surnames, String password) {
		this.id = 0;
		this.identification = identification;
		this.name = name;
		this.surnames = surnames;
		this.password = password;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getIdentification() {
		return identification; 
	}
	
	public void setIdentification(String identification) {
		this.identification = identification; 
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	@Override
	public String toString() {
		return identification + surnames + name;
	}
	
}
