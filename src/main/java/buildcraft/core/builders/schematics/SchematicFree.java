/*    */ package buildcraft.core.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ import java.util.LinkedList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicFree
/*    */   extends SchematicBlock
/*    */ {
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {}
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */   
/*    */   public LinkedList<ItemStack> getStacksToDisplay(LinkedList<ItemStack> stackConsumed) {
/* 32 */     LinkedList<ItemStack> displayStacks = new LinkedList<ItemStack>();
/* 33 */     displayStacks.add(new ItemStack(this.block, 1, this.meta));
/* 34 */     return displayStacks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\schematics\SchematicFree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */