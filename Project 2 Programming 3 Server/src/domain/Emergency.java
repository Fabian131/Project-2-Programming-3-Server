package domain;

public class Emergency {

	private int id;
	private String userId;
	private String type;
	private String location;
	private String description;
	private String status;
	private long startTime;
	private String operatorId;

	public Emergency() {}

	public Emergency(String userId, String type, String location, String description) {
		this.id = 0;
		this.userId = userId;
		this.type = type;
		this.location = location;
		this.description = description;
		this.status = "Pendiente"; // Estado inicial
		this.startTime = System.currentTimeMillis();
		this.operatorId = null;
	}


	public Emergency(int id,String userId, String type, String location, String description, String state) {
		this.id = id;
		this.userId = userId;
		this.type = type;
		this.location = location;
		this.description = description;
		this.status = state;
		this.startTime = System.currentTimeMillis();
		this.operatorId = null;
	}

	public String getColor() {
		long duration = System.currentTimeMillis() - startTime;
		long secondsElapsed = duration / 1000;

		switch(status) {
		case "Pendiente":
			if (secondsElapsed > 300) return "Rojo"; // +5 minutos
			if (secondsElapsed > 180) return "Amarillo"; // +3 minutos
			return "Verde";

		case "En proceso":
			if (secondsElapsed > 300) return "Rojo";
			if (secondsElapsed > 180) return "Amarillo";
			return "Celeste";

		case "Resuelta":
			return "Verde";

		default:
			return "Verde";
		}
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}



}
