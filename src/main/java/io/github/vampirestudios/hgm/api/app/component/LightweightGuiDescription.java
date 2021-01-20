package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.component.render.BackgroundPainter;
import io.github.vampirestudios.hgm.gui.GuiDescription;
import javax.annotation.Nullable;
import net.minecraft.screen.PropertyDelegate;

/**
 * A GuiDescription without any associated Minecraft classes
 */
public class LightweightGuiDescription implements GuiDescription {
	protected WPanel rootPanel = new WGridPanel();
	protected int titleColor = 0xFFFFFFFF;
	protected int darkmodeTitleColor = 0xFFFFFFFF;
	protected PropertyDelegate propertyDelegate;
	
	@Override
	public WPanel getRootPanel() {
		return rootPanel;
	}

	@Override
	public int getTitleColor() {
		return /*(LibGuiClient.config.darkMode) ? darkmodeTitleColor : */titleColor;
	}

	@Override
	public GuiDescription setRootPanel(WPanel panel) {
		this.rootPanel = panel;
		return this;
	}

	@Override
	public GuiDescription setTitleColor(int color) {
		this.titleColor = color;
		return this;
	}

	@Override
	public void addPainters() {
		if (this.rootPanel!=null) {
			this.rootPanel.setBackgroundPainter(BackgroundPainter.VANILLA);
		}
	}

	@Override
	@Nullable
	public PropertyDelegate getPropertyDelegate() {
		return propertyDelegate;
	}

	@Override
	public GuiDescription setPropertyDelegate(PropertyDelegate delegate) {
		this.propertyDelegate = delegate;
		return this;
	}

}