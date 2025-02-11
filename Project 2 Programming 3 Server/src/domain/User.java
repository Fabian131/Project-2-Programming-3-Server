package domain;

public class User {
	
	private int id; 
	private String identification; 
	private String surnames; 
	private String name;
	private String password; 
	private String imagePath; //para la ruta de la imagen
	
	public User() {}
	
	public User(String identification, String surnames, String name, String password, String imagePath) {
		this.id = 0;
		this.identification = identification; 
		this.surnames = surnames;
		this.name = name;
		this.password = password; 
		this.imagePath = imagePath; 
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
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return identification + surnames + name;
	}
	
}
