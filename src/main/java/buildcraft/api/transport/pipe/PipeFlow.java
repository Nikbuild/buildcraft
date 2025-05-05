/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import buildcraft.api.core.EnumPipePart;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PipeFlow
/*    */   implements ICapabilityProvider
/*    */ {
/*    */   public static final int NET_ID_FULL_STATE = 0;
/*    */   public static final int NET_ID_UPDATE = 1;
/*    */   public final IPipe pipe;
/*    */   
/*    */   public PipeFlow(IPipe pipe) {
/* 34 */     this.pipe = pipe;
/*    */   }
/*    */   
/*    */   public PipeFlow(IPipe pipe, NBTTagCompound nbt) {
/* 38 */     this.pipe = pipe;
/*    */   }
/*    */   
/*    */   public NBTTagCompound writeToNbt() {
/* 42 */     return new NBTTagCompound();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writePayload(int id, PacketBuffer buffer, Side side) {}
/*    */ 
/*    */   
/*    */   public void readPayload(int id, PacketBuffer buffer, Side side) throws IOException {}
/*    */   
/*    */   public void sendPayload(int id) {
/* 52 */     Side side = (this.pipe.getHolder().getPipeWorld()).field_72995_K ? Side.CLIENT : Side.SERVER;
/* 53 */     sendCustomPayload(id, buf -> writePayload(id, buf, side));
/*    */   }
/*    */   
/*    */   public final void sendCustomPayload(int id, IPipeHolder.IWriter writer) {
/* 57 */     this.pipe.getHolder().sendMessage(IPipeHolder.PipeMessageReceiver.FLOW, buffer -> {
/*    */           buffer.writeBoolean(true);
/*    */           buffer.writeShort(id);
/*    */           writer.write(buffer);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTick() {}
/*    */ 
/*    */   
/*    */   public void addDrops(NonNullList<ItemStack> toDrop, int fortune) {}
/*    */ 
/*    */   
/*    */   public boolean onFlowActivate(EntityPlayer player, RayTraceResult trace, float hitX, float hitY, float hitZ, EnumPipePart part) {
/* 73 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
/* 78 */     return (getCapability(capability, facing) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
/* 83 */     return null;
/*    */   }
/*    */   
/*    */   public abstract boolean canConnect(EnumFacing paramEnumFacing, PipeFlow paramPipeFlow);
/*    */   
/*    */   public abstract boolean canConnect(EnumFacing paramEnumFacing, TileEntity paramTileEntity);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeFlow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */