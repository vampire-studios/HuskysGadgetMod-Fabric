package io.github.vampirestudios.hgm.network;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.network.task.MessageNotification;
import io.github.vampirestudios.hgm.network.task.MessageRequest;
import net.minecraft.util.Identifier;

public class MessageChannels {
    public static final MessageChannel<MessageNotification> NOTIFICATION = new MessageChannel<>(
            new Identifier(HuskysGadgetMod.MOD_ID, "notification"), MessageNotification::serialize, MessageNotification::deserialize
    );
    public static final MessageChannel<MessageRequest> REQUEST = new MessageChannel<>(
            new Identifier(HuskysGadgetMod.MOD_ID, "request"), MessageRequest::serialize, MessageRequest::deserialize
    );
}