/*     */ package buildcraft.robotics.boards;
/*     */ 
/*     */ import buildcraft.api.boards.RedstoneBoardNBT;
/*     */ import buildcraft.api.boards.RedstoneBoardRobot;
/*     */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import buildcraft.api.crops.CropManager;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.ResourceId;
/*     */ import buildcraft.api.robots.ResourceIdBlock;
/*     */ import buildcraft.core.lib.inventory.filters.AggregateFilter;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.core.lib.utils.IBlockFilter;
/*     */ import buildcraft.robotics.ai.AIRobotFetchAndEquipItemStack;
/*     */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*     */ import buildcraft.robotics.ai.AIRobotPlant;
/*     */ import buildcraft.robotics.ai.AIRobotSearchAndGotoBlock;
/*     */ import buildcraft.robotics.statements.ActionRobotFilter;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoardRobotPlanter
/*     */   extends RedstoneBoardRobot
/*     */ {
/*     */   private BlockIndex blockFound;
/*     */   
/*  35 */   private IStackFilter filter = new IStackFilter()
/*     */     {
/*     */       public boolean matches(ItemStack stack)
/*     */       {
/*  39 */         return CropManager.isSeed(stack);
/*     */       }
/*     */     };
/*     */   
/*     */   public BoardRobotPlanter(EntityRobotBase iRobot) {
/*  44 */     super(iRobot);
/*     */   }
/*     */ 
/*     */   
/*     */   public RedstoneBoardRobotNBT getNBTHandler() {
/*  49 */     return BCBoardNBT.REGISTRY.get("planter");
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  54 */     if (this.robot.func_70694_bm() == null) {
/*  55 */       startDelegateAI((AIRobot)new AIRobotFetchAndEquipItemStack(this.robot, (IStackFilter)new AggregateFilter(new IStackFilter[] { this.filter, 
/*  56 */                 ActionRobotFilter.getGateFilter(this.robot.getLinkedStation()) })));
/*     */     } else {
/*  58 */       final ItemStack itemStack = this.robot.func_70694_bm();
/*  59 */       IBlockFilter blockFilter = new IBlockFilter()
/*     */         {
/*     */           public boolean matches(World world, int x, int y, int z) {
/*  62 */             return (!BuildCraftAPI.getWorldProperty("replaceable").get(world, x, y, z) && BoardRobotPlanter.this
/*  63 */               .isPlantable(itemStack, world, x, y, z) && 
/*  64 */               !BoardRobotPlanter.this.robot.getRegistry().isTaken((ResourceId)new ResourceIdBlock(x, y, z)));
/*     */           }
/*     */         };
/*  67 */       startDelegateAI((AIRobot)new AIRobotSearchAndGotoBlock(this.robot, true, blockFilter, 1.0D));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  73 */     if (ai instanceof AIRobotSearchAndGotoBlock) {
/*  74 */       if (ai.success()) {
/*  75 */         this.blockFound = ((AIRobotSearchAndGotoBlock)ai).getBlockFound();
/*  76 */         startDelegateAI((AIRobot)new AIRobotPlant(this.robot, this.blockFound));
/*     */       } else {
/*  78 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       } 
/*  80 */     } else if (ai instanceof AIRobotPlant) {
/*  81 */       releaseBlockFound();
/*  82 */     } else if (ai instanceof AIRobotFetchAndEquipItemStack && 
/*  83 */       !ai.success()) {
/*  84 */       startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void releaseBlockFound() {
/*  90 */     if (this.blockFound != null) {
/*  91 */       this.robot.getRegistry().release((ResourceId)new ResourceIdBlock(this.blockFound));
/*  92 */       this.blockFound = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isPlantable(ItemStack seed, World world, int x, int y, int z) {
/*  97 */     synchronized (world) {
/*  98 */       return CropManager.canSustainPlant(world, seed, x, y, z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 104 */     super.writeSelfToNBT(nbt);
/*     */     
/* 106 */     if (this.blockFound != null) {
/* 107 */       NBTTagCompound sub = new NBTTagCompound();
/* 108 */       this.blockFound.writeTo(sub);
/* 109 */       nbt.func_74782_a("blockFound", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 115 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 117 */     if (nbt.func_74764_b("blockFound"))
/* 118 */       this.blockFound = new BlockIndex(nbt.func_74775_l("blockFound")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotPlanter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */