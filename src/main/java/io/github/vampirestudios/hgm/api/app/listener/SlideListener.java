package io.github.vampirestudios.hgm.api.app.listener;

import io.github.vampirestudios.hgm.api.app.component.Slider;

/**
 * The slider listener interface. Used for getting
 * the percentage value on a {@link Slider} every
 * time it is moved.
 */
public interface SlideListener {
    /**
     * Called when a sliders position has moved
     *
     * @param percentage the percentage from the left
     */
    void onSlide(float percentage);
}
