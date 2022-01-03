package org.loudonlune.arches;

import java.util.logging.Logger;

import org.loudonlune.arches.event.EventHandler;
import org.loudonlune.arches.event.KeyPressEvent;
import org.loudonlune.arches.event.KeyReleaseEvent;

@EventHandler
public class TestKeyEventHandler {
	public void onKeyPressEvent(KeyPressEvent kpe) {
		Logger.getGlobal().info("A key was pressed. Key codepoint: " + kpe.getKey());
	}
	
	public void onKeyReleaseEvent(KeyReleaseEvent kre) {
		Logger.getGlobal().info("A key was released. Key codepoint: " + kre.getKey());
	}
}
