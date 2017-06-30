package xl.application.social.whatsup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.web.dto.ErrorReply;

import java.net.URI;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * An integration test to check if everything work together.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WhatsUpIntegrationTest {

	private static final String APP_URL_TEMPLATE = "http://localhost:%d%s";

	@LocalServerPort
	private int port;

	private WhatsUpHttpClient client;

	@Before
	public void beforeEachTest() {
		client = new WhatsUpHttpClient();
	}

	@Test
	public void invalidLogin() {
		String url = String.format(APP_URL_TEMPLATE, port, "/auth/login");
		ResponseEntity<ErrorReply> response = client.postForEntity(url, Collections.emptyMap(), ErrorReply.class);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Unauthorized", response.getBody().getError());
	}

	@Test
	public void accessDenied() {
		String delUrl = String.format(APP_URL_TEMPLATE, port, "/api/del");
		MultiValueMap<String, String> delArgs = delArguments(0);
		ResponseEntity<Void> delResponse = client.postForEntity(delUrl, delArgs, Void.class);

		assertEquals(HttpStatus.FORBIDDEN, delResponse.getStatusCode());
	}

	@Test
	public void generalUsage() {
		String loginUrl = String.format(APP_URL_TEMPLATE, port, "/auth/login");
		MultiValueMap<String, String> loginArgs = loginArguments("bernard");
		ResponseEntity<Void> loginResponse = client.postForEntity(loginUrl, loginArgs, Void.class);

		assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

		String submitUrl = String.format(APP_URL_TEMPLATE, port, "/api/submit");
		MultiValueMap<String, String> submitArgs = submitArguments("Hello, World!", "http://www.google.com?q=Hello");
		ResponseEntity<Topic> submitResponse = client.postForEntity(submitUrl, submitArgs, Topic.class);

		assertEquals(HttpStatus.OK, submitResponse.getStatusCode());
		assertEquals("bernard", submitResponse.getBody().getAuthor());
		assertEquals(submitArgs.getFirst("title"), submitResponse.getBody().getTitle());
		assertEquals(submitArgs.getFirst("link"), submitResponse.getBody().getLink());

		Topic topic = submitResponse.getBody();

		String voteUrl = String.format(APP_URL_TEMPLATE, port, "/api/vote");
		MultiValueMap<String, String> voteArgs = voteArguments(topic.getId(), 1);
		ResponseEntity<Void> voteResponse = client.postForEntity(voteUrl, voteArgs, Void.class);

		assertEquals(HttpStatus.OK, voteResponse.getStatusCode());

		ParameterizedTypeReference<Page<Topic>> pageType = new ParameterizedTypeReference<Page<Topic>>() {
		};

		String listUrl = String.format(APP_URL_TEMPLATE, port, "/hot");
		RequestEntity<Void> listRequest = new RequestEntity<>(HttpMethod.GET, URI.create(listUrl));
		ResponseEntity<Page<Topic>> listResponse = client.exchange(listRequest, pageType);

		assertFalse(listResponse.getBody().getContents().isEmpty());
		assertEquals(topic, listResponse.getBody().getContents().get(0));

		String byAuthorUrl = String.format(APP_URL_TEMPLATE, port, "/user/bernard");
		RequestEntity<Void> byAuthorRequest = new RequestEntity<>(HttpMethod.GET, URI.create(byAuthorUrl));
		ResponseEntity<Page<Topic>> byAuthorResponse = client.exchange(byAuthorRequest, pageType);

		assertFalse(byAuthorResponse.getBody().getContents().isEmpty());
		assertEquals(topic, listResponse.getBody().getContents().get(0));
	}

	private MultiValueMap<String, String> loginArguments(String username) {
		MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
		args.add("username", username);
		return args;
	}

	private MultiValueMap<String, String> submitArguments(String title, String link) {
		MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
		args.add("title", title);
		args.add("link", link);
		return args;
	}

	private MultiValueMap<String, String> voteArguments(long id, int dir) {
		MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
		args.add("id", Long.toString(id));
		args.add("dir", Integer.toString(dir));
		return args;
	}

	private MultiValueMap<String, String> delArguments(long id) {
		MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
		args.add("id", Long.toString(id));
		return args;
	}
}
