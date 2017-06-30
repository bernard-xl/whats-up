package xl.application.social.whatsup.model.read;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test if {@link OrderedKeyTest} can be encoded and decoded properly.
 */
public class OrderedKeyTest {

    private static final String ENCODED_SAMPLE = "AAAAAAAAABlACR64UeuFHw==";
    private static final OrderedKey KEY_SAMPLE = new OrderedKey(25, 3.14);

    @Test
    public void encode() {
        String encoded = OrderedKey.encode(KEY_SAMPLE);
        assertEquals(ENCODED_SAMPLE, encoded);
    }

    @Test
    public void decode() {
        OrderedKey key = OrderedKey.decode(ENCODED_SAMPLE);
        assertEquals(key, KEY_SAMPLE);
    }
}
