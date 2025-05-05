/*    */ package buildcraft.transport.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ import buildcraft.core.lib.inventory.SimpleInventory;
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
/*    */ public class BptPipeFiltered
/*    */   extends BptPipeExtension
/*    */ {
/*    */   public BptPipeFiltered(Item i) {
/* 22 */     super(i);
/*    */   }
/*    */ 
/*    */   
/*    */   public void rotateLeft(SchematicTile slot, IBuilderContext context) {
/* 27 */     SimpleInventory inv = new SimpleInventory(54, "Filters", 1);
/* 28 */     SimpleInventory newInv = new SimpleInventory(54, "Filters", 1);
/* 29 */     inv.readFromNBT(slot.tileNBT);
/*    */     
/* 31 */     for (int dir = 0; dir <= 5; dir++) {
/* 32 */       ForgeDirection r = ForgeDirection.values()[dir].getRotation(ForgeDirection.UP);
/*    */       
/* 34 */       for (int s = 0; s < 9; s++) {
/* 35 */         newInv.func_70299_a(r.ordinal() * 9 + s, inv.func_70301_a(dir * 9 + s));
/*    */       }
/*    */     } 
/*    */     
/* 39 */     newInv.writeToNBT(slot.tileNBT);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\schematics\BptPipeFiltered.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */