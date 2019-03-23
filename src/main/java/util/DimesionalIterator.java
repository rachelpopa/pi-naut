package util;

import java.util.Iterator;
import java.util.List;

public interface DimesionalIterator<E> extends Iterator<E> {
	E previous();
	E current();
	E next();
	int previousIndex();
	int currentIndex();
	int nextIndex();
	List<E> getList();
}
