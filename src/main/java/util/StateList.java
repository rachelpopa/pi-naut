package util;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

public class StateList<T> implements StateIterator<T> {

	private int pos = -1;
	private List<T> list;
	private boolean circular = false;

	public StateList(List<T> list) {
		if (!list.isEmpty()) {
			this.pos = 0;
		}
		this.list = list;
	}

	public StateList(List<T> list, boolean circular) {
		if (!list.isEmpty()) {
			this.pos = 0;
		}
		this.list = list;
		this.circular = circular;
	}

	public void next(List<T> newList) {
		pos = getNewPos(newList);
		list = newList;
	}

	private int getNewPos(List<T> newList) {
		if (newList.isEmpty()) {
			return -1;
		} else if (nonNull(current()) && newList.stream().anyMatch(current()::equals)) {
			return newList.indexOf(current());
		} else {
			return 0;
		}
	}

	@Override
	public boolean hasNext() {
		if (notInitialized()) {
			return false;
		}
		return circular || !isTail();
	}

	@Override
	public T next() {
		if (notInitialized()) {
			return null;
		}
		if (!isTail() || circular) {
			pos = nextIndex();
		}
		return list.get(pos);
	}

	@Override
	public int nextIndex() {
		if (!hasNext()) {
			return pos;
		}
		return isTail() ? 0 : pos + 1;
	}

	@Override
	public boolean hasCurrent() {
		return !notInitialized();
	}

	@Override
	public T current() {
		if (notInitialized()) {
			return null;
		}
		return list.get(pos);
	}

	@Override
	public int currentIndex() {
		return pos;
	}

	@Override
	public boolean hasPrevious() {
		if (notInitialized()) {
			return false;
		}
		return circular || !isHead();
	}

	@Override
	public T previous() {
		if (notInitialized()) {
			return null;
		}
		if (!isHead() || circular) {
			pos = previousIndex();
		}
		return list.get(pos);
	}

	@Override
	public int previousIndex() {
		if (!hasPrevious()) {
			return pos;
		}
		return isHead() ? list.size() - 1 : pos - 1;
	}

	@Override
	public void remove() {
		if (notInitialized()) {
			return;
		}
		list.remove(pos);
		pos = previousIndex();
	}

	@Override
	public void set(T t) {
		if (list.indexOf(t) == -1) {
			return;
		}
		list.set(list.indexOf(t), t);
	}

	@Override
	public void add(T t) {
		list.add(t);
	}

	public T get(int index) {
		return list.get(index);
	}

	public T jumpTo(T item) {
		int i = list.indexOf(item);
		if (i > -1 && i != currentIndex()) {
			pos = i;
		}
		return current();
	}

	public int size() {
		return list.size();
	}

	public Stream<T> stream() {
		return list.stream();
	}

	public boolean isCircular() {
		return circular;
	}

	private boolean notInitialized() {
		return pos == -1;
	}

	private boolean isHead() {
		return pos == 0;
	}

	private boolean isTail() {
		return pos == list.size() - 1;
	}

}
