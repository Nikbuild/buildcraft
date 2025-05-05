/*     */ package buildcraft.robotics.boards;
/*     */ 
/*     */ import buildcraft.api.boards.RedstoneBoardNBT;
/*     */ import buildcraft.api.boards.RedstoneBoardRobot;
/*     */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.ResourceId;
/*     */ import buildcraft.api.robots.ResourceIdBlock;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.core.lib.utils.IBlockFilter;
/*     */ import buildcraft.robotics.ai.AIRobotFetchAndEquipItemStack;
/*     */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*     */ import buildcraft.robotics.ai.AIRobotSearchAndGotoBlock;
/*     */ import buildcraft.robotics.ai.AIRobotStripesHandler;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoardRobotStripes
/*     */   extends RedstoneBoardRobot
/*     */ {
/*     */   private BlockIndex blockFound;
/*     */   
/*     */   public BoardRobotStripes(EntityRobotBase iRobot) {
/*  33 */     super(iRobot);
/*     */   }
/*     */ 
/*     */   
/*     */   public RedstoneBoardRobotNBT getNBTHandler() {
/*  38 */     return BCBoardNBT.REGISTRY.get("stripes");
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  43 */     if (this.robot.func_70694_bm() == null) {
/*  44 */       startDelegateAI((AIRobot)new AIRobotFetchAndEquipItemStack(this.robot, new IStackFilter()
/*     */             {
/*     */               public boolean matches(ItemStack stack) {
/*  47 */                 return (stack != null);
/*     */               }
/*     */             }));
/*     */     } else {
/*  51 */       startDelegateAI((AIRobot)new AIRobotSearchAndGotoBlock(this.robot, true, new IBlockFilter()
/*     */             {
/*     */               public boolean matches(World world, int x, int y, int z) {
/*  54 */                 return (world.func_147439_a(x, y, z).isAir((IBlockAccess)world, x, y, z) && 
/*  55 */                   !BoardRobotStripes.this.robot.getRegistry().isTaken((ResourceId)new ResourceIdBlock(x, y, z)));
/*     */               }
/*     */             }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  63 */     if (ai instanceof AIRobotSearchAndGotoBlock) {
/*  64 */       if (ai.success()) {
/*  65 */         this.blockFound = ((AIRobotSearchAndGotoBlock)ai).getBlockFound();
/*  66 */         startDelegateAI((AIRobot)new AIRobotStripesHandler(this.robot, this.blockFound));
/*     */       } else {
/*  68 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       } 
/*  70 */     } else if (ai instanceof AIRobotFetchAndEquipItemStack) {
/*  71 */       if (!ai.success()) {
/*  72 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       }
/*  74 */     } else if (ai instanceof AIRobotStripesHandler) {
/*  75 */       releaseBlockFound();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void releaseBlockFound() {
/*  80 */     if (this.blockFound != null) {
/*  81 */       this.robot.getRegistry().release((ResourceId)new ResourceIdBlock(this.blockFound));
/*  82 */       this.blockFound = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  88 */     if (this.blockFound != null) {
/*  89 */       this.robot.getRegistry().release((ResourceId)new ResourceIdBlock(this.blockFound));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/*  95 */     super.writeSelfToNBT(nbt);
/*     */     
/*  97 */     if (this.blockFound != null) {
/*  98 */       NBTTagCompound sub = new NBTTagCompound();
/*  99 */       this.blockFound.writeTo(sub);
/* 100 */       nbt.func_74782_a("blockFound", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 106 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 108 */     if (nbt.func_74764_b("blockFound"))
/* 109 */       this.blockFound = new BlockIndex(nbt.func_74775_l("blockFound")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotStripes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */