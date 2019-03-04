package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	@JsonProperty("login")
	private String user;
	private String name;
	private String avatar_url;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

}
