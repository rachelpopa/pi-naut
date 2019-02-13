package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedHashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
	private String id;
	private String type;
	private LinkedHashMap payload;
	private Repository repository;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LinkedHashMap getPayload() {
		return payload;
	}

	public void setPayload(LinkedHashMap payload) {
		this.payload = payload;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
