package org.loudonlune.arches.assets;

import java.io.File;
import java.util.Collection;
import java.util.Stack;
import java.util.regex.Pattern;

public class AssetPath {
	public static final String DELIMITER = ":";
	private Stack<String> pathElements;
	
	protected static Pattern pathPattern = Pattern.compile("^[a-zA-Z0-9_]$");
	protected static boolean validate(String path) {
		for (String s : path.split(DELIMITER)) {
			if (!pathPattern.matcher(s).matches())
				return false;
		}
		
		return true;
	}
	
	public AssetPath() {
		this("");
	}
	
	public static AssetPath fromString(String path) {
		if (!validate(path))
			return null;
		return new AssetPath(path);
	}
	
	private AssetPath(String path) {
		pathElements = new Stack<String>();
		
		for (String elem : path.split(DELIMITER))
			pathElements.push(elem);
	}
	
	public Collection<String> getElements() {
		return pathElements;
	}
	
	public File asRelativeFile() {
		return new File(
				getElements()
				.stream()
				.reduce("", 
						(t, u) -> t.isEmpty() ? u : "/" + u
				));
	}
	
	public void join(AssetPath other) {
		for (String elem : other.pathElements)
			pathElements.push(elem);
	}
	
	public boolean join(String name) {
		if (!validate(name))
			return false;
		
		pathElements.push(name);
		return true;
	}
	
	public void pop() {
		pathElements.pop();
	}
}
