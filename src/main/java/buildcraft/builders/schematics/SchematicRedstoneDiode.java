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
/*    */ public class SchematicRedstoneDiode
/*    */   extends SchematicBlockFloored
/*    */ {
/*    */   private Item baseItem;
/*    */   
/*    */   public SchematicRedstoneDiode(Item baseItem) {
/* 23 */     this.baseItem = baseItem;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 28 */     requirements.add(new ItemStack(this.baseItem));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void rotateLeft(IBuilderContext context) {
/* 38 */     int step = this.meta - (this.meta & 0x3);
/*    */     
/* 40 */     switch (this.meta - step) {
/*    */       case 0:
/* 42 */         this.meta = 1 + step;
/*    */         break;
/*    */       case 1:
/* 45 */         this.meta = 2 + step;
/*    */         break;
/*    */       case 2:
/* 48 */         this.meta = 3 + step;
/*    */         break;
/*    */       case 3:
/* 51 */         this.meta = 0 + step;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicRedstoneDiode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */