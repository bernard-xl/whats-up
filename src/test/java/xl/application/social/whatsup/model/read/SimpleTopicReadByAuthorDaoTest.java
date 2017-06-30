package xl.application.social.whatsup.model.read;

import org.junit.Test;
import xl.application.social.whatsup.exception.ArgumentNotValidException;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.entity.Topics;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.util.PaginationCursor;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test the functionality of {@link SimpleTopicReadByAuthorDao}.
 */
public class SimpleTopicReadByAuthorDaoTest {

    @Test
    public void maintainListForEachAuthor() {
        SimpleTopicReadByAuthorDao dao = new SimpleTopicReadByAuthorDao();

        for (int i = 0; i < 3; i++) {
            dao.insert(Topics.get(i, "Author Test => " + i, "JUnit"));
        }

        for (int i = 0; i < 2; i++) {
            dao.insert(Topics.get(i, "Author Test -> " + i, "Tester"));
        }

        List<Topic> jUnitTopics = dao.list("JUnit", PaginationCursor.HEAD).getContents();
        assertEquals(3, jUnitTopics.size());

        List<Topic> testerTopics = dao.list("Tester", PaginationCursor.HEAD).getContents();
        assertEquals(2, testerTopics.size());
    }

    @Test
    public void bidirectionPagination() {
        SimpleTopicReadByAuthorDao dao = new SimpleTopicReadByAuthorDao();

        for (int i = 0; i < 12; i++) {
            dao.insert(Topics.get(i, "Author Test => " + i, "JUnit"));
        }

        Page<Topic> page0 = dao.list("JUnit", new PaginationCursor(null, null, 4));
        assertNull(page0.getPrevious());
        assertEquals("4", page0.getNext());

        Page<Topic> page1 = dao.list("JUnit", new PaginationCursor(page0.getNext(), null, 4));
        assertEquals("4", page1.getPrevious());
        assertEquals("8", page1.getNext());

        Page<Topic> page2 = dao.list("JUnit", new PaginationCursor(page1.getNext(), null, 4));
        assertEquals("8", page2.getPrevious());
        assertNull(page2.getNext());

        Page<Topic> prevOfPage2 = dao.list("JUnit", new PaginationCursor(null, page2.getPrevious(), 4));
        assertEquals("4", prevOfPage2.getPrevious());
        assertEquals("8", prevOfPage2.getNext());
    }

    @Test(expected = ArgumentNotValidException.class)
    public void handleInvalidCursor() {
        SimpleTopicReadByAuthorDao dao = new SimpleTopicReadByAuthorDao();
        dao.insert(Topics.get(0, "Invalid Cursor", "JUnit"));

        dao.list("JUnit", new PaginationCursor("x", null, 0));
    }
}
