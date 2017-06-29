package xl.application.social.whatsup.model.read.impl;


import java.nio.ByteBuffer;
import java.util.Base64;

class OrderedKey implements Comparable<OrderedKey> {

    private long id;
    private double score;

    public static final OrderedKey MIN = new OrderedKey(Long.MIN_VALUE, Double.MIN_VALUE);
    public static final OrderedKey MAX = new OrderedKey(Long.MAX_VALUE, Double.MAX_VALUE);

    public static String encode(OrderedKey key) {
        ByteBuffer buff = ByteBuffer.allocate(Long.SIZE * 2);
        buff.putLong(key.id);
        buff.putDouble(key.score);

        return Base64.getMimeEncoder().encodeToString(buff.array());
    }

    public static OrderedKey decode(String encoded) {
        byte[] bytes = Base64.getMimeDecoder().decode(encoded);
        ByteBuffer buff = ByteBuffer.wrap(bytes);

        return new OrderedKey(buff.getLong(), buff.getDouble());
    }

    public OrderedKey(long id, double score) {
        this.id = id;
        this.score = score;
    }

    @Override
    public int compareTo(OrderedKey other) {
        int cmp = Double.compare(other.score, score);
        return (cmp == 0)? Long.compare(other.id, id) : cmp;
    }
}
