/*     */ package buildcraft.robotics.boards;
/*     */ 
/*     */ import buildcraft.api.boards.RedstoneBoardNBT;
/*     */ import buildcraft.api.boards.RedstoneBoardRobot;
/*     */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import buildcraft.api.core.IWorldProperty;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.ResourceId;
/*     */ import buildcraft.api.robots.ResourceIdBlock;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.core.lib.utils.IBlockFilter;
/*     */ import buildcraft.robotics.ai.AIRobotFetchAndEquipItemStack;
/*     */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*     */ import buildcraft.robotics.ai.AIRobotSearchAndGotoBlock;
/*     */ import buildcraft.robotics.ai.AIRobotUseToolOnBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoardRobotFarmer
/*     */   extends RedstoneBoardRobot
/*     */ {
/*     */   private BlockIndex blockFound;
/*     */   
/*     */   public BoardRobotFarmer(EntityRobotBase iRobot) {
/*  36 */     super(iRobot);
/*     */   }
/*     */ 
/*     */   
/*     */   public RedstoneBoardRobotNBT getNBTHandler() {
/*  41 */     return BCBoardNBT.REGISTRY.get("farmer");
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  46 */     final IWorldProperty isDirt = BuildCraftAPI.getWorldProperty("dirt");
/*  47 */     if (this.robot.func_70694_bm() == null) {
/*  48 */       startDelegateAI((AIRobot)new AIRobotFetchAndEquipItemStack(this.robot, new IStackFilter()
/*     */             {
/*     */               public boolean matches(ItemStack stack) {
/*  51 */                 return (stack != null && stack.func_77973_b() instanceof net.minecraft.item.ItemHoe);
/*     */               }
/*     */             }));
/*     */     } else {
/*  55 */       startDelegateAI((AIRobot)new AIRobotSearchAndGotoBlock(this.robot, false, new IBlockFilter()
/*     */             {
/*     */               public boolean matches(World world, int x, int y, int z) {
/*  58 */                 return (isDirt.get(world, x, y, z) && 
/*  59 */                   !BoardRobotFarmer.this.robot.getRegistry().isTaken((ResourceId)new ResourceIdBlock(x, y, z)) && BoardRobotFarmer.this
/*  60 */                   .isAirAbove(world, x, y, z));
/*     */               }
/*     */             }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  68 */     if (ai instanceof AIRobotSearchAndGotoBlock) {
/*  69 */       if (ai.success()) {
/*  70 */         this.blockFound = ((AIRobotSearchAndGotoBlock)ai).getBlockFound();
/*  71 */         startDelegateAI((AIRobot)new AIRobotUseToolOnBlock(this.robot, this.blockFound));
/*     */       } else {
/*  73 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       } 
/*  75 */     } else if (ai instanceof AIRobotFetchAndEquipItemStack) {
/*  76 */       if (!ai.success()) {
/*  77 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       }
/*  79 */     } else if (ai instanceof AIRobotUseToolOnBlock) {
/*  80 */       releaseBlockFound();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void releaseBlockFound() {
/*  85 */     if (this.blockFound != null) {
/*  86 */       this.robot.getRegistry().release((ResourceId)new ResourceIdBlock(this.blockFound));
/*  87 */       this.blockFound = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  93 */     releaseBlockFound();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/*  98 */     super.writeSelfToNBT(nbt);
/*     */     
/* 100 */     if (this.blockFound != null) {
/* 101 */       NBTTagCompound sub = new NBTTagCompound();
/* 102 */       this.blockFound.writeTo(sub);
/* 103 */       nbt.func_74782_a("blockFound", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 109 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 111 */     if (nbt.func_74764_b("blockFound")) {
/* 112 */       this.blockFound = new BlockIndex(nbt.func_74775_l("blockFound"));
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isAirAbove(World world, int x, int y, int z) {
/* 117 */     synchronized (world) {
/* 118 */       return world.func_147437_c(x, y + 1, z);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotFarmer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */