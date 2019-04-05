package util;

import java.util.ListIterator;

public interface StateIterator<E> extends ListIterator<E> {
	boolean hasCurrent();
	E current();
	int currentIndex();
}
