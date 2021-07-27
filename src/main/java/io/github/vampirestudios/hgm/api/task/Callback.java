package io.github.vampirestudios.hgm.api.task;

import net.minecraft.nbt.NbtCompound;

/**
 * A simple interface to handle processing responses by {@link Task}.
 * Callbacks are necessary for the updating of any UI component.
 */
public interface Callback<T> {

    /**
     * Executes the callback. You should perform any changes to
     * your UI in this method. The NBT tag contains the same data
     * as {@link Task#processResponse(NbtCompound)}'s
     * tag does.
     *
     * @param t       the response object
     * @param success if the {@link Task} performed it's intended action correctly.
     */
    void execute(T t, boolean success);
}
