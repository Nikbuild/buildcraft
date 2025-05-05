/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import buildcraft.api.core.BuildCraftAPI;
/*    */ import buildcraft.api.crops.CropManager;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.utils.BlockUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ public class AIRobotHarvest
/*    */   extends AIRobot
/*    */ {
/*    */   private BlockIndex blockFound;
/* 21 */   private int delay = 0;
/*    */   
/*    */   public AIRobotHarvest(EntityRobotBase iRobot) {
/* 24 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotHarvest(EntityRobotBase iRobot, BlockIndex iBlockFound) {
/* 28 */     super(iRobot);
/* 29 */     this.blockFound = iBlockFound;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 34 */     if (this.blockFound == null) {
/* 35 */       setSuccess(false);
/* 36 */       terminate();
/*    */       
/*    */       return;
/*    */     } 
/* 40 */     if (this.delay++ > 20) {
/* 41 */       if (!BuildCraftAPI.getWorldProperty("harvestable").get(this.robot.field_70170_p, this.blockFound.x, this.blockFound.y, this.blockFound.z)) {
/*    */         
/* 43 */         setSuccess(false);
/* 44 */         terminate();
/*    */         return;
/*    */       } 
/* 47 */       List<ItemStack> drops = new ArrayList<ItemStack>();
/* 48 */       if (!CropManager.harvestCrop(this.robot.field_70170_p, this.blockFound.x, this.blockFound.y, this.blockFound.z, drops)) {
/*    */         
/* 50 */         setSuccess(false);
/* 51 */         terminate();
/*    */         return;
/*    */       } 
/* 54 */       for (ItemStack stack : drops) {
/* 55 */         BlockUtils.dropItem((WorldServer)this.robot.field_70170_p, 
/* 56 */             MathHelper.func_76128_c(this.robot.field_70165_t), MathHelper.func_76128_c(this.robot.field_70163_u), 
/* 57 */             MathHelper.func_76128_c(this.robot.field_70161_v), 6000, stack);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canLoadFromNBT() {
/* 64 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 69 */     super.writeSelfToNBT(nbt);
/*    */     
/* 71 */     if (this.blockFound != null) {
/* 72 */       NBTTagCompound sub = new NBTTagCompound();
/* 73 */       this.blockFound.writeTo(sub);
/* 74 */       nbt.func_74782_a("blockFound", (NBTBase)sub);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 80 */     super.loadSelfFromNBT(nbt);
/*    */     
/* 82 */     if (nbt.func_74764_b("blockFound"))
/* 83 */       this.blockFound = new BlockIndex(nbt.func_74775_l("blockFound")); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotHarvest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */