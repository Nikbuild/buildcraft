/*    */ package buildcraft.api.boards;
/*    */ 
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public abstract class RedstoneBoardRobotNBT
/*    */   extends RedstoneBoardNBT<EntityRobotBase>
/*    */ {
/*    */   public abstract ResourceLocation getRobotTexture();
/*    */   
/*    */   public abstract RedstoneBoardRobot create(EntityRobotBase paramEntityRobotBase);
/*    */   
/*    */   public RedstoneBoardRobot create(NBTTagCompound nbt, EntityRobotBase robot) {
/* 16 */     return create(robot);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\boards\RedstoneBoardRobotNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */