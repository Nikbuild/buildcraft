/*    */ package buildcraft.core.tablet;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.api.tablet.TabletBitmap;
/*    */ import buildcraft.api.tablet.TabletProgram;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class TabletServer
/*    */   extends TabletBase
/*    */ {
/*    */   protected final EntityPlayer player;
/*    */   
/*    */   public TabletServer(EntityPlayer player) {
/* 16 */     this.player = player;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float time) {
/* 21 */     synchronized (this.programs) {
/* 22 */       while (this.programs.size() > 0 && ((TabletProgram)this.programs.getLast()).hasEnded()) {
/* 23 */         closeProgram();
/*    */       }
/*    */       
/* 26 */       if (this.programs.size() == 0) {
/* 27 */         launchProgram("menu");
/*    */       }
/*    */       
/* 30 */       super.tick(time);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Side getSide() {
/* 36 */     return Side.SERVER;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void refreshScreen(TabletBitmap newDisplay) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void receiveMessage(NBTTagCompound compound) {
/* 46 */     if (!receiveMessageInternal(compound) && 
/* 47 */       compound.func_74764_b("doRemoveProgram")) {
/* 48 */       synchronized (this.programs) {
/* 49 */         this.programs.removeLast();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void launchProgram(String name) {
/* 57 */     if (launchProgramInternal(name)) {
/* 58 */       NBTTagCompound compound = new NBTTagCompound();
/* 59 */       compound.func_74778_a("programToLaunch", name);
/* 60 */       BuildCraftCore.instance.sendToPlayer(this.player, new PacketTabletMessage(compound));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void closeProgram() {
/* 65 */     this.programs.removeLast();
/* 66 */     NBTTagCompound compound = new NBTTagCompound();
/* 67 */     compound.func_74757_a("doRemoveProgram", true);
/* 68 */     BuildCraftCore.instance.sendToPlayer(this.player, new PacketTabletMessage(compound));
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(NBTTagCompound compound) {
/* 73 */     compound.func_74757_a("__program", true);
/* 74 */     BuildCraftCore.instance.sendToPlayer(this.player, new PacketTabletMessage(compound));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\TabletServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */