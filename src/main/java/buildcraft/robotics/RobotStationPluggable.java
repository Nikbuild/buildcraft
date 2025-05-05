/*     */ package buildcraft.robotics;
/*     */ 
/*     */ import buildcraft.BuildCraftRobotics;
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.render.ITextureStates;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.robots.IDockingStationProvider;
/*     */ import buildcraft.api.robots.RobotManager;
/*     */ import buildcraft.api.tiles.IDebuggable;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableItem;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableRenderer;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.lib.utils.MatrixTranformations;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import cofh.api.energy.IEnergyReceiver;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class RobotStationPluggable
/*     */   extends PipePluggable
/*     */   implements IPipePluggableItem, IEnergyReceiver, IDebuggable, IDockingStationProvider {
/*     */   private RobotStationState renderState;
/*     */   private DockingStationPipe station;
/*     */   
/*     */   public class RobotStationPluggableRenderer implements IPipePluggableRenderer {
/*  34 */     private float zFightOffset = 2.4414062E-4F;
/*     */ 
/*     */     
/*     */     public void renderPluggable(RenderBlocks renderblocks, IPipe pipe, ForgeDirection side, PipePluggable pipePluggable, ITextureStates blockStateMachine, int renderPass, int x, int y, int z) {
/*  38 */       if (renderPass != 0) {
/*     */         return;
/*     */       }
/*     */       
/*  42 */       RobotStationPluggable.RobotStationState state = ((RobotStationPluggable)pipePluggable).getRenderState();
/*     */       
/*  44 */       switch (state) {
/*     */         case None:
/*     */         case Available:
/*  47 */           blockStateMachine.getTextureState().set(BuildCraftTransport.instance.pipeIconProvider
/*  48 */               .getIcon(PipeIconProvider.TYPE.PipeRobotStation.ordinal()));
/*     */           break;
/*     */         case Reserved:
/*  51 */           blockStateMachine.getTextureState().set(BuildCraftTransport.instance.pipeIconProvider
/*  52 */               .getIcon(PipeIconProvider.TYPE.PipeRobotStationReserved.ordinal()));
/*     */           break;
/*     */         case Linked:
/*  55 */           blockStateMachine.getTextureState().set(BuildCraftTransport.instance.pipeIconProvider
/*  56 */               .getIcon(PipeIconProvider.TYPE.PipeRobotStationLinked.ordinal()));
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/*  61 */       float[][] zeroState = new float[3][2];
/*     */       
/*  63 */       zeroState[0][0] = 0.4325F;
/*  64 */       zeroState[0][1] = 0.5675F;
/*     */       
/*  66 */       zeroState[1][0] = 0.0F;
/*  67 */       zeroState[1][1] = 0.1875F + this.zFightOffset;
/*     */       
/*  69 */       zeroState[2][0] = 0.4325F;
/*  70 */       zeroState[2][1] = 0.5675F;
/*     */       
/*  72 */       float[][] rotated = MatrixTranformations.deepClone(zeroState);
/*  73 */       MatrixTranformations.transform(rotated, side);
/*     */       
/*  75 */       renderblocks.func_147782_a(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/*     */ 
/*     */       
/*  78 */       renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */ 
/*     */       
/*  81 */       zeroState[0][0] = 0.25F;
/*  82 */       zeroState[0][1] = 0.75F;
/*     */       
/*  84 */       zeroState[1][0] = 0.1875F;
/*  85 */       zeroState[1][1] = 0.25F + this.zFightOffset;
/*     */       
/*  87 */       zeroState[2][0] = 0.25F;
/*  88 */       zeroState[2][1] = 0.75F;
/*     */       
/*  90 */       rotated = MatrixTranformations.deepClone(zeroState);
/*  91 */       MatrixTranformations.transform(rotated, side);
/*     */       
/*  93 */       renderblocks.func_147782_a(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/*     */ 
/*     */       
/*  96 */       renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum RobotStationState {
/* 101 */     None,
/* 102 */     Available,
/* 103 */     Reserved,
/* 104 */     Linked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValid = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getDropItems(IPipeTile pipe) {
/* 127 */     return new ItemStack[] { new ItemStack(BuildCraftRobotics.robotStationItem) };
/*     */   }
/*     */ 
/*     */   
/*     */   public DockingStation getStation() {
/* 132 */     return this.station;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlocking(IPipeTile pipe, ForgeDirection direction) {
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 142 */     if (this.station != null && this.station
/* 143 */       .getPipe() != null && 
/* 144 */       !(this.station.getPipe().getWorld()).field_72995_K) {
/* 145 */       RobotManager.registryProvider.getRegistry(this.station.world).removeStation(this.station);
/* 146 */       this.isValid = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(IPipeTile pipe, ForgeDirection direction) {
/* 152 */     if (!this.isValid && !(pipe.getWorld()).field_72995_K) {
/* 153 */       this
/* 154 */         .station = (DockingStationPipe)RobotManager.registryProvider.getRegistry(pipe.getWorld()).getStation(pipe
/* 155 */           .x(), pipe
/* 156 */           .y(), pipe
/* 157 */           .z(), direction);
/*     */ 
/*     */       
/* 160 */       if (this.station == null) {
/* 161 */         this.station = new DockingStationPipe(pipe, direction);
/* 162 */         RobotManager.registryProvider.getRegistry(pipe.getWorld()).registerStation(this.station);
/*     */       } 
/*     */       
/* 165 */       this.isValid = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(ForgeDirection side) {
/* 171 */     float[][] bounds = new float[3][2];
/*     */     
/* 173 */     bounds[0][0] = 0.25F;
/* 174 */     bounds[0][1] = 0.75F;
/*     */     
/* 176 */     bounds[1][0] = 0.125F;
/* 177 */     bounds[1][1] = 0.251F;
/*     */     
/* 179 */     bounds[2][0] = 0.25F;
/* 180 */     bounds[2][1] = 0.75F;
/*     */     
/* 182 */     MatrixTranformations.transform(bounds, side);
/* 183 */     return AxisAlignedBB.func_72330_a(bounds[0][0], bounds[1][0], bounds[2][0], bounds[0][1], bounds[1][1], bounds[2][1]);
/*     */   }
/*     */   
/*     */   private void refreshRenderState() {
/* 187 */     this
/* 188 */       .renderState = this.station.isTaken() ? (this.station.isMainStation() ? RobotStationState.Linked : RobotStationState.Reserved) : RobotStationState.Available;
/*     */   }
/*     */ 
/*     */   
/*     */   public RobotStationState getRenderState() {
/* 193 */     if (this.renderState == null) {
/* 194 */       this.renderState = RobotStationState.None;
/*     */     }
/* 196 */     return this.renderState;
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipePluggableRenderer getRenderer() {
/* 201 */     return new RobotStationPluggableRenderer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 206 */     refreshRenderState();
/* 207 */     data.writeByte(getRenderState().ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresRenderUpdate(PipePluggable o) {
/* 212 */     return (getRenderState() != ((RobotStationPluggable)o).getRenderState());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/*     */     try {
/* 218 */       this.renderState = RobotStationState.values()[data.readUnsignedByte()];
/* 219 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 220 */       this.renderState = RobotStationState.None;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public PipePluggable createPipePluggable(IPipe pipe, ForgeDirection side, ItemStack stack) {
/* 226 */     return new RobotStationPluggable();
/*     */   }
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
/* 231 */     if (this.station != null && this.station.robotTaking() != null && this.station.robotTaking().getBattery() != null && this.station
/* 232 */       .robotTaking().getDockingStation() == this.station) {
/* 233 */       return ((EntityRobot)this.station.robotTaking()).receiveEnergy(maxReceive, simulate);
/*     */     }
/* 235 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection from) {
/* 240 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection from) {
/* 245 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getDebugInfo(List<String> info, ForgeDirection side, ItemStack debugger, EntityPlayer player) {
/* 255 */     if (this.station == null) {
/* 256 */       info.add("RobotStationPluggable: No station found!");
/*     */     } else {
/* 258 */       refreshRenderState();
/* 259 */       info.add("Docking Station (side " + side.name() + ", " + getRenderState().name() + ")");
/* 260 */       if (this.station.robotTaking() != null && this.station.robotTaking() instanceof IDebuggable)
/* 261 */         ((IDebuggable)this.station.robotTaking()).getDebugInfo(info, ForgeDirection.UNKNOWN, debugger, player); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\RobotStationPluggable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */