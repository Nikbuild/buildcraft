/*     */ package buildcraft.api.blueprints;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
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
/*     */ public class SchematicBlock
/*     */   extends SchematicBlockBase
/*     */ {
/*  30 */   public static final BlockIndex[] RELATIVE_INDEXES = new BlockIndex[] { new BlockIndex(0, -1, 0), new BlockIndex(0, 1, 0), new BlockIndex(0, 0, -1), new BlockIndex(0, 0, 1), new BlockIndex(-1, 0, 0), new BlockIndex(1, 0, 0) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   public Block block = null;
/*  40 */   public int meta = 0;
/*  41 */   public BuildingPermission defaultPermission = BuildingPermission.ALL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public ItemStack[] storedRequirements = new ItemStack[0];
/*     */   
/*     */   private boolean doNotUse = false;
/*     */ 
/*     */   
/*     */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/*  54 */     if (this.block != null) {
/*  55 */       if (this.storedRequirements.length != 0) {
/*  56 */         Collections.addAll(requirements, this.storedRequirements);
/*     */       } else {
/*  58 */         requirements.add(new ItemStack(this.block, 1, this.meta));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/*  65 */     return (this.block == context.world().func_147439_a(x, y, z) && this.meta == context.world().func_72805_g(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/*  70 */     super.placeInWorld(context, x, y, z, stacks);
/*     */     
/*  72 */     setBlockInWorld(context, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {
/*  77 */     super.storeRequirements(context, x, y, z);
/*     */     
/*  79 */     if (this.block != null) {
/*  80 */       ArrayList<ItemStack> req = this.block.getDrops(context.world(), x, y, z, context
/*  81 */           .world().func_72805_g(x, y, z), 0);
/*     */       
/*  83 */       if (req != null) {
/*  84 */         this.storedRequirements = new ItemStack[req.size()];
/*  85 */         req.toArray(this.storedRequirements);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/*  92 */     super.writeSchematicToNBT(nbt, registry);
/*     */     
/*  94 */     writeBlockToNBT(nbt, registry);
/*  95 */     writeRequirementsToNBT(nbt, registry);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 100 */     super.readSchematicFromNBT(nbt, registry);
/*     */     
/* 102 */     readBlockFromNBT(nbt, registry);
/* 103 */     if (!doNotUse()) {
/* 104 */       readRequirementsFromNBT(nbt, registry);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
/* 112 */     Set<BlockIndex> indexes = new HashSet<BlockIndex>();
/* 113 */     if (this.block instanceof net.minecraft.block.BlockFalling) {
/* 114 */       indexes.add(RELATIVE_INDEXES[ForgeDirection.DOWN.ordinal()]);
/*     */     }
/* 116 */     return indexes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schematic.BuildingStage getBuildStage() {
/* 121 */     if (this.block instanceof net.minecraftforge.fluids.BlockFluidBase || this.block instanceof net.minecraft.block.BlockLiquid) {
/* 122 */       return Schematic.BuildingStage.EXPANDING;
/*     */     }
/* 124 */     return Schematic.BuildingStage.STANDALONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BuildingPermission getBuildingPermission() {
/* 130 */     return this.defaultPermission;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setBlockInWorld(IBuilderContext context, int x, int y, int z) {
/* 136 */     context.world().func_147465_d(x, y, z, this.block, this.meta, 3);
/* 137 */     context.world().func_72921_c(x, y, z, this.meta, 3);
/*     */   }
/*     */   
/*     */   public boolean doNotUse() {
/* 141 */     return this.doNotUse;
/*     */   }
/*     */   
/*     */   protected void readBlockFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
/*     */     try {
/* 146 */       this.block = registry.getBlockForId(nbt.func_74762_e("blockId"));
/* 147 */       this.meta = nbt.func_74762_e("blockMeta");
/* 148 */     } catch (MappingNotFoundException e) {
/* 149 */       this.doNotUse = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readRequirementsFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 154 */     if (nbt.func_74764_b("rq")) {
/* 155 */       NBTTagList rq = nbt.func_150295_c("rq", 10);
/*     */       
/* 157 */       ArrayList<ItemStack> rqs = new ArrayList<ItemStack>();
/* 158 */       for (int i = 0; i < rq.func_74745_c(); i++) {
/*     */         try {
/* 160 */           NBTTagCompound sub = rq.func_150305_b(i);
/* 161 */           if (sub.func_74762_e("id") >= 0) {
/* 162 */             registry.stackToWorld(sub);
/* 163 */             rqs.add(ItemStack.func_77949_a(sub));
/*     */           } else {
/* 165 */             this.defaultPermission = BuildingPermission.CREATIVE_ONLY;
/*     */           } 
/* 167 */         } catch (MappingNotFoundException e) {
/* 168 */           this.defaultPermission = BuildingPermission.CREATIVE_ONLY;
/* 169 */         } catch (Throwable t) {
/* 170 */           t.printStackTrace();
/* 171 */           this.defaultPermission = BuildingPermission.CREATIVE_ONLY;
/*     */         } 
/*     */       } 
/*     */       
/* 175 */       this.storedRequirements = rqs.<ItemStack>toArray(new ItemStack[rqs.size()]);
/*     */     } else {
/* 177 */       this.storedRequirements = new ItemStack[0];
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void writeBlockToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 182 */     nbt.func_74768_a("blockId", registry.getIdForBlock(this.block));
/* 183 */     nbt.func_74768_a("blockMeta", this.meta);
/*     */   }
/*     */   
/*     */   protected void writeRequirementsToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 187 */     if (this.storedRequirements.length > 0) {
/* 188 */       NBTTagList rq = new NBTTagList();
/*     */       
/* 190 */       for (ItemStack stack : this.storedRequirements) {
/* 191 */         NBTTagCompound sub = new NBTTagCompound();
/* 192 */         stack.func_77955_b(sub);
/* 193 */         registry.stackToRegistry(sub);
/* 194 */         rq.func_74742_a((NBTBase)sub);
/*     */       } 
/*     */       
/* 197 */       nbt.func_74782_a("rq", (NBTBase)rq);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\SchematicBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */