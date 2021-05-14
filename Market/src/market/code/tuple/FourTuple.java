package market.code.tuple;

public class FourTuple<T,Z,Q,W> extends ThreeTuple<T,Z,Q>{
    public final W four;

    public FourTuple(T first, Z second, Q three,W four) {
        super(first, second, three);
        this.four = four;
    }
}
