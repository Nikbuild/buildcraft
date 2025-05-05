/*    */ package buildcraft.api.blueprints;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicFluid
/*    */   extends SchematicBlock
/*    */ {
/*    */   private final ItemStack fluidItem;
/*    */   
/*    */   public SchematicFluid(FluidStack fluidStack) {
/* 21 */     this.fluidItem = new ItemStack(fluidStack.getFluid().getBlock(), 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 26 */     if (this.meta == 0) {
/* 27 */       requirements.add(this.fluidItem);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 38 */     if (this.meta == 0) {
/* 39 */       return (this.block == context.world().func_147439_a(x, y, z) && context.world().func_72805_g(x, y, z) == 0);
/*    */     }
/* 41 */     return (this.block == context.world().func_147439_a(x, y, z));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void rotateLeft(IBuilderContext context) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doNotBuild() {
/* 52 */     return (this.meta != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 57 */     if (this.meta == 0) {
/* 58 */       context.world().func_147465_d(x, y, z, this.block, 0, 3);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void postProcessing(IBuilderContext context, int x, int y, int z) {
/* 64 */     if (this.meta != 0) {
/* 65 */       context.world().func_147465_d(x, y, z, this.block, this.meta, 3);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LinkedList<ItemStack> getStacksToDisplay(LinkedList<ItemStack> stackConsumed) {
/* 72 */     LinkedList<ItemStack> result = new LinkedList<ItemStack>();
/* 73 */     result.add(this.fluidItem);
/* 74 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyRequirement(LinkedList<ItemStack> stacksUsed) {
/* 79 */     return 240;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\SchematicFluid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */