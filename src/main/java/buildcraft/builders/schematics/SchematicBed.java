/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.init.Items;
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
/*    */ public class SchematicBed
/*    */   extends SchematicBlock
/*    */ {
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 23 */     if ((this.meta & 0x8) == 0) {
/* 24 */       requirements.add(new ItemStack(Items.field_151104_aV));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void rotateLeft(IBuilderContext context) {
/* 35 */     int orientation = this.meta & 0x7;
/* 36 */     int others = this.meta - orientation;
/*    */     
/* 38 */     switch (orientation) {
/*    */       case 0:
/* 40 */         this.meta = 1 + others;
/*    */         break;
/*    */       case 1:
/* 43 */         this.meta = 2 + others;
/*    */         break;
/*    */       case 2:
/* 46 */         this.meta = 3 + others;
/*    */         break;
/*    */       case 3:
/* 49 */         this.meta = 0 + others;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 56 */     if ((this.meta & 0x8) != 0) {
/*    */       return;
/*    */     }
/*    */     
/* 60 */     context.world().func_147465_d(x, y, z, this.block, this.meta, 3);
/*    */     
/* 62 */     int x2 = x;
/* 63 */     int z2 = z;
/*    */     
/* 65 */     switch (this.meta) {
/*    */       case 0:
/* 67 */         z2++;
/*    */         break;
/*    */       case 1:
/* 70 */         x2--;
/*    */         break;
/*    */       case 2:
/* 73 */         z2--;
/*    */         break;
/*    */       case 3:
/* 76 */         x2++;
/*    */         break;
/*    */     } 
/*    */     
/* 80 */     context.world().func_147465_d(x2, y, z2, this.block, this.meta + 8, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doNotBuild() {
/* 85 */     return ((this.meta & 0x8) != 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicBed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */