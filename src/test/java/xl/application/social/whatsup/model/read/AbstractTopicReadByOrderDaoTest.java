package xl.application.social.whatsup.model.read;

import org.junit.Test;
import xl.application.social.whatsup.exception.ArgumentNotValidException;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.entity.Topics;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.util.PaginationCursor;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Test the common functionality of all {@link TopicReadByOrderDao}.
 */
public class AbstractTopicReadByOrderDaoTest {

    @Test
    public void bidirectionalNavigation() {
        SimpleTopicReadByOrderDao dao = new SimpleTopicReadByOrderDao();
        ArrayList<Topic> samples = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Topic topic = Topics.get(12 - i, "Navigation Test " + (12 - i));
            dao.insert(topic);
            samples.add(topic);
        }

        Page<Topic> page0 = dao.list(new PaginationCursor(null, null, 4));
        assertNull(page0.getPrevious());
        assertNotNull(page0.getNext());
        assertEquals(samples.subList(0, 4), page0.getContents());

        Page<Topic> page1 = dao.list(new PaginationCursor(page0.getNext(), null, 4));
        assertNotNull(page1.getPrevious());
        assertNotNull(page1.getNext());
        assertEquals(samples.subList(4, 8), page1.getContents());

        Page<Topic> page2 = dao.list(new PaginationCursor(page1.getNext(), null, 4));
        assertNotNull(page2.getPrevious());
        assertNull(page2.getNext());
        assertEquals(samples.subList(8, 12), page2.getContents());

        Page<Topic> prevOfPage2 = dao.list(new PaginationCursor(null, page2.getPrevious(), 4));
        assertNotNull(prevOfPage2.getPrevious());
        assertNotNull(prevOfPage2.getNext());
        assertEquals(page1.getContents(), prevOfPage2.getContents());

        Page<Topic> prevOfPage1 = dao.list(new PaginationCursor(null, page1.getPrevious(), 4));
        assertNull(prevOfPage1.getPrevious());
        assertNotNull(prevOfPage1.getNext());
        assertEquals(page0.getContents(), prevOfPage1.getContents());
    }

    @Test(expected = ArgumentNotValidException.class)
    public void handleInvalidCursor() {
        SimpleTopicReadByOrderDao dao = new SimpleTopicReadByOrderDao();
        dao.insert(Topics.get(0, "Invalid Cursor "));

        dao.list(new PaginationCursor("xyz", null, 0));
    }

    @Test
    public void insertThenDelete() {
        SimpleTopicReadByOrderDao dao = new SimpleTopicReadByOrderDao();
        ArrayList<Topic> samples = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Topic topic = Topics.get(12 - i, "Delete Test " + (12 - i));
            dao.insert(topic);
            samples.add(topic);
        }

        for (Topic sample : samples) {
            dao.delete(sample);
        }

        Page<Topic> queried = dao.list(PaginationCursor.HEAD);
        assertNull(queried.getPrevious());
        assertNull(queried.getNext());
        assertTrue(queried.getContents().isEmpty());
    }

    private static class SimpleTopicReadByOrderDao extends AbstractTopicReadByOrderDao {

        @Override
        public ListingOrder getOrder() {
            return null;
        }

        @Override
        protected double scoreOf(Topic topic) {
            return topic.getId();
        }
    }
}
