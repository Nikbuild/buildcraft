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
/*    */ 
/*    */ public class SchematicPumpkin
/*    */   extends SchematicBlock
/*    */ {
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 22 */     requirements.add(new ItemStack(this.block, 1, 0));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 32 */     return (this.block == context.world().func_147439_a(x, y, z));
/*    */   }
/*    */ 
/*    */   
/*    */   public void rotateLeft(IBuilderContext context) {
/* 37 */     switch (this.meta) {
/*    */       case 0:
/* 39 */         this.meta = 1;
/*    */         break;
/*    */       case 1:
/* 42 */         this.meta = 2;
/*    */         break;
/*    */       case 2:
/* 45 */         this.meta = 3;
/*    */         break;
/*    */       case 3:
/* 48 */         this.meta = 0;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicPumpkin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */