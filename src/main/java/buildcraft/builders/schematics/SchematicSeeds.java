/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.core.builders.schematics.SchematicBlockFloored;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicSeeds
/*    */   extends SchematicBlockFloored
/*    */ {
/*    */   public Item seeds;
/*    */   
/*    */   public SchematicSeeds(Item seeds) {
/* 23 */     this.seeds = seeds;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 28 */     requirements.add(new ItemStack(this.seeds));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 38 */     context.world().func_147465_d(x, y, z, this.block, 0, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 43 */     return (context.world().func_147439_a(x, y, z) == this.block);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicSeeds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */