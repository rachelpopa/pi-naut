package util;

import java.util.List;

public class StateList<T> implements StateIterator<T> {

	private int pos = -1;
	private final List<T> list;
	private boolean circular = false;

	public StateList(List<T> list) {
		this.list = list;
		if (!list.isEmpty()) {
			this.pos = 0;
		}
	}

	public StateList(List<T> list, boolean circular) {
		this.list = list;
		if (!list.isEmpty()) {
			this.pos = 0;
		}
		this.circular = circular;
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
		return isTail()
				? 0
				: pos + 1;
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
		return isHead()
				? list.size() - 1
				: pos - 1;
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

	public List<T> getList() {
		return list;
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
