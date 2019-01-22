package forneymonegerie;

public class Forneymonegerie implements ForneymonegerieInterface {
	private ForneymonType[] collection;
	private int size;
	private int typeSize;

	private static final int START_SIZE = 16;

	Forneymonegerie() {
		collection = new ForneymonType[START_SIZE];
		size = 0;
		typeSize = 0;
	}

	public boolean empty() {
		return typeSize == 0;
	};

	public int size() {
		return size;
	};

	public int typeSize() {
		return typeSize;
	};

	public boolean collect(String typeToAdd) {
		ForneymonType forneymonToAdd = new ForneymonType(typeToAdd, 1);
		if (empty()) {
			insertAt(forneymonToAdd, 0);
			return true;
		} else {
			for (int i = 0; i < typeSize; i++) {
				if (isSameType(collection[i], forneymonToAdd)) {
					collection[i].count++;
					size++;
					return true;
				} else if (!isSameType(collection[i], forneymonToAdd) && (i == typeSize - 1)) {
					insertAt(forneymonToAdd, typeSize);
					return true;
				}
			}
		}
		return false;
	};

	public boolean release(String typeToRelease) {
		int index = placementInCollection(typeToRelease);
		if (index != -1) {
			if (collection[index].count > 1) {
				collection[index].count--;
				size--;
				return true;
			} else {
				removeAt(index);
				size--;
				return true;
			}
		}
		return false;
	}

	public void releaseType(String typeToNuke) {
		int index = placementInCollection(typeToNuke);
		if (index != -1) {
			size -= collection[index].count;
			removeAt(index);
		}
	}

	public int countType(String typeToCount) {
		ForneymonType forneymon = new ForneymonType(typeToCount, 0);
		ForneymonType current;
		for (int i = 0; i < typeSize; i++) {
			current = collection[i];
			if (isSameType(current, forneymon)) {
				return current.count;
			}
		}
		return 0;
	};

	public boolean contains(String typeToCheck) {
		ForneymonType forneymon = new ForneymonType(typeToCheck, 0);
		ForneymonType current;
		for (int i = 0; i < typeSize; i++) {
			current = collection[i];
			if (isSameType(current, forneymon)) {
				return true;
			}
		}
		return false;
	}

	public String nth(int n) {
		String nthType = "";
		int numForneymon = n + 1;
		if (n < 0 || n >= size) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < typeSize; i++) {
			if (numForneymon - collection[i].count > 0) {
				numForneymon -= collection[i].count;
			} else {
				nthType = collection[i].type;
				break;
			}
		}
		return nthType;
	};

	public String rarestType() {
		ForneymonType rarestType = collection[0];
		if (size == 0) {
			return null;
		}
		for (int i = 1; i < typeSize; i++) {
			if (collection[i].count <= rarestType.count) {
				rarestType = collection[i];
			}
		}
		return rarestType.type;
	};

	public Forneymonegerie clone() {
		Forneymonegerie clone = new Forneymonegerie();
		for (int i = 0; i < this.typeSize; i++) {
			clone.collect(this.collection[i].type);
			clone.collection[i].count = this.collection[i].count;
		}
		clone.size = this.size;
		clone.typeSize = this.typeSize;
		return clone;
	};

	public void trade(Forneymonegerie other) {
		this.checkAndGrow();
		ForneymonType[] originalCollection = this.collection;
		int originalSize = this.size;
		int originalTypeSize = this.typeSize;
		this.collection = other.collection;
		this.typeSize = other.typeSize;
		this.size = other.size;
		other.collection = originalCollection;
		other.typeSize = originalTypeSize;
		other.size = originalSize;
	}

  @Override
	public String toString() {
		String stringRepresentation = "[ ";
		for (int i = 0; i < typeSize; i++) {
			String type = collection[i].type;
			int count = collection[i].count;
			stringRepresentation += ("\"" + type + "\": " + count);
			if (i != typeSize - 1) {
				stringRepresentation += ", ";
			} else {
				stringRepresentation += " ]";
			}
		}
		return stringRepresentation;
	}

	public static Forneymonegerie diffMon(Forneymonegerie y1, Forneymonegerie y2) {
		Forneymonegerie diffMonagerie = y1.clone();
		int diffMonIndex;
		for (int i = 0; i < y2.typeSize; i++) {
			if (diffMonagerie.contains(y2.collection[i].type)) {
				diffMonIndex = diffMonagerie.placementInCollection(y2.collection[i].type);
				if (diffMonagerie.collection[diffMonIndex].count != y2.collection[i].count) {
					releaseDiffInForneymonCount(diffMonagerie, y2, diffMonIndex, i);
				} else {
					diffMonagerie.releaseType(y2.collection[i].type);
				}
			}
		}
		return diffMonagerie;
	};

	public static void releaseDiffInForneymonCount(Forneymonegerie y1, Forneymonegerie y2, int indexY1, int indexY2) {
		int y1Count = y1.collection[indexY1].count;
		int y2Count = y2.collection[indexY2].count;
		int differenceInForneymonCount = y1Count - y2Count;
		if (differenceInForneymonCount > 0) {
			for (int j = 0; j < differenceInForneymonCount; j++) {
				y1.release(y2.collection[indexY2].type);
			}
		} else {
			y1.releaseType(y2.collection[indexY2].type);

		}
	}

	public static boolean sameCollection(Forneymonegerie y1, Forneymonegerie y2) {
		return diffMon(y1, y2).empty() && diffMon(y2, y1).empty();
	};

	public void removeAt(int index) {
		shiftLeft(index);
		typeSize--;
	}

	private void insertAt(ForneymonType toAdd, int index) {
		checkAndGrow();
		shiftRight(index);
		collection[index] = toAdd;
		size++;
		typeSize++;

	}

	public boolean isSameType(ForneymonType y1, ForneymonType y2) {
		return y1.type == y2.type;
	}

	public int placementInCollection(String type) {
		for (int i = 0; i < typeSize; i++) {
			if (collection[i].type == type) {
				return i;
			}
		}
		return -1;
	}

	private void checkAndGrow() {
		if (typeSize < collection.length) {
			return;
		}
		ForneymonType[] newItems = new ForneymonType[collection.length * 2];
		for (int i = 0; i < collection.length; i++) {
			newItems[i] = collection[i];
		}
		collection = newItems;
	}

	private void shiftLeft(int index) {
		for (int i = index; i < typeSize - 1; i++) {
			collection[i] = collection[i + 1];
		}
	}

	private void shiftRight(int index) {
		for (int i = typeSize - 1; i >= index; i--) {
			collection[i + 1] = collection[i];
		}
	}

	private class ForneymonType {
		String type;
		int count;

		ForneymonType(String t, int c) {
			type = t;
			count = c;
		}
	}
}
