package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.IIcon2;
import io.github.vampirestudios.hgm.core.ScreenDrawing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class WButton extends Component {
	private Text label;
	protected int color = Label.DEFAULT_TEXT_COLOR;
	protected int darkmodeColor = Label.DEFAULT_TEXT_COLOR;
	private boolean enabled = true;
	public IIcon2 icon;
	
	private Runnable onClick;
	
	public WButton() {
		
	}
	
	public WButton(Text text) {
		this.label = text;
	}

	public WButton(IIcon2 icon) {
		this.icon = icon;
	}

	public WButton(Text text, IIcon2 icon) {
		this.label = text;
		this.icon = icon;
	}
	
	@Override
	public boolean canResize() {
		return true;
	}
	
	
	@Override
	public void paintForeground(int x, int y, int mouseX, int mouseY) {
		boolean hovered = (mouseX>=x && mouseY>=y && mouseX<x+getWidth() && mouseY<y+getHeight());
		int state = 1; //1=regular. 2=hovered. 0=disabled.
		if (!enabled) state = 0;
		else if (hovered) state = 2;
		
		float px = 1/256f;
		float buttonLeft = 0 * px;
		float buttonTop = (46 + (state*20)) * px;
		int halfWidth = getWidth()/2;
		if (halfWidth>198) halfWidth=198;
		float buttonWidth = halfWidth*px;
		float buttonHeight = 20*px;
		
		float buttonEndLeft = (200-(getWidth()/2)) * px;
		
		ScreenDrawing.rect(AbstractButtonWidget.WIDGETS_LOCATION, x, y, getWidth()/2, 20, buttonLeft, buttonTop, buttonLeft+buttonWidth, buttonTop+buttonHeight, 0xFFFFFFFF);
		ScreenDrawing.rect(AbstractButtonWidget.WIDGETS_LOCATION, x+(getWidth()/2), y, getWidth()/2, 20, buttonEndLeft, buttonTop, 200*px, buttonTop+buttonHeight, 0xFFFFFFFF);
		
		if (label!=null) {
			int color = 0xE0E0E0;
			if (!enabled) {
				color = 0xA0A0A0;
			} else if (hovered) {
				color = 0xFFFFA0;
			}
			
			ScreenDrawing.drawCenteredWithShadow(new MatrixStack(), label.asString(), x+(getWidth()/2), y + ((20 - 8) / 2), color); //LibGuiClient.config.darkMode ? darkmodeColor : color);
		}

		ScreenDrawing.textureFillGui(x, y, icon.width(), icon.height(), icon.texture(), icon.textureU(), icon.textureV());

		super.paintForeground(x, y, mouseX, mouseY);
	}
	
	
	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		
		if (enabled) {
			MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));

			if (onClick!=null) onClick.run();
		}
	}

	public void setOnClick(Runnable r) {
		this.onClick = r;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}