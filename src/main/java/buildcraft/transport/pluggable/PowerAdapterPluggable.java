/*     */ package buildcraft.transport.pluggable;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.render.ITextureStates;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableRenderer;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.lib.render.FakeBlock;
/*     */ import buildcraft.core.lib.utils.MatrixTranformations;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class PowerAdapterPluggable
/*     */   extends PipePluggable
/*     */   implements IEnergyHandler {
/*     */   private static final int MAX_POWER = 40;
/*     */   private IPipeTile container;
/*     */   
/*     */   protected static final class PowerAdapterPluggableRenderer
/*     */     implements IPipePluggableRenderer {
/*  29 */     private float zFightOffset = 2.4414062E-4F;
/*     */ 
/*     */     
/*     */     public void renderPluggable(RenderBlocks renderblocks, IPipe pipe, ForgeDirection side, PipePluggable pipePluggable, ITextureStates blockStateMachine, int renderPass, int x, int y, int z) {
/*  33 */       if (renderPass != 0) {
/*     */         return;
/*     */       }
/*     */       
/*  37 */       float[][] zeroState = new float[3][2];
/*     */       
/*  39 */       IIcon[] icons = FakeBlock.INSTANCE.getTextureState().popArray();
/*  40 */       int bottom = side.ordinal();
/*     */       
/*  42 */       for (int i = 0; i < 6; i++) {
/*  43 */         icons[i] = BuildCraftTransport.instance.pipeIconProvider.getIcon(((i & 0x6) == (bottom & 0x6)) ? PipeIconProvider.TYPE.PipePowerAdapterBottom
/*  44 */             .ordinal() : PipeIconProvider.TYPE.PipePowerAdapterSide.ordinal());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  49 */       zeroState[0][0] = 0.1875F;
/*  50 */       zeroState[0][1] = 0.8125F;
/*     */       
/*  52 */       zeroState[1][0] = 0.0F;
/*  53 */       zeroState[1][1] = 0.1251F;
/*     */       
/*  55 */       zeroState[2][0] = 0.1875F;
/*  56 */       zeroState[2][1] = 0.8125F;
/*     */       
/*  58 */       float[][] rotated = MatrixTranformations.deepClone(zeroState);
/*  59 */       MatrixTranformations.transform(rotated, side);
/*     */       
/*  61 */       renderblocks.func_147782_a(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/*  62 */       renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */       
/*  64 */       icons[bottom ^ 0x1] = BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipePowerAdapterTop.ordinal()); icons[bottom] = BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipePowerAdapterTop.ordinal());
/*     */ 
/*     */       
/*  67 */       zeroState[0][0] = 0.25F + this.zFightOffset;
/*  68 */       zeroState[0][1] = 0.75F - this.zFightOffset;
/*     */       
/*  70 */       zeroState[1][0] = 0.125F;
/*  71 */       zeroState[1][1] = 0.25F + this.zFightOffset;
/*     */       
/*  73 */       zeroState[2][0] = 0.25F + this.zFightOffset;
/*  74 */       zeroState[2][1] = 0.75F - this.zFightOffset;
/*     */       
/*  76 */       rotated = MatrixTranformations.deepClone(zeroState);
/*  77 */       MatrixTranformations.transform(rotated, side);
/*     */       
/*  79 */       renderblocks.func_147782_a(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/*  80 */       renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */       
/*  82 */       FakeBlock.INSTANCE.getTextureState().pushArray();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(IPipeTile pipe, ForgeDirection direction) {
/*  92 */     this.container = pipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate() {
/*  97 */     this.container = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getDropItems(IPipeTile pipe) {
/* 112 */     return new ItemStack[] { new ItemStack(BuildCraftTransport.powerAdapterItem) };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlocking(IPipeTile pipe, ForgeDirection direction) {
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(ForgeDirection side) {
/* 122 */     float[][] bounds = new float[3][2];
/*     */     
/* 124 */     bounds[0][0] = 0.1875F;
/* 125 */     bounds[0][1] = 0.8125F;
/*     */     
/* 127 */     bounds[1][0] = 0.0F;
/* 128 */     bounds[1][1] = 0.251F;
/*     */     
/* 130 */     bounds[2][0] = 0.1875F;
/* 131 */     bounds[2][1] = 0.8125F;
/*     */     
/* 133 */     MatrixTranformations.transform(bounds, side);
/* 134 */     return AxisAlignedBB.func_72330_a(bounds[0][0], bounds[1][0], bounds[2][0], bounds[0][1], bounds[1][1], bounds[2][1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipePluggableRenderer getRenderer() {
/* 139 */     return new PowerAdapterPluggableRenderer();
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
/*     */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
/* 154 */     int maxR = Math.min(40, maxReceive);
/* 155 */     if (this.container != null && this.container.getPipe() instanceof IEnergyHandler) {
/* 156 */       int energyCanReceive = ((IEnergyHandler)this.container.getPipe()).receiveEnergy(from, maxR, true);
/* 157 */       if (!simulate) {
/* 158 */         return ((IEnergyHandler)this.container.getPipe()).receiveEnergy(from, energyCanReceive, false);
/*     */       }
/* 160 */       return energyCanReceive;
/*     */     } 
/*     */     
/* 163 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
/* 168 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection from) {
/* 173 */     if (this.container.getPipe() instanceof IEnergyHandler) {
/* 174 */       return ((IEnergyHandler)this.container.getPipe()).getEnergyStored(from);
/*     */     }
/* 176 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection from) {
/* 182 */     if (this.container.getPipe() instanceof IEnergyHandler) {
/* 183 */       return ((IEnergyHandler)this.container.getPipe()).getMaxEnergyStored(from);
/*     */     }
/* 185 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresRenderUpdate(PipePluggable o) {
/* 196 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pluggable\PowerAdapterPluggable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */