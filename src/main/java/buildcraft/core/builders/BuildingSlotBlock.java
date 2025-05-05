/*     */ package buildcraft.core.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftBuilders;
/*     */ import buildcraft.api.blueprints.BuildingPermission;
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.blueprints.MappingNotFoundException;
/*     */ import buildcraft.api.blueprints.MappingRegistry;
/*     */ import buildcraft.api.blueprints.Schematic;
/*     */ import buildcraft.api.blueprints.SchematicBlock;
/*     */ import buildcraft.api.blueprints.SchematicBlockBase;
/*     */ import buildcraft.api.blueprints.SchematicFactory;
/*     */ import buildcraft.api.blueprints.SchematicMask;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.blueprints.IndexRequirementMap;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuildingSlotBlock
/*     */   extends BuildingSlot
/*     */ {
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   public SchematicBlockBase schematic;
/*     */   public IndexRequirementMap internalRequirementRemovalListener;
/*     */   
/*     */   public enum Mode
/*     */   {
/*  46 */     ClearIfInvalid, Build;
/*     */   }
/*     */   
/*  49 */   public Mode mode = Mode.Build;
/*     */   
/*  51 */   public int buildStage = 0;
/*     */ 
/*     */   
/*     */   public SchematicBlockBase getSchematic() {
/*  55 */     if (this.schematic == null) {
/*  56 */       return (SchematicBlockBase)new SchematicMask(false);
/*     */     }
/*  58 */     return this.schematic;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeToWorld(IBuilderContext context) {
/*  64 */     if (this.internalRequirementRemovalListener != null) {
/*  65 */       this.internalRequirementRemovalListener.remove(this);
/*     */     }
/*     */     
/*  68 */     if (this.mode == Mode.ClearIfInvalid) {
/*  69 */       if (!getSchematic().isAlreadyBuilt(context, this.x, this.y, this.z)) {
/*  70 */         if (BuildCraftBuilders.dropBrokenBlocks) {
/*  71 */           return BlockUtils.breakBlock((WorldServer)context.world(), this.x, this.y, this.z);
/*     */         }
/*  73 */         context.world().func_147468_f(this.x, this.y, this.z);
/*  74 */         return true;
/*     */       } 
/*     */     } else {
/*     */       
/*     */       try {
/*  79 */         getSchematic().placeInWorld(context, this.x, this.y, this.z, this.stackConsumed);
/*     */ 
/*     */ 
/*     */         
/*  83 */         if (!getSchematic().isAlreadyBuilt(context, this.x, this.y, this.z)) {
/*  84 */           if (context.world().func_147437_c(this.x, this.y, this.z))
/*  85 */             return false; 
/*  86 */           if (!(getSchematic() instanceof SchematicBlock) || context
/*  87 */             .world().func_147439_a(this.x, this.y, this.z).func_149667_c(((SchematicBlock)getSchematic()).block)) {
/*  88 */             BCLog.logger.warn("Placed block does not match expectations! Most likely a bug in BuildCraft or a supported mod. Removed mismatched block.");
/*  89 */             BCLog.logger.warn("Location: " + this.x + ", " + this.y + ", " + this.z + " - Block: " + Block.field_149771_c.func_148750_c(context.world().func_147439_a(this.x, this.y, this.z)) + "@" + context.world().func_72805_g(this.x, this.y, this.z));
/*  90 */             context.world().func_147475_p(this.x, this.y, this.z);
/*  91 */             context.world().func_147468_f(this.x, this.y, this.z);
/*  92 */             return true;
/*     */           } 
/*  94 */           return false;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 100 */         if (!context.world().func_147437_c(this.x, this.y, this.z) && 
/* 101 */           getSchematic().getBuildingPermission() == BuildingPermission.ALL && 
/* 102 */           getSchematic() instanceof SchematicBlock) {
/* 103 */           SchematicBlock sb = (SchematicBlock)getSchematic();
/*     */           
/* 105 */           ItemStack[] oldRequirementsArray = sb.storedRequirements;
/* 106 */           List<ItemStack> oldRequirements = Arrays.asList(oldRequirementsArray);
/* 107 */           sb.storedRequirements = new ItemStack[0];
/* 108 */           sb.storeRequirements(context, this.x, this.y, this.z);
/* 109 */           for (ItemStack s : sb.storedRequirements) {
/* 110 */             boolean contains = false;
/* 111 */             for (ItemStack ss : oldRequirements) {
/* 112 */               if (getSchematic().isItemMatchingRequirement(s, ss)) {
/* 113 */                 contains = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 117 */             if (!contains) {
/* 118 */               BCLog.logger.warn("Blueprint has MISMATCHING REQUIREMENTS! Potential corrupted/hacked blueprint! Removed mismatched block.");
/* 119 */               BCLog.logger.warn("Location: " + this.x + ", " + this.y + ", " + this.z + " - ItemStack: " + s.toString());
/* 120 */               context.world().func_147475_p(this.x, this.y, this.z);
/* 121 */               context.world().func_147468_f(this.x, this.y, this.z);
/* 122 */               return true;
/*     */             } 
/*     */           } 
/*     */           
/* 126 */           sb.storedRequirements = oldRequirementsArray;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 136 */         TileEntity e = context.world().func_147438_o(this.x, this.y, this.z);
/*     */         
/* 138 */         if (e != null) {
/* 139 */           e.func_145845_h();
/*     */         }
/*     */         
/* 142 */         return true;
/* 143 */       } catch (Throwable t) {
/* 144 */         t.printStackTrace();
/* 145 */         context.world().func_147468_f(this.x, this.y, this.z);
/* 146 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcessing(IBuilderContext context) {
/* 155 */     getSchematic().postProcessing(context, this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkedList<ItemStack> getRequirements(IBuilderContext context) {
/* 160 */     if (this.mode == Mode.ClearIfInvalid) {
/* 161 */       return new LinkedList<ItemStack>();
/*     */     }
/* 163 */     LinkedList<ItemStack> req = new LinkedList<ItemStack>();
/*     */     
/* 165 */     getSchematic().getRequirementsForPlacement(context, req);
/*     */     
/* 167 */     return req;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Position getDestination() {
/* 173 */     return new Position(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCompleted(IBuilderContext context, double complete) {
/* 178 */     if (this.mode == Mode.ClearIfInvalid) {
/* 179 */       context.world().func_147443_d(0, this.x, this.y, this.z, (int)(complete * 10.0D) - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlreadyBuilt(IBuilderContext context) {
/* 186 */     return this.schematic.isAlreadyBuilt(context, this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 191 */     nbt.func_74774_a("mode", (byte)this.mode.ordinal());
/* 192 */     nbt.func_74768_a("x", this.x);
/* 193 */     nbt.func_74768_a("y", this.y);
/* 194 */     nbt.func_74768_a("z", this.z);
/*     */     
/* 196 */     if (this.schematic != null) {
/* 197 */       NBTTagCompound schematicNBT = new NBTTagCompound();
/* 198 */       SchematicFactory.getFactory(this.schematic.getClass())
/* 199 */         .saveSchematicToWorldNBT(schematicNBT, (Schematic)this.schematic, registry);
/* 200 */       nbt.func_74782_a("schematic", (NBTBase)schematicNBT);
/*     */     } 
/*     */     
/* 203 */     NBTTagList nbtStacks = new NBTTagList();
/*     */     
/* 205 */     if (this.stackConsumed != null) {
/* 206 */       for (ItemStack stack : this.stackConsumed) {
/* 207 */         NBTTagCompound nbtStack = new NBTTagCompound();
/* 208 */         stack.func_77955_b(nbtStack);
/* 209 */         nbtStacks.func_74742_a((NBTBase)nbtStack);
/*     */       } 
/*     */     }
/*     */     
/* 213 */     nbt.func_74782_a("stackConsumed", (NBTBase)nbtStacks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt, MappingRegistry registry) throws MappingNotFoundException {
/* 218 */     this.mode = Mode.values()[nbt.func_74771_c("mode")];
/* 219 */     this.x = nbt.func_74762_e("x");
/* 220 */     this.y = nbt.func_74762_e("y");
/* 221 */     this.z = nbt.func_74762_e("z");
/*     */     
/* 223 */     if (nbt.func_74764_b("schematic")) {
/* 224 */       this
/* 225 */         .schematic = (SchematicBlockBase)SchematicFactory.createSchematicFromWorldNBT(nbt.func_74775_l("schematic"), registry);
/*     */     }
/*     */     
/* 228 */     this.stackConsumed = new LinkedList<ItemStack>();
/*     */     
/* 230 */     NBTTagList nbtStacks = nbt.func_150295_c("stackConsumed", 10);
/*     */     
/* 232 */     for (int i = 0; i < nbtStacks.func_74745_c(); i++) {
/* 233 */       this.stackConsumed.add(ItemStack.func_77949_a(nbtStacks
/* 234 */             .func_150305_b(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemStack> getStacksToDisplay() {
/* 241 */     if (this.mode == Mode.ClearIfInvalid) {
/* 242 */       return this.stackConsumed;
/*     */     }
/* 244 */     return getSchematic().getStacksToDisplay(this.stackConsumed);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyRequirement() {
/* 250 */     return this.schematic.getEnergyRequirement(this.stackConsumed);
/*     */   }
/*     */ 
/*     */   
/*     */   public int buildTime() {
/* 255 */     if (this.schematic == null) {
/* 256 */       return 1;
/*     */     }
/* 258 */     return this.schematic.buildTime();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\BuildingSlotBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */