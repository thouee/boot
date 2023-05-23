package th.code.lang.util.wrapper;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public abstract class StreamWrapper<T> {

    private Stream<T> stream;
    private Predicate<T> predicate;

    public static <T> StreamWrapper<T> lambdaQuery(Collection<T> collection) {
        return lambdaQuery(collection.stream());
    }

    public static <T> StreamWrapper<T> lambdaQuery(Stream<T> stream) {
        AndStreamWrapper<T> wrapper = new AndStreamWrapper<>();
        wrapper.setStream(stream);
        wrapper.setPredicate(t -> true);
        return wrapper;
    }

    /**
     * 注：使用 <code>.not().list()</code> 时，<code>.not()</code> 不会起任何作用
     *
     * @return List<T>
     */
    public List<T> list() {
        return stream.filter(predicate).collect(Collectors.toList());
    }

    /**
     * 注：使用 <code>.not().one()</code> 时，<code>.not()</code> 不会起任何作用
     *
     * @return T
     */
    public T one() {
        return stream.filter(predicate).findAny().orElse(null);
    }

    public StreamWrapper<T> and() {
        AndStreamWrapper<T> wrapper = new AndStreamWrapper<>();
        wrapper.setStream(stream);
        wrapper.setPredicate(predicate);
        return wrapper;
    }

    public StreamWrapper<T> or() {
        OrStreamWrapper<T> wrapper = new OrStreamWrapper<>();
        wrapper.setStream(stream);
        wrapper.setPredicate(predicate);
        return wrapper;
    }

    /**
     * 对下一个条件取反
     *
     * @return StreamWrapper<T>
     */
    public StreamWrapper<T> not() {
        NotStreamWrapper<T> wrapper = new NotStreamWrapper<>();
        wrapper.setStream(stream);
        wrapper.setPredicate(predicate);
        return wrapper;
    }

    /**
     * 对以上所有条件取反 <br> 注：使用 <code>.not().negate()</code> 时，<code>.not()</code> 不会起任何作用
     *
     * @return StreamWrapper<T>
     */
    public StreamWrapper<T> negate() {
        setPredicate(getPredicate().negate());
        return this;
    }


    public abstract StreamWrapper<T> eq(boolean condition, Function<T, ? extends CharSequence> getFun, CharSequence searchSeq);

    public abstract StreamWrapper<T> like(boolean condition, Function<T, ? extends CharSequence> getFun, CharSequence searchSeq);

    public abstract StreamWrapper<T> in(boolean condition, Function<T, ? extends CharSequence> getFun, Collection<?> collection);


    static class AndStreamWrapper<T> extends StreamWrapper<T> {
        @Override
        public StreamWrapper<T> eq(boolean condition, Function<T, ? extends CharSequence> getFun, CharSequence searchSeq) {
            if (!condition) {
                return this;
            }
            setPredicate(getPredicate().and(t -> StringUtils.equals(getFun.apply(t), searchSeq)));
            return this;
        }

        @Override
        public StreamWrapper<T> like(boolean condition, Function<T, ? extends CharSequence> getFun, CharSequence searchSeq) {
            if (!condition) {
                return this;
            }
            setPredicate(getPredicate().and(t -> StringUtils.containsIgnoreCase(getFun.apply(t), searchSeq)));
            return this;
        }

        @Override
        public StreamWrapper<T> in(boolean condition, Function<T, ? extends CharSequence> getFun, Collection<?> collection) {
            if (!condition) {
                return this;
            }
            setPredicate(getPredicate().and(t -> collection.contains(getFun.apply(t))));
            return this;
        }
    }

    static class OrStreamWrapper<T> extends StreamWrapper<T> {
        @Override
        public StreamWrapper<T> eq(boolean condition, Function<T, ? extends CharSequence> getFun, CharSequence searchSeq) {
            if (!condition) {
                return this;
            }
            setPredicate(getPredicate().or(t -> StringUtils.equals(getFun.apply(t), searchSeq)));
            return and();
        }

        @Override
        public StreamWrapper<T> like(boolean condition, Function<T, ? extends CharSequence> getFun, CharSequence searchSeq) {
            if (!condition) {
                return this;
            }
            setPredicate(getPredicate().or(t -> StringUtils.containsIgnoreCase(getFun.apply(t), searchSeq)));
            return and();
        }

        @Override
        public StreamWrapper<T> in(boolean condition, Function<T, ? extends CharSequence> getFun, Collection<?> collection) {
            if (!condition) {
                return this;
            }
            setPredicate(getPredicate().or(t -> collection.contains(getFun.apply(t))));
            return and();
        }
    }

    static class NotStreamWrapper<T> extends StreamWrapper<T> {
        @Override
        public StreamWrapper<T> eq(boolean condition, Function<T, ? extends CharSequence> getFun, CharSequence searchSeq) {
            if (!condition) {
                return this;
            }
            Predicate<T> other = t -> StringUtils.equals(getFun.apply(t), searchSeq);
            setPredicate(getPredicate().and(other.negate()));
            return and();
        }

        @Override
        public StreamWrapper<T> like(boolean condition, Function<T, ? extends CharSequence> getFun, CharSequence searchSeq) {
            if (!condition) {
                return this;
            }
            Predicate<T> other = t -> StringUtils.containsIgnoreCase(getFun.apply(t), searchSeq);
            setPredicate(getPredicate().and(other.negate()));
            return and();
        }

        @Override
        public StreamWrapper<T> in(boolean condition, Function<T, ? extends CharSequence> getFun, Collection<?> collection) {
            if (!condition) {
                return this;
            }
            Predicate<T> other = t -> collection.contains(getFun.apply(t));
            setPredicate(getPredicate().and(other.negate()));
            return and();
        }
    }
}
