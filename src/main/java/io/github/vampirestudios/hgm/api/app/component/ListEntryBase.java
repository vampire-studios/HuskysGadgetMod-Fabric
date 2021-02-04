//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.api.app.component;

public class ListEntryBase<TYPE> extends Container {
    protected final TYPE entry;
    protected final int listIndex;

    public ListEntryBase(int x, int y, int width, int height, TYPE entry, int listIndex) {
        super(x, y, width, height);
        this.entry = entry;
        this.listIndex = listIndex;
    }

    public TYPE getEntry() {
        return this.entry;
    }

    public int getListIndex() {
        return this.listIndex;
    }
}
