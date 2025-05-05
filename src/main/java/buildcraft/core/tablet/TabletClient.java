/*    */ package buildcraft.core.tablet;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.api.tablet.TabletBitmap;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TabletClient
/*    */   extends TabletBase
/*    */ {
/* 15 */   protected final TabletRenderer renderer = new TabletRenderer(new TabletBitmap(getScreenWidth(), getScreenHeight()));
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick(float time) {
/* 20 */     super.tick(time);
/*    */   }
/*    */   
/*    */   public void updateGui(float time, GuiTablet gui, boolean force) {
/* 24 */     this.renderer.tick(time);
/*    */     
/* 26 */     if (this.renderer.shouldChange() || force) {
/* 27 */       gui.copyDisplay(this.renderer.get());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Side getSide() {
/* 33 */     return Side.CLIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void refreshScreen(TabletBitmap newDisplay) {
/* 38 */     this.renderer.update(newDisplay);
/*    */   }
/*    */ 
/*    */   
/*    */   public void receiveMessage(NBTTagCompound compound) {
/* 43 */     if (!receiveMessageInternal(compound));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void launchProgram(String name) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendMessage(NBTTagCompound compound) {
/* 56 */     compound.func_74757_a("__program", true);
/* 57 */     BuildCraftCore.instance.sendToServer(new PacketTabletMessage(compound));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\TabletClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */