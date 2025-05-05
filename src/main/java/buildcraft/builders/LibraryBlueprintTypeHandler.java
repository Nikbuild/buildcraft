/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.BuildCraftBuilders;
/*    */ import buildcraft.api.library.LibraryTypeHandler;
/*    */ import buildcraft.api.library.LibraryTypeHandlerNBT;
/*    */ import buildcraft.core.blueprints.BlueprintBase;
/*    */ import buildcraft.core.blueprints.LibraryId;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class LibraryBlueprintTypeHandler extends LibraryTypeHandlerNBT {
/*    */   private final boolean isBlueprint;
/*    */   
/*    */   public LibraryBlueprintTypeHandler(boolean isBlueprint) {
/* 15 */     super(isBlueprint ? "bpt" : "tpl");
/* 16 */     this.isBlueprint = isBlueprint;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHandler(ItemStack stack, LibraryTypeHandler.HandlerType type) {
/* 21 */     if (this.isBlueprint) {
/* 22 */       return (stack.func_77973_b() instanceof ItemBlueprintStandard && (type == LibraryTypeHandler.HandlerType.LOAD || ItemBlueprint.isContentReadable(stack)));
/*    */     }
/* 24 */     return (stack.func_77973_b() instanceof ItemBlueprintTemplate && (type == LibraryTypeHandler.HandlerType.LOAD || ItemBlueprint.isContentReadable(stack)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTextColor() {
/* 30 */     return this.isBlueprint ? 3166336 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName(ItemStack stack) {
/* 35 */     LibraryId id = ItemBlueprint.getId(stack);
/* 36 */     return (id != null) ? id.name : "<<CORRUPT>>";
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack load(ItemStack stack, NBTTagCompound compound) {
/* 41 */     BlueprintBase blueprint = BlueprintBase.loadBluePrint((NBTTagCompound)compound.func_74737_b());
/* 42 */     blueprint.id.name = compound.func_74779_i("__filename");
/* 43 */     blueprint.id.extension = getOutputExtension();
/* 44 */     BuildCraftBuilders.serverDB.add(blueprint.id, compound);
/* 45 */     return blueprint.getStack();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean store(ItemStack stack, NBTTagCompound compound) {
/* 50 */     LibraryId id = ItemBlueprint.getId(stack);
/* 51 */     if (id == null) {
/* 52 */       return false;
/*    */     }
/*    */     
/* 55 */     NBTTagCompound nbt = BuildCraftBuilders.serverDB.load(id);
/* 56 */     if (nbt == null) {
/* 57 */       return false;
/*    */     }
/*    */     
/* 60 */     for (Object o : nbt.func_150296_c()) {
/* 61 */       compound.func_74782_a((String)o, nbt.func_74781_a((String)o));
/*    */     }
/* 63 */     id.write(compound);
/* 64 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\LibraryBlueprintTypeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */