/*    */ package buildcraft.core.lib.network;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import cpw.mods.fml.client.FMLClientHandler;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class PacketGuiWidget
/*    */   extends Packet
/*    */ {
/*    */   private byte windowId;
/*    */   private byte widgetId;
/*    */   private byte[] payload;
/*    */   
/*    */   public PacketGuiWidget() {}
/*    */   
/*    */   public PacketGuiWidget(int windowId, int widgetId, byte[] data) {
/* 30 */     this.windowId = (byte)windowId;
/* 31 */     this.widgetId = (byte)widgetId;
/* 32 */     this.payload = data;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 37 */     data.writeByte(this.windowId);
/* 38 */     data.writeByte(this.widgetId);
/* 39 */     data.writeBytes(this.payload);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 44 */     this.windowId = data.readByte();
/* 45 */     this.widgetId = data.readByte();
/*    */     
/* 47 */     EntityClientPlayerMP entityClientPlayerMP = (FMLClientHandler.instance().getClient()).field_71439_g;
/*    */     
/* 49 */     if (((EntityPlayer)entityClientPlayerMP).field_71070_bA instanceof BuildCraftContainer && ((EntityPlayer)entityClientPlayerMP).field_71070_bA.field_75152_c == this.windowId) {
/* 50 */       ((BuildCraftContainer)((EntityPlayer)entityClientPlayerMP).field_71070_bA).handleWidgetClientData(this.widgetId, data);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getID() {
/* 56 */     return 81;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketGuiWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */