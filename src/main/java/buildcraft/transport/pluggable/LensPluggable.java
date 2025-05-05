/*     */ package buildcraft.transport.pluggable;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.api.core.render.ITextureStates;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableRenderer;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.lib.render.FakeBlock;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.core.lib.utils.MatrixTranformations;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import buildcraft.transport.pipes.events.PipeEventItem;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class LensPluggable
/*     */   extends PipePluggable
/*     */ {
/*     */   public int color;
/*     */   public boolean isFilter;
/*     */   protected IPipeTile container;
/*     */   private ForgeDirection side;
/*     */   
/*     */   private static final class LensPluggableRenderer
/*     */     implements IPipePluggableRenderer {
/*  33 */     public static final IPipePluggableRenderer INSTANCE = new LensPluggableRenderer();
/*     */ 
/*     */ 
/*     */     
/*     */     private static final float zFightOffset = 2.4414062E-4F;
/*     */ 
/*     */ 
/*     */     
/*     */     public void renderPluggable(RenderBlocks renderblocks, IPipe pipe, ForgeDirection side, PipePluggable pipePluggable, ITextureStates blockStateMachine, int renderPass, int x, int y, int z) {
/*  42 */       float[][] zeroState = new float[3][2];
/*     */ 
/*     */       
/*  45 */       zeroState[0][0] = 0.1875F;
/*  46 */       zeroState[0][1] = 0.8125F;
/*     */       
/*  48 */       zeroState[1][0] = 0.0F;
/*  49 */       zeroState[1][1] = 0.125F;
/*     */       
/*  51 */       zeroState[2][0] = 0.1875F;
/*  52 */       zeroState[2][1] = 0.8125F;
/*     */       
/*  54 */       if (renderPass == 1) {
/*  55 */         int color = ((LensPluggable)pipePluggable).color;
/*     */         
/*  57 */         blockStateMachine.setRenderMask(1 << side.ordinal() | 1 << (side.ordinal() ^ 0x1));
/*     */         
/*  59 */         for (int i = 0; i < 3; i++) {
/*  60 */           zeroState[i][0] = zeroState[i][0] + 2.4414062E-4F;
/*  61 */           zeroState[i][1] = zeroState[i][1] - 2.4414062E-4F;
/*     */         } 
/*     */         
/*  64 */         if (color == -1) {
/*  65 */           blockStateMachine.getTextureState().set(BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipeLensClearOverlay.ordinal()));
/*     */         } else {
/*  67 */           blockStateMachine.getTextureState().set(BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipeLensOverlay.ordinal()));
/*  68 */           ((FakeBlock)blockStateMachine).setColor(ColorUtils.getRGBColor(15 - color));
/*     */         } 
/*     */         
/*  71 */         blockStateMachine.setRenderAllSides();
/*     */       }
/*  73 */       else if (((LensPluggable)pipePluggable).isFilter) {
/*  74 */         blockStateMachine.getTextureState().set(BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipeFilter.ordinal()));
/*     */       } else {
/*  76 */         blockStateMachine.getTextureState().set(BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipeLens.ordinal()));
/*     */       } 
/*     */ 
/*     */       
/*  80 */       float[][] rotated = MatrixTranformations.deepClone(zeroState);
/*  81 */       MatrixTranformations.transform(rotated, side);
/*     */       
/*  83 */       renderblocks.func_147782_a(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/*  84 */       renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */       
/*  86 */       ((FakeBlock)blockStateMachine).setColor(16777215);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public LensPluggable() {}
/*     */ 
/*     */   
/*     */   public LensPluggable(ItemStack stack) {
/*  95 */     this.color = stack.func_77960_j() & 0xF;
/*  96 */     this.isFilter = (stack.func_77960_j() >= 16);
/*  97 */     if (stack.func_77960_j() >= 32) {
/*  98 */       this.isFilter = (stack.func_77960_j() == 33);
/*  99 */       this.color = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(IPipeTile pipe, ForgeDirection direction) {
/* 105 */     this.container = pipe;
/* 106 */     this.side = direction;
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 111 */     this.container = null;
/* 112 */     this.side = ForgeDirection.UNKNOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getDropItems(IPipeTile pipe) {
/* 117 */     int meta = this.color | (this.isFilter ? 16 : 0);
/* 118 */     if (this.color == -1) {
/* 119 */       meta = this.isFilter ? 33 : 32;
/*     */     }
/*     */     
/* 122 */     return new ItemStack[] { new ItemStack(BuildCraftTransport.lensItem, 1, meta) };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlocking(IPipeTile pipe, ForgeDirection direction) {
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(ForgeDirection side) {
/* 132 */     float[][] bounds = new float[3][2];
/*     */     
/* 134 */     bounds[0][0] = 0.1875F;
/* 135 */     bounds[0][1] = 0.8125F;
/*     */     
/* 137 */     bounds[1][0] = 0.0F;
/* 138 */     bounds[1][1] = 0.125F;
/*     */     
/* 140 */     bounds[2][0] = 0.1875F;
/* 141 */     bounds[2][1] = 0.8125F;
/*     */     
/* 143 */     MatrixTranformations.transform(bounds, side);
/* 144 */     return AxisAlignedBB.func_72330_a(bounds[0][0], bounds[1][0], bounds[2][0], bounds[0][1], bounds[1][1], bounds[2][1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipePluggableRenderer getRenderer() {
/* 149 */     return LensPluggableRenderer.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound tag) {
/* 154 */     this.color = tag.func_74771_c("c");
/* 155 */     this.isFilter = tag.func_74767_n("f");
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound tag) {
/* 160 */     tag.func_74774_a("c", (byte)this.color);
/* 161 */     tag.func_74757_a("f", this.isFilter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 166 */     data.writeByte(this.color + 1 & 0x1F | (this.isFilter ? 32 : 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 171 */     int flags = data.readUnsignedByte();
/* 172 */     this.color = (flags & 0x1F) - 1;
/* 173 */     this.isFilter = ((flags & 0x20) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresRenderUpdate(PipePluggable o) {
/* 178 */     LensPluggable other = (LensPluggable)o;
/* 179 */     return (other.color != this.color || other.isFilter != this.isFilter);
/*     */   }
/*     */   
/*     */   private void color(TravelingItem item) {
/* 183 */     if ((item.toCenter && item.input.getOpposite() == this.side) || (!item.toCenter && item.output == this.side))
/*     */     {
/* 185 */       if (this.color == -1) {
/* 186 */         item.color = null;
/*     */       } else {
/* 188 */         item.color = EnumColor.fromId(this.color);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void eventHandler(PipeEventItem.ReachedEnd event) {
/* 194 */     if (!this.isFilter) {
/* 195 */       color(event.item);
/*     */     }
/*     */   }
/*     */   
/*     */   public void eventHandler(PipeEventItem.Entered event) {
/* 200 */     if (!this.isFilter)
/* 201 */       color(event.item); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pluggable\LensPluggable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */