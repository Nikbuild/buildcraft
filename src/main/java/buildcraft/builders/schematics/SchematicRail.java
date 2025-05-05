/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.core.builders.schematics.SchematicBlockFloored;
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
/*    */ public class SchematicRail
/*    */   extends SchematicBlockFloored
/*    */ {
/*    */   public void rotateLeft(IBuilderContext context) {
/* 21 */     switch (this.meta) {
/*    */       case 0:
/* 23 */         this.meta = 1;
/*    */         break;
/*    */       case 1:
/* 26 */         this.meta = 0;
/*    */         break;
/*    */       
/*    */       case 2:
/* 30 */         this.meta = 5;
/*    */         break;
/*    */       case 3:
/* 33 */         this.meta = 4;
/*    */         break;
/*    */       case 4:
/* 36 */         this.meta = 2;
/*    */         break;
/*    */       case 5:
/* 39 */         this.meta = 3;
/*    */         break;
/*    */       
/*    */       case 6:
/*    */       case 7:
/*    */       case 8:
/* 45 */         this.meta++;
/*    */         break;
/*    */       case 9:
/* 48 */         this.meta = 6;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 55 */     context.world().func_147465_d(x, y, z, this.block, 0, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 60 */     return (this.block == context.world().func_147439_a(x, y, z));
/*    */   }
/*    */ 
/*    */   
/*    */   public void postProcessing(IBuilderContext context, int x, int y, int z) {
/* 65 */     context.world().func_72921_c(x, y, z, this.meta, 3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicRail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */