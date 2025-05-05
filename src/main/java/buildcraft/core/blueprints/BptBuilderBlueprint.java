/*     */ package buildcraft.core.blueprints;
/*     */ 
/*     */ import buildcraft.api.blueprints.Schematic;
/*     */ import buildcraft.api.blueprints.SchematicBlock;
/*     */ import buildcraft.api.blueprints.SchematicBlockBase;
/*     */ import buildcraft.api.blueprints.SchematicEntity;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.api.core.StackKey;
/*     */ import buildcraft.core.builders.BuilderItemMetaPair;
/*     */ import buildcraft.core.builders.BuildingSlot;
/*     */ import buildcraft.core.builders.BuildingSlotBlock;
/*     */ import buildcraft.core.builders.BuildingSlotEntity;
/*     */ import buildcraft.core.builders.IBuildingItemsProvider;
/*     */ import buildcraft.core.builders.TileAbstractBuilder;
/*     */ import buildcraft.core.lib.inventory.InventoryCopy;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public class BptBuilderBlueprint
/*     */   extends BptBuilderBase
/*     */ {
/*  58 */   protected HashSet<Integer> builtEntities = new HashSet<Integer>();
/*  59 */   protected HashMap<BuilderItemMetaPair, List<BuildingSlotBlock>> buildList = new HashMap<BuilderItemMetaPair, List<BuildingSlotBlock>>();
/*     */   
/*     */   protected int[] buildStageOccurences;
/*  62 */   private ArrayList<RequirementItemStack> neededItems = new ArrayList<RequirementItemStack>();
/*     */   
/*  64 */   private LinkedList<BuildingSlotEntity> entityList = new LinkedList<BuildingSlotEntity>();
/*  65 */   private LinkedList<BuildingSlot> postProcessing = new LinkedList<BuildingSlot>();
/*     */   private BuildingSlotMapIterator iterator;
/*  67 */   private IndexRequirementMap requirementMap = new IndexRequirementMap();
/*     */   
/*     */   public BptBuilderBlueprint(Blueprint bluePrint, World world, int x, int y, int z) {
/*  70 */     super(bluePrint, world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void internalInit() {
/*  75 */     for (int j = this.blueprint.sizeY - 1; j >= 0; j--) {
/*  76 */       int yCoord = j + this.y - this.blueprint.anchorY;
/*     */       
/*  78 */       if (yCoord >= 0 && yCoord < this.context.world.func_72800_K())
/*     */       {
/*     */ 
/*     */         
/*  82 */         for (int k = 0; k < this.blueprint.sizeX; k++) {
/*  83 */           int xCoord = k + this.x - this.blueprint.anchorX;
/*     */           
/*  85 */           for (int m = 0; m < this.blueprint.sizeZ; m++) {
/*  86 */             int zCoord = m + this.z - this.blueprint.anchorZ;
/*     */             
/*  88 */             if (!isLocationUsed(xCoord, yCoord, zCoord)) {
/*  89 */               SchematicBlock slot = (SchematicBlock)this.blueprint.get(k, j, m);
/*     */               
/*  91 */               if (slot != null || this.blueprint.excavate) {
/*     */ 
/*     */ 
/*     */                 
/*  95 */                 if (slot == null) {
/*  96 */                   slot = new SchematicBlock();
/*  97 */                   slot.meta = 0;
/*  98 */                   slot.block = Blocks.field_150350_a;
/*     */                 } 
/*     */                 
/* 101 */                 if (SchematicRegistry.INSTANCE.isAllowedForBuilding(slot.block, slot.meta)) {
/*     */ 
/*     */ 
/*     */                   
/* 105 */                   BuildingSlotBlock b = new BuildingSlotBlock();
/* 106 */                   b.schematic = (SchematicBlockBase)slot;
/* 107 */                   b.x = xCoord;
/* 108 */                   b.y = yCoord;
/* 109 */                   b.z = zCoord;
/* 110 */                   b.mode = BuildingSlotBlock.Mode.ClearIfInvalid;
/* 111 */                   b.buildStage = 0;
/*     */                   
/* 113 */                   addToBuildList(b);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }  } 
/* 119 */     }  LinkedList<BuildingSlotBlock> tmpStandalone = new LinkedList<BuildingSlotBlock>();
/* 120 */     LinkedList<BuildingSlotBlock> tmpExpanding = new LinkedList<BuildingSlotBlock>();
/*     */     
/* 122 */     for (int i = 0; i < this.blueprint.sizeY; i++) {
/* 123 */       int yCoord = i + this.y - this.blueprint.anchorY;
/*     */       
/* 125 */       if (yCoord >= 0 && yCoord < this.context.world.func_72800_K())
/*     */       {
/*     */ 
/*     */         
/* 129 */         for (int k = 0; k < this.blueprint.sizeX; k++) {
/* 130 */           int xCoord = k + this.x - this.blueprint.anchorX;
/*     */           
/* 132 */           for (int m = 0; m < this.blueprint.sizeZ; m++) {
/* 133 */             int zCoord = m + this.z - this.blueprint.anchorZ;
/*     */             
/* 135 */             SchematicBlock slot = (SchematicBlock)this.blueprint.get(k, i, m);
/*     */             
/* 137 */             if (slot != null)
/*     */             {
/*     */ 
/*     */               
/* 141 */               if (SchematicRegistry.INSTANCE.isAllowedForBuilding(slot.block, slot.meta)) {
/*     */ 
/*     */ 
/*     */                 
/* 145 */                 BuildingSlotBlock b = new BuildingSlotBlock();
/* 146 */                 b.schematic = (SchematicBlockBase)slot;
/* 147 */                 b.x = xCoord;
/* 148 */                 b.y = yCoord;
/* 149 */                 b.z = zCoord;
/* 150 */                 b.mode = BuildingSlotBlock.Mode.Build;
/*     */                 
/* 152 */                 if (!isLocationUsed(xCoord, yCoord, zCoord)) {
/* 153 */                   switch (slot.getBuildStage()) {
/*     */                     case STANDALONE:
/* 155 */                       tmpStandalone.add(b);
/* 156 */                       b.buildStage = 1;
/*     */                       break;
/*     */                     case EXPANDING:
/* 159 */                       tmpExpanding.add(b);
/* 160 */                       b.buildStage = 2;
/*     */                       break;
/*     */                   } 
/*     */                 } else {
/* 164 */                   this.postProcessing.add(b);
/*     */                 } 
/*     */               }  } 
/*     */           } 
/*     */         }  } 
/*     */     } 
/* 170 */     for (BuildingSlotBlock b : tmpStandalone) {
/* 171 */       addToBuildList(b);
/*     */     }
/* 173 */     for (BuildingSlotBlock b : tmpExpanding) {
/* 174 */       addToBuildList(b);
/*     */     }
/*     */     
/* 177 */     int seqId = 0;
/*     */     
/* 179 */     for (SchematicEntity e : ((Blueprint)this.blueprint).entities) {
/*     */       
/* 181 */       BuildingSlotEntity b = new BuildingSlotEntity();
/* 182 */       b.schematic = e;
/* 183 */       b.sequenceNumber = seqId;
/*     */       
/* 185 */       if (!this.builtEntities.contains(Integer.valueOf(seqId))) {
/* 186 */         this.entityList.add(b);
/*     */       } else {
/* 188 */         this.postProcessing.add(b);
/*     */       } 
/*     */       
/* 191 */       seqId++;
/*     */     } 
/*     */     
/* 194 */     recomputeNeededItems();
/*     */   }
/*     */   
/*     */   public void deploy() {
/* 198 */     initialize();
/*     */     
/* 200 */     for (List<BuildingSlotBlock> lb : this.buildList.values()) {
/* 201 */       for (BuildingSlotBlock b : lb) {
/* 202 */         if (b.mode == BuildingSlotBlock.Mode.ClearIfInvalid) {
/* 203 */           this.context.world.func_147468_f(b.x, b.y, b.z); continue;
/* 204 */         }  if (!b.schematic.doNotBuild()) {
/* 205 */           b.stackConsumed = new LinkedList();
/*     */           
/*     */           try {
/* 208 */             for (ItemStack stk : b.getRequirements(this.context)) {
/* 209 */               if (stk != null) {
/* 210 */                 b.stackConsumed.add(stk.func_77946_l());
/*     */               }
/*     */             } 
/* 213 */           } catch (Throwable t) {
/*     */             
/* 215 */             t.printStackTrace();
/* 216 */             BCLog.logger.throwing(t);
/*     */           } 
/*     */           
/* 219 */           b.writeToWorld(this.context);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     for (BuildingSlotEntity e : this.entityList) {
/* 225 */       e.stackConsumed = new LinkedList();
/*     */       
/*     */       try {
/* 228 */         for (ItemStack stk : e.getRequirements(this.context)) {
/* 229 */           if (stk != null) {
/* 230 */             e.stackConsumed.add(stk.func_77946_l());
/*     */           }
/*     */         } 
/* 233 */       } catch (Throwable t) {
/*     */         
/* 235 */         t.printStackTrace();
/* 236 */         BCLog.logger.throwing(t);
/*     */       } 
/*     */       
/* 239 */       e.writeToWorld(this.context);
/*     */     } 
/*     */     
/* 242 */     for (List<BuildingSlotBlock> lb : this.buildList.values()) {
/* 243 */       for (BuildingSlotBlock b : lb) {
/* 244 */         if (b.mode != BuildingSlotBlock.Mode.ClearIfInvalid) {
/* 245 */           b.postProcessing(this.context);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     for (BuildingSlotEntity e : this.entityList) {
/* 251 */       e.postProcessing(this.context);
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkDone() {
/* 256 */     if (getBuildListCount() == 0 && this.entityList.size() == 0) {
/* 257 */       this.done = true;
/*     */     } else {
/* 259 */       this.done = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getBuildListCount() {
/* 264 */     int out = 0;
/* 265 */     if (this.buildStageOccurences != null) {
/* 266 */       for (int i = 0; i < this.buildStageOccurences.length; i++) {
/* 267 */         out += this.buildStageOccurences[i];
/*     */       }
/*     */     }
/* 270 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public BuildingSlot reserveNextBlock(World world) {
/* 275 */     if (getBuildListCount() != 0) {
/* 276 */       BuildingSlot slot = internalGetNextBlock(world, (TileAbstractBuilder)null);
/* 277 */       checkDone();
/*     */       
/* 279 */       if (slot != null) {
/* 280 */         slot.reserved = true;
/*     */       }
/*     */       
/* 283 */       return slot;
/*     */     } 
/*     */     
/* 286 */     return null;
/*     */   }
/*     */   
/*     */   private void addToBuildList(BuildingSlotBlock b) {
/* 290 */     if (b != null) {
/* 291 */       BuilderItemMetaPair imp = new BuilderItemMetaPair(this.context, b);
/* 292 */       if (!this.buildList.containsKey(imp)) {
/* 293 */         this.buildList.put(imp, new ArrayList<BuildingSlotBlock>());
/*     */       }
/* 295 */       ((List<BuildingSlotBlock>)this.buildList.get(imp)).add(b);
/*     */       
/* 297 */       if (this.buildStageOccurences == null) {
/* 298 */         this.buildStageOccurences = new int[Math.max(3, b.buildStage + 1)];
/* 299 */       } else if (this.buildStageOccurences.length <= b.buildStage) {
/* 300 */         int[] newBSO = new int[b.buildStage + 1];
/* 301 */         System.arraycopy(this.buildStageOccurences, 0, newBSO, 0, this.buildStageOccurences.length);
/* 302 */         this.buildStageOccurences = newBSO;
/*     */       } 
/* 304 */       this.buildStageOccurences[b.buildStage] = this.buildStageOccurences[b.buildStage] + 1;
/*     */       
/* 306 */       if (b.mode == BuildingSlotBlock.Mode.Build) {
/* 307 */         this.requirementMap.add(b, this.context);
/* 308 */         b.internalRequirementRemovalListener = this.requirementMap;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BuildingSlot getNextBlock(World world, TileAbstractBuilder inv) {
/* 315 */     if (getBuildListCount() != 0) {
/* 316 */       BuildingSlot slot = internalGetNextBlock(world, inv);
/* 317 */       checkDone();
/* 318 */       return slot;
/*     */     } 
/*     */     
/* 321 */     if (this.entityList.size() != 0) {
/* 322 */       BuildingSlot slot = internalGetNextEntity(world, inv);
/* 323 */       checkDone();
/* 324 */       return slot;
/*     */     } 
/*     */     
/* 327 */     checkDone();
/* 328 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean readyForSlotLookup(TileAbstractBuilder builder) {
/* 332 */     return (builder == null || builder.energyAvailable() >= 160);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BuildingSlot internalGetNextBlock(World world, TileAbstractBuilder builder) {
/* 341 */     if (!readyForSlotLookup(builder)) {
/* 342 */       return null;
/*     */     }
/*     */     
/* 345 */     if (this.iterator == null) {
/* 346 */       this.iterator = new BuildingSlotMapIterator(this, builder);
/*     */     }
/*     */ 
/*     */     
/* 350 */     this.iterator.refresh(builder);
/*     */     BuildingSlotBlock slot;
/* 352 */     while (readyForSlotLookup(builder) && (slot = this.iterator.next()) != null) {
/* 353 */       if (!world.func_72899_e(slot.x, slot.y, slot.z)) {
/*     */         continue;
/*     */       }
/*     */       
/* 357 */       boolean skipped = false;
/*     */       
/* 359 */       for (int i = 0; i < slot.buildStage; i++) {
/* 360 */         if (this.buildStageOccurences[i] > 0) {
/* 361 */           this.iterator.skipKey();
/* 362 */           skipped = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 367 */       if (skipped) {
/*     */         continue;
/*     */       }
/*     */       
/* 371 */       if (slot.built) {
/* 372 */         this.iterator.remove();
/* 373 */         markLocationUsed(slot.x, slot.y, slot.z);
/* 374 */         this.postProcessing.add(slot);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 379 */       if (slot.reserved) {
/*     */         continue;
/*     */       }
/*     */       
/*     */       try {
/* 384 */         if (slot.isAlreadyBuilt(this.context)) {
/* 385 */           if (slot.mode == BuildingSlotBlock.Mode.Build) {
/* 386 */             this.requirementMap.remove(slot);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 392 */             this.postProcessing.add(slot);
/*     */           } 
/*     */           
/* 395 */           this.iterator.remove();
/*     */           
/*     */           continue;
/*     */         } 
/* 399 */         if (BlockUtils.isUnbreakableBlock(world, slot.x, slot.y, slot.z)) {
/*     */           
/* 401 */           this.iterator.remove();
/* 402 */           markLocationUsed(slot.x, slot.y, slot.z);
/* 403 */           this.requirementMap.remove(slot); continue;
/*     */         } 
/* 405 */         if (slot.mode == BuildingSlotBlock.Mode.ClearIfInvalid) {
/* 406 */           if (BuildCraftAPI.isSoftBlock(world, slot.x, slot.y, slot.z) || 
/*     */             
/* 408 */             isBlockBreakCanceled(world, slot.x, slot.y, slot.z)) {
/* 409 */             this.iterator.remove();
/* 410 */             markLocationUsed(slot.x, slot.y, slot.z); continue;
/*     */           } 
/* 412 */           if (builder == null) {
/* 413 */             createDestroyItems(slot);
/* 414 */             return (BuildingSlot)slot;
/* 415 */           }  if (canDestroy(builder, this.context, slot)) {
/* 416 */             consumeEnergyToDestroy(builder, slot);
/* 417 */             createDestroyItems(slot);
/*     */             
/* 419 */             this.iterator.remove();
/* 420 */             markLocationUsed(slot.x, slot.y, slot.z);
/* 421 */             return (BuildingSlot)slot;
/*     */           }  continue;
/*     */         } 
/* 424 */         if (!slot.schematic.doNotBuild()) {
/* 425 */           if (builder == null)
/* 426 */             return (BuildingSlot)slot; 
/* 427 */           if (!checkRequirements(builder, (Schematic)slot.schematic) || 
/* 428 */             !BuildCraftAPI.isSoftBlock(world, slot.x, slot.y, slot.z) || this.requirementMap
/* 429 */             .contains(new BlockIndex(slot.x, slot.y, slot.z)))
/*     */             continue; 
/* 431 */           if (isBlockPlaceCanceled(world, slot.x, slot.y, slot.z, slot.schematic)) {
/*     */ 
/*     */             
/* 434 */             this.iterator.remove();
/* 435 */             this.requirementMap.remove(slot);
/* 436 */             markLocationUsed(slot.x, slot.y, slot.z);
/*     */ 
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 445 */           builder.consumeEnergy(slot.getEnergyRequirement());
/* 446 */           useRequirements((IInventory)builder, (BuildingSlot)slot);
/*     */           
/* 448 */           this.iterator.remove();
/* 449 */           markLocationUsed(slot.x, slot.y, slot.z);
/* 450 */           this.postProcessing.add(slot);
/* 451 */           return (BuildingSlot)slot;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 456 */         this.postProcessing.add(slot);
/* 457 */         this.requirementMap.remove(slot);
/* 458 */         this.iterator.remove();
/*     */       
/*     */       }
/* 461 */       catch (Throwable t) {
/*     */         
/* 463 */         t.printStackTrace();
/* 464 */         BCLog.logger.throwing(t);
/* 465 */         this.iterator.remove();
/* 466 */         this.requirementMap.remove(slot);
/*     */       } 
/*     */     } 
/*     */     
/* 470 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private BuildingSlot internalGetNextEntity(World world, TileAbstractBuilder builder) {
/* 475 */     Iterator<BuildingSlotEntity> it = this.entityList.iterator();
/*     */     
/* 477 */     while (it.hasNext()) {
/* 478 */       BuildingSlotEntity slot = it.next();
/*     */       
/* 480 */       if (slot.isAlreadyBuilt(this.context)) {
/* 481 */         it.remove();
/* 482 */         recomputeNeededItems(); continue;
/*     */       } 
/* 484 */       if (checkRequirements(builder, (Schematic)slot.schematic)) {
/* 485 */         builder.consumeEnergy(slot.getEnergyRequirement());
/* 486 */         useRequirements((IInventory)builder, (BuildingSlot)slot);
/*     */         
/* 488 */         it.remove();
/* 489 */         recomputeNeededItems();
/* 490 */         this.postProcessing.add(slot);
/* 491 */         this.builtEntities.add(Integer.valueOf(slot.sequenceNumber));
/* 492 */         return (BuildingSlot)slot;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 497 */     return null;
/*     */   }
/*     */   
/*     */   public boolean checkRequirements(TileAbstractBuilder builder, Schematic slot) {
/* 501 */     LinkedList<ItemStack> tmpReq = new LinkedList<ItemStack>();
/*     */     
/*     */     try {
/* 504 */       LinkedList<ItemStack> req = new LinkedList<ItemStack>();
/*     */       
/* 506 */       slot.getRequirementsForPlacement(this.context, req);
/*     */       
/* 508 */       for (ItemStack stk : req) {
/* 509 */         if (stk != null) {
/* 510 */           tmpReq.add(stk.func_77946_l());
/*     */         }
/*     */       } 
/* 513 */     } catch (Throwable t) {
/*     */       
/* 515 */       t.printStackTrace();
/* 516 */       BCLog.logger.throwing(t);
/*     */     } 
/*     */     
/* 519 */     LinkedList<ItemStack> stacksUsed = new LinkedList<ItemStack>();
/*     */     
/* 521 */     if (this.context.world().func_72912_H().func_76077_q() == WorldSettings.GameType.CREATIVE) {
/* 522 */       for (ItemStack s : tmpReq) {
/* 523 */         stacksUsed.add(s);
/*     */       }
/*     */       
/* 526 */       return (builder.energyAvailable() >= slot.getEnergyRequirement(stacksUsed));
/*     */     } 
/*     */     
/* 529 */     InventoryCopy inventoryCopy = new InventoryCopy((IInventory)builder);
/*     */     
/* 531 */     for (ItemStack reqStk : tmpReq) {
/* 532 */       boolean itemBlock = reqStk.func_77973_b() instanceof ItemBlock;
/* 533 */       Fluid fluid = itemBlock ? FluidRegistry.lookupFluidForBlock(((ItemBlock)reqStk.func_77973_b()).field_150939_a) : null;
/*     */       
/* 535 */       if (fluid != null && builder.drainBuild(new FluidStack(fluid, 1000), true)) {
/*     */         continue;
/*     */       }
/*     */       
/* 539 */       for (IInvSlot slotInv : InventoryIterator.getIterable((IInventory)inventoryCopy, ForgeDirection.UNKNOWN)) {
/* 540 */         if (!builder.isBuildingMaterialSlot(slotInv.getIndex())) {
/*     */           continue;
/*     */         }
/*     */         
/* 544 */         ItemStack invStk = slotInv.getStackInSlot();
/* 545 */         if (invStk == null || invStk.field_77994_a == 0) {
/*     */           continue;
/*     */         }
/*     */         
/* 549 */         FluidStack fluidStack = (fluid != null) ? FluidContainerRegistry.getFluidForFilledItem(invStk) : null;
/* 550 */         boolean compatibleContainer = (fluidStack != null && fluidStack.getFluid() == fluid && fluidStack.amount >= 1000);
/*     */         
/* 552 */         if (slot.isItemMatchingRequirement(invStk, reqStk) || compatibleContainer) {
/*     */           try {
/* 554 */             stacksUsed.add(slot.useItem(this.context, reqStk, slotInv));
/* 555 */           } catch (Throwable t) {
/*     */             
/* 557 */             t.printStackTrace();
/* 558 */             BCLog.logger.throwing(t);
/*     */           } 
/*     */           
/* 561 */           if (reqStk.field_77994_a == 0) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 567 */       if (reqStk.field_77994_a != 0) {
/* 568 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 572 */     return (builder.energyAvailable() >= slot.getEnergyRequirement(stacksUsed));
/*     */   }
/*     */ 
/*     */   
/*     */   public void useRequirements(IInventory inv, BuildingSlot slot) {
/* 577 */     if (slot instanceof BuildingSlotBlock && ((BuildingSlotBlock)slot).mode == BuildingSlotBlock.Mode.ClearIfInvalid) {
/*     */       return;
/*     */     }
/*     */     
/* 581 */     LinkedList<ItemStack> tmpReq = new LinkedList<ItemStack>();
/*     */     
/*     */     try {
/* 584 */       for (ItemStack stk : slot.getRequirements(this.context)) {
/* 585 */         if (stk != null) {
/* 586 */           tmpReq.add(stk.func_77946_l());
/*     */         }
/*     */       } 
/* 589 */     } catch (Throwable t) {
/*     */       
/* 591 */       t.printStackTrace();
/* 592 */       BCLog.logger.throwing(t);
/*     */     } 
/*     */ 
/*     */     
/* 596 */     if (this.context.world().func_72912_H().func_76077_q() == WorldSettings.GameType.CREATIVE) {
/* 597 */       for (ItemStack s : tmpReq) {
/* 598 */         slot.addStackConsumed(s);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 604 */     ListIterator<ItemStack> itr = tmpReq.listIterator();
/*     */     
/* 606 */     while (itr.hasNext()) {
/* 607 */       ItemStack reqStk = itr.next();
/* 608 */       boolean smallStack = (reqStk.field_77994_a == 1);
/* 609 */       ItemStack usedStack = reqStk;
/*     */       
/* 611 */       boolean itemBlock = reqStk.func_77973_b() instanceof ItemBlock;
/* 612 */       Fluid fluid = itemBlock ? FluidRegistry.lookupFluidForBlock(((ItemBlock)reqStk.func_77973_b()).field_150939_a) : null;
/*     */       
/* 614 */       if (fluid != null && inv instanceof TileAbstractBuilder && ((TileAbstractBuilder)inv)
/*     */         
/* 616 */         .drainBuild(new FluidStack(fluid, 1000), true)) {
/*     */         continue;
/*     */       }
/*     */       
/* 620 */       for (IInvSlot slotInv : InventoryIterator.getIterable(inv, ForgeDirection.UNKNOWN)) {
/* 621 */         if (inv instanceof TileAbstractBuilder && 
/* 622 */           !((TileAbstractBuilder)inv).isBuildingMaterialSlot(slotInv.getIndex())) {
/*     */           continue;
/*     */         }
/*     */         
/* 626 */         ItemStack invStk = slotInv.getStackInSlot();
/*     */         
/* 628 */         if (invStk == null || invStk.field_77994_a == 0) {
/*     */           continue;
/*     */         }
/*     */         
/* 632 */         FluidStack fluidStack = (fluid != null) ? FluidContainerRegistry.getFluidForFilledItem(invStk) : null;
/* 633 */         boolean fluidFound = (fluidStack != null && fluidStack.getFluid() == fluid && fluidStack.amount >= 1000);
/*     */         
/* 635 */         if (fluidFound || slot.getSchematic().isItemMatchingRequirement(invStk, reqStk)) {
/*     */           try {
/* 637 */             usedStack = slot.getSchematic().useItem(this.context, reqStk, slotInv);
/* 638 */             slot.addStackConsumed(usedStack);
/* 639 */           } catch (Throwable t) {
/*     */             
/* 641 */             t.printStackTrace();
/* 642 */             BCLog.logger.throwing(t);
/*     */           } 
/*     */           
/* 645 */           if (reqStk.field_77994_a == 0) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 651 */       if (reqStk.field_77994_a != 0) {
/*     */         return;
/*     */       }
/*     */       
/* 655 */       if (smallStack) {
/* 656 */         itr.set(usedStack);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<RequirementItemStack> getNeededItems() {
/* 662 */     return this.neededItems;
/*     */   }
/*     */   
/*     */   protected void onRemoveBuildingSlotBlock(BuildingSlotBlock slot) {
/* 666 */     this.buildStageOccurences[slot.buildStage] = this.buildStageOccurences[slot.buildStage] - 1;
/* 667 */     LinkedList<ItemStack> stacks = new LinkedList<ItemStack>();
/*     */     
/*     */     try {
/* 670 */       stacks = slot.getRequirements(this.context);
/* 671 */     } catch (Throwable t) {
/*     */       
/* 673 */       t.printStackTrace();
/* 674 */       BCLog.logger.throwing(t);
/*     */     } 
/*     */     
/* 677 */     HashMap<StackKey, Integer> computeStacks = new HashMap<StackKey, Integer>();
/*     */     
/* 679 */     for (ItemStack stack : stacks) {
/* 680 */       if (stack == null || stack.func_77973_b() == null || stack.field_77994_a == 0) {
/*     */         continue;
/*     */       }
/*     */       
/* 684 */       StackKey key = new StackKey(stack);
/*     */       
/* 686 */       if (!computeStacks.containsKey(key)) {
/* 687 */         computeStacks.put(key, Integer.valueOf(stack.field_77994_a)); continue;
/*     */       } 
/* 689 */       Integer num = computeStacks.get(key);
/* 690 */       num = Integer.valueOf(num.intValue() + stack.field_77994_a);
/* 691 */       computeStacks.put(key, num);
/*     */     } 
/*     */ 
/*     */     
/* 695 */     for (RequirementItemStack ris : this.neededItems) {
/* 696 */       StackKey stackKey = new StackKey(ris.stack);
/* 697 */       if (computeStacks.containsKey(stackKey)) {
/* 698 */         Integer num = computeStacks.get(stackKey);
/* 699 */         if (ris.size <= num.intValue()) {
/* 700 */           recomputeNeededItems();
/*     */           return;
/*     */         } 
/* 703 */         this.neededItems.set(this.neededItems.indexOf(ris), new RequirementItemStack(ris.stack, ris.size - num.intValue()));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 708 */     sortNeededItems();
/*     */   }
/*     */   
/*     */   private void sortNeededItems() {
/* 712 */     Collections.sort(this.neededItems, new Comparator<RequirementItemStack>()
/*     */         {
/*     */           public int compare(RequirementItemStack o1, RequirementItemStack o2) {
/* 715 */             if (o1.size != o2.size) {
/* 716 */               return (o1.size < o2.size) ? 1 : -1;
/*     */             }
/* 718 */             ItemStack os1 = o1.stack;
/* 719 */             ItemStack os2 = o2.stack;
/* 720 */             if (Item.func_150891_b(os1.func_77973_b()) > Item.func_150891_b(os2.func_77973_b()))
/* 721 */               return -1; 
/* 722 */             if (Item.func_150891_b(os1.func_77973_b()) < Item.func_150891_b(os2.func_77973_b()))
/* 723 */               return 1; 
/* 724 */             if (os1.func_77960_j() > os2.func_77960_j())
/* 725 */               return -1; 
/* 726 */             if (os1.func_77960_j() < os2.func_77960_j()) {
/* 727 */               return 1;
/*     */             }
/* 729 */             return 0;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void recomputeNeededItems() {
/* 737 */     this.neededItems.clear();
/*     */     
/* 739 */     HashMap<StackKey, Integer> computeStacks = new HashMap<StackKey, Integer>();
/*     */     
/* 741 */     for (List<BuildingSlotBlock> lb : this.buildList.values()) {
/* 742 */       for (BuildingSlotBlock slot : lb) {
/* 743 */         if (slot == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 747 */         LinkedList<ItemStack> stacks = new LinkedList<ItemStack>();
/*     */         
/*     */         try {
/* 750 */           stacks = slot.getRequirements(this.context);
/* 751 */         } catch (Throwable t) {
/*     */           
/* 753 */           t.printStackTrace();
/* 754 */           BCLog.logger.throwing(t);
/*     */         } 
/*     */         
/* 757 */         for (ItemStack stack : stacks) {
/* 758 */           if (stack == null || stack.func_77973_b() == null || stack.field_77994_a == 0) {
/*     */             continue;
/*     */           }
/*     */           
/* 762 */           StackKey key = new StackKey(stack);
/*     */           
/* 764 */           if (!computeStacks.containsKey(key)) {
/* 765 */             computeStacks.put(key, Integer.valueOf(stack.field_77994_a)); continue;
/*     */           } 
/* 767 */           Integer num = computeStacks.get(key);
/* 768 */           num = Integer.valueOf(num.intValue() + stack.field_77994_a);
/*     */           
/* 770 */           computeStacks.put(key, num);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 776 */     for (BuildingSlotEntity slot : this.entityList) {
/* 777 */       LinkedList<ItemStack> stacks = new LinkedList<ItemStack>();
/*     */       
/*     */       try {
/* 780 */         stacks = slot.getRequirements(this.context);
/* 781 */       } catch (Throwable t) {
/*     */         
/* 783 */         t.printStackTrace();
/* 784 */         BCLog.logger.throwing(t);
/*     */       } 
/*     */       
/* 787 */       for (ItemStack stack : stacks) {
/* 788 */         if (stack == null || stack.func_77973_b() == null || stack.field_77994_a == 0) {
/*     */           continue;
/*     */         }
/*     */         
/* 792 */         StackKey key = new StackKey(stack);
/*     */         
/* 794 */         if (!computeStacks.containsKey(key)) {
/* 795 */           computeStacks.put(key, Integer.valueOf(stack.field_77994_a)); continue;
/*     */         } 
/* 797 */         Integer num = computeStacks.get(key);
/* 798 */         num = Integer.valueOf(num.intValue() + stack.field_77994_a);
/*     */         
/* 800 */         computeStacks.put(key, num);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 806 */     for (Map.Entry<StackKey, Integer> e : computeStacks.entrySet()) {
/* 807 */       this.neededItems.add(new RequirementItemStack(((StackKey)e.getKey()).stack.func_77946_l(), ((Integer)e.getValue()).intValue()));
/*     */     }
/*     */     
/* 810 */     sortNeededItems();
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcessing(World world) {
/* 815 */     for (BuildingSlot s : this.postProcessing) {
/*     */       try {
/* 817 */         s.postProcessing(this.context);
/* 818 */       } catch (Throwable t) {
/*     */         
/* 820 */         t.printStackTrace();
/* 821 */         BCLog.logger.throwing(t);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveBuildStateToNBT(NBTTagCompound nbt, IBuildingItemsProvider builder) {
/* 828 */     super.saveBuildStateToNBT(nbt, builder);
/*     */     
/* 830 */     int[] entitiesBuiltArr = new int[this.builtEntities.size()];
/*     */     
/* 832 */     int id = 0;
/*     */     
/* 834 */     for (Integer i : this.builtEntities) {
/* 835 */       entitiesBuiltArr[id] = i.intValue();
/* 836 */       id++;
/*     */     } 
/*     */     
/* 839 */     nbt.func_74783_a("builtEntities", entitiesBuiltArr);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadBuildStateToNBT(NBTTagCompound nbt, IBuildingItemsProvider builder) {
/* 844 */     super.loadBuildStateToNBT(nbt, builder);
/*     */     
/* 846 */     int[] entitiesBuiltArr = nbt.func_74759_k("builtEntities");
/*     */     
/* 848 */     for (int i = 0; i < entitiesBuiltArr.length; i++)
/* 849 */       this.builtEntities.add(Integer.valueOf(i)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\BptBuilderBlueprint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */