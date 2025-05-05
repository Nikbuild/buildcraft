/*     */ package buildcraft.api.transport.pipe;
/*     */ 
/*     */ import buildcraft.api.statements.containers.IRedstoneStatementContainer;
/*     */ import buildcraft.api.transport.IWireManager;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IPipeHolder
/*     */   extends IRedstoneStatementContainer
/*     */ {
/*     */   World getPipeWorld();
/*     */   
/*     */   BlockPos getPipePos();
/*     */   
/*     */   TileEntity getPipeTile();
/*     */   
/*     */   IPipe getPipe();
/*     */   
/*     */   @Nullable
/*     */   PipePluggable getPluggable(EnumFacing paramEnumFacing);
/*     */   
/*     */   @Nullable
/*     */   TileEntity getNeighbourTile(EnumFacing paramEnumFacing);
/*     */   
/*     */   @Nullable
/*     */   IPipe getNeighbourPipe(EnumFacing paramEnumFacing);
/*     */   
/*     */   @Nullable
/*     */   <T> T getCapabilityFromPipe(EnumFacing paramEnumFacing, @Nonnull Capability<T> paramCapability);
/*     */   
/*     */   IWireManager getWireManager();
/*     */   
/*     */   GameProfile getOwner();
/*     */   
/*     */   boolean fireEvent(PipeEvent paramPipeEvent);
/*     */   
/*     */   void scheduleRenderUpdate();
/*     */   
/*     */   void scheduleNetworkUpdate(PipeMessageReceiver... paramVarArgs);
/*     */   
/*     */   void scheduleNetworkGuiUpdate(PipeMessageReceiver... paramVarArgs);
/*     */   
/*     */   void sendMessage(PipeMessageReceiver paramPipeMessageReceiver, IWriter paramIWriter);
/*     */   
/*     */   void sendGuiMessage(PipeMessageReceiver paramPipeMessageReceiver, IWriter paramIWriter);
/*     */   
/*     */   void onPlayerOpen(EntityPlayer paramEntityPlayer);
/*     */   
/*     */   void onPlayerClose(EntityPlayer paramEntityPlayer);
/*     */   
/*     */   public static interface IWriter
/*     */   {
/*     */     void write(PacketBuffer param1PacketBuffer);
/*     */   }
/*     */   
/*     */   public enum PipeMessageReceiver
/*     */   {
/*  76 */     BEHAVIOUR(null),
/*  77 */     FLOW(null),
/*  78 */     PLUGGABLE_DOWN((String)EnumFacing.DOWN),
/*  79 */     PLUGGABLE_UP((String)EnumFacing.UP),
/*  80 */     PLUGGABLE_NORTH((String)EnumFacing.NORTH),
/*  81 */     PLUGGABLE_SOUTH((String)EnumFacing.SOUTH),
/*  82 */     PLUGGABLE_WEST((String)EnumFacing.WEST),
/*  83 */     PLUGGABLE_EAST((String)EnumFacing.EAST),
/*  84 */     WIRES(null);
/*     */ 
/*     */     
/*  87 */     public static final PipeMessageReceiver[] VALUES = values();
/*  88 */     public static final PipeMessageReceiver[] PLUGGABLES = new PipeMessageReceiver[6];
/*     */     
/*     */     static {
/*  91 */       for (PipeMessageReceiver type : VALUES) {
/*  92 */         if (type.face != null) {
/*  93 */           PLUGGABLES[type.face.ordinal()] = type;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public final EnumFacing face;
/*     */     
/*     */     PipeMessageReceiver(EnumFacing face) {
/* 101 */       this.face = face;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\IPipeHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */