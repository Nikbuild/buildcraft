/*     */ package buildcraft.api.blueprints;
/*     */ 
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Schematic
/*     */ {
/*     */   public enum BuildingStage
/*     */   {
/*  44 */     STANDALONE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     EXPANDING;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemMatchingRequirement(ItemStack suppliedStack, ItemStack requiredStack) {
/*  61 */     return BuilderAPI.schematicHelper.isEqualItem(suppliedStack, requiredStack);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack useItem(IBuilderContext context, ItemStack req, IInvSlot slot) {
/*  80 */     ItemStack stack = slot.getStackInSlot();
/*  81 */     ItemStack result = stack.func_77946_l();
/*     */     
/*  83 */     if (stack.field_77994_a >= req.field_77994_a) {
/*  84 */       result.field_77994_a = req.field_77994_a;
/*  85 */       stack.field_77994_a -= req.field_77994_a;
/*  86 */       req.field_77994_a = 0;
/*     */     } else {
/*  88 */       req.field_77994_a -= stack.field_77994_a;
/*  89 */       stack.field_77994_a = 0;
/*     */     } 
/*     */     
/*  92 */     if (stack.field_77994_a == 0) {
/*  93 */       stack.field_77994_a = 1;
/*  94 */       if (stack.func_77973_b().hasContainerItem(stack)) {
/*  95 */         ItemStack newStack = stack.func_77973_b().getContainerItem(stack);
/*  96 */         slot.setStackInSlot(newStack);
/*     */       } else {
/*  98 */         slot.setStackInSlot(null);
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotateLeft(IBuilderContext context) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateToBlueprint(Translation transform) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateToWorld(Translation transform) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void idsToBlueprint(MappingRegistry registry) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void idsToWorld(MappingRegistry registry) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyRequirement(LinkedList<ItemStack> stacksUsed) {
/* 180 */     int result = 0;
/*     */     
/* 182 */     if (stacksUsed != null) {
/* 183 */       for (ItemStack s : stacksUsed) {
/* 184 */         result += s.field_77994_a * 240;
/*     */       }
/*     */     }
/*     */     
/* 188 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedList<ItemStack> getStacksToDisplay(LinkedList<ItemStack> stackConsumed) {
/* 197 */     return stackConsumed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuildingStage getBuildStage() {
/* 204 */     return BuildingStage.STANDALONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doNotBuild() {
/* 225 */     return false;
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
/*     */   public boolean doNotUse() {
/* 237 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuildingPermission getBuildingPermission() {
/* 245 */     return BuildingPermission.ALL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcessing(IBuilderContext context, int x, int y, int z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int buildTime() {
/* 276 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\Schematic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */