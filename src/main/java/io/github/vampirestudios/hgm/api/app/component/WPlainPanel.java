package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;

public class WPlainPanel extends WPanel {

	public void add(Component w, int x, int y) {
		children.add(w);
		w.parent = this;
		w.setLocation(x, y);
		if (w.canResize()) {
			w.setSize(18, 18);
		}
		
		expandToFit(w);
		//valid = false;
	}

	public void add(Component w, int x, int y, int width, int height) {
		children.add(w);
		w.parent = this;
		w.setLocation(x, y);
		if (w.canResize()) {
			w.setSize(width, height);
		}

		expandToFit(w);
		//valid = false;
	}
}