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
/*     */ import buildcraft.core.lib.inventory.filters.IFluidFilter;
/*     */ import buildcraft.core.lib.utils.IBlockFilter;
/*     */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationAndUnloadFluids;
/*     */ import buildcraft.robotics.ai.AIRobotPumpBlock;
/*     */ import buildcraft.robotics.ai.AIRobotSearchAndGotoBlock;
/*     */ import buildcraft.robotics.statements.ActionRobotFilter;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoardRobotPump
/*     */   extends RedstoneBoardRobot
/*     */ {
/*     */   private BlockIndex blockFound;
/*  40 */   private IFluidFilter fluidFilter = null;
/*     */   
/*     */   public BoardRobotPump(EntityRobotBase iRobot) {
/*  43 */     super(iRobot);
/*     */   }
/*     */ 
/*     */   
/*     */   public RedstoneBoardRobotNBT getNBTHandler() {
/*  48 */     return BCBoardNBT.REGISTRY.get("pump");
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  53 */     final IWorldProperty isFluidSource = BuildCraftAPI.getWorldProperty("fluidSource");
/*  54 */     FluidStack tank = (this.robot.getTankInfo(ForgeDirection.UNKNOWN)[0]).fluid;
/*     */     
/*  56 */     if (tank != null && tank.amount > 0) {
/*  57 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndUnloadFluids(this.robot));
/*     */     } else {
/*  59 */       updateFilter();
/*     */       
/*  61 */       startDelegateAI((AIRobot)new AIRobotSearchAndGotoBlock(this.robot, false, new IBlockFilter()
/*     */             {
/*     */               public boolean matches(World world, int x, int y, int z)
/*     */               {
/*  65 */                 if (isFluidSource.get(world, x, y, z) && 
/*  66 */                   !BoardRobotPump.this.robot.getRegistry().isTaken((ResourceId)new ResourceIdBlock(x, y, z))) {
/*  67 */                   return BoardRobotPump.this.matchesGateFilter(world, x, y, z);
/*     */                 }
/*  69 */                 return false;
/*     */               }
/*     */             }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  78 */     if (ai instanceof AIRobotSearchAndGotoBlock) {
/*  79 */       if (ai.success()) {
/*  80 */         this.blockFound = ((AIRobotSearchAndGotoBlock)ai).getBlockFound();
/*  81 */         startDelegateAI((AIRobot)new AIRobotPumpBlock(this.robot, this.blockFound));
/*     */       } else {
/*  83 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       } 
/*  85 */     } else if (ai instanceof AIRobotPumpBlock) {
/*  86 */       releaseBlockFound();
/*  87 */     } else if (ai instanceof AIRobotGotoStationAndUnloadFluids) {
/*     */       
/*  89 */       if (!ai.success()) {
/*  90 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void releaseBlockFound() {
/*  96 */     if (this.blockFound != null) {
/*  97 */       this.robot.getRegistry().release((ResourceId)new ResourceIdBlock(this.blockFound));
/*  98 */       this.blockFound = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateFilter() {
/* 103 */     this.fluidFilter = ActionRobotFilter.getGateFluidFilter(this.robot.getLinkedStation());
/* 104 */     if (this.fluidFilter instanceof buildcraft.core.lib.inventory.filters.PassThroughFluidFilter)
/* 105 */       this.fluidFilter = null; 
/*     */   }
/*     */   
/*     */   private boolean matchesGateFilter(World world, int x, int y, int z) {
/*     */     Block block;
/* 110 */     if (this.fluidFilter == null) {
/* 111 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 115 */     synchronized (world) {
/* 116 */       block = world.func_147439_a(x, y, z);
/*     */     } 
/*     */     
/* 119 */     Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
/*     */     
/* 121 */     return this.fluidFilter.matches(fluid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadFromNBT() {
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 131 */     super.writeSelfToNBT(nbt);
/* 132 */     if (this.blockFound != null) {
/* 133 */       NBTTagCompound sub = new NBTTagCompound();
/* 134 */       this.blockFound.writeTo(sub);
/* 135 */       nbt.func_74782_a("blockFound", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 141 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 143 */     if (nbt.func_74764_b("blockFound"))
/* 144 */       this.blockFound = new BlockIndex(nbt.func_74775_l("blockFound")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotPump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */