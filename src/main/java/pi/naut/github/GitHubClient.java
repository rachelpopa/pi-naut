package pi.naut.github;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import pi.naut.github.model.Event;
import pi.naut.github.model.Notification;
import pi.naut.github.model.User;

import java.util.List;

@Client(GitHubClientConfiguration.GITHUB_API_URL)
@Header(name = "User-Agent", value = "${github.user}")
@Header(name = "Authorization", value = "token ${github.token}")
@Requires(property = "${github}")
public interface GitHubClient {

    @Get("graphql")
    String queryGraph(String query);

    @Get("user")
    User getCurrentUser();

    @Get("notifications")
    List<Notification> getNotifications();

    @Get("users/${github.user}/events")
    List<Event> getCurrentUserEvents();

}
