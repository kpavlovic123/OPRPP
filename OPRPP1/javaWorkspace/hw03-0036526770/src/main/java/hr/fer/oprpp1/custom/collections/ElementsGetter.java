package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter<T> {
	boolean hasNextElement();
	T getNextElement();
	default void processRemaining(Processor<? super T> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	};
}
