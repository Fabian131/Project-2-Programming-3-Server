package domain;

public class Operator {
	
	private int id; 
	private String surnames; 
	private String name; 
	private transient boolean isAvailable; 
	
	public Operator() {}

	public Operator(String surnames, String name) {
		this.id = 0; 
		this.surnames = surnames;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return surnames + name;
	}

}
