package org.loudonlune.arches.rendering;

import java.util.List;

import org.loudonlune.arches.assets.Asset;
import org.loudonlune.arches.event.Event;

/**
 * Allows handlers to slip in extra assets to load if they determine it needed.
 * @author Adam Hassick
 */
public class AssetPreInitializationEvent extends Event {
	private Renderer renderer;
	private List<Asset> assetList;
	
	public AssetPreInitializationEvent(Renderer renderer, List<Asset> assetList) {
		this.renderer = renderer;
		this.assetList = assetList;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public List<Asset> getAssetList() {
		return assetList;
	}
}
