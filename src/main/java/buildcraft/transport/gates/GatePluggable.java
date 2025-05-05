/*     */ package buildcraft.transport.gates;
/*     */ import buildcraft.api.core.render.ITextureStates;
/*     */ import buildcraft.api.gates.GateExpansions;
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableRenderer;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.transport.Gate;
/*     */ import buildcraft.transport.TileGenericPipe;
/*     */ import buildcraft.transport.render.PipeRendererTESR;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class GatePluggable extends PipePluggable {
/*     */   public GateDefinition.GateMaterial material;
/*     */   public GateDefinition.GateLogic logic;
/*     */   public IGateExpansion[] expansions;
/*     */   public boolean isLit;
/*     */   public boolean isPulsing;
/*     */   public Gate realGate;
/*     */   public Gate instantiatedGate;
/*     */   private float pulseStage;
/*     */   
/*     */   private static final class GatePluggableRenderer implements IPipePluggableRenderer, IPipePluggableDynamicRenderer {
/*  33 */     public static final GatePluggableRenderer INSTANCE = new GatePluggableRenderer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void renderPluggable(IPipe pipe, ForgeDirection side, PipePluggable pipePluggable, double x, double y, double z) {
/*  41 */       PipeRendererTESR.renderGate(x, y, z, (GatePluggable)pipePluggable, side);
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderPluggable(RenderBlocks renderblocks, IPipe pipe, ForgeDirection side, PipePluggable pipePluggable, ITextureStates blockStateMachine, int renderPass, int x, int y, int z) {
/*  46 */       if (renderPass == 0) {
/*  47 */         PipeRendererTESR.renderGateStatic(renderblocks, side, (GatePluggable)pipePluggable, blockStateMachine, x, y, z);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GatePluggable() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GatePluggable(Gate gate) {
/*  64 */     this.instantiatedGate = gate;
/*  65 */     initFromGate(gate);
/*     */   }
/*     */   
/*     */   private void initFromGate(Gate gate) {
/*  69 */     this.material = gate.material;
/*  70 */     this.logic = gate.logic;
/*     */     
/*  72 */     Set<IGateExpansion> gateExpansions = gate.expansions.keySet();
/*  73 */     this.expansions = gateExpansions.<IGateExpansion>toArray(new IGateExpansion[gateExpansions.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  78 */     nbt.func_74774_a("mat", (byte)this.material.ordinal());
/*  79 */     nbt.func_74774_a("logic", (byte)this.logic.ordinal());
/*     */     
/*  81 */     NBTTagList expansionsList = nbt.func_150295_c("ex", 8);
/*  82 */     for (IGateExpansion expansion : this.expansions) {
/*  83 */       expansionsList.func_74742_a((NBTBase)new NBTTagString(expansion.getUniqueIdentifier()));
/*     */     }
/*  85 */     nbt.func_74782_a("ex", (NBTBase)expansionsList);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  90 */     this.material = GateDefinition.GateMaterial.fromOrdinal(nbt.func_74771_c("mat"));
/*  91 */     this.logic = GateDefinition.GateLogic.fromOrdinal(nbt.func_74771_c("logic"));
/*     */     
/*  93 */     NBTTagList expansionsList = nbt.func_150295_c("ex", 8);
/*  94 */     int expansionsSize = expansionsList.func_74745_c();
/*  95 */     this.expansions = new IGateExpansion[expansionsSize];
/*  96 */     for (int i = 0; i < expansionsSize; i++) {
/*  97 */       this.expansions[i] = GateExpansions.getExpansion(expansionsList.func_150307_f(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf buf) {
/* 103 */     buf.writeByte(this.material.ordinal());
/* 104 */     buf.writeByte(this.logic.ordinal());
/* 105 */     buf.writeBoolean((this.realGate != null) ? this.realGate.isGateActive() : false);
/* 106 */     buf.writeBoolean((this.realGate != null) ? this.realGate.isGatePulsing() : false);
/*     */     
/* 108 */     int expansionsSize = this.expansions.length;
/* 109 */     buf.writeShort(expansionsSize);
/*     */     
/* 111 */     for (IGateExpansion expansion : this.expansions) {
/* 112 */       buf.writeShort(GateExpansions.getExpansionID(expansion));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf buf) {
/* 118 */     this.material = GateDefinition.GateMaterial.fromOrdinal(buf.readByte());
/* 119 */     this.logic = GateDefinition.GateLogic.fromOrdinal(buf.readByte());
/* 120 */     this.isLit = buf.readBoolean();
/* 121 */     this.isPulsing = buf.readBoolean();
/*     */     
/* 123 */     int expansionsSize = buf.readUnsignedShort();
/* 124 */     this.expansions = new IGateExpansion[expansionsSize];
/*     */     
/* 126 */     for (int i = 0; i < expansionsSize; i++) {
/* 127 */       this.expansions[i] = GateExpansions.getExpansionByID(buf.readUnsignedShort());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requiresRenderUpdate(PipePluggable o) {
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getDropItems(IPipeTile pipe) {
/* 139 */     ItemStack gate = ItemGate.makeGateItem(this.material, this.logic);
/* 140 */     for (IGateExpansion expansion : this.expansions) {
/* 141 */       ItemGate.addGateExpansion(gate, expansion);
/*     */     }
/* 143 */     return new ItemStack[] { gate };
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(IPipeTile pipe, ForgeDirection direction) {
/* 148 */     if (this.isPulsing || this.pulseStage > 0.11F) {
/*     */ 
/*     */       
/* 151 */       this.pulseStage = (this.pulseStage + 0.01F) % 1.0F;
/*     */     } else {
/* 153 */       this.pulseStage = 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onAttachedPipe(IPipeTile pipe, ForgeDirection direction) {
/* 159 */     TileGenericPipe pipeReal = (TileGenericPipe)pipe;
/* 160 */     if (!(pipeReal.getWorld()).field_72995_K) {
/* 161 */       if (this.instantiatedGate != null) {
/* 162 */         pipeReal.pipe.gates[direction.ordinal()] = this.instantiatedGate;
/*     */       } else {
/* 164 */         Gate gate = pipeReal.pipe.gates[direction.ordinal()];
/* 165 */         if (gate == null || gate.material != this.material || gate.logic != this.logic) {
/* 166 */           pipeReal.pipe.gates[direction.ordinal()] = GateFactory.makeGate(pipeReal.pipe, this.material, this.logic, direction);
/* 167 */           for (IGateExpansion expansion : this.expansions) {
/* 168 */             pipeReal.pipe.gates[direction.ordinal()].addGateExpansion(expansion);
/*     */           }
/* 170 */           pipeReal.scheduleRenderUpdate();
/*     */         } 
/*     */       } 
/*     */       
/* 174 */       this.realGate = pipeReal.pipe.gates[direction.ordinal()];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDetachedPipe(IPipeTile pipe, ForgeDirection direction) {
/* 180 */     TileGenericPipe pipeReal = (TileGenericPipe)pipe;
/* 181 */     if (!(pipeReal.getWorld()).field_72995_K) {
/* 182 */       Gate gate = pipeReal.pipe.gates[direction.ordinal()];
/* 183 */       if (gate != null) {
/* 184 */         gate.resetGate();
/* 185 */         pipeReal.pipe.gates[direction.ordinal()] = null;
/*     */       } 
/* 187 */       pipeReal.scheduleRenderUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlocking(IPipeTile pipe, ForgeDirection direction) {
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 198 */     if (obj == this) {
/* 199 */       return true;
/*     */     }
/* 201 */     if (!(obj instanceof GatePluggable)) {
/* 202 */       return false;
/*     */     }
/* 204 */     GatePluggable o = (GatePluggable)obj;
/* 205 */     if (o.material.ordinal() != this.material.ordinal()) {
/* 206 */       return false;
/*     */     }
/* 208 */     if (o.logic.ordinal() != this.logic.ordinal()) {
/* 209 */       return false;
/*     */     }
/* 211 */     if (o.expansions.length != this.expansions.length) {
/* 212 */       return false;
/*     */     }
/* 214 */     for (int i = 0; i < this.expansions.length; i++) {
/* 215 */       if (o.expansions[i] != this.expansions[i]) {
/* 216 */         return false;
/*     */       }
/*     */     } 
/* 219 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(ForgeDirection side) {
/* 224 */     float min = 0.3F;
/* 225 */     float max = 0.7F;
/*     */     
/* 227 */     float[][] bounds = new float[3][2];
/*     */     
/* 229 */     bounds[0][0] = min;
/* 230 */     bounds[0][1] = max;
/*     */     
/* 232 */     bounds[1][0] = 0.15F;
/* 233 */     bounds[1][1] = 0.25F;
/*     */     
/* 235 */     bounds[2][0] = min;
/* 236 */     bounds[2][1] = max;
/*     */     
/* 238 */     MatrixTranformations.transform(bounds, side);
/* 239 */     return AxisAlignedBB.func_72330_a(bounds[0][0], bounds[1][0], bounds[2][0], bounds[0][1], bounds[1][1], bounds[2][1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipePluggableRenderer getRenderer() {
/* 244 */     return GatePluggableRenderer.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipePluggableDynamicRenderer getDynamicRenderer() {
/* 249 */     return GatePluggableRenderer.INSTANCE;
/*     */   }
/*     */   
/*     */   public float getPulseStage() {
/* 253 */     return this.pulseStage;
/*     */   }
/*     */   
/*     */   public GateDefinition.GateMaterial getMaterial() {
/* 257 */     return this.material;
/*     */   }
/*     */   
/*     */   public GateDefinition.GateLogic getLogic() {
/* 261 */     return this.logic;
/*     */   }
/*     */   
/*     */   public IGateExpansion[] getExpansions() {
/* 265 */     return this.expansions;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\GatePluggable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */