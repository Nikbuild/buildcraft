/*     */ package buildcraft.robotics;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.robots.IRequestProvider;
/*     */ import buildcraft.api.robots.IRobotRegistry;
/*     */ import buildcraft.api.robots.ResourceId;
/*     */ import buildcraft.api.robots.ResourceIdRequest;
/*     */ import buildcraft.api.robots.RobotManager;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackRequest
/*     */ {
/*     */   private IRequestProvider requester;
/*     */   private int slot;
/*     */   private ItemStack stack;
/*     */   private DockingStation station;
/*     */   private BlockIndex stationIndex;
/*     */   private ForgeDirection stationSide;
/*     */   
/*     */   public StackRequest(IRequestProvider requester, int slot, ItemStack stack) {
/*  37 */     this.requester = requester;
/*  38 */     this.slot = slot;
/*  39 */     this.stack = stack;
/*  40 */     this.station = null;
/*     */   }
/*     */   
/*     */   private StackRequest(int slot, ItemStack stack, BlockIndex stationIndex, ForgeDirection stationSide) {
/*  44 */     this.requester = null;
/*  45 */     this.slot = slot;
/*  46 */     this.stack = stack;
/*  47 */     this.station = null;
/*  48 */     this.stationIndex = stationIndex;
/*  49 */     this.stationSide = stationSide;
/*     */   }
/*     */   
/*     */   public IRequestProvider getRequester(World world) {
/*  53 */     if (this.requester == null) {
/*  54 */       DockingStation dockingStation = getStation(world);
/*  55 */       if (dockingStation != null) {
/*  56 */         this.requester = dockingStation.getRequestProvider();
/*     */       }
/*     */     } 
/*  59 */     return this.requester;
/*     */   }
/*     */   
/*     */   public int getSlot() {
/*  63 */     return this.slot;
/*     */   }
/*     */   
/*     */   public ItemStack getStack() {
/*  67 */     return this.stack;
/*     */   }
/*     */   
/*     */   public DockingStation getStation(World world) {
/*  71 */     if (this.station == null) {
/*  72 */       IRobotRegistry robotRegistry = RobotManager.registryProvider.getRegistry(world);
/*  73 */       this.station = robotRegistry.getStation(this.stationIndex.x, this.stationIndex.y, this.stationIndex.z, this.stationSide);
/*     */     } 
/*  75 */     return this.station;
/*     */   }
/*     */   
/*     */   public void setStation(DockingStation station) {
/*  79 */     this.station = station;
/*  80 */     this.stationIndex = station.index();
/*  81 */     this.stationSide = station.side();
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  85 */     nbt.func_74768_a("slot", this.slot);
/*     */     
/*  87 */     NBTTagCompound stackNBT = new NBTTagCompound();
/*  88 */     this.stack.func_77955_b(stackNBT);
/*  89 */     nbt.func_74782_a("stack", (NBTBase)stackNBT);
/*     */     
/*  91 */     if (this.station != null) {
/*  92 */       NBTTagCompound stationIndexNBT = new NBTTagCompound();
/*  93 */       this.station.index().writeTo(stationIndexNBT);
/*  94 */       nbt.func_74782_a("stationIndex", (NBTBase)stationIndexNBT);
/*  95 */       nbt.func_74774_a("stationSide", (byte)this.station.side().ordinal());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static StackRequest loadFromNBT(NBTTagCompound nbt) {
/* 100 */     if (nbt.func_74764_b("stationIndex")) {
/* 101 */       int slot = nbt.func_74762_e("slot");
/*     */       
/* 103 */       ItemStack stack = ItemStack.func_77949_a(nbt.func_74775_l("stack"));
/*     */       
/* 105 */       BlockIndex stationIndex = new BlockIndex(nbt.func_74775_l("stationIndex"));
/* 106 */       ForgeDirection stationSide = ForgeDirection.values()[nbt.func_74771_c("stationSide")];
/*     */       
/* 108 */       return new StackRequest(slot, stack, stationIndex, stationSide);
/*     */     } 
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceId getResourceId(World world) {
/* 115 */     return (getStation(world) != null) ? (ResourceId)new ResourceIdRequest(getStation(world), this.slot) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\StackRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */