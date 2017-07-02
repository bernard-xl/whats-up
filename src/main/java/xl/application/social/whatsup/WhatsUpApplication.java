package xl.application.social.whatsup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.write.TopicWriteService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class WhatsUpApplication {

	private final TopicWriteService topics;

	public WhatsUpApplication(TopicWriteService topics) {
		this.topics = topics;
	}

	/**
	 * Insert some data after startup.
	 */
	@EventListener(ContextRefreshedEvent.class)
	public void submitSomeTopics() throws URISyntaxException, IOException {
		List<String> authors = Arrays.asList("Bernard", "Annie", "John", "George");
		URL topicsUrl = getClass().getClassLoader().getResource("topics.txt");
		int authorIdx = 0;
		int upvoteCount = 1;
		int downvoteCount = 1;

		for (String title : Files.readAllLines(Paths.get(topicsUrl.toURI()))) {
			String q = URLEncoder.encode(title, "UTF-8");
			String link = "http://www.google.com?q=" + q;

			Topic topic = topics.submit(title, link, authors.get(authorIdx));
			topics.vote(topic.getId(), 1, upvoteCount);
			topics.vote(topic.getId(), -1, downvoteCount);

			authorIdx = (authorIdx + 1) % authors.size();
			upvoteCount = (upvoteCount + 2) % 15;
			downvoteCount = (downvoteCount + 1) % 15;
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(WhatsUpApplication.class, args);
	}
}
