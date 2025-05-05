/*    */ package buildcraft.api.tablet;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ public abstract class TabletProgram
/*    */ {
/*    */   public void tick(float time) {}
/*    */   
/*    */   public boolean hasEnded() {
/* 11 */     return false;
/*    */   }
/*    */   
/*    */   public void receiveMessage(NBTTagCompound compound) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tablet\TabletProgram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */