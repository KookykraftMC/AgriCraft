package com.infinityraider.agricraft.network;

import com.infinityraider.agricraft.AgriCraft;
import com.infinityraider.agricraft.container.ContainerSeedStorageBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageContainerSeedStorage extends MessageBase {
    private Item item;
    private int meta;
    private int amount;
    private EntityPlayer player;
    private int slotId;

    @SuppressWarnings("unused")
    public MessageContainerSeedStorage() {}

    public MessageContainerSeedStorage(ItemStack stack, int slotId) {
        this.item = stack.getItem();
        this.meta = stack.getItemDamage();
        this.amount = stack.stackSize;
        this.player = AgriCraft.proxy.getClientPlayer();
        this.slotId = slotId;
    }

    @Override
    public Side getMessageHandlerSide() {
        return Side.SERVER;
    }

    @Override
    protected void processMessage(MessageContext ctx) {
        Container container = player.openContainer;
        if(container!=null && container instanceof ContainerSeedStorageBase) {
            ContainerSeedStorageBase storage = (ContainerSeedStorageBase) container;
            storage.moveStackFromTileEntityToPlayer(slotId, new ItemStack(item, amount, meta));
        }
    }

    @Override
    protected IMessage getReply(MessageContext ctx) {
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.item = this.readItemFromByteBuf(buf);
        this.meta = buf.readInt();
        this.amount = buf.readInt();
        this.player = this.readPlayerFromByteBuf(buf);
        this.slotId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        this.writeItemToByteBuf(this.item, buf);
        buf.writeInt(this.meta);
        buf.writeInt(this.amount);
        this.writePlayerToByteBuf(this.player, buf);
        buf.writeInt(this.slotId);
    }
}
