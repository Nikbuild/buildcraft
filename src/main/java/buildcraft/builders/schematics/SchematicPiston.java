/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.core.builders.schematics.SchematicRotateMeta;
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
/*    */ public class SchematicPiston
/*    */   extends SchematicRotateMeta
/*    */ {
/*    */   public SchematicPiston() {
/* 21 */     super(new int[] { 2, 5, 3, 4 }, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 26 */     int localMeta = this.meta & 0x7;
/*    */     
/* 28 */     context.world().func_147465_d(x, y, z, this.block, localMeta, 3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicPiston.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */