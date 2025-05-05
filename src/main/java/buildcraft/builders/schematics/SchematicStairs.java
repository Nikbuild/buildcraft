/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.Schematic;
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
/*    */ public class SchematicStairs
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
/* 37 */     int pos = this.meta & 0x3;
/* 38 */     int others = this.meta - pos;
/*    */     
/* 40 */     switch (pos) {
/*    */       case 0:
/* 42 */         pos = 2;
/*    */         break;
/*    */       case 1:
/* 45 */         pos = 3;
/*    */         break;
/*    */       case 2:
/* 48 */         pos = 1;
/*    */         break;
/*    */       case 3:
/* 51 */         pos = 0;
/*    */         break;
/*    */     } 
/*    */     
/* 55 */     this.meta = pos + others;
/*    */   }
/*    */ 
/*    */   
/*    */   public Schematic.BuildingStage getBuildStage() {
/* 60 */     return Schematic.BuildingStage.STANDALONE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicStairs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */