/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
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
/*    */ public class SchematicSilverfish
/*    */   extends SchematicBlock
/*    */ {
/*    */   private Block getRealBlock() {
/* 22 */     if (this.meta == 0)
/* 23 */       return Blocks.field_150348_b; 
/* 24 */     if (this.meta == 1)
/* 25 */       return Blocks.field_150347_e; 
/* 26 */     if (this.meta <= 5) {
/* 27 */       return Blocks.field_150417_aV;
/*    */     }
/* 29 */     return Blocks.field_150348_b;
/*    */   }
/*    */ 
/*    */   
/*    */   private int getRealMetadata() {
/* 34 */     if (this.meta >= 2 && this.meta <= 5) {
/* 35 */       return this.meta - 2;
/*    */     }
/* 37 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 42 */     requirements.add(new ItemStack(getRealBlock(), 0, getRealMetadata()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 52 */     context.world().func_147465_d(x, y, z, getRealBlock(), getRealMetadata(), 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 57 */     return (context.world().func_147439_a(x, y, z) == getRealBlock() && context.world().func_72805_g(x, y, z) == getRealMetadata());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicSilverfish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */