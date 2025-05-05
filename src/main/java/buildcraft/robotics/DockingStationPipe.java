/*     */ package buildcraft.robotics;
/*     */ 
/*     */ import buildcraft.BuildCraftRobotics;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.api.gates.IGate;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.IRequestProvider;
/*     */ import buildcraft.api.robots.RobotManager;
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.transport.IInjectable;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.lib.inventory.InventoryWrapper;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import buildcraft.transport.gates.ActionIterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DockingStationPipe
/*     */   extends DockingStation
/*     */   implements IRequestProvider
/*     */ {
/*  35 */   private IInjectable injectablePipe = new IInjectable()
/*     */     {
/*     */       public boolean canInjectItems(ForgeDirection from) {
/*  38 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public int injectItem(ItemStack stack, boolean doAdd, ForgeDirection from, EnumColor color) {
/*  43 */         if (doAdd) {
/*  44 */           float cx = DockingStationPipe.this.x() + 0.5F + 0.2F * (DockingStationPipe.this.side()).offsetX;
/*  45 */           float cy = DockingStationPipe.this.y() + 0.5F + 0.2F * (DockingStationPipe.this.side()).offsetY;
/*  46 */           float cz = DockingStationPipe.this.z() + 0.5F + 0.2F * (DockingStationPipe.this.side()).offsetZ;
/*  47 */           TravelingItem item = TravelingItem.make(cx, cy, cz, stack);
/*  48 */           if (item.isCorrupted()) {
/*  49 */             return 0;
/*     */           }
/*     */           
/*  52 */           ((PipeTransportItems)((Pipe)DockingStationPipe.this.getPipe().getPipe()).transport)
/*  53 */             .injectItem(item, from);
/*     */         } 
/*  55 */         return stack.field_77994_a;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private IPipeTile pipe;
/*     */ 
/*     */ 
/*     */   
/*     */   public DockingStationPipe(IPipeTile iPipe, ForgeDirection side) {
/*  66 */     super(new BlockIndex(iPipe.x(), iPipe.y(), iPipe.z()), side);
/*  67 */     this.pipe = iPipe;
/*  68 */     this.world = iPipe.getWorld();
/*     */   }
/*     */   
/*     */   public IPipeTile getPipe() {
/*  72 */     if (this.pipe == null) {
/*  73 */       TileEntity tile = this.world.func_147438_o(x(), y(), z());
/*  74 */       if (tile instanceof IPipeTile) {
/*  75 */         this.pipe = (IPipeTile)tile;
/*     */       }
/*     */     } 
/*     */     
/*  79 */     if (this.pipe == null || ((TileEntity)this.pipe).func_145837_r()) {
/*     */       
/*  81 */       RobotManager.registryProvider.getRegistry(this.world).removeStation(this);
/*  82 */       this.pipe = null;
/*     */     } 
/*     */     
/*  85 */     return this.pipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<StatementSlot> getActiveActions() {
/*  90 */     return (Iterable<StatementSlot>)new ActionIterator(getPipe().getPipe());
/*     */   }
/*     */ 
/*     */   
/*     */   public IInjectable getItemOutput() {
/*  95 */     if (getPipe().getPipeType() != IPipeTile.PipeType.ITEM) {
/*  96 */       return null;
/*     */     }
/*     */     
/*  99 */     return this.injectablePipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForgeDirection getItemOutputSide() {
/* 104 */     return side().getOpposite();
/*     */   }
/*     */ 
/*     */   
/*     */   public ISidedInventory getItemInput() {
/* 109 */     if (getPipe().getPipeType() != IPipeTile.PipeType.ITEM) {
/* 110 */       return null;
/*     */     }
/*     */     
/* 113 */     if (!(getPipe().getPipe() instanceof buildcraft.transport.pipes.PipeItemsWood)) {
/* 114 */       return null;
/*     */     }
/*     */     
/* 117 */     int meta = ((TileEntity)getPipe()).func_145832_p();
/* 118 */     ForgeDirection dir = ForgeDirection.getOrientation(meta);
/*     */     
/* 120 */     TileEntity connectedTile = getPipe().getWorld().func_147438_o(x() + dir.offsetX, 
/* 121 */         y() + dir.offsetY, z() + dir.offsetZ);
/* 122 */     if (connectedTile instanceof IInventory) {
/* 123 */       return InventoryWrapper.getWrappedInventory(connectedTile);
/*     */     }
/*     */     
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForgeDirection getItemInputSide() {
/* 131 */     if (getPipe().getPipeType() != IPipeTile.PipeType.ITEM) {
/* 132 */       return ForgeDirection.UNKNOWN;
/*     */     }
/*     */     
/* 135 */     if (!(getPipe().getPipe() instanceof buildcraft.transport.pipes.PipeItemsWood)) {
/* 136 */       return ForgeDirection.UNKNOWN;
/*     */     }
/*     */     
/* 139 */     int meta = ((TileEntity)getPipe()).func_145832_p();
/* 140 */     return ForgeDirection.getOrientation(meta).getOpposite();
/*     */   }
/*     */ 
/*     */   
/*     */   public IFluidHandler getFluidInput() {
/* 145 */     if (getPipe().getPipeType() != IPipeTile.PipeType.FLUID) {
/* 146 */       return null;
/*     */     }
/*     */     
/* 149 */     if (!(getPipe().getPipe() instanceof buildcraft.transport.pipes.PipeFluidsWood)) {
/* 150 */       return null;
/*     */     }
/*     */     
/* 153 */     int meta = ((TileEntity)getPipe()).func_145832_p();
/* 154 */     ForgeDirection dir = ForgeDirection.getOrientation(meta);
/*     */     
/* 156 */     TileEntity connectedTile = getPipe().getWorld().func_147438_o(x() + dir.offsetX, 
/* 157 */         y() + dir.offsetY, z() + dir.offsetZ);
/* 158 */     if (connectedTile instanceof IFluidHandler) {
/* 159 */       return (IFluidHandler)connectedTile;
/*     */     }
/*     */     
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForgeDirection getFluidInputSide() {
/* 167 */     if (getPipe().getPipeType() != IPipeTile.PipeType.FLUID) {
/* 168 */       return ForgeDirection.UNKNOWN;
/*     */     }
/*     */     
/* 171 */     if (!(getPipe().getPipe() instanceof buildcraft.transport.pipes.PipeFluidsWood)) {
/* 172 */       return ForgeDirection.UNKNOWN;
/*     */     }
/*     */     
/* 175 */     int meta = ((TileEntity)getPipe()).func_145832_p();
/* 176 */     return ForgeDirection.getOrientation(meta).getOpposite();
/*     */   }
/*     */ 
/*     */   
/*     */   public IFluidHandler getFluidOutput() {
/* 181 */     if (getPipe().getPipeType() != IPipeTile.PipeType.FLUID) {
/* 182 */       return null;
/*     */     }
/*     */     
/* 185 */     return (IFluidHandler)((Pipe)getPipe().getPipe()).transport;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForgeDirection getFluidOutputSide() {
/* 190 */     return ForgeDirection.UNKNOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean providesPower() {
/* 195 */     return (getPipe().getPipeType() == IPipeTile.PipeType.POWER);
/*     */   }
/*     */ 
/*     */   
/*     */   public IRequestProvider getRequestProvider() {
/* 200 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 201 */       TileEntity nearbyTile = getPipe().getWorld().func_147438_o(x() + dir.offsetX, 
/* 202 */           y() + dir.offsetY, z() + dir.offsetZ);
/* 203 */       if (nearbyTile instanceof IRequestProvider) {
/* 204 */         return (IRequestProvider)nearbyTile;
/*     */       }
/*     */     } 
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 212 */     getPipe();
/*     */     
/* 214 */     if (this.pipe == null || this.pipe.getPipe() == null) {
/* 215 */       return false;
/*     */     }
/* 217 */     return ((Pipe)this.pipe.getPipe()).isInitialized();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean take(EntityRobotBase robot) {
/* 222 */     getPipe();
/* 223 */     if (this.pipe == null) {
/* 224 */       return false;
/*     */     }
/*     */     
/* 227 */     boolean result = super.take(robot);
/* 228 */     if (result) {
/* 229 */       this.pipe.scheduleRenderUpdate();
/*     */     }
/* 231 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean takeAsMain(EntityRobotBase robot) {
/* 236 */     getPipe();
/* 237 */     if (this.pipe == null) {
/* 238 */       return false;
/*     */     }
/*     */     
/* 241 */     boolean result = super.takeAsMain(robot);
/* 242 */     if (result) {
/* 243 */       this.pipe.scheduleRenderUpdate();
/*     */     }
/* 245 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafeRelease(EntityRobotBase robot) {
/* 250 */     super.unsafeRelease(robot);
/* 251 */     if (robotTaking() == null && getPipe() != null) {
/* 252 */       getPipe().scheduleRenderUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/* 258 */     this.pipe = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRequestsCount() {
/* 263 */     return 127;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getRequest(int slot) {
/* 268 */     ForgeDirection side = ForgeDirection.getOrientation((slot & 0x70) >> 4);
/* 269 */     int action = (slot & 0xC) >> 2;
/* 270 */     int param = slot & 0x3;
/* 271 */     IGate gate = getPipe().getPipe().getGate(side);
/* 272 */     if (gate == null) {
/* 273 */       return null;
/*     */     }
/*     */     
/* 276 */     List<IStatement> actions = gate.getActions();
/* 277 */     if (actions.size() <= action) {
/* 278 */       return null;
/*     */     }
/*     */     
/* 281 */     if (actions.get(action) != BuildCraftRobotics.actionStationRequestItems) {
/* 282 */       return null;
/*     */     }
/*     */     
/* 285 */     List<StatementSlot> activeActions = gate.getActiveActions();
/*     */     
/* 287 */     StatementSlot slotStmt = null;
/* 288 */     for (StatementSlot stmt : activeActions) {
/* 289 */       if (stmt.statement == actions.get(action)) {
/* 290 */         slotStmt = stmt;
/*     */         break;
/*     */       } 
/*     */     } 
/* 294 */     if (slotStmt == null) {
/* 295 */       return null;
/*     */     }
/* 297 */     if (slotStmt.parameters.length <= param) {
/* 298 */       return null;
/*     */     }
/*     */     
/* 301 */     if (slotStmt.parameters[param] == null) {
/* 302 */       return null;
/*     */     }
/*     */     
/* 305 */     return slotStmt.parameters[param].getItemStack();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack offerItem(int slot, ItemStack stack) {
/* 310 */     int consumed = this.injectablePipe.injectItem(stack, true, this.side.getOpposite(), null);
/* 311 */     if (stack.field_77994_a > consumed) {
/* 312 */       ItemStack newStack = stack.func_77946_l();
/* 313 */       newStack.field_77994_a -= consumed;
/* 314 */       return newStack;
/*     */     } 
/* 316 */     return null;
/*     */   }
/*     */   
/*     */   public DockingStationPipe() {}
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\DockingStationPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */