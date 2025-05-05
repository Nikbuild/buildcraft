/*     */ package buildcraft.api.robots;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.EnumPipePart;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.transport.IInjectable;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DockingStation
/*     */ {
/*     */   public EnumFacing side;
/*     */   public World world;
/*  26 */   private long robotTakingId = Long.MAX_VALUE;
/*     */   
/*     */   private EntityRobotBase robotTaking;
/*     */   
/*     */   private boolean linkIsMain = false;
/*     */   private BlockPos pos;
/*     */   
/*     */   public DockingStation(BlockPos iIndex, EnumFacing iSide) {
/*  34 */     this.pos = iIndex;
/*  35 */     this.side = iSide;
/*     */   }
/*     */   
/*     */   public DockingStation() {}
/*     */   
/*     */   public boolean isMainStation() {
/*  41 */     return this.linkIsMain;
/*     */   }
/*     */   
/*     */   public BlockPos getPos() {
/*  45 */     return this.pos;
/*     */   }
/*     */   
/*     */   public EnumFacing side() {
/*  49 */     return this.side;
/*     */   }
/*     */   
/*     */   public EntityRobotBase robotTaking() {
/*  53 */     if (this.robotTakingId == Long.MAX_VALUE)
/*  54 */       return null; 
/*  55 */     if (this.robotTaking == null) {
/*  56 */       this.robotTaking = RobotManager.registryProvider.getRegistry(this.world).getLoadedRobot(this.robotTakingId);
/*     */     }
/*     */     
/*  59 */     return this.robotTaking;
/*     */   }
/*     */   
/*     */   public void invalidateRobotTakingEntity() {
/*  63 */     this.robotTaking = null;
/*     */   }
/*     */   
/*     */   public long linkedId() {
/*  67 */     return this.robotTakingId;
/*     */   }
/*     */   
/*     */   public boolean takeAsMain(EntityRobotBase robot) {
/*  71 */     if (this.robotTakingId == Long.MAX_VALUE) {
/*  72 */       IRobotRegistry registry = RobotManager.registryProvider.getRegistry(this.world);
/*  73 */       this.linkIsMain = true;
/*  74 */       this.robotTaking = robot;
/*  75 */       this.robotTakingId = robot.getRobotId();
/*  76 */       registry.registryMarkDirty();
/*  77 */       robot.setMainStation(this);
/*  78 */       registry.take(this, robot.getRobotId());
/*     */       
/*  80 */       return true;
/*     */     } 
/*  82 */     return (this.robotTakingId == robot.getRobotId());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean take(EntityRobotBase robot) {
/*  87 */     if (this.robotTaking == null) {
/*  88 */       IRobotRegistry registry = RobotManager.registryProvider.getRegistry(this.world);
/*  89 */       this.linkIsMain = false;
/*  90 */       this.robotTaking = robot;
/*  91 */       this.robotTakingId = robot.getRobotId();
/*  92 */       registry.registryMarkDirty();
/*  93 */       registry.take(this, robot.getRobotId());
/*     */       
/*  95 */       return true;
/*     */     } 
/*  97 */     return (robot.getRobotId() == this.robotTakingId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void release(EntityRobotBase robot) {
/* 102 */     if (this.robotTaking == robot && !this.linkIsMain) {
/* 103 */       IRobotRegistry registry = RobotManager.registryProvider.getRegistry(this.world);
/* 104 */       unsafeRelease(robot);
/* 105 */       registry.registryMarkDirty();
/* 106 */       registry.release(this, robot.getRobotId());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafeRelease(EntityRobotBase robot) {
/* 112 */     if (this.robotTaking == robot) {
/* 113 */       this.linkIsMain = false;
/* 114 */       this.robotTaking = null;
/* 115 */       this.robotTakingId = Long.MAX_VALUE;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 120 */     nbt.func_74783_a("pos", new int[] { getPos().func_177958_n(), getPos().func_177956_o(), getPos().func_177952_p() });
/* 121 */     nbt.func_74774_a("side", (byte)this.side.ordinal());
/* 122 */     nbt.func_74757_a("isMain", this.linkIsMain);
/* 123 */     nbt.func_74772_a("robotId", this.robotTakingId);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 127 */     if (nbt.func_74764_b("index")) {
/*     */       
/* 129 */       NBTTagCompound indexNBT = nbt.func_74775_l("index");
/* 130 */       int x = indexNBT.func_74762_e("i");
/* 131 */       int y = indexNBT.func_74762_e("j");
/* 132 */       int z = indexNBT.func_74762_e("k");
/* 133 */       this.pos = new BlockPos(x, y, z);
/*     */     } else {
/* 135 */       int[] array = nbt.func_74759_k("pos");
/* 136 */       if (array.length == 3) {
/* 137 */         this.pos = new BlockPos(array[0], array[1], array[2]);
/* 138 */       } else if (array.length != 0) {
/* 139 */         BCLog.logger.warn("Found an integer array that was not the right length! (" + Arrays.toString(array) + ")");
/*     */       } else {
/* 141 */         BCLog.logger.warn("Did not find any integer positions! This is a bug!");
/*     */       } 
/*     */     } 
/* 144 */     this.side = EnumFacing.values()[nbt.func_74771_c("side")];
/* 145 */     this.linkIsMain = nbt.func_74767_n("isMain");
/* 146 */     this.robotTakingId = nbt.func_74763_f("robotId");
/*     */   }
/*     */   
/*     */   public boolean isTaken() {
/* 150 */     return (this.robotTakingId != Long.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public long robotIdTaking() {
/* 154 */     return this.robotTakingId;
/*     */   }
/*     */   
/*     */   public BlockPos index() {
/* 158 */     return this.pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 163 */     return "{" + this.pos + ", " + this.side + " :" + this.robotTakingId + "}";
/*     */   }
/*     */   
/*     */   public boolean linkIsDocked() {
/* 167 */     if (robotTaking() != null) {
/* 168 */       return (robotTaking().getDockingStation() == this);
/*     */     }
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRelease() {
/* 175 */     return (!isMainStation() && !linkIsDocked());
/*     */   }
/*     */   
/*     */   public boolean isInitialized() {
/* 179 */     return true;
/*     */   }
/*     */   
/*     */   public abstract Iterable<StatementSlot> getActiveActions();
/*     */   
/*     */   public IInjectable getItemOutput() {
/* 185 */     return null;
/*     */   }
/*     */   
/*     */   public EnumPipePart getItemOutputSide() {
/* 189 */     return EnumPipePart.CENTER;
/*     */   }
/*     */   
/*     */   public IInventory getItemInput() {
/* 193 */     return null;
/*     */   }
/*     */   
/*     */   public EnumPipePart getItemInputSide() {
/* 197 */     return EnumPipePart.CENTER;
/*     */   }
/*     */   
/*     */   public IFluidHandler getFluidOutput() {
/* 201 */     return null;
/*     */   }
/*     */   
/*     */   public EnumPipePart getFluidOutputSide() {
/* 205 */     return EnumPipePart.CENTER;
/*     */   }
/*     */   
/*     */   public IFluidHandler getFluidInput() {
/* 209 */     return null;
/*     */   }
/*     */   
/*     */   public EnumPipePart getFluidInputSide() {
/* 213 */     return EnumPipePart.CENTER;
/*     */   }
/*     */   
/*     */   public boolean providesPower() {
/* 217 */     return false;
/*     */   }
/*     */   
/*     */   public IRequestProvider getRequestProvider() {
/* 221 */     return null;
/*     */   }
/*     */   
/*     */   public void onChunkUnload() {}
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\DockingStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */