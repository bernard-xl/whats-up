package xl.application.social.whatsup.model.read.impl;

import org.junit.Test;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.util.PaginationCursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleTopicReadByNewDaoTest {

    @Test
    public void orderByNew() {
        SimpleTopicReadByNewDao dao = new SimpleTopicReadByNewDao();
        ArrayList<Topic> samples = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Topic topic = TestUtils.createTopic(i, 0, 0);
            dao.insert(topic);
            samples.add(topic);
        }

        List<Topic> queried = dao.list(PaginationCursor.HEAD).getContents();

        Collections.reverse(samples);
        assertEquals(samples, queried);
    }
}
