   
   
   initialCapacity是初始容量，loadFactor翻译过来叫负载因子
   
    static final int DEFAULT_INITIAL_CAPACITY = 4;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final int MAXIMUM_CAPACITY = 1 << 30;

	// put 时 初始化一个 数组 
	 static final HashMapEntry<?,?>[] EMPTY_TABLE = {};
    transient HashMapEntry<K,V>[] table = (HashMapEntry<K,V>[]) EMPTY_TABLE;	
   
   public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        } else if (initialCapacity < DEFAULT_INITIAL_CAPACITY) {
            initialCapacity = DEFAULT_INITIAL_CAPACITY;
        }

        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
      
        threshold = initialCapacity;
        init();
    }

  
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
	
    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashMap(Map<? extends K, ? extends V> m) {
        this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1,
                      DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
        inflateTable(threshold);

        putAllForCreate(m);
    }
		// 数据 对象 结构
	  /** @hide */  // Android added.
    static class HashMapEntry<K,V> implements Map.Entry<K,V> {
        final K key;
        V value;
        HashMapEntry<K,V> next;
        int hash;

        /**
         * Creates new entry.
         */
        HashMapEntry(int h, K k, V v, HashMapEntry<K,V> n) {
            value = v;
            next = n;
            key = k;
            hash = h;
        }
		// 创建一个数组
	  void resize(int newCapacity) {
        HashMapEntry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }

        HashMapEntry[] newTable = new HashMapEntry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
    }
	
	
	