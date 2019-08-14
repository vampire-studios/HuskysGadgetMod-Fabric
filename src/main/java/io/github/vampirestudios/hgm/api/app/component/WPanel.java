package io.github.vampirestudios.hgm.api.app.component;

import com.google.common.collect.Lists;
import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.component.render.BackgroundPainter;
import io.github.vampirestudios.hgm.gui.GuiDescription;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;

public class WPanel extends Component {
	protected final List<Component> children = Lists.newArrayList();
	@Environment(EnvType.CLIENT)
	private BackgroundPainter backgroundPainter = null;

	public void remove(Component w) {
		children.remove(w);
	}
	
	@Override
	public boolean canResize() {
		return true;
	}
	
	@Environment(EnvType.CLIENT)
	public WPanel setBackgroundPainter(BackgroundPainter painter) {
		this.backgroundPainter = painter;
		return this;
	}
	
	/**
	 * Uses this Panel's layout rules to reposition and resize components to fit nicely in the panel.
	 */
	public void layout() {
		for(Component child : children) {
			if (child instanceof WPanel) ((WPanel) child).layout();
			expandToFit(child);
		}
	}
	
	protected void expandToFit(Component w) {
		int pushRight = w.getX()+w.getWidth();
		int pushDown =  w.getY()+w.getHeight();
		this.setSize(Math.max(this.getWidth(), pushRight), Math.max(this.getHeight(), pushDown));
	}
	
	@Override
	public Component mouseReleased(int x, int y, int button) {
		if (children.isEmpty()) return super.mouseReleased(x, y, button);
		for(int i=children.size()-1; i>=0; i--) { //Backwards so topmost widgets get priority
			Component child = children.get(i);
			if (    x>=child.getX() &&
					y>=child.getY() &&
					x<child.getX()+child.getWidth() &&
					y<child.getY()+child.getHeight()) {
				return child.mouseReleased(x-child.getX(), y-child.getY(), button);
			}
		}
		return super.mouseReleased(x, y, button);
	}
	
	@Override
	public Component mouseClicked(int x, int y, int button) {
		if (children.isEmpty()) return super.mouseClicked(x, y, button);
		for(int i=children.size()-1; i>=0; i--) { //Backwards so topmost widgets get priority
			Component child = children.get(i);
			if (    x>=child.getX() &&
					y>=child.getY() &&
					x<child.getX()+child.getWidth() &&
					y<child.getY()+child.getHeight()) {
				return child.mouseClicked(x-child.getX(), y-child.getY(), button);
			}
		}
		return super.mouseClicked(x, y, button);
	}
	
	@Override
	public void mouseDragged(int x, int y, int button) {
		if (children.isEmpty()) return;
		for(int i=children.size()-1; i>=0; i--) { //Backwards so topmost widgets get priority
			Component child = children.get(i);
			if (    x>=child.getX() &&
					y>=child.getY() &&
					x<child.getX()+child.getWidth() &&
					y<child.getY()+child.getHeight()) {
				child.mouseDragged(x-child.getX(), y-child.getY(), button);
				return; //Only send the message to the first valid recipient
			}
		}
		super.mouseDragged(x, y, button);
	}
	
	@Override
	public void onClick(int x, int y, int button) {
		if (children.isEmpty()) return;
		for(int i=children.size()-1; i>=0; i--) { //Backwards so topmost widgets get priority
			Component child = children.get(i);
			if (    x>=child.getX() &&
					y>=child.getY() &&
					x<child.getX()+child.getWidth() &&
					y<child.getY()+child.getHeight()) {
				child.onClick(x-child.getX(), y-child.getY(), button);
				return; //Only send the message to the first valid recipient
			}
		}
	}
	
	@Override
	public void validate(GuiDescription c) {
		layout();
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void paintBackground(int x, int y) {
		if (backgroundPainter!=null) backgroundPainter.paintBackground(x, y, this);
		
		for(Component child : children) {
			child.paintBackground(x + child.getX(), y + child.getY());
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void paintForeground(int x, int y, int mouseX, int mouseY) {
		for(Component child : children) {
			child.paintForeground(x + child.getX(), y + child.getY(), mouseX, mouseY);
		}
	}
}