/*    */ package buildcraft.builders.schematics;
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
/*    */ public class SchematicCustomStack
/*    */   extends SchematicBlock
/*    */ {
/*    */   final ItemStack customStack;
/*    */   
/*    */   public SchematicCustomStack(ItemStack customStack) {
/* 23 */     this.customStack = customStack;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 28 */     requirements.add(this.customStack.func_77946_l());
/*    */   }
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicCustomStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */