/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AIRobotUseToolOnBlock
/*     */   extends AIRobot
/*     */ {
/*     */   private BlockIndex useToBlock;
/*  28 */   private int useCycles = 0;
/*     */   
/*     */   public AIRobotUseToolOnBlock(EntityRobotBase iRobot) {
/*  31 */     super(iRobot);
/*     */   }
/*     */   
/*     */   public AIRobotUseToolOnBlock(EntityRobotBase iRobot, BlockIndex index) {
/*  35 */     this(iRobot);
/*     */     
/*  37 */     this.useToBlock = index;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  42 */     this.robot.aimItemAt(this.useToBlock.x, this.useToBlock.y, this.useToBlock.z);
/*  43 */     this.robot.setItemActive(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  48 */     this.useCycles++;
/*     */     
/*  50 */     if (this.useCycles > 40) {
/*  51 */       ItemStack stack = this.robot.func_70694_bm();
/*     */ 
/*     */       
/*  54 */       EntityPlayer player = CoreProxy.proxy.getBuildCraftPlayer((WorldServer)this.robot.field_70170_p).get();
/*  55 */       if (BlockUtils.useItemOnBlock(this.robot.field_70170_p, player, stack, this.useToBlock.x, this.useToBlock.y, this.useToBlock.z, ForgeDirection.UP)) {
/*     */         
/*  57 */         if (this.robot.func_70694_bm().func_77984_f()) {
/*  58 */           this.robot.func_70694_bm().func_77972_a(1, (EntityLivingBase)this.robot);
/*     */           
/*  60 */           if (this.robot.func_70694_bm().func_77960_j() >= this.robot.func_70694_bm().func_77958_k()) {
/*  61 */             this.robot.setItemInUse(null);
/*     */           }
/*     */         } else {
/*  64 */           this.robot.setItemInUse(null);
/*     */         } 
/*     */       } else {
/*  67 */         setSuccess(false);
/*  68 */         if (!this.robot.func_70694_bm().func_77984_f()) {
/*  69 */           BlockUtils.dropItem((WorldServer)this.robot.field_70170_p, 
/*  70 */               MathHelper.func_76128_c(this.robot.field_70165_t), 
/*  71 */               MathHelper.func_76128_c(this.robot.field_70163_u), 
/*  72 */               MathHelper.func_76128_c(this.robot.field_70161_v), 6000, stack);
/*  73 */           this.robot.setItemInUse(null);
/*     */         } 
/*     */       } 
/*     */       
/*  77 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  83 */     this.robot.setItemActive(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyCost() {
/*  88 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadFromNBT() {
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/*  98 */     super.writeSelfToNBT(nbt);
/*     */     
/* 100 */     if (this.useToBlock != null) {
/* 101 */       NBTTagCompound sub = new NBTTagCompound();
/* 102 */       this.useToBlock.writeTo(sub);
/* 103 */       nbt.func_74782_a("blockFound", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 109 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 111 */     if (nbt.func_74764_b("blockFound"))
/* 112 */       this.useToBlock = new BlockIndex(nbt.func_74775_l("blockFound")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotUseToolOnBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */