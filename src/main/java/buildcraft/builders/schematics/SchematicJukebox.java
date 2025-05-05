/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ import buildcraft.api.core.JavaTools;
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
/*    */ public class SchematicJukebox
/*    */   extends SchematicTile
/*    */ {
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {
/* 23 */     super.storeRequirements(context, x, y, z);
/* 24 */     if (this.tileNBT != null && this.tileNBT.func_74764_b("RecordItem")) {
/* 25 */       ItemStack recordStack = ItemStack.func_77949_a(this.tileNBT.func_74775_l("RecordItem"));
/* 26 */       this.storedRequirements = (ItemStack[])JavaTools.concat((Object[])this.storedRequirements, (Object[])new ItemStack[] { recordStack });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicJukebox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */