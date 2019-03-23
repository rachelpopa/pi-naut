package util;

import java.util.List;

import static java.util.Objects.isNull;

public class ImmutableList<T> implements Iterable<T> {
	private final List<T> list;
	private boolean circular = false;

	public ImmutableList(List<T> list) {
		this.list = list;
	}

	public ImmutableList(List<T> list, boolean circular) {
		this.list = list;
		this.circular = circular;
	}

	@Override
	public DimesionalIterator<T> iterator() {
		return new DimesionalIterator<T>() {
			int pos = -1;

			@Override
			public boolean hasNext() {
				if (isNull(list) || list.isEmpty()) {
					return false;
				}
				return circular || isTail() || (!circular && notInitialized());
			}

			@Override
			public T current() {
				if (notInitialized() && hasNext()) {
					pos = nextIndex();
					if (notInitialized()) {
						return null;
					}
				}
				return list.get(pos);
			}

			@Override
			public T next() {
				if (!isTail() || circular) {
					pos = nextIndex();
					if (notInitialized()) {
						return null;
					}
				}
				return list.get(pos);
			}

			@Override
			public T previous() {
				if (!isHead() || circular) {
					pos = previousIndex();
					if (notInitialized()) {
						return null;
					}
				}
				return list.get(pos);
			}

			@Override
			public int currentIndex() {
				return pos;
			}

			@Override
			public int nextIndex() {
				if ((notInitialized() && !hasNext()) || (!circular && isTail())) {
					return pos;
				} else if (circular && isTail()) {
					return 0;
				} else {
					return pos + 1;
				}
			}

			@Override
			public int previousIndex() {
				if (notInitialized() || (!circular && isHead())) {
					return pos;
				} else if (circular && isHead()) {
					return list.size() - 1;
				} else {
					return pos - 1;
				}
			}

			public List<T> getList() {
				return list;
			}

			private boolean notInitialized() {
				return pos == -1;
			}

			private boolean isHead() {
				if (notInitialized()) {
					return false;
				}
				return pos == 0;
			}

			private boolean isTail() {
				if (notInitialized()) {
					return false;
				}
				return pos == list.size() - 1;
			}

		};
	}
}
