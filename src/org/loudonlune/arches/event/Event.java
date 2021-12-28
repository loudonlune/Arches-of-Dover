package org.loudonlune.arches.event;

/**
 * Abstract class that can be fired by the EventBus.
 * @author Adam Hassick
 */
public abstract class Event {
	private Long id = null;
	private boolean fired = false;
	protected boolean cancelled = false;
	
	/**
	 * Fires the event.
	 * Meant to be called ONLY by the EventBus when it fires this event.
	 */
	void fire() {
		fired = true;
	}
	
	/**
	 * Registers the event.
	 * Meant to be called ONLY by the EventBus when the event is queued for firing.
	 * @param id The ID assigned by the bus.
	 */
	void register(long id) {
		this.id = id;
	}
	
	/**
	 * @return Whether or not this event has been queued.
	 */
	public boolean isQueued() {
		return id != null;
	}
	
	/**
	 * @return The ID of this event as assigned by the EventBus. Returns null if not queued or already fired.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return True if this event has been fired by the EventBus.
	 */
	public boolean isFired() {
		return fired;
	}
	
	/**
	 * @return True if this event has been cancelled.
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Sets the event cancellation status.
	 * @param c Value to set cancellation status to.
	 */
	public void setCancelled(boolean c) {
		cancelled = c;
	}
}
