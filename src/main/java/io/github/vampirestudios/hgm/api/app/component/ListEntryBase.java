//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.api.app.component;

import javax.annotation.Nullable;

public class ListEntryBase<TYPE> extends Container {
    @Nullable
    protected final TYPE entry;
    protected final int listIndex;

    public ListEntryBase(int x, int y, int width, int height, @Nullable TYPE entry, int listIndex) {
        super(x, y, width, height);
        this.entry = entry;
        this.listIndex = listIndex;
    }

    @Nullable
    public TYPE getEntry() {
        return this.entry;
    }

    public int getListIndex() {
        return this.listIndex;
    }
}
