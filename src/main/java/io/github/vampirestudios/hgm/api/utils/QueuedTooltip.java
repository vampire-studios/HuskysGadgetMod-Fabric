/*
 * Roughly Enough Items by Danielshe.
 * Licensed under the MIT License.
 */

package io.github.vampirestudios.hgm.api.utils;

import com.google.common.collect.Lists;
import io.github.vampirestudios.hgm.api.ClientUtils;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class QueuedTooltip {
    
    private Point location;
    private List<String> text;
    private Consumer<QueuedTooltip> consumer = null;
    
    private QueuedTooltip(Point location, List<String> text) {
        this.location = location;
        this.text = Collections.unmodifiableList(text);
    }
    
    public static QueuedTooltip create(Point location, List<String> text) {
        return new QueuedTooltip(location, text);
    }
    
    public static QueuedTooltip create(Point location, String... text) {
        return QueuedTooltip.create(location, Lists.newArrayList(text));
    }
    
    public static QueuedTooltip create(List<String> text) {
        return QueuedTooltip.create(ClientUtils.getMouseLocation(), text);
    }
    
    public static QueuedTooltip create(String... text) {
        return QueuedTooltip.create(ClientUtils.getMouseLocation(), text);
    }
    
    @Deprecated
    public QueuedTooltip setSpecialRenderer(Consumer<QueuedTooltip> consumer) {
        this.consumer = consumer;
        return this;
    }
    
    @Deprecated
    public Consumer<QueuedTooltip> getConsumer() {
        return consumer;
    }
    
    public Point getLocation() {
        return location;
    }
    
    public int getX() {
        return getLocation().x;
    }
    
    public int getY() {
        return getLocation().y;
    }
    
    public List<String> getText() {
        return text;
    }
    
}
