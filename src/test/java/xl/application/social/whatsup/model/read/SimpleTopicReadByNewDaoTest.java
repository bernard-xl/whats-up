package xl.application.social.whatsup.model.read;

import org.junit.Test;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.entity.Topics;
import xl.application.social.whatsup.util.PaginationCursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test if {@link SimpleTopicReadByNewDao} can sort topics correctly after insert.
 */
public class SimpleTopicReadByNewDaoTest {

    @Test
    public void orderedAsExpectedAfterInsert() {
        SimpleTopicReadByNewDao dao = new SimpleTopicReadByNewDao();
        ArrayList<Topic> samples = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Topic topic = Topics.get(i, "New Insert " + i);
            dao.insert(topic);
            samples.add(topic);
        }

        List<Topic> queried = dao.list(PaginationCursor.HEAD).getContents();

        Collections.reverse(samples);
        assertEquals(samples, queried);
    }
}
