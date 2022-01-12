package org.loudonlune.arches.assets;

import java.util.Optional;

public abstract class Asset {
	protected AssetPath path;
	protected Optional<LoadedAsset> loadedAsset;
	
	public Asset(AssetPath path) {
		this.path = path;
		loadedAsset = Optional.empty();
	}
	
	
}
