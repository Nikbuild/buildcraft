/*     */ package buildcraft.api.transport.pluggable;
/*     */ 
/*     */ import buildcraft.api.transport.pipe.IPipeHolder;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PipePluggable
/*     */ {
/*     */   public final PluggableDefinition definition;
/*     */   public final IPipeHolder holder;
/*     */   public final EnumFacing side;
/*     */   
/*     */   public PipePluggable(PluggableDefinition definition, IPipeHolder holder, EnumFacing side) {
/*  38 */     this.definition = definition;
/*  39 */     this.holder = holder;
/*  40 */     this.side = side;
/*     */   }
/*     */   
/*     */   public NBTTagCompound writeToNbt() {
/*  44 */     NBTTagCompound nbt = new NBTTagCompound();
/*  45 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCreationPayload(PacketBuffer buffer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePayload(PacketBuffer buffer, Side side) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPayload(PacketBuffer buffer, Side side, MessageContext ctx) throws IOException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scheduleNetworkUpdate() {
/*  64 */     this.holder.scheduleNetworkUpdate(new IPipeHolder.PipeMessageReceiver[] { IPipeHolder.PipeMessageReceiver.PLUGGABLES[this.side.ordinal()] });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {}
/*     */ 
/*     */   
/*     */   public abstract AxisAlignedBB getBoundingBox();
/*     */   
/*     */   public boolean isBlocking() {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getCapability(@Nonnull Capability<T> cap) {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getInternalCapability(@Nonnull Capability<T> cap) {
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove() {}
/*     */ 
/*     */   
/*     */   public void addDrops(NonNullList<ItemStack> toDrop, int fortune) {
/*  98 */     ItemStack stack = getPickStack();
/*  99 */     if (!stack.func_190926_b()) {
/* 100 */       toDrop.add(stack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickStack() {
/* 108 */     return ItemStack.field_190927_a;
/*     */   }
/*     */   
/*     */   public boolean onPluggableActivate(EntityPlayer player, RayTraceResult trace, float hitX, float hitY, float hitZ) {
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PluggableModelKey getModelRenderKey(BlockRenderLayer layer) {
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getBlockColor(int tintIndex) {
/* 126 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeConnected() {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSideSolid() {
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(@Nullable Entity exploder, Explosion explosion) {
/* 143 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean canConnectToRedstone(@Nullable EnumFacing to) {
/* 147 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pluggable\PipePluggable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */