/*    */ package buildcraft.factory.gui;
/*    */ 
/*    */ import buildcraft.BuildCraftFactory;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.network.Packet;
/*    */ import buildcraft.core.lib.network.command.CommandWriter;
/*    */ import buildcraft.core.lib.network.command.PacketCommand;
/*    */ import buildcraft.factory.TileRefinery;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ICrafting;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerRefinery
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   public TileRefinery refinery;
/*    */   
/*    */   public ContainerRefinery(InventoryPlayer inventory, TileRefinery refinery) {
/* 30 */     super(0);
/*    */     
/* 32 */     for (int l = 0; l < 3; l++) {
/* 33 */       for (int k1 = 0; k1 < 9; k1++) {
/* 34 */         func_75146_a(new Slot((IInventory)inventory, k1 + l * 9 + 9, 8 + k1 * 18, 123 + l * 18));
/*    */       }
/*    */     } 
/*    */     
/* 38 */     for (int i1 = 0; i1 < 9; i1++) {
/* 39 */       func_75146_a(new Slot((IInventory)inventory, i1, 8 + i1 * 18, 181));
/*    */     }
/*    */     
/* 42 */     this.refinery = refinery;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 47 */     return (entityplayer.field_70170_p.func_147438_o(this.refinery.field_145851_c, this.refinery.field_145848_d, this.refinery.field_145849_e) == this.refinery);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFilter(final int slot, final Fluid filter) {
/* 53 */     this.refinery.setFilter(slot, filter);
/*    */     
/* 55 */     if ((this.refinery.func_145831_w()).field_72995_K) {
/* 56 */       CommandWriter payload = new CommandWriter()
/*    */         {
/*    */           public void write(ByteBuf data) {
/* 59 */             data.writeByte(slot);
/* 60 */             data.writeShort((filter != null) ? filter.getID() : -1);
/*    */           }
/*    */         };
/* 63 */       BuildCraftFactory.instance.sendToServer((Packet)new PacketCommand(this.refinery, "setFilter", payload));
/*    */     } 
/*    */   }
/*    */   
/*    */   public Fluid getFilter(int slot) {
/* 68 */     return this.refinery.getFilter(slot);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_75137_b(int i, int j) {
/* 74 */     this.refinery.getGUINetworkData(i, j);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 79 */     super.func_75142_b();
/* 80 */     for (Object crafter : this.field_75149_d)
/* 81 */       this.refinery.sendGUINetworkData((Container)this, (ICrafting)crafter); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\gui\ContainerRefinery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */