/* Wilson Joshua Beach Entry class
 * Entry class has key and value
 * get key, get value
 * and set key, set value
 */
public class Entry<K, V> {
		private K k;
		private V v;

		public Entry(K key, V value) {
			k = key;
			v = value;
		}

		public K getKey() {
			return k;
		}

		public V getValue() {
			return v;
		}
		
		public void setKey(K key) { 
			k= key;
		}
		public V setValue(V value) {
			V old = v;
			v = value;
			return old;
		}
	}

