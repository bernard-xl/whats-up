package xl.application.social.whatsup.model.read;

import org.junit.Test;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.entity.Topics;
import xl.application.social.whatsup.util.PaginationCursor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test if {@link SimpleTopicReadByHotDao} can sort topics correctly after insert and update.
 */
public class SimpleTopicReadByHotDaoTest {

    @Test
    public void orderedAsExpectedAfterInsert() {
        SimpleTopicReadByHotDao dao = new SimpleTopicReadByHotDao();
        ArrayList<Topic> samples = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Topic topic = Topics.get(i, "Hot Insert " + i, i, 0);
            samples.add(topic);
            dao.insert(topic);
        }

        List<Topic> queried = dao.list(PaginationCursor.HEAD).getContents();

        assertEquals(5, queried.size());
        assertEquals(samples.get(4), queried.get(0));
        assertEquals(samples.get(0), queried.get(4));
    }

    @Test
    public void orderedAsExpectedAfterUpdate() {
        SimpleTopicReadByHotDao dao = new SimpleTopicReadByHotDao();
        Topic sample1 = Topics.get(0, "Hot Update 0");
        Topic sample2 = Topics.get(1, "Hot Update 1");

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
