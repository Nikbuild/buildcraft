/*    */ package buildcraft.transport.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ import net.minecraft.item.Item;
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
/*    */ public class BptPipeRotatable
/*    */   extends BptPipeExtension
/*    */ {
/*    */   public BptPipeRotatable(Item i) {
/* 21 */     super(i);
/*    */   }
/*    */ 
/*    */   
/*    */   public void rotateLeft(SchematicTile slot, IBuilderContext context) {
/* 26 */     int orientation = slot.meta & 0x7;
/* 27 */     int others = slot.meta - orientation;
/*    */     
/* 29 */     slot.meta = ForgeDirection.values()[orientation].getRotation(ForgeDirection.UP).ordinal() + others;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\schematics\BptPipeRotatable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */