/*    */ package buildcraft.robotics.gui;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.network.Packet;
/*    */ import buildcraft.core.lib.network.command.CommandWriter;
/*    */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*    */ import buildcraft.core.lib.network.command.PacketCommand;
/*    */ import buildcraft.core.lib.utils.NetworkUtils;
/*    */ import buildcraft.robotics.TileRequester;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
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
/*    */ public class ContainerRequester
/*    */   extends BuildCraftContainer
/*    */   implements ICommandReceiver
/*    */ {
/*    */   public GuiRequester gui;
/* 32 */   public ItemStack[] requests = new ItemStack[20];
/*    */   
/*    */   private TileRequester requester;
/*    */   
/*    */   public ContainerRequester(IInventory playerInventory, TileRequester iRequester) {
/* 37 */     super(iRequester.func_70302_i_());
/*    */     
/* 39 */     this.requester = iRequester;
/*    */     
/* 41 */     for (int x = 0; x < 4; x++) {
/* 42 */       for (int y = 0; y < 5; y++) {
/* 43 */         func_75146_a(new Slot((IInventory)iRequester, x * 5 + y, 117 + x * 18, 7 + y * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 48 */     for (int l = 0; l < 3; l++) {
/* 49 */       for (int k1 = 0; k1 < 9; k1++) {
/* 50 */         func_75146_a(new Slot(playerInventory, k1 + l * 9 + 9, 19 + k1 * 18, 101 + l * 18));
/*    */       }
/*    */     } 
/*    */     
/* 54 */     for (int i1 = 0; i1 < 9; i1++) {
/* 55 */       func_75146_a(new Slot(playerInventory, i1, 19 + i1 * 18, 159));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer player) {
/* 61 */     return true;
/*    */   }
/*    */   
/*    */   public void getRequestList() {
/* 65 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "getRequestList", null));
/*    */   }
/*    */ 
/*    */   
/*    */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 70 */     if (side.isServer() && "getRequestList".equals(command)) {
/* 71 */       final ItemStack[] stacks = new ItemStack[20];
/*    */       
/* 73 */       for (int i = 0; i < 20; i++) {
/* 74 */         stacks[i] = this.requester.getRequestTemplate(i);
/*    */       }
/*    */       
/* 77 */       BuildCraftCore.instance.sendToPlayer((EntityPlayer)sender, (Packet)new PacketCommand(this, "receiveRequestList", new CommandWriter()
/*    */             {
/*    */               public void write(ByteBuf data) {
/* 80 */                 for (ItemStack s : stacks) {
/* 81 */                   NetworkUtils.writeStack(data, s);
/*    */                 }
/*    */               }
/*    */             }));
/* 85 */     } else if (side.isClient() && "receiveRequestList".equals(command)) {
/* 86 */       this.requests = new ItemStack[20];
/* 87 */       for (int i = 0; i < 20; i++)
/* 88 */         this.requests[i] = NetworkUtils.readStack(stream); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\gui\ContainerRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */