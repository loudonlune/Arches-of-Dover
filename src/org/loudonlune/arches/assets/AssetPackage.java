package org.loudonlune.arches.assets;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class AssetPackage extends Asset {
	private Class<? extends Asset> expectedType;
	private List<Asset> children;
	
	public AssetPackage(AssetPath path, Class<? extends Asset> expectedType) {
		super(path);
		this.expectedType = expectedType;
		this.children = new ArrayList<>();
		
		if (Modifier.isAbstract(expectedType.getModifiers()))
			throw new IllegalArgumentException("AssetPackage constructor only accepts final classes.");
	}

	public Class<? extends Asset> getExpectedType() {
		return expectedType;
	}
	
	public void search() {
		
	}

	public List<Asset> getChildren() {
		return children;
	}
}
