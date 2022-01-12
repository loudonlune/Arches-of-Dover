package org.loudonlune.arches.rendering;

public interface Renderable {
	static final Renderable NO_OP = new Renderable() {

		@Override
		public void render(RenderContext rxcon) {}
		
	};
	
	public void render(final RenderContext rxcon);
}
