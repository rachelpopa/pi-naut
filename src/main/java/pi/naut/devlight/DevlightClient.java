package pi.naut.devlight;

import lombok.NonNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static javax.ws.rs.client.Entity.json;

public class DevlightClient {

  private static final String DEFAULT_BASE_URI = "https://devlights:8443/";
  private static final String API = "api/devlight";

  private final Client client;
  private final String user;
  private final String token;
  private final String baseUri;

  public DevlightClient(@NonNull String user, @NonNull String token) {
    this.client = ClientBuilder.newBuilder().build();
    this.user = user;
    this.token = token;
    this.baseUri = DEFAULT_BASE_URI;
  }

  public DevlightClient(@NonNull String user, @NonNull String token, @NonNull String baseUri) {
    this.client = ClientBuilder.newBuilder().build();
    this.user = user;
    this.token = token;
    this.baseUri = baseUri;
  }

  public void setLightState(@NonNull LightState lightState) {
    baseTarget()
      .queryParam("user", user)
      .queryParam("token", token)
      .request()
      .put(json(lightState));
  }

  private WebTarget baseTarget() {
    return client.target(baseUri)
      .path(API);
  }

}
