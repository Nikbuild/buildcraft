/*    */ package buildcraft.api.robots;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ResourceId
/*    */ {
/*    */   public void writeToNBT(NBTTagCompound nbt) {
/* 14 */     nbt.func_74778_a("resourceName", RobotManager.getResourceIdName((Class)getClass()));
/*    */   }
/*    */   
/*    */   protected void readFromNBT(NBTTagCompound nbt) {}
/*    */   
/*    */   public static ResourceId load(NBTTagCompound nbt) {
/*    */     try {
/*    */       Class<?> cls;
/* 22 */       if (nbt.func_74764_b("class")) {
/*    */         
/* 24 */         cls = RobotManager.getResourceIdByLegacyClassName(nbt.func_74779_i("class"));
/*    */       } else {
/* 26 */         cls = RobotManager.getResourceIdByName(nbt.func_74779_i("resourceName"));
/*    */       } 
/*    */       
/* 29 */       ResourceId id = (ResourceId)cls.newInstance();
/* 30 */       id.readFromNBT(nbt);
/*    */       
/* 32 */       return id;
/* 33 */     } catch (Throwable e) {
/* 34 */       e.printStackTrace();
/*    */ 
/*    */       
/* 37 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\ResourceId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */