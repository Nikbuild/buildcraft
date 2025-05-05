/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import buildcraft.api.crops.CropManager;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.utils.BlockUtils;
/*    */ import buildcraft.core.proxy.CoreProxy;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ public class AIRobotPlant extends AIRobot {
/*    */   private BlockIndex blockFound;
/* 17 */   private int delay = 0;
/*    */   
/*    */   public AIRobotPlant(EntityRobotBase iRobot) {
/* 20 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotPlant(EntityRobotBase iRobot, BlockIndex iBlockFound) {
/* 24 */     this(iRobot);
/*    */     
/* 26 */     this.blockFound = iBlockFound;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 31 */     this.robot.aimItemAt(this.blockFound.x, this.blockFound.y, this.blockFound.z);
/* 32 */     this.robot.setItemActive(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 37 */     if (this.blockFound == null) {
/* 38 */       setSuccess(false);
/* 39 */       terminate();
/*    */     } 
/*    */     
/* 42 */     if (this.delay++ > 40) {
/*    */       
/* 44 */       EntityPlayer player = CoreProxy.proxy.getBuildCraftPlayer((WorldServer)this.robot.field_70170_p).get();
/* 45 */       if (!CropManager.plantCrop(this.robot.field_70170_p, player, this.robot.func_70694_bm(), this.blockFound.x, this.blockFound.y, this.blockFound.z))
/*    */       {
/*    */         
/* 48 */         setSuccess(false);
/*    */       }
/* 50 */       if ((this.robot.func_70694_bm()).field_77994_a > 0) {
/* 51 */         BlockUtils.dropItem((WorldServer)this.robot.field_70170_p, 
/* 52 */             MathHelper.func_76128_c(this.robot.field_70165_t), MathHelper.func_76128_c(this.robot.field_70163_u), 
/* 53 */             MathHelper.func_76128_c(this.robot.field_70161_v), 6000, this.robot.func_70694_bm());
/*    */       }
/* 55 */       this.robot.setItemInUse(null);
/* 56 */       terminate();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void end() {
/* 62 */     this.robot.setItemActive(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canLoadFromNBT() {
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 72 */     super.writeSelfToNBT(nbt);
/*    */     
/* 74 */     if (this.blockFound != null) {
/* 75 */       NBTTagCompound sub = new NBTTagCompound();
/* 76 */       this.blockFound.writeTo(sub);
/* 77 */       nbt.func_74782_a("blockFound", (NBTBase)sub);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 83 */     super.loadSelfFromNBT(nbt);
/*    */     
/* 85 */     if (nbt.func_74764_b("blockFound"))
/* 86 */       this.blockFound = new BlockIndex(nbt.func_74775_l("blockFound")); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */