package xl.application.social.whatsup.model.write;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.event.TopicDeleted;
import xl.application.social.whatsup.model.event.TopicEvent;
import xl.application.social.whatsup.model.event.TopicSubmitted;
import xl.application.social.whatsup.model.event.TopicVoted;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test if {@link SimpleTopicWriteService} invokes dao and event publisher properly.
 */
public class SimpleTopicWriteServiceTest {

    @Test
    public void dispatchEventAndInvokeDao() {
        ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);
        TopicWriteDao dao = mock(TopicWriteDao.class);
        SimpleTopicWriteService topics = new SimpleTopicWriteService(publisher, dao);

        when(dao.create(any(), any(), any(), any())).then(invocation -> {
            Object[] args = invocation.getArguments();
            return new Topic(0, (String) args[0], (String) args[1], (String) args[2], (Instant) args[3]);
        });

        Topic topic = topics.submit("Unit Test", null, "JUnit");
        assertEquals(0, topic.getId());

        when(dao.find(anyLong())).thenReturn(Optional.of(topic));

        topics.vote(topic.getId(), 1);
        assertEquals(1, topic.getUpvote());

        when(dao.delete(anyLong())).thenReturn(Optional.of(topic));
        topics.delete(topic.getId());

        verify(dao, times(1)).create(any(), any(), any(), any());

        ArgumentCaptor<TopicEvent> eventCaptor = ArgumentCaptor.forClass(TopicEvent.class);
        verify(publisher, times(3)).publishEvent(eventCaptor.capture());

        List<TopicEvent> dispatchedEvents = eventCaptor.getAllValues();
        assertEquals(3, dispatchedEvents.size());
        assertTrue(dispatchedEvents.get(0) instanceof TopicSubmitted);
        assertTrue(dispatchedEvents.get(1) instanceof TopicVoted);
        assertTrue(dispatchedEvents.get(2) instanceof TopicDeleted);

        for (TopicEvent event : dispatchedEvents) {
            assertEquals(topic, event.getTopic());
        }
    }
}
