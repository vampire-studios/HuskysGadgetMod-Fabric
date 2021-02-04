package io.github.vampirestudios.hgm.core;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.component.WPanel;
import io.github.vampirestudios.hgm.gui.GuiDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class ClientCottonScreen extends Screen {
	protected GuiDescription description;
	protected int left = 0;
	protected int top = 0;
	protected int containerWidth = 0;
	protected int containerHeight = 0;
	
	protected Component lastResponder = null;
	
	public ClientCottonScreen(GuiDescription description) {
		super(new LiteralText(""));
		this.description = description;
	}
	
	public ClientCottonScreen(Text title, GuiDescription description) {
		super(title);
		this.description = description;
	}
	
	public GuiDescription getDescription() {
		return description;
	}

	
	@Override
	public void init(MinecraftClient client, int screenWidth, int screenHeight) {
		
		super.init(client, screenWidth, screenHeight);
		
		description.addPainters();
		reposition(screenWidth, screenHeight);
	}
	
	public void reposition(int screenWidth, int screenHeight) {
		if (description!=null) {
			WPanel root = description.getRootPanel();
			if (root!=null) {
				this.containerWidth = root.getWidth();
				this.containerHeight = root.getHeight();
				
				this.left = (screenWidth - root.getWidth()) / 2;
				this.top = (screenHeight - root.getHeight()) / 2;
			}
		}
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		
		if (description!=null) {
			WPanel root = description.getRootPanel();
			if (root!=null) {
				root.paintForeground(left, top, mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void renderBackground(MatrixStack matrixStack) {
		super.renderBackground(matrixStack);
		
		if (description!=null) {
			WPanel root = description.getRootPanel();
			if (root!=null) {
				root.paintBackground(left, top);
			}
		}
		
		if (getTitle() != null) {
			textRenderer.draw(new MatrixStack(), getTitle(), left, top, description.getTitleColor());
		}
	}
	
	
	@Override
	public void tick() {
		super.tick();
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		if (description.getRootPanel()==null) return super.mouseClicked(mouseX, mouseY, mouseButton);
		
		boolean result = super.mouseClicked(mouseX, mouseY, mouseButton);
		int containerX = (int)mouseX-left;
		int containerY = (int)mouseY-top;
		if (containerX<0 || containerY<0 || containerX>=width || containerY>=height) return result;
		lastResponder = description.getRootPanel().mouseClicked(containerX, containerY, mouseButton);
		return result;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
		if (description.getRootPanel()==null) return super.mouseReleased(mouseX, mouseY, mouseButton);
		
		boolean result = super.mouseReleased(mouseX, mouseY, mouseButton);
		int containerX = (int)mouseX-left;
		int containerY = (int)mouseY-top;
		if (containerX<0 || containerY<0 || containerX>=width || containerY>=height) return result;

		Component responder = description.getRootPanel().mouseReleased(containerX, containerY, mouseButton);
		if (responder!=null && responder==lastResponder) description.getRootPanel().onClick(containerX, containerY, mouseButton);
		lastResponder = null;
		return result;
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int mouseButton, double unknown_1, double unknown_2) {
		if (description.getRootPanel()==null) return super.mouseDragged(mouseX, mouseY, mouseButton, unknown_1, unknown_2);
		
		boolean result = super.mouseDragged(mouseX, mouseY, mouseButton, unknown_1, unknown_2);
		int containerX = (int)mouseX-left;
		int containerY = (int)mouseY-top;
		if (containerX<0 || containerY<0 || containerX>=width || containerY>=height) return result;
		description.getRootPanel().mouseDragged(containerX, containerY, mouseButton);
		return result;
	}
}