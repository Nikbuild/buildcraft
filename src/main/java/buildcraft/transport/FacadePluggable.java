/*     */ package buildcraft.transport;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.pluggable.IFacadePluggable;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableRenderer;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class FacadePluggable extends PipePluggable implements IFacadePluggable {
/*     */   public ItemFacade.FacadeState[] states;
/*     */   private ItemFacade.FacadeState activeState;
/*     */   private IPipeTile pipe;
/*     */   private Block block;
/*     */   private int meta;
/*     */   private boolean transparent;
/*     */   private boolean renderAsHollow;
/*     */   
/*     */   public static final class FacadePluggableRenderer implements IPipePluggableRenderer {
/*  25 */     public static final IPipePluggableRenderer INSTANCE = new FacadePluggableRenderer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void renderPluggable(RenderBlocks renderblocks, IPipe pipe, ForgeDirection side, PipePluggable pipePluggable, ITextureStates blockStateMachine, int renderPass, int x, int y, int z) {
/*  33 */       FacadeRenderHelper.pipeFacadeRenderer(renderblocks, blockStateMachine, pipe.getTile(), renderPass, x, y, z, side, (IFacadePluggable)pipePluggable);
/*     */     }
/*     */   }
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
/*     */   
/*     */   public FacadePluggable(ItemFacade.FacadeState[] states) {
/*  48 */     this.states = states;
/*  49 */     prepareStates();
/*     */   }
/*     */ 
/*     */   
/*     */   public FacadePluggable() {}
/*     */ 
/*     */   
/*     */   public void invalidate() {
/*  57 */     this.pipe = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(IPipeTile pipe, ForgeDirection direction) {
/*  62 */     this.pipe = pipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresRenderUpdate(PipePluggable o) {
/*  67 */     FacadePluggable other = (FacadePluggable)o;
/*  68 */     return (other.block != this.block || other.meta != this.meta || other.transparent != this.transparent || other.renderAsHollow != this.renderAsHollow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  73 */     if (this.states != null) {
/*  74 */       nbt.func_74782_a("states", (NBTBase)ItemFacade.FacadeState.writeArray(this.states));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  80 */     if (nbt.func_74764_b("states")) {
/*  81 */       this.states = ItemFacade.FacadeState.readArray(nbt.func_150295_c("states", 10));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getDropItems(IPipeTile pipe) {
/*  87 */     if (this.states != null) {
/*  88 */       return new ItemStack[] { ItemFacade.getFacade(this.states) };
/*     */     }
/*  90 */     return new ItemStack[] { ItemFacade.getFacade(new ItemFacade.FacadeState[] { new ItemFacade.FacadeState(getCurrentBlock(), getCurrentMetadata(), null, isHollow()) }) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlocking(IPipeTile pipe, ForgeDirection direction) {
/*  96 */     return !isHollow();
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getCurrentBlock() {
/* 101 */     prepareStates();
/* 102 */     return (this.activeState == null) ? this.block : this.activeState.block;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentMetadata() {
/* 107 */     prepareStates();
/* 108 */     return (this.activeState == null) ? this.meta : this.activeState.metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTransparent() {
/* 113 */     prepareStates();
/* 114 */     return (this.activeState == null) ? this.transparent : this.activeState.transparent;
/*     */   }
/*     */   
/*     */   public boolean isHollow() {
/* 118 */     prepareStates();
/* 119 */     return (this.activeState == null) ? this.renderAsHollow : this.activeState.hollow;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(ForgeDirection side) {
/* 124 */     float[][] bounds = new float[3][2];
/*     */     
/* 126 */     bounds[0][0] = 0.0F;
/* 127 */     bounds[0][1] = 1.0F;
/*     */     
/* 129 */     bounds[1][0] = 0.0F;
/* 130 */     bounds[1][1] = 0.125F;
/*     */     
/* 132 */     bounds[2][0] = 0.0F;
/* 133 */     bounds[2][1] = 1.0F;
/*     */     
/* 135 */     MatrixTranformations.transform(bounds, side);
/* 136 */     return AxisAlignedBB.func_72330_a(bounds[0][0], bounds[1][0], bounds[2][0], bounds[0][1], bounds[1][1], bounds[2][1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSolidOnSide(IPipeTile pipe, ForgeDirection direction) {
/* 141 */     return !isHollow();
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipePluggableRenderer getRenderer() {
/* 146 */     return FacadePluggableRenderer.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 151 */     prepareStates();
/*     */     
/* 153 */     if (this.activeState == null || this.activeState.block == null) {
/* 154 */       data.writeShort(0);
/*     */     } else {
/* 156 */       data.writeShort(Block.func_149682_b(this.activeState.block));
/*     */     } 
/*     */     
/* 159 */     data.writeByte(((this.activeState != null && this.activeState.transparent) ? 128 : 0) | ((this.activeState != null && this.activeState.hollow) ? 64 : 0) | ((this.activeState == null) ? 0 : this.activeState.metadata));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 166 */     int blockId = data.readUnsignedShort();
/* 167 */     if (blockId > 0) {
/* 168 */       this.block = Block.func_149729_e(blockId);
/*     */     } else {
/* 170 */       this.block = null;
/*     */     } 
/*     */     
/* 173 */     int flags = data.readUnsignedByte();
/*     */     
/* 175 */     this.meta = flags & 0xF;
/* 176 */     this.transparent = ((flags & 0x80) != 0);
/* 177 */     this.renderAsHollow = ((flags & 0x40) != 0);
/*     */   }
/*     */   
/*     */   private void prepareStates() {
/* 181 */     if (this.states != null && this.states.length > 1) {
/* 182 */       if (this.pipe == null || this.pipe.getPipe() == null) {
/* 183 */         this.activeState = this.states[0];
/*     */         
/*     */         return;
/*     */       } 
/* 187 */       IPipe p = this.pipe.getPipe();
/* 188 */       int defaultStateId = -1;
/* 189 */       int activeStateId = -1;
/*     */       
/* 191 */       for (int i = 0; i < this.states.length; i++) {
/* 192 */         ItemFacade.FacadeState state = this.states[i];
/* 193 */         if (state.wire == null) {
/* 194 */           defaultStateId = i;
/*     */         
/*     */         }
/* 197 */         else if (p.isWireActive(state.wire)) {
/* 198 */           activeStateId = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 203 */       this.activeState = (activeStateId < 0) ? ((defaultStateId < 0) ? this.states[0] : this.states[defaultStateId]) : this.states[activeStateId];
/* 204 */     } else if (this.activeState == null) {
/* 205 */       this.activeState = (this.states != null && this.states.length > 0) ? this.states[0] : null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\FacadePluggable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */