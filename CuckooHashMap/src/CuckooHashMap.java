import java.util.*;

public class CuckooHashMap<K, V> {
//Initialize the two arrays of type entry
	private Entry<K, V>[] table1;
	private Entry<K, V>[] table2;
	private int prime;
	private int capacity;
	private int size;

	@SuppressWarnings("unchecked")
	//default constructor 
	public CuckooHashMap() {
		capacity = 4;
		prime = 10007;
		table1 = new Entry[capacity / 2];
		table2 = new Entry[capacity / 2];
	}

	@SuppressWarnings("unchecked")
	public CuckooHashMap(int cap) {
		//checks if even or odd 
		if (cap % 2 == 0) {
			capacity = cap;
		} else
		//makes even because we divide capacity by two often
			capacity = cap + 1;
		prime = 10007;

		table1 = new Entry[capacity / 2];
		table2 = new Entry[capacity / 2];
	}

	@SuppressWarnings("unchecked")
	public CuckooHashMap(int cap, int prime) {
		//constructor with non default settings
		if (cap % 2 == 0) {
			capacity = cap;
		} else
			capacity = cap + 1;
		this.prime = prime;

		table1 = new Entry[capacity / 2];
		table2 = new Entry[capacity / 2];
	}

	public float loadFactor() {
		//load factor = num of entries / space available
		return (float) size / (float) capacity;

	}

	public int size() {
		return size;
	}

	public int capacity() {
		return capacity;
	}

	public V get(K key) {
		//our two hash functions are called to make two hash values based on key 
		final int hash = h1(key);
		final int hash2 = h2(key);
		//if the index exists and hash value equals the key then we found it
		if (table1[hash] != null && table1[hash].getKey().equals(key)) {
			//so we now can return the value at the index of hash value and get its value
			return table1[hash].getValue();
		}
		//same process as above but do on the second array
		if (table2[hash2] != null && table2[hash2].getKey().equals(key)) {
			return table2[hash2].getValue();
		}
		return null;
	}
	
	public V remove(K key) {
		//get hash value based on key
		int hash = h1(key);
		V removed = null;
		//if we find the value remove it
		if (table1[hash] != null && table1[hash].getKey().equals(key)) {
			removed = table1[hash].getValue();
			//set to null
			table1[hash] = null;
			//decrease size
			size--;

		}
		//same process as above but check second array
		int hash2 = h2(key);
		if (table2[hash2] != null && table2[hash2].getKey().equals(key)) {
			removed = table2[hash2].getValue();
			table2[hash2] = null;
			size--;
		}
		if (loadFactor() < .25 && capacity > 4) {
			resize(capacity / 2);
		}
		return removed;
	}

	public V put(K key, V value) {
		//make our entry to put in array & a temp
		Entry<K, V> entry = new Entry<>(key, value);
		Entry<K, V> temp;

		if (table1[h1(entry.getKey())] != null && table1[h1(entry.getKey())].getKey().equals(key)) {
			return table1[h1(entry.getKey())].setValue(value);
		}
		if (table2[h2(entry.getKey())] != null && table2[h2(entry.getKey())].getKey().equals(key)) {
			return table2[h2(entry.getKey())].setValue(value);
		}
		for (int i = 0; i < size() + 1; i++) {
			temp = table1[h1(entry.getKey())];
			table1[h1(entry.getKey())] = entry;
			if (temp == null) {
				size++;
				if (loadFactor() > 0.5) {
					resize(capacity * 2);
				}
				return null;
			}
			entry = temp;
			temp = table2[h2(entry.getKey())];
			table2[h2(entry.getKey())] = entry;
			if (temp == null) {
				size++;
				if (loadFactor() > 0.5) {
					resize(capacity * 2);
				}
				return null;
			}
			entry = temp;
		}
		rehash();
		return put(entry.getKey(), entry.getValue());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> arrayList = new ArrayList<Entry<K, V>>();
		for (int i = 0; i < capacity / 2; i++) {
			if (table1[i] != null) {
				arrayList.add(table1[i]);
			}
			if (table2[i] != null) {
				arrayList.add(table2[i]);
			}

		}
		return arrayList;
	}

	public Iterable<K> keySet() {
		ArrayList<K> arrayList = new ArrayList<K>();
		for (int i = 0; i < capacity / 2; i++) {
			if (table1[i] != null) {
				arrayList.add(table1[i].getKey());
			}
			if (table2[i] != null) {
				arrayList.add(table2[i].getKey());
			}

		}
		return arrayList;

	}

	public Iterable<V> valueSet() {
		ArrayList<V> arrayList = new ArrayList<V>();
		for (int i = 0; i < capacity / 2; i++) {
			if (table1[i] != null) {
				arrayList.add(table1[i].getValue());
			}
			if (table2[i] != null) {
				arrayList.add(table2[i].getValue());
			}

		}
		return arrayList;
	}

	private void rehash() {
		nextPrime();
		resize(capacity);
	}

	private void resize(int newCap) {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(capacity);
		for (Entry<K, V> e : entrySet()) {
			buffer.add(e);
		}
		capacity = newCap;
		size = 0;
		table1 = new Entry[capacity / 2];
		table2 = new Entry[capacity / 2];
		for (Entry<K, V> e : buffer) {
			put(e.getKey(), e.getValue());
		}
	}

	private int h1(K key) {
		return (Math.abs(key.hashCode()) % prime) % (capacity / 2);

	}

	private int h2(K key) {
		return ((Math.abs(key.hashCode()) / prime) % prime) % (capacity / 2);
	}

	public int nextPrime() {
		int count;
		prime++;
		while (true) {
			count = 0;
			for (int i = 2; i <= Math.sqrt((double) prime); i++) {
				if (prime % i == 0)
					count++;
			}
			if (count == 0)
				return prime;
			else {
				prime++;
				continue;
			}
		}
	}

}
