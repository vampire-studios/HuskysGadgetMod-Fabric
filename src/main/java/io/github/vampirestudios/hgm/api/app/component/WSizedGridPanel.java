package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;

public class WSizedGridPanel extends WPanel {
    private int gridSize = 16;
    
    public WSizedGridPanel(int gridSize) {
        this.gridSize = gridSize;
    }
    
    public void add(Component w, int x, int y) {
        children.add(w);
        w.setParent(this);
        w.setLocation(x * gridSize, y * gridSize);
        if (w.canResize()) {
            w.setSize(gridSize, gridSize);
        }
        
        expandToFit(w);
    }
    
    public void add(Component w, int x, int y, int width, int height) {
        children.add(w);
        w.setParent(this);
        w.setLocation(x * gridSize, y * gridSize);
        if (w.canResize()) {
            w.setSize(width * gridSize, height * gridSize);
        }
        
        expandToFit(w);
    }
}