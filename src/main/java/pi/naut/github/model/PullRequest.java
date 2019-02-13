package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequest {
	private long id;
	private int number;
	private String title;
	@JsonProperty("repo")
	private Repository repository;
	private StateType state;
	private boolean merged;
	private boolean mergable;
	private String mergableState;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public boolean isMerged() {
		return merged;
	}

	public void setMerged(boolean merged) {
		this.merged = merged;
	}

	public boolean isMergable() {
		return mergable;
	}

	public void setMergable(boolean mergable) {
		this.mergable = mergable;
	}

	public String getMergableState() {
		return mergableState;
	}

	public void setMergableState(String mergableState) {
		this.mergableState = mergableState;
	}
}
