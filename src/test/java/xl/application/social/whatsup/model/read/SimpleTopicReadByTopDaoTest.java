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
 * Test if {@link SimpleTopicReadByTopDao} can sort topics correctly after insert and update.
 */
public class SimpleTopicReadByTopDaoTest {

    @Test
    public void orderedAsExpectedAfterInsert() {
        SimpleTopicReadByTopDao dao = new SimpleTopicReadByTopDao();
        ArrayList<Topic> samples = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Topic topic = Topics.get(i, "Top Insert " + i, i, 0);
            samples.add(topic);
            dao.insert(topic);
        }

        List<Topic> queried = dao.list(PaginationCursor.HEAD).getContents();

        Collections.reverse(samples);
        assertEquals(samples, queried);
    }

    @Test
    public void orderedAsExpectedAfterUpdate() {
        SimpleTopicReadByTopDao dao = new SimpleTopicReadByTopDao();
        Topic sample1 = Topics.get(0, "Top Update 0");
        Topic sample2 = Topics.get(1, "Top Update 1");

        dao.insert(sample1);
        dao.insert(sample2);

        for (int i = 0; i < 10; i++) {
            sample1.doUpvote();
        }
        dao.update(sample1);

        List<Topic> queried = dao.list(PaginationCursor.HEAD).getContents();

        assertEquals(2, queried.size());
        assertEquals(sample1, queried.get(0));
    }
}
