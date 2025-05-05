/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import buildcraft.api.core.EnumPipePart;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.entity.Entity;
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
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ public abstract class PipeBehaviour
/*    */   implements ICapabilityProvider
/*    */ {
/*    */   public final IPipe pipe;
/*    */   
/*    */   public PipeBehaviour(IPipe pipe) {
/* 26 */     this.pipe = pipe;
/*    */   }
/*    */   
/*    */   public PipeBehaviour(IPipe pipe, NBTTagCompound nbt) {
/* 30 */     this.pipe = pipe;
/*    */   }
/*    */   
/*    */   public NBTTagCompound writeToNbt() {
/* 34 */     NBTTagCompound nbt = new NBTTagCompound();
/*    */     
/* 36 */     return nbt;
/*    */   }
/*    */   
/*    */   public void writePayload(PacketBuffer buffer, Side side) {}
/*    */   
/*    */   public void readPayload(PacketBuffer buffer, Side side, MessageContext ctx) {}
/*    */   
/*    */   public int getTextureIndex(EnumFacing face) {
/* 44 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canConnect(EnumFacing face, PipeBehaviour other) {
/* 50 */     return true;
/*    */   }
/*    */   
/*    */   public boolean canConnect(EnumFacing face, TileEntity oTile) {
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   public boolean onPipeActivate(EntityPlayer player, RayTraceResult trace, float hitX, float hitY, float hitZ, EnumPipePart part) {
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityCollide(Entity entity) {}
/*    */   
/*    */   public void onTick() {}
/*    */   
/*    */   public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
/* 67 */     return (getCapability(capability, facing) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
/* 72 */     return null;
/*    */   }
/*    */   
/*    */   public void addDrops(NonNullList<ItemStack> toDrop, int fortune) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeBehaviour.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */