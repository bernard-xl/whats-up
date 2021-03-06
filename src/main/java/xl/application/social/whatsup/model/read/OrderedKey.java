package xl.application.social.whatsup.model.read;


import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Objects;

class OrderedKey implements Comparable<OrderedKey> {

    private long id;
    private double score;

    public static String encode(OrderedKey key) {
        ByteBuffer buff = ByteBuffer.allocate(Long.BYTES * 2);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedKey that = (OrderedKey) o;
        return id == that.id &&
                Double.compare(that.score, score) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score);
    }
}
