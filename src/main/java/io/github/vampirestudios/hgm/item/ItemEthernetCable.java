package io.github.vampirestudios.hgm.item;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.entity.NetworkDeviceBlockEntity;
import io.github.vampirestudios.hgm.block.entity.RouterBlockEntity;
import io.github.vampirestudios.hgm.core.network.Router;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class ItemEthernetCable extends BaseItem {
    public ItemEthernetCable() {
        super(new Settings().maxCount(1));
    }

    private static double getDistance(BlockPos source, BlockPos target) {
        return Math.sqrt(source.getSquaredDistance(new Vec3i(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5)));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext itemUsageContext_1) {
        if (!itemUsageContext_1.getWorld().isClient) {
            ItemStack heldItem = itemUsageContext_1.getPlayer().getStackInHand(itemUsageContext_1.getHand());
            BlockEntity tileEntity = itemUsageContext_1.getWorld().getBlockEntity(itemUsageContext_1.getBlockPos());

            if (tileEntity instanceof RouterBlockEntity) {
                if (!heldItem.hasTag()) {
                    sendGameInfoMessage(itemUsageContext_1.getPlayer(), "message.invalid_cable");
                    return ActionResult.SUCCESS;
                }

                RouterBlockEntity tileEntityRouter = (RouterBlockEntity) tileEntity;
                Router router = tileEntityRouter.getRouter();

                CompoundTag tag = heldItem.getTag();
                BlockPos devicePos = BlockPos.fromLong(tag.getLong("pos"));

                BlockEntity tileEntity1 = itemUsageContext_1.getWorld().getBlockEntity(devicePos);
                if (tileEntity1 instanceof NetworkDeviceBlockEntity) {
                    NetworkDeviceBlockEntity TileEntityNetworkDevice = (NetworkDeviceBlockEntity) tileEntity1;
                    if (!router.hasDevice(TileEntityNetworkDevice)) {
                        if (router.addDevice(TileEntityNetworkDevice)) {
                            TileEntityNetworkDevice.connect(router);
                            heldItem.decrement(1);
                            if (getDistance(tileEntity1.getPos(), tileEntityRouter.getPos()) > HuskysGadgetMod.config.routerSettings.signalRange) {
                                sendGameInfoMessage(itemUsageContext_1.getPlayer(), "message.successful_registered");
                            } else {
                                sendGameInfoMessage(itemUsageContext_1.getPlayer(), "message.successful_connection");
                            }
                        } else {
                            sendGameInfoMessage(itemUsageContext_1.getPlayer(), "message.router_max_devices");
                        }
                    } else {
                        sendGameInfoMessage(itemUsageContext_1.getPlayer(), "message.device_already_connected");
                    }
                } else {
                    if (router.addDevice(tag.getUuid("id"), tag.getString("name"))) {
                        heldItem.decrement(1);
                        sendGameInfoMessage(itemUsageContext_1.getPlayer(), "message.successful_registered");
                    } else {
                        sendGameInfoMessage(itemUsageContext_1.getPlayer(), "message.router_max_devices");
                    }
                }
                return ActionResult.SUCCESS;
            }

            if (tileEntity instanceof NetworkDeviceBlockEntity) {
                NetworkDeviceBlockEntity TileEntityNetworkDevice = (NetworkDeviceBlockEntity) tileEntity;
                if (!heldItem.hasTag()) {
                    heldItem.setTag(new CompoundTag());
                }
                CompoundTag tag = heldItem.getTag();
                Objects.requireNonNull(tag).putUuid("id", TileEntityNetworkDevice.getId());
                tag.putString("name", TileEntityNetworkDevice.getDeviceName());
                tag.putLong("pos", TileEntityNetworkDevice.getPos().asLong());
                heldItem.setCustomName(new LiteralText(Formatting.GRAY.toString() + Formatting.BOLD.toString() + I18n.translate("item.ethernet_cable.name")));

                sendGameInfoMessage(itemUsageContext_1.getPlayer(), "message.select_router");
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.SUCCESS;
    }

    private void sendGameInfoMessage(PlayerEntity player, String message) {
        /*if (player instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) player).connection.sendPacket(new SChatPacket(new TranslationTextComponent(message), ChatType.GAME_INFO));
        }*/
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isClient) {
            ItemStack heldItem = playerIn.getStackInHand(handIn);
            if (playerIn.isSneaking()) {
                heldItem.removeCustomName();
                heldItem.setTag(null);
                return new TypedActionResult<>(ActionResult.SUCCESS, heldItem);
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        if (stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if (tag != null) {
                tooltip.add(new LiteralText(Formatting.RED.toString() + Formatting.BOLD.toString() + "ID: " + Formatting.RESET.toString() + tag.getUuid("id")));
                tooltip.add(new LiteralText(Formatting.RED.toString() + Formatting.BOLD.toString() + "Device: " + Formatting.RESET.toString() + tag.getString("name")));

                BlockPos devicePos = BlockPos.fromLong(tag.getLong("pos"));
                StringBuilder builder = new StringBuilder();
                builder.append(Formatting.RED.toString() + Formatting.BOLD.toString() + "X: " + Formatting.RESET.toString() + devicePos.getX() + " ");
                builder.append(Formatting.RED.toString() + Formatting.BOLD.toString() + "Y: " + Formatting.RESET.toString() + devicePos.getY() + " ");
                builder.append(Formatting.RED.toString() + Formatting.BOLD.toString() + "Z: " + Formatting.RESET.toString() + devicePos.getZ());
                tooltip.add(new LiteralText(builder.toString()));
            }
        } else {
            if (!Screen.hasShiftDown()) {
                tooltip.add(new LiteralText(Formatting.GRAY.toString() + "Use this cable to connect"));
                tooltip.add(new LiteralText(Formatting.GRAY.toString() + "a device to a router."));
                tooltip.add(new LiteralText(Formatting.YELLOW.toString() + "Hold SHIFT for How-To"));
                return;
            }

            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "Start by right clicking a"));
            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "device with this cable"));
            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "then right click the "));
            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "router you want to"));
            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "connect this device to."));
        }
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean hasEnchantmentGlint(ItemStack itemStack_1) {
        return itemStack_1.hasTag();
    }

}
