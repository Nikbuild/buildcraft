/*     */ package buildcraft.robotics.boards;
/*     */ 
/*     */ import buildcraft.api.boards.RedstoneBoardRobot;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.ResourceId;
/*     */ import buildcraft.api.robots.ResourceIdBlock;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementParameterItemStack;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.core.lib.utils.IBlockFilter;
/*     */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*     */ import buildcraft.robotics.ai.AIRobotSearchAndGotoBlock;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BoardRobotGenericSearchBlock
/*     */   extends RedstoneBoardRobot
/*     */ {
/*     */   private BlockIndex blockFound;
/*  27 */   private ArrayList<Block> blockFilter = new ArrayList<Block>();
/*  28 */   private ArrayList<Integer> metaFilter = new ArrayList<Integer>();
/*     */   
/*     */   public BoardRobotGenericSearchBlock(EntityRobotBase iRobot) {
/*  31 */     super(iRobot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isExpectedBlock(World paramWorld, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  43 */     updateFilter();
/*     */     
/*  45 */     startDelegateAI((AIRobot)new AIRobotSearchAndGotoBlock(this.robot, false, new IBlockFilter()
/*     */           {
/*     */             public boolean matches(World world, int x, int y, int z) {
/*  48 */               if (BoardRobotGenericSearchBlock.this.isExpectedBlock(world, x, y, z) && 
/*  49 */                 !BoardRobotGenericSearchBlock.this.robot.getRegistry().isTaken((ResourceId)new ResourceIdBlock(x, y, z))) {
/*  50 */                 return BoardRobotGenericSearchBlock.this.matchesGateFilter(world, x, y, z);
/*     */               }
/*  52 */               return false;
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  60 */     if (ai instanceof AIRobotSearchAndGotoBlock) {
/*  61 */       if (ai.success()) {
/*  62 */         this.blockFound = ((AIRobotSearchAndGotoBlock)ai).getBlockFound();
/*     */       } else {
/*  64 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  71 */     releaseBlockFound(true);
/*     */   }
/*     */   
/*     */   protected BlockIndex blockFound() {
/*  75 */     return this.blockFound;
/*     */   }
/*     */   
/*     */   protected void releaseBlockFound(boolean success) {
/*  79 */     if (this.blockFound != null) {
/*     */       
/*  81 */       this.robot.getRegistry().release((ResourceId)new ResourceIdBlock(this.blockFound));
/*  82 */       this.blockFound = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void updateFilter() {
/*  87 */     this.blockFilter.clear();
/*  88 */     this.metaFilter.clear();
/*     */     
/*  90 */     for (StatementSlot slot : this.robot.getLinkedStation().getActiveActions()) {
/*  91 */       if (slot.statement instanceof buildcraft.robotics.statements.ActionRobotFilter)
/*  92 */         for (IStatementParameter p : slot.parameters) {
/*  93 */           if (p != null && p instanceof StatementParameterItemStack) {
/*  94 */             StatementParameterItemStack param = (StatementParameterItemStack)p;
/*  95 */             ItemStack stack = param.getItemStack();
/*     */             
/*  97 */             if (stack != null && stack.func_77973_b() instanceof ItemBlock) {
/*  98 */               this.blockFilter.add(((ItemBlock)stack.func_77973_b()).field_150939_a);
/*  99 */               this.metaFilter.add(Integer.valueOf(stack.func_77960_j()));
/*     */             } 
/*     */           } 
/*     */         }  
/*     */     } 
/*     */   }
/*     */   protected boolean matchesGateFilter(World world, int x, int y, int z) {
/*     */     Block block;
/*     */     int meta;
/* 108 */     if (this.blockFilter.size() == 0) {
/* 109 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 114 */     synchronized (world) {
/* 115 */       block = world.func_147439_a(x, y, z);
/* 116 */       meta = world.func_72805_g(x, y, z);
/*     */     } 
/*     */     
/* 119 */     for (int i = 0; i < this.blockFilter.size(); i++) {
/* 120 */       if (this.blockFilter.get(i) == block && ((Integer)this.metaFilter.get(i)).intValue() == meta) {
/* 121 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 130 */     super.writeSelfToNBT(nbt);
/*     */     
/* 132 */     if (this.blockFound != null) {
/* 133 */       NBTTagCompound sub = new NBTTagCompound();
/* 134 */       this.blockFound.writeTo(sub);
/* 135 */       nbt.func_74782_a("indexStored", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 141 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 143 */     if (nbt.func_74764_b("indexStored"))
/* 144 */       this.blockFound = new BlockIndex(nbt.func_74775_l("indexStored")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotGenericSearchBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */