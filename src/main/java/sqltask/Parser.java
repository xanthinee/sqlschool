package sqltask;

public interface Parser<T> {

    T parse(String input);
}
