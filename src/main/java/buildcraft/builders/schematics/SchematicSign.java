/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ public class SchematicSign
/*    */   extends SchematicTile
/*    */ {
/*    */   boolean isWall;
/*    */   
/*    */   public SchematicSign(boolean isWall) {
/* 26 */     this.isWall = isWall;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 31 */     requirements.add(new ItemStack(Items.field_151155_ap));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void rotateLeft(IBuilderContext context) {
/* 41 */     if (!this.isWall) {
/* 42 */       double angle = this.meta * 360.0D / 16.0D;
/* 43 */       angle += 90.0D;
/* 44 */       if (angle >= 360.0D) {
/* 45 */         angle -= 360.0D;
/*    */       }
/* 47 */       this.meta = (int)(angle / 360.0D * 16.0D);
/*    */     } else {
/* 49 */       this.meta = ForgeDirection.values()[this.meta].getRotation(ForgeDirection.UP).ordinal();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */