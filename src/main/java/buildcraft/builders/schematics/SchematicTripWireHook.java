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
/*    */ public class SchematicTripWireHook
/*    */   extends SchematicBlock
/*    */ {
/*    */   public void rotateLeft(IBuilderContext context) {
/* 22 */     int pos = this.meta & 0x3;
/* 23 */     int others = this.meta - pos;
/*    */     
/* 25 */     switch (pos) {
/*    */       case 0:
/* 27 */         pos = 1;
/*    */         break;
/*    */       case 1:
/* 30 */         pos = 2;
/*    */         break;
/*    */       case 2:
/* 33 */         pos = 3;
/*    */         break;
/*    */       case 3:
/* 36 */         pos = 0;
/*    */         break;
/*    */     } 
/*    */     
/* 40 */     this.meta = pos + others;
/*    */   }
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 45 */     context.world().func_147465_d(x, y, z, this.block, this.meta, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 50 */     return (this.block == context.world().func_147439_a(x, y, z));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicTripWireHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */