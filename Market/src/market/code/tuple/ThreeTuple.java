package market.code.tuple;

public class ThreeTuple<T,Z,Q> extends TwoTuple<T,Z> {
    public final Q three;

    public ThreeTuple(T first, Z second,Q three) {
        super(first, second);
        this.three=three;
    }
}
