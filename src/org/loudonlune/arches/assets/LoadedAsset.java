package org.loudonlune.arches.assets;

public abstract class LoadedAsset {
	private Asset asset;
	
	protected LoadedAsset(Asset asset) {
		this.asset = asset;
	}

	public Asset getAsset() {
		return asset;
	}
	
	public abstract void unload();
}
