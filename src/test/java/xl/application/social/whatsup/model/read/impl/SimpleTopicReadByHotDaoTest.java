package xl.application.social.whatsup.model.read.impl;

import org.junit.Test;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.util.PaginationCursor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleTopicReadByHotDaoTest {

    @Test
    public void orderByHot() {
        SimpleTopicReadByHotDao dao = new SimpleTopicReadByHotDao();
        ArrayList<Topic> samples = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Topic topic = TestUtils.createTopic(i, i, 0);
            samples.add(topic);
            dao.insert(topic);
        }

        List<Topic> queried = dao.list(PaginationCursor.HEAD).getContents();

        assertEquals(5, queried.size());
        assertEquals(samples.get(4), queried.get(0));
        assertEquals(samples.get(0), queried.get(4));
    }
}
