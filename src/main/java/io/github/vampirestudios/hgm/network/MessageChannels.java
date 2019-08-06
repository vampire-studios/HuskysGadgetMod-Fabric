package io.github.vampirestudios.hgm.network;

import com.jamieswhiteshirt.clotheslinefabric.common.network.message.*;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.network.task.MessageNotification;
import io.github.vampirestudios.hgm.network.task.MessageRequest;
import io.github.vampirestudios.hgm.network.task.MessageResponse;
import net.minecraft.util.Identifier;

public class MessageChannels {
    public static final MessageChannel<MessageNotification> NOTIFICATION = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "notification"), MessageNotification::serialize, MessageNotification::deserialize
    );
    public static final MessageChannel<MessageRequest> REQUEST = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "request"), MessageRequest::serialize, MessageRequest::deserialize
    );
    public static final MessageChannel<MessageResponse> HIT_NETWORK = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "hit_network"), MessageResponse::serialize, MessageResponse::deserialize
    );
    public static final MessageChannel<RemoveAttachmentMessage> REMOVE_ATTACHMENT = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "remove_attachment"), RemoveAttachmentMessage::serialize, RemoveAttachmentMessage::deserialize
    );
    public static final MessageChannel<RemoveNetworkMessage> REMOVE_NETWORK = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "remove_network"), RemoveNetworkMessage::serialize, RemoveNetworkMessage::deserialize
    );
    public static final MessageChannel<ResetConnectorStateMessage> RESET_CONNECTOR_STATE = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "reset_connector_state"), ResetConnectorStateMessage::serialize, ResetConnectorStateMessage::deserialize
    );
    public static final MessageChannel<SetAttachmentMessage> SET_ATTACHMENT = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "set_attachment"), SetAttachmentMessage::serialize, SetAttachmentMessage::deserialize
    );
    public static final MessageChannel<SetConnectorStateMessage> SET_CONNECTOR_STATE = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "set_connector_state"), SetConnectorStateMessage::serialize, SetConnectorStateMessage::deserialize
    );
    public static final MessageChannel<StopUsingItemOnMessage> STOP_USING_ITEM_ON = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "stop_using_item_on"), StopUsingItemOnMessage::serialize, StopUsingItemOnMessage::deserialize
    );
    public static final MessageChannel<TryUseItemOnNetworkMessage> TRY_USE_ITEM_ON_NETWORK = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "try_use_item_on_network"), TryUseItemOnNetworkMessage::serialize, TryUseItemOnNetworkMessage::deserialize
    );
    public static final MessageChannel<UpdateNetworkMessage> UPDATE_NETWORK = new MessageChannel<>(
        new Identifier(HuskysGadgetMod.MOD_ID, "update_network"), UpdateNetworkMessage::serialize, UpdateNetworkMessage::deserialize
    );
}