/*    */ package buildcraft.core.builders;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.MappingNotFoundException;
/*    */ import buildcraft.api.blueprints.MappingRegistry;
/*    */ import buildcraft.api.blueprints.Schematic;
/*    */ import buildcraft.api.core.Position;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuildingSlot
/*    */ {
/*    */   public LinkedList<ItemStack> stackConsumed;
/*    */   public boolean reserved = false;
/*    */   public boolean built = false;
/*    */   
/*    */   public boolean writeToWorld(IBuilderContext context) {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeCompleted(IBuilderContext context, double complete) {}
/*    */ 
/*    */   
/*    */   public void postProcessing(IBuilderContext context) {}
/*    */ 
/*    */   
/*    */   public LinkedList<ItemStack> getRequirements(IBuilderContext context) {
/* 44 */     return new LinkedList<ItemStack>();
/*    */   }
/*    */   
/*    */   public abstract Position getDestination();
/*    */   
/*    */   public void addStackConsumed(ItemStack stack) {
/* 50 */     if (this.stackConsumed == null) {
/* 51 */       this.stackConsumed = new LinkedList<ItemStack>();
/*    */     }
/*    */     
/* 54 */     this.stackConsumed.add(stack);
/*    */   }
/*    */   
/*    */   public List<ItemStack> getStacksToDisplay() {
/* 58 */     return getSchematic().getStacksToDisplay(this.stackConsumed);
/*    */   }
/*    */   
/*    */   public abstract boolean isAlreadyBuilt(IBuilderContext paramIBuilderContext);
/*    */   
/*    */   public abstract Schematic getSchematic();
/*    */   
/*    */   public abstract void writeToNBT(NBTTagCompound paramNBTTagCompound, MappingRegistry paramMappingRegistry);
/*    */   
/*    */   public abstract void readFromNBT(NBTTagCompound paramNBTTagCompound, MappingRegistry paramMappingRegistry) throws MappingNotFoundException;
/*    */   
/*    */   public abstract int getEnergyRequirement();
/*    */   
/*    */   public abstract int buildTime();
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\BuildingSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */