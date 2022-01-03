package org.loudonlune.arches.event;

public abstract class KeyEvent extends Event {
	private long key;
	
	public KeyEvent(long key) {
		this.setKey(key);
	}

	public long getKey() {
		return key;
	}

	protected void setKey(long key) {
		this.key = key;
	}
}
