package org.loudonlune.arches.rendering;

import org.loudonlune.arches.event.Event;

public class ContextValidateEvent extends Event {
	private RenderContext rxcon;
	
	public ContextValidateEvent(RenderContext rxcon) {
		this.rxcon = rxcon;
	}
	
	public RenderContext getRenderContext() {
		return rxcon;
	}
}
