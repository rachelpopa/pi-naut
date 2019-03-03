package util;

import java.util.Iterator;

public interface CircularIterator<E> extends Iterator<E> {
	boolean hasPrevious();
	E previous();
	int nextIndex();
	int previousIndex();
}
