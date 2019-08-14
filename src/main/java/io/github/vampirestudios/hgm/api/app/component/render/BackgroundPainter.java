package io.github.vampirestudios.hgm.api.app.component.render;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.core.ScreenDrawing;

public interface BackgroundPainter {

	/**
	 * Paint the specified panel to the screen.
	 * @param left The absolute position of the left of the panel, in gui-screen coordinates
	 * @param top The absolute position of the top of the panel, in gui-screen coordinates
	 * @param panel The panel being painted
	 */
	public void paintBackground(int left, int top, Component panel);

	
	public static BackgroundPainter VANILLA = (left, top, panel) ->
			ScreenDrawing.drawGuiPanel(left-8, top-8, panel.getWidth()+16, panel.getHeight()+16, false);

	public static BackgroundPainter VANILLA_DARK = (left, top, panel) ->
			ScreenDrawing.drawGuiPanel(left-8, top-8, panel.getWidth()+16, panel.getHeight()+16, true);

	public static BackgroundPainter createColorful(int panelColor) {
		return (left, top, panel) ->
				ScreenDrawing.drawGuiPanel(left-8, top-8, panel.getWidth()+16, panel.getHeight()+16, panelColor);
	}
	
	public static BackgroundPainter createColorful(int panelColor, float contrast) {
		return (left, top, panel) -> {
			int shadowColor = ScreenDrawing.multiplyColor(panelColor, 1.0f - contrast);
			int highlightColor = ScreenDrawing.multiplyColor(panelColor, 1.0f + contrast);
			
			ScreenDrawing.drawGuiPanel(left-8, top-8, panel.getWidth()+16, panel.getHeight()+16, shadowColor, panelColor, highlightColor, 0xFF000000);
		};
	}
}