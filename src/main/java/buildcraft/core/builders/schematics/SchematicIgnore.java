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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicIgnore
/*    */   extends SchematicBlock
/*    */ {
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {}
/*    */   
/*    */   public void rotateLeft(IBuilderContext context) {}
/*    */   
/*    */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {}
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {}
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */   
/*    */   public boolean doNotBuild() {
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\schematics\SchematicIgnore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */