/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.core.builders.schematics.SchematicRotateMeta;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.init.Blocks;
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
/*    */ public class SchematicEnderChest
/*    */   extends SchematicRotateMeta
/*    */ {
/*    */   public SchematicEnderChest() {
/* 23 */     super(new int[] { 2, 5, 3, 4 }, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 28 */     requirements.add(new ItemStack(Blocks.field_150343_Z, 8));
/* 29 */     requirements.add(new ItemStack(Items.field_151061_bv, 1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 39 */     return (this.block == context.world().func_147439_a(x, y, z));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicEnderChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */