package io.github.vampirestudios.hgm.gui;

import io.github.vampirestudios.hgm.api.app.component.WPanel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.PropertyDelegate;

import javax.annotation.Nullable;

public interface GuiDescription {
	public WPanel getRootPanel();
	public int getTitleColor();
	
	public GuiDescription setRootPanel(WPanel panel);
	public GuiDescription setTitleColor(int color);
	public GuiDescription setPropertyDelegate(PropertyDelegate delegate);
	
	@Environment(EnvType.CLIENT)
	public void addPainters();
	
	@Nullable
	public PropertyDelegate getPropertyDelegate();
	
}