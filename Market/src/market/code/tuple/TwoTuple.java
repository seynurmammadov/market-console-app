package market.code.tuple;

public class TwoTuple<T,Z> extends Holder<T>{
   public final Z second;

    public TwoTuple(T first,Z second) {
        super(first);
        this.second = second;
    }
}
