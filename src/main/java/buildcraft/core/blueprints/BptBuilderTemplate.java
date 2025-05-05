/*     */ package buildcraft.core.blueprints;
/*     */ 
/*     */ import buildcraft.api.blueprints.SchematicBlockBase;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.core.builders.BuildingSlot;
/*     */ import buildcraft.core.builders.BuildingSlotBlock;
/*     */ import buildcraft.core.builders.BuildingSlotIterator;
/*     */ import buildcraft.core.builders.TileAbstractBuilder;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
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
/*     */ 
/*     */ public class BptBuilderTemplate
/*     */   extends BptBuilderBase
/*     */ {
/*  32 */   private LinkedList<BuildingSlotBlock> clearList = new LinkedList<BuildingSlotBlock>();
/*  33 */   private LinkedList<BuildingSlotBlock> buildList = new LinkedList<BuildingSlotBlock>(); private BuildingSlotIterator iteratorBuild;
/*     */   private BuildingSlotIterator iteratorClear;
/*     */   
/*     */   public BptBuilderTemplate(BlueprintBase bluePrint, World world, int x, int y, int z) {
/*  37 */     super(bluePrint, world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void internalInit() {
/*  42 */     if (this.blueprint.excavate) {
/*  43 */       for (int i = this.blueprint.sizeY - 1; i >= 0; i--) {
/*  44 */         int yCoord = i + this.y - this.blueprint.anchorY;
/*     */         
/*  46 */         if (yCoord >= 0 && yCoord < this.context.world.func_72800_K())
/*     */         {
/*     */ 
/*     */           
/*  50 */           for (int k = 0; k < this.blueprint.sizeX; k++) {
/*  51 */             int xCoord = k + this.x - this.blueprint.anchorX;
/*     */             
/*  53 */             for (int m = 0; m < this.blueprint.sizeZ; m++) {
/*  54 */               int zCoord = m + this.z - this.blueprint.anchorZ;
/*     */               
/*  56 */               SchematicBlockBase slot = this.blueprint.get(k, i, m);
/*     */               
/*  58 */               if (slot == null && !isLocationUsed(xCoord, yCoord, zCoord)) {
/*  59 */                 BuildingSlotBlock b = new BuildingSlotBlock();
/*     */                 
/*  61 */                 b.schematic = null;
/*  62 */                 b.x = xCoord;
/*  63 */                 b.y = yCoord;
/*  64 */                 b.z = zCoord;
/*  65 */                 b.mode = BuildingSlotBlock.Mode.ClearIfInvalid;
/*  66 */                 b.buildStage = 0;
/*     */                 
/*  68 */                 this.clearList.add(b);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*  75 */     for (int j = 0; j < this.blueprint.sizeY; j++) {
/*  76 */       int yCoord = j + this.y - this.blueprint.anchorY;
/*     */       
/*  78 */       if (yCoord >= 0 && yCoord < this.context.world.func_72800_K())
/*     */       {
/*     */ 
/*     */         
/*  82 */         for (int i = 0; i < this.blueprint.sizeX; i++) {
/*  83 */           int xCoord = i + this.x - this.blueprint.anchorX;
/*     */           
/*  85 */           for (int k = 0; k < this.blueprint.sizeZ; k++) {
/*  86 */             int zCoord = k + this.z - this.blueprint.anchorZ;
/*     */             
/*  88 */             SchematicBlockBase slot = this.blueprint.get(i, j, k);
/*     */             
/*  90 */             if (slot != null && !isLocationUsed(xCoord, yCoord, zCoord)) {
/*  91 */               BuildingSlotBlock b = new BuildingSlotBlock();
/*     */               
/*  93 */               b.schematic = slot;
/*  94 */               b.x = xCoord;
/*  95 */               b.y = yCoord;
/*  96 */               b.z = zCoord;
/*     */               
/*  98 */               b.mode = BuildingSlotBlock.Mode.Build;
/*  99 */               b.buildStage = 1;
/*     */               
/* 101 */               this.buildList.add(b);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 107 */     this.iteratorBuild = new BuildingSlotIterator(this.buildList);
/* 108 */     this.iteratorClear = new BuildingSlotIterator(this.clearList);
/*     */   }
/*     */   
/*     */   private void checkDone() {
/* 112 */     if (this.buildList.size() == 0 && this.clearList.size() == 0) {
/* 113 */       this.done = true;
/*     */     } else {
/* 115 */       this.done = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BuildingSlot reserveNextBlock(World world) {
/* 121 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BuildingSlot getNextBlock(World world, TileAbstractBuilder inv) {
/* 126 */     if (this.buildList.size() != 0 || this.clearList.size() != 0) {
/* 127 */       BuildingSlotBlock slot = internalGetNextBlock(world, inv);
/* 128 */       checkDone();
/*     */       
/* 130 */       if (slot != null) {
/* 131 */         return (BuildingSlot)slot;
/*     */       }
/*     */     } else {
/* 134 */       checkDone();
/*     */     } 
/*     */     
/* 137 */     return null;
/*     */   }
/*     */   
/*     */   private BuildingSlotBlock internalGetNextBlock(World world, TileAbstractBuilder builder) {
/* 141 */     BuildingSlotBlock result = null;
/*     */     
/* 143 */     IInvSlot firstSlotToConsume = null;
/*     */     
/* 145 */     for (IInvSlot invSlot : InventoryIterator.getIterable((IInventory)builder, ForgeDirection.UNKNOWN)) {
/* 146 */       if (!builder.isBuildingMaterialSlot(invSlot.getIndex())) {
/*     */         continue;
/*     */       }
/*     */       
/* 150 */       ItemStack stack = invSlot.getStackInSlot();
/*     */       
/* 152 */       if (stack != null && stack.field_77994_a > 0) {
/* 153 */         firstSlotToConsume = invSlot;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     this.iteratorClear.startIteration();
/* 160 */     while (this.iteratorClear.hasNext()) {
/* 161 */       BuildingSlotBlock slot = this.iteratorClear.next();
/*     */       
/* 163 */       if (slot.buildStage > ((BuildingSlotBlock)this.clearList.getFirst()).buildStage) {
/* 164 */         this.iteratorClear.reset();
/*     */         
/*     */         break;
/*     */       } 
/* 168 */       if (!world.func_72899_e(slot.x, slot.y, slot.z)) {
/*     */         continue;
/*     */       }
/*     */       
/* 172 */       if (canDestroy(builder, this.context, slot)) {
/* 173 */         if (BlockUtils.isUnbreakableBlock(world, slot.x, slot.y, slot.z) || 
/* 174 */           isBlockBreakCanceled(world, slot.x, slot.y, slot.z) || 
/* 175 */           BuildCraftAPI.isSoftBlock(world, slot.x, slot.y, slot.z)) {
/* 176 */           this.iteratorClear.remove();
/* 177 */           markLocationUsed(slot.x, slot.y, slot.z); continue;
/*     */         } 
/* 179 */         consumeEnergyToDestroy(builder, slot);
/* 180 */         createDestroyItems(slot);
/*     */         
/* 182 */         result = slot;
/* 183 */         this.iteratorClear.remove();
/* 184 */         markLocationUsed(slot.x, slot.y, slot.z);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     if (result != null) {
/* 191 */       return result;
/*     */     }
/*     */ 
/*     */     
/* 195 */     if (firstSlotToConsume == null) {
/* 196 */       return null;
/*     */     }
/*     */     
/* 199 */     this.iteratorBuild.startIteration();
/*     */     
/* 201 */     while (this.iteratorBuild.hasNext()) {
/* 202 */       BuildingSlotBlock slot = this.iteratorBuild.next();
/*     */       
/* 204 */       if (slot.buildStage > ((BuildingSlotBlock)this.buildList.getFirst()).buildStage) {
/* 205 */         this.iteratorBuild.reset();
/*     */         
/*     */         break;
/*     */       } 
/* 209 */       if (BlockUtils.isUnbreakableBlock(world, slot.x, slot.y, slot.z) || 
/* 210 */         isBlockPlaceCanceled(world, slot.x, slot.y, slot.z, slot.schematic) || 
/* 211 */         !BuildCraftAPI.isSoftBlock(world, slot.x, slot.y, slot.z)) {
/* 212 */         this.iteratorBuild.remove();
/* 213 */         markLocationUsed(slot.x, slot.y, slot.z); continue;
/* 214 */       }  if (builder.consumeEnergy(240)) {
/* 215 */         slot.addStackConsumed(firstSlotToConsume.decreaseStackInSlot(1));
/* 216 */         result = slot;
/* 217 */         this.iteratorBuild.remove();
/* 218 */         markLocationUsed(slot.x, slot.y, slot.z);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 223 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\BptBuilderTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */