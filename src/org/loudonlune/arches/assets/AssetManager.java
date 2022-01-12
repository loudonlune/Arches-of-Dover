package org.loudonlune.arches.assets;

import java.io.File;

public class AssetManager {
	private File root;
	
	public AssetManager() {
		this(new File("assets"));
	}
	
	public AssetManager(File root) {
		this.root = root;
	}
	
	public File resolve(AssetPath path) {
		return new File(root, path.asRelativeFile().getPath());
	}
	
	public boolean exists(AssetPath path) {
		return resolve(path).exists();
	}

	
	
	
}
