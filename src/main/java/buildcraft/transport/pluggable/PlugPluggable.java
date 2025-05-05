/*     */ package buildcraft.transport.pluggable;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.render.ITextureStates;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableRenderer;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.lib.utils.MatrixTranformations;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class PlugPluggable
/*     */   extends PipePluggable
/*     */ {
/*     */   protected static final class PlugPluggableRenderer
/*     */     implements IPipePluggableRenderer {
/*  23 */     public static final IPipePluggableRenderer INSTANCE = new PlugPluggableRenderer();
/*  24 */     private float zFightOffset = 2.4414062E-4F;
/*     */ 
/*     */     
/*     */     public void renderPluggable(RenderBlocks renderblocks, IPipe pipe, ForgeDirection side, PipePluggable pipePluggable, ITextureStates blockStateMachine, int renderPass, int x, int y, int z) {
/*  28 */       if (renderPass != 0) {
/*     */         return;
/*     */       }
/*     */       
/*  32 */       float[][] zeroState = new float[3][2];
/*     */ 
/*     */       
/*  35 */       zeroState[0][0] = 0.25F + this.zFightOffset;
/*  36 */       zeroState[0][1] = 0.75F - this.zFightOffset;
/*     */       
/*  38 */       zeroState[1][0] = 0.125F;
/*  39 */       zeroState[1][1] = 0.251F;
/*     */       
/*  41 */       zeroState[2][0] = 0.25F + this.zFightOffset;
/*  42 */       zeroState[2][1] = 0.75F - this.zFightOffset;
/*     */       
/*  44 */       blockStateMachine.getTextureState().set(BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipePlug.ordinal()));
/*     */       
/*  46 */       float[][] rotated = MatrixTranformations.deepClone(zeroState);
/*  47 */       MatrixTranformations.transform(rotated, side);
/*     */       
/*  49 */       renderblocks.func_147782_a(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/*  50 */       renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */ 
/*     */       
/*  53 */       zeroState[0][0] = 0.3125F + this.zFightOffset;
/*  54 */       zeroState[0][1] = 0.6875F + this.zFightOffset;
/*     */       
/*  56 */       zeroState[1][0] = 0.25F;
/*  57 */       zeroState[1][1] = 0.375F;
/*     */       
/*  59 */       zeroState[2][0] = 0.3125F;
/*  60 */       zeroState[2][1] = 0.6875F;
/*     */       
/*  62 */       rotated = MatrixTranformations.deepClone(zeroState);
/*  63 */       MatrixTranformations.transform(rotated, side);
/*     */       
/*  65 */       renderblocks.func_147782_a(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/*  66 */       renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getDropItems(IPipeTile pipe) {
/*  86 */     return new ItemStack[] { new ItemStack(BuildCraftTransport.plugItem) };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlocking(IPipeTile pipe, ForgeDirection direction) {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(ForgeDirection side) {
/*  96 */     float[][] bounds = new float[3][2];
/*     */     
/*  98 */     bounds[0][0] = 0.25F;
/*  99 */     bounds[0][1] = 0.75F;
/*     */     
/* 101 */     bounds[1][0] = 0.125F;
/* 102 */     bounds[1][1] = 0.251F;
/*     */     
/* 104 */     bounds[2][0] = 0.25F;
/* 105 */     bounds[2][1] = 0.75F;
/*     */     
/* 107 */     MatrixTranformations.transform(bounds, side);
/* 108 */     return AxisAlignedBB.func_72330_a(bounds[0][0], bounds[1][0], bounds[2][0], bounds[0][1], bounds[1][1], bounds[2][1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipePluggableRenderer getRenderer() {
/* 113 */     return PlugPluggableRenderer.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requiresRenderUpdate(PipePluggable o) {
/* 128 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pluggable\PlugPluggable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */