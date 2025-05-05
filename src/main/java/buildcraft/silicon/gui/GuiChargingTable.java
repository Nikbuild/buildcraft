/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.silicon.TileChargingTable;
/*    */ import buildcraft.silicon.TileLaserTableBase;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiChargingTable
/*    */   extends GuiLaserTable
/*    */ {
/* 18 */   public static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftsilicon:textures/gui/charging_table.png");
/*    */   
/*    */   public GuiChargingTable(InventoryPlayer playerInventory, TileChargingTable chargingTable) {
/* 21 */     super(playerInventory, new ContainerChargingTable(playerInventory, chargingTable), (TileLaserTableBase)chargingTable, TEXTURE);
/* 22 */     this.field_146999_f = 176;
/* 23 */     this.field_147000_g = 132;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\GuiChargingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */