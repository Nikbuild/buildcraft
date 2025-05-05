/*      */ package buildcraft.transport;
/*      */ 
/*      */ import buildcraft.BuildCraftCore;
/*      */ import buildcraft.BuildCraftTransport;
/*      */ import buildcraft.api.core.BCLog;
/*      */ import buildcraft.api.core.EnumColor;
/*      */ import buildcraft.api.core.IIconProvider;
/*      */ import buildcraft.api.core.ISerializable;
/*      */ import buildcraft.api.core.Position;
/*      */ import buildcraft.api.gates.IGateExpansion;
/*      */ import buildcraft.api.power.IRedstoneEngineReceiver;
/*      */ import buildcraft.api.tiles.IDebuggable;
/*      */ import buildcraft.api.transport.IPipe;
/*      */ import buildcraft.api.transport.IPipeConnection;
/*      */ import buildcraft.api.transport.IPipeTile;
/*      */ import buildcraft.api.transport.PipeManager;
/*      */ import buildcraft.api.transport.PipeWire;
/*      */ import buildcraft.api.transport.pluggable.PipePluggable;
/*      */ import buildcraft.core.DefaultProps;
/*      */ import buildcraft.core.internal.IDropControlInventory;
/*      */ import buildcraft.core.lib.ITileBufferHolder;
/*      */ import buildcraft.core.lib.TileBuffer;
/*      */ import buildcraft.core.lib.network.IGuiReturnHandler;
/*      */ import buildcraft.core.lib.network.ISyncedTile;
/*      */ import buildcraft.core.lib.network.Packet;
/*      */ import buildcraft.core.lib.network.PacketTileState;
/*      */ import buildcraft.core.lib.utils.Utils;
/*      */ import buildcraft.transport.gates.GateFactory;
/*      */ import buildcraft.transport.gates.GatePluggable;
/*      */ import buildcraft.transport.pluggable.PlugPluggable;
/*      */ import cofh.api.energy.IEnergyHandler;
/*      */ import cpw.mods.fml.relauncher.Side;
/*      */ import cpw.mods.fml.relauncher.SideOnly;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.util.List;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraftforge.common.util.ForgeDirection;
/*      */ import net.minecraftforge.fluids.Fluid;
/*      */ import net.minecraftforge.fluids.FluidStack;
/*      */ import net.minecraftforge.fluids.FluidTankInfo;
/*      */ import net.minecraftforge.fluids.IFluidHandler;
/*      */ import org.apache.logging.log4j.Level;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TileGenericPipe
/*      */   extends TileEntity
/*      */   implements IFluidHandler, IPipeTile, ITileBufferHolder, IEnergyHandler, IDropControlInventory, ISyncedTile, ISolidSideTile, IGuiReturnHandler, IRedstoneEngineReceiver, IDebuggable, IPipeConnection
/*      */ {
/*      */   public boolean initialized = false;
/*   73 */   public final PipeRenderState renderState = new PipeRenderState();
/*   74 */   public final PipePluggableState pluggableState = new PipePluggableState();
/*   75 */   public final CoreState coreState = new CoreState();
/*   76 */   public boolean[] pipeConnectionsBuffer = new boolean[6];
/*      */   
/*      */   public Pipe pipe;
/*      */   public int redstoneInput;
/*   80 */   public int[] redstoneInputSide = new int[ForgeDirection.VALID_DIRECTIONS.length];
/*      */   
/*      */   protected boolean deletePipe = false;
/*      */   protected boolean sendClientUpdate = false;
/*      */   protected boolean blockNeighborChange = false;
/*   85 */   protected int blockNeighborChangedSides = 0;
/*      */   protected boolean refreshRenderState = false;
/*      */   protected boolean pipeBound = false;
/*      */   protected boolean resyncGateExpansions = false;
/*      */   protected boolean attachPluggables = false;
/*   90 */   protected SideProperties sideProperties = new SideProperties();
/*      */   
/*      */   private TileBuffer[] tileBuffer;
/*   93 */   private int glassColor = -1;
/*      */   
/*      */   public static class CoreState implements ISerializable {
/*   96 */     public int pipeId = -1;
/*      */ 
/*      */     
/*      */     public void writeData(ByteBuf data) {
/*  100 */       data.writeInt(this.pipeId);
/*      */     }
/*      */ 
/*      */     
/*      */     public void readData(ByteBuf data) {
/*  105 */       this.pipeId = data.readInt();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SideProperties {
/*  110 */     PipePluggable[] pluggables = new PipePluggable[ForgeDirection.VALID_DIRECTIONS.length];
/*      */     
/*      */     public void writeToNBT(NBTTagCompound nbt) {
/*  113 */       for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/*  114 */         PipePluggable pluggable = this.pluggables[i];
/*  115 */         String key = "pluggable[" + i + "]";
/*  116 */         if (pluggable == null) {
/*  117 */           nbt.func_82580_o(key);
/*      */         } else {
/*  119 */           NBTTagCompound pluggableData = new NBTTagCompound();
/*  120 */           pluggableData.func_74778_a("pluggableName", PipeManager.getPluggableName(pluggable.getClass()));
/*  121 */           pluggable.writeToNBT(pluggableData);
/*  122 */           nbt.func_74782_a(key, (NBTBase)pluggableData);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public void readFromNBT(NBTTagCompound nbt) {
/*      */       int i;
/*  128 */       for (i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/*  129 */         String key = "pluggable[" + i + "]";
/*  130 */         if (nbt.func_74764_b(key)) {
/*      */           
/*      */           try {
/*      */             
/*  134 */             NBTTagCompound pluggableData = nbt.func_74775_l(key);
/*  135 */             Class<?> pluggableClass = null;
/*      */             
/*  137 */             if (pluggableData.func_74764_b("pluggableClass")) {
/*  138 */               String c = pluggableData.func_74779_i("pluggableClass");
/*  139 */               if ("buildcraft.transport.gates.ItemGate$GatePluggable".equals(c)) {
/*  140 */                 pluggableClass = GatePluggable.class;
/*  141 */               } else if ("buildcraft.transport.ItemFacade$FacadePluggable".equals(c)) {
/*  142 */                 pluggableClass = FacadePluggable.class;
/*  143 */               } else if ("buildcraft.transport.ItemPlug$PlugPluggable".equals(c)) {
/*  144 */                 pluggableClass = PlugPluggable.class;
/*  145 */               } else if ("buildcraft.transport.gates.ItemRobotStation$RobotStationPluggable".equals(c) || "buildcraft.transport.ItemRobotStation$RobotStationPluggable"
/*  146 */                 .equals(c)) {
/*  147 */                 pluggableClass = PipeManager.getPluggableByName("robotStation");
/*      */               } 
/*      */             } else {
/*  150 */               pluggableClass = PipeManager.getPluggableByName(pluggableData.func_74779_i("pluggableName"));
/*      */             } 
/*  152 */             if (pluggableClass != null)
/*  153 */               if (!PipePluggable.class.isAssignableFrom(pluggableClass)) {
/*  154 */                 BCLog.logger.warn("Wrong pluggable class: " + pluggableClass);
/*      */               } else {
/*      */                 
/*  157 */                 PipePluggable pluggable = (PipePluggable)pluggableClass.newInstance();
/*  158 */                 pluggable.readFromNBT(pluggableData);
/*  159 */                 this.pluggables[i] = pluggable;
/*      */               }  
/*  161 */           } catch (Exception e) {
/*  162 */             BCLog.logger.warn("Failed to load side state");
/*  163 */             e.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  168 */       for (i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/*  169 */         PlugPluggable plugPluggable; PipePluggable pluggable = null;
/*  170 */         if (nbt.func_74764_b("facadeState[" + i + "]")) {
/*  171 */           pluggable = new FacadePluggable(ItemFacade.FacadeState.readArray(nbt.func_150295_c("facadeState[" + i + "]", 10)));
/*      */         
/*      */         }
/*  174 */         else if (nbt.func_74764_b("facadeBlocks[" + i + "]")) {
/*      */           
/*  176 */           Block block = (Block)Block.field_149771_c.func_148754_a(nbt.func_74762_e("facadeBlocks[" + i + "]"));
/*  177 */           int blockId = nbt.func_74762_e("facadeBlocks[" + i + "]");
/*      */           
/*  179 */           if (blockId != 0) {
/*  180 */             int metadata = nbt.func_74762_e("facadeMeta[" + i + "]");
/*  181 */             pluggable = new FacadePluggable(new ItemFacade.FacadeState[] { ItemFacade.FacadeState.create(block, metadata) });
/*      */           } 
/*  183 */         } else if (nbt.func_74764_b("facadeBlocksStr[" + i + "][0]")) {
/*      */           
/*  185 */           ItemFacade.FacadeState mainState = ItemFacade.FacadeState.create((Block)Block.field_149771_c
/*  186 */               .func_82594_a(nbt.func_74779_i("facadeBlocksStr[" + i + "][0]")), nbt
/*  187 */               .func_74762_e("facadeMeta[" + i + "][0]"));
/*      */           
/*  189 */           if (nbt.func_74764_b("facadeBlocksStr[" + i + "][1]")) {
/*  190 */             ItemFacade.FacadeState phasedState = ItemFacade.FacadeState.create((Block)Block.field_149771_c
/*  191 */                 .func_82594_a(nbt.func_74779_i("facadeBlocksStr[" + i + "][1]")), nbt
/*  192 */                 .func_74762_e("facadeMeta[" + i + "][1]"), 
/*  193 */                 PipeWire.fromOrdinal(nbt.func_74762_e("facadeWires[" + i + "]")));
/*      */             
/*  195 */             pluggable = new FacadePluggable(new ItemFacade.FacadeState[] { mainState, phasedState });
/*      */           } else {
/*  197 */             pluggable = new FacadePluggable(new ItemFacade.FacadeState[] { mainState });
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  202 */         if (nbt.func_74767_n("plug[" + i + "]")) {
/*  203 */           plugPluggable = new PlugPluggable();
/*      */         }
/*      */         
/*  206 */         if (plugPluggable != null) {
/*  207 */           this.pluggables[i] = (PipePluggable)plugPluggable;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public void rotateLeft() {
/*  213 */       PipePluggable[] newPluggables = new PipePluggable[ForgeDirection.VALID_DIRECTIONS.length];
/*  214 */       for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*  215 */         newPluggables[dir.getRotation(ForgeDirection.UP).ordinal()] = this.pluggables[dir.ordinal()];
/*      */       }
/*  217 */       this.pluggables = newPluggables;
/*      */     }
/*      */     
/*      */     public boolean dropItem(TileGenericPipe pipe, ForgeDirection direction, EntityPlayer player) {
/*  221 */       boolean result = false;
/*  222 */       PipePluggable pluggable = this.pluggables[direction.ordinal()];
/*  223 */       if (pluggable != null) {
/*  224 */         pluggable.onDetachedPipe(pipe, direction);
/*  225 */         if (!(pipe.getWorld()).field_72995_K) {
/*  226 */           ItemStack[] stacks = pluggable.getDropItems(pipe);
/*  227 */           if (stacks != null) {
/*  228 */             for (ItemStack stack : stacks) {
/*  229 */               Utils.dropTryIntoPlayerInventory(pipe.field_145850_b, pipe.field_145851_c, pipe.field_145848_d, pipe.field_145849_e, stack, player);
/*      */             }
/*      */           }
/*      */         } 
/*      */         
/*  234 */         result = true;
/*      */       } 
/*  236 */       return result;
/*      */     }
/*      */     
/*      */     public void invalidate() {
/*  240 */       for (PipePluggable p : this.pluggables) {
/*  241 */         if (p != null) {
/*  242 */           p.invalidate();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public void validate(TileGenericPipe pipe) {
/*  248 */       for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
/*  249 */         PipePluggable p = this.pluggables[d.ordinal()];
/*      */         
/*  251 */         if (p != null) {
/*  252 */           p.validate(pipe, d);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_145841_b(NBTTagCompound nbt) {
/*  263 */     super.func_145841_b(nbt);
/*      */     
/*  265 */     if (this.glassColor >= 0) {
/*  266 */       nbt.func_74774_a("stainedColor", (byte)this.glassColor);
/*      */     }
/*  268 */     for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/*  269 */       String key = "redstoneInputSide[" + i + "]";
/*  270 */       nbt.func_74774_a(key, (byte)this.redstoneInputSide[i]);
/*      */     } 
/*      */     
/*  273 */     if (this.pipe != null) {
/*  274 */       nbt.func_74768_a("pipeId", Item.func_150891_b(this.pipe.item));
/*  275 */       this.pipe.writeToNBT(nbt);
/*      */     } else {
/*  277 */       nbt.func_74768_a("pipeId", this.coreState.pipeId);
/*      */     } 
/*      */     
/*  280 */     this.sideProperties.writeToNBT(nbt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_145839_a(NBTTagCompound nbt) {
/*  285 */     super.func_145839_a(nbt);
/*      */     
/*  287 */     this.glassColor = nbt.func_74764_b("stainedColor") ? nbt.func_74771_c("stainedColor") : -1;
/*      */     
/*  289 */     this.redstoneInput = 0;
/*      */     
/*  291 */     for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/*  292 */       String key = "redstoneInputSide[" + i + "]";
/*  293 */       if (nbt.func_74764_b(key)) {
/*  294 */         this.redstoneInputSide[i] = nbt.func_74771_c(key);
/*      */         
/*  296 */         if (this.redstoneInputSide[i] > this.redstoneInput) {
/*  297 */           this.redstoneInput = this.redstoneInputSide[i];
/*      */         }
/*      */       } else {
/*  300 */         this.redstoneInputSide[i] = 0;
/*      */       } 
/*      */     } 
/*      */     
/*  304 */     this.coreState.pipeId = nbt.func_74762_e("pipeId");
/*  305 */     this.pipe = BlockGenericPipe.createPipe(Item.func_150899_d(this.coreState.pipeId));
/*  306 */     bindPipe();
/*      */     
/*  308 */     if (this.pipe != null) {
/*  309 */       this.pipe.readFromNBT(nbt);
/*      */     } else {
/*  311 */       BCLog.logger.log(Level.WARN, "Pipe failed to load from NBT at {0},{1},{2}", new Object[] { Integer.valueOf(this.field_145851_c), Integer.valueOf(this.field_145848_d), Integer.valueOf(this.field_145849_e) });
/*  312 */       this.deletePipe = true;
/*      */     } 
/*      */     
/*  315 */     this.sideProperties.readFromNBT(nbt);
/*  316 */     this.attachPluggables = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_145843_s() {
/*  321 */     this.initialized = false;
/*  322 */     this.tileBuffer = null;
/*      */     
/*  324 */     if (this.pipe != null) {
/*  325 */       this.pipe.invalidate();
/*      */     }
/*      */     
/*  328 */     this.sideProperties.invalidate();
/*      */     
/*  330 */     super.func_145843_s();
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_145829_t() {
/*  335 */     super.func_145829_t();
/*  336 */     this.initialized = false;
/*  337 */     this.tileBuffer = null;
/*  338 */     bindPipe();
/*      */     
/*  340 */     if (this.pipe != null) {
/*  341 */       this.pipe.validate();
/*      */     }
/*      */     
/*  344 */     this.sideProperties.validate(this);
/*      */   }
/*      */   
/*      */   protected void notifyBlockChanged() {
/*  348 */     this.field_145850_b.func_147460_e(this.field_145851_c, this.field_145848_d, this.field_145849_e, getBlock());
/*  349 */     scheduleRenderUpdate();
/*  350 */     sendUpdateToClient();
/*  351 */     if (this.pipe != null) {
/*  352 */       this.pipe.scheduleWireUpdate();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_145845_h() {
/*  358 */     if (!this.field_145850_b.field_72995_K) {
/*  359 */       if (this.deletePipe) {
/*  360 */         this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */       }
/*      */       
/*  363 */       if (this.pipe == null) {
/*      */         return;
/*      */       }
/*      */       
/*  367 */       if (!this.initialized) {
/*  368 */         initialize(this.pipe);
/*      */       }
/*      */     } 
/*      */     
/*  372 */     if (this.attachPluggables) {
/*  373 */       this.attachPluggables = false;
/*      */       
/*  375 */       for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/*  376 */         if (this.sideProperties.pluggables[i] != null) {
/*  377 */           this.pipe.eventBus.registerHandler(this.sideProperties.pluggables[i]);
/*  378 */           this.sideProperties.pluggables[i].onAttachedPipe(this, ForgeDirection.getOrientation(i));
/*      */         } 
/*      */       } 
/*  381 */       notifyBlockChanged();
/*      */     } 
/*      */     
/*  384 */     if (!BlockGenericPipe.isValid(this.pipe)) {
/*      */       return;
/*      */     }
/*      */     
/*  388 */     this.pipe.updateEntity();
/*      */     
/*  390 */     for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/*  391 */       PipePluggable p = getPipePluggable(direction);
/*  392 */       if (p != null) {
/*  393 */         p.update(this, direction);
/*      */       }
/*      */     } 
/*      */     
/*  397 */     if (this.field_145850_b.field_72995_K) {
/*  398 */       if (this.resyncGateExpansions) {
/*  399 */         syncGateExpansions();
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  405 */     if (this.blockNeighborChange) {
/*  406 */       for (int i = 0; i < 6; i++) {
/*  407 */         if ((this.blockNeighborChangedSides & 1 << i) != 0) {
/*  408 */           this.blockNeighborChangedSides ^= 1 << i;
/*  409 */           computeConnection(ForgeDirection.getOrientation(i));
/*      */         } 
/*      */       } 
/*  412 */       this.pipe.onNeighborBlockChange(0);
/*  413 */       this.blockNeighborChange = false;
/*  414 */       this.refreshRenderState = true;
/*      */     } 
/*      */     
/*  417 */     if (this.refreshRenderState) {
/*  418 */       refreshRenderState();
/*  419 */       this.refreshRenderState = false;
/*      */     } 
/*      */     
/*  422 */     if (this.sendClientUpdate) {
/*  423 */       this.sendClientUpdate = false;
/*      */       
/*  425 */       if (this.field_145850_b instanceof WorldServer) {
/*  426 */         WorldServer world = (WorldServer)this.field_145850_b;
/*  427 */         Packet updatePacket = getBCDescriptionPacket();
/*      */         
/*  429 */         for (Object o : world.field_73010_i) {
/*  430 */           EntityPlayerMP player = (EntityPlayerMP)o;
/*      */           
/*  432 */           if (world.func_73040_p().func_72694_a(player, this.field_145851_c >> 4, this.field_145849_e >> 4)) {
/*  433 */             BuildCraftCore.instance.sendToPlayer((EntityPlayer)player, updatePacket);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void initializeFromItemMetadata(int i) {
/*  441 */     if (i >= 1 && i <= 16) {
/*  442 */       setPipeColor(i - 1 & 0xF);
/*      */     } else {
/*  444 */       setPipeColor(-1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getItemMetadata() {
/*  449 */     return (getPipeColor() >= 0) ? (1 + getPipeColor()) : 0;
/*      */   }
/*      */   
/*      */   public int getPipeColor() {
/*  453 */     return this.field_145850_b.field_72995_K ? this.renderState.getGlassColor() : this.glassColor;
/*      */   }
/*      */   
/*      */   public boolean setPipeColor(int color) {
/*  457 */     if (!this.field_145850_b.field_72995_K && color >= -1 && color < 16 && this.glassColor != color) {
/*  458 */       this.glassColor = color;
/*  459 */       notifyBlockChanged();
/*  460 */       this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145854_h);
/*  461 */       return true;
/*      */     } 
/*  463 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void refreshRenderState() {
/*  470 */     this.renderState.setGlassColor((byte)this.glassColor);
/*      */ 
/*      */     
/*  473 */     for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
/*  474 */       this.renderState.pipeConnectionMatrix.setConnected(o, this.pipeConnectionsBuffer[o.ordinal()]);
/*      */     }
/*      */ 
/*      */     
/*  478 */     for (int i = 0; i < 7; i++) {
/*  479 */       ForgeDirection o = ForgeDirection.getOrientation(i);
/*  480 */       this.renderState.textureMatrix.setIconIndex(o, this.pipe.getIconIndex(o));
/*      */     } 
/*      */ 
/*      */     
/*  484 */     for (PipeWire color : PipeWire.values()) {
/*  485 */       this.renderState.wireMatrix.setWire(color, this.pipe.wireSet[color.ordinal()]);
/*      */       
/*  487 */       for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/*  488 */         this.renderState.wireMatrix.setWireConnected(color, direction, this.pipe.isWireConnectedTo(getTile(direction), color, direction));
/*      */       }
/*      */       
/*  491 */       boolean lit = (this.pipe.wireSignalStrength[color.ordinal()] > 0);
/*      */       
/*  493 */       switch (color) {
/*      */         case RED:
/*  495 */           this.renderState.wireMatrix.setWireIndex(color, lit ? 1 : 0);
/*      */           break;
/*      */         case BLUE:
/*  498 */           this.renderState.wireMatrix.setWireIndex(color, lit ? 3 : 2);
/*      */           break;
/*      */         case GREEN:
/*  501 */           this.renderState.wireMatrix.setWireIndex(color, lit ? 5 : 4);
/*      */           break;
/*      */         case YELLOW:
/*  504 */           this.renderState.wireMatrix.setWireIndex(color, lit ? 7 : 6);
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/*  514 */     this.pluggableState.setPluggables(this.sideProperties.pluggables);
/*      */     
/*  516 */     if (this.renderState.isDirty()) {
/*  517 */       this.renderState.clean();
/*      */     }
/*  519 */     sendUpdateToClient();
/*      */   }
/*      */   
/*      */   public void initialize(Pipe<?> pipe) {
/*  523 */     this.initialized = false;
/*      */     
/*  525 */     this.field_145854_h = func_145838_q();
/*      */     
/*  527 */     if (pipe == null) {
/*  528 */       BCLog.logger.log(Level.WARN, "Pipe failed to initialize at {0},{1},{2}, deleting", new Object[] { Integer.valueOf(this.field_145851_c), Integer.valueOf(this.field_145848_d), Integer.valueOf(this.field_145849_e) });
/*  529 */       this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */       
/*      */       return;
/*      */     } 
/*  533 */     this.pipe = pipe;
/*      */     
/*  535 */     for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
/*  536 */       TileEntity tile = getTile(o);
/*      */       
/*  538 */       if (tile instanceof ITileBufferHolder) {
/*  539 */         ((ITileBufferHolder)tile).blockCreated(o, (Block)BuildCraftTransport.genericPipeBlock, this);
/*      */       }
/*  541 */       if (tile instanceof IPipeTile) {
/*  542 */         ((IPipeTile)tile).scheduleNeighborChange();
/*      */       }
/*      */     } 
/*      */     
/*  546 */     bindPipe();
/*      */     
/*  548 */     computeConnections();
/*  549 */     scheduleNeighborChange();
/*  550 */     scheduleRenderUpdate();
/*      */     
/*  552 */     if (!pipe.isInitialized()) {
/*  553 */       pipe.initialize();
/*      */     }
/*      */     
/*  556 */     this.initialized = true;
/*      */   }
/*      */   
/*      */   private void bindPipe() {
/*  560 */     if (!this.pipeBound && this.pipe != null) {
/*  561 */       this.pipe.setTile(this);
/*  562 */       this.coreState.pipeId = Item.func_150891_b(this.pipe.item);
/*  563 */       this.pipeBound = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isInitialized() {
/*  568 */     return this.initialized;
/*      */   }
/*      */   
/*      */   public void scheduleNeighborChange() {
/*  572 */     this.blockNeighborChange = true;
/*  573 */     this.blockNeighborChangedSides = 63;
/*      */   }
/*      */   
/*      */   public void scheduleNeighborChange(ForgeDirection direction) {
/*  577 */     this.blockNeighborChange = true;
/*  578 */     this.blockNeighborChangedSides |= (direction == ForgeDirection.UNKNOWN) ? 63 : (1 << direction.ordinal());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canInjectItems(ForgeDirection from) {
/*  583 */     if (getPipeType() != IPipeTile.PipeType.ITEM) {
/*  584 */       return false;
/*      */     }
/*  586 */     return isPipeConnected(from);
/*      */   }
/*      */ 
/*      */   
/*      */   public int injectItem(ItemStack payload, boolean doAdd, ForgeDirection from, EnumColor color) {
/*  591 */     if (BlockGenericPipe.isValid(this.pipe) && this.pipe.transport instanceof PipeTransportItems && isPipeConnected(from) && this.pipe
/*  592 */       .inputOpen(from)) {
/*      */       
/*  594 */       if (doAdd) {
/*  595 */         Position itemPos = new Position(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.5D, from.getOpposite());
/*  596 */         itemPos.moveBackwards(0.4D);
/*      */         
/*  598 */         TravelingItem pipedItem = TravelingItem.make(itemPos.x, itemPos.y, itemPos.z, payload);
/*  599 */         if (pipedItem.isCorrupted()) {
/*  600 */           return 0;
/*      */         }
/*      */         
/*  603 */         pipedItem.color = color;
/*  604 */         ((PipeTransportItems)this.pipe.transport).injectItem(pipedItem, itemPos.orientation);
/*      */       } 
/*  606 */       return payload.field_77994_a;
/*      */     } 
/*      */     
/*  609 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int injectItem(ItemStack payload, boolean doAdd, ForgeDirection from) {
/*  614 */     return injectItem(payload, doAdd, from, (EnumColor)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public IPipeTile.PipeType getPipeType() {
/*  619 */     if (BlockGenericPipe.isValid(this.pipe)) {
/*  620 */       return this.pipe.transport.getPipeType();
/*      */     }
/*  622 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public int x() {
/*  627 */     return this.field_145851_c;
/*      */   }
/*      */ 
/*      */   
/*      */   public int y() {
/*  632 */     return this.field_145848_d;
/*      */   }
/*      */ 
/*      */   
/*      */   public int z() {
/*  637 */     return this.field_145849_e;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet getBCDescriptionPacket() {
/*  643 */     bindPipe();
/*  644 */     updateCoreState();
/*      */     
/*  646 */     PacketTileState packet = new PacketTileState(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */     
/*  648 */     if (this.pipe != null && this.pipe.transport != null) {
/*  649 */       this.pipe.transport.sendDescriptionPacket();
/*      */     }
/*      */     
/*  652 */     packet.addStateForSerialization((byte)0, this.coreState);
/*  653 */     packet.addStateForSerialization((byte)1, this.renderState);
/*  654 */     packet.addStateForSerialization((byte)2, this.pluggableState);
/*      */     
/*  656 */     if (this.pipe instanceof ISerializable) {
/*  657 */       packet.addStateForSerialization((byte)3, (ISerializable)this.pipe);
/*      */     }
/*      */     
/*  660 */     return (Packet)packet;
/*      */   }
/*      */ 
/*      */   
/*      */   public Packet func_145844_m() {
/*  665 */     return (Packet)Utils.toPacket(getBCDescriptionPacket(), 1);
/*      */   }
/*      */   
/*      */   public void sendUpdateToClient() {
/*  669 */     this.sendClientUpdate = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void blockRemoved(ForgeDirection from) {}
/*      */ 
/*      */   
/*      */   public TileBuffer[] getTileCache() {
/*  678 */     if (this.tileBuffer == null && this.pipe != null) {
/*  679 */       this.tileBuffer = TileBuffer.makeBuffer(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, this.pipe.transport.delveIntoUnloadedChunks());
/*      */     }
/*  681 */     return this.tileBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void blockCreated(ForgeDirection from, Block block, TileEntity tile) {
/*  686 */     TileBuffer[] cache = getTileCache();
/*  687 */     if (cache != null) {
/*  688 */       cache[from.getOpposite().ordinal()].set(block, tile);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Block getBlock(ForgeDirection to) {
/*  694 */     TileBuffer[] cache = getTileCache();
/*  695 */     if (cache != null) {
/*  696 */       return cache[to.ordinal()].getBlock();
/*      */     }
/*  698 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public TileEntity getTile(ForgeDirection to) {
/*  704 */     return getTile(to, false);
/*      */   }
/*      */   
/*      */   public TileEntity getTile(ForgeDirection to, boolean forceUpdate) {
/*  708 */     TileBuffer[] cache = getTileCache();
/*  709 */     if (cache != null) {
/*  710 */       return cache[to.ordinal()].getTile(forceUpdate);
/*      */     }
/*  712 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canPipeConnect_internal(TileEntity with, ForgeDirection side) {
/*  717 */     if ((!(this.pipe instanceof IPipeConnectionForced) || !((IPipeConnectionForced)this.pipe).ignoreConnectionOverrides(side)) && 
/*  718 */       with instanceof IPipeConnection) {
/*  719 */       IPipeConnection.ConnectOverride override = ((IPipeConnection)with).overridePipeConnection(this.pipe.transport.getPipeType(), side.getOpposite());
/*  720 */       if (override != IPipeConnection.ConnectOverride.DEFAULT) {
/*  721 */         return (override == IPipeConnection.ConnectOverride.CONNECT);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  726 */     if (with instanceof IPipeTile) {
/*  727 */       IPipeTile other = (IPipeTile)with;
/*      */       
/*  729 */       if (other.hasBlockingPluggable(side.getOpposite())) {
/*  730 */         return false;
/*      */       }
/*      */       
/*  733 */       if (other.getPipeColor() >= 0 && this.glassColor >= 0 && other.getPipeColor() != this.glassColor) {
/*  734 */         return false;
/*      */       }
/*      */       
/*  737 */       Pipe<?> otherPipe = (Pipe)other.getPipe();
/*      */       
/*  739 */       if (!BlockGenericPipe.isValid(otherPipe)) {
/*  740 */         return false;
/*      */       }
/*      */       
/*  743 */       if (!otherPipe.canPipeConnect(this, side.getOpposite())) {
/*  744 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  748 */     return this.pipe.canPipeConnect(with, side);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canPipeConnect(TileEntity with, ForgeDirection side) {
/*  759 */     if (with == null) {
/*  760 */       return false;
/*      */     }
/*      */     
/*  763 */     if (hasBlockingPluggable(side)) {
/*  764 */       return false;
/*      */     }
/*      */     
/*  767 */     if (!BlockGenericPipe.isValid(this.pipe)) {
/*  768 */       return false;
/*      */     }
/*      */     
/*  771 */     return canPipeConnect_internal(with, side);
/*      */   }
/*      */   
/*      */   public boolean hasBlockingPluggable(ForgeDirection side) {
/*  775 */     PipePluggable pluggable = getPipePluggable(side);
/*  776 */     if (pluggable == null) {
/*  777 */       return false;
/*      */     }
/*      */     
/*  780 */     if (pluggable instanceof IPipeConnection) {
/*  781 */       IPipe neighborPipe = getNeighborPipe(side);
/*  782 */       if (neighborPipe != null) {
/*  783 */         IPipeConnection.ConnectOverride override = ((IPipeConnection)pluggable).overridePipeConnection(neighborPipe.getTile().getPipeType(), side);
/*  784 */         if (override == IPipeConnection.ConnectOverride.CONNECT)
/*  785 */           return true; 
/*  786 */         if (override == IPipeConnection.ConnectOverride.DISCONNECT) {
/*  787 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*  791 */     return pluggable.isBlocking(this, side);
/*      */   }
/*      */   
/*      */   protected void computeConnections() {
/*  795 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/*  796 */       computeConnection(side);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void computeConnection(ForgeDirection side) {
/*  801 */     TileBuffer[] cache = getTileCache();
/*  802 */     if (cache == null) {
/*      */       return;
/*      */     }
/*      */     
/*  806 */     TileBuffer t = cache[side.ordinal()];
/*      */     
/*  808 */     if (t.exists() || !this.initialized) {
/*  809 */       t.refresh();
/*      */       
/*  811 */       this.pipeConnectionsBuffer[side.ordinal()] = canPipeConnect(t.getTile(), side);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPipeConnected(ForgeDirection with) {
/*  817 */     if (this.field_145850_b.field_72995_K) {
/*  818 */       return this.renderState.pipeConnectionMatrix.isConnected(with);
/*      */     }
/*  820 */     return this.pipeConnectionsBuffer[with.ordinal()];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doDrop() {
/*  826 */     if (BlockGenericPipe.isValid(this.pipe)) {
/*  827 */       return this.pipe.doDrop();
/*      */     }
/*  829 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkUnload() {
/*  835 */     if (this.pipe != null) {
/*  836 */       this.pipe.onChunkUnload();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/*  845 */     if (BlockGenericPipe.isValid(this.pipe) && this.pipe.transport instanceof IFluidHandler && !hasBlockingPluggable(from)) {
/*  846 */       return ((IFluidHandler)this.pipe.transport).fill(from, resource, doFill);
/*      */     }
/*  848 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/*  854 */     if (BlockGenericPipe.isValid(this.pipe) && this.pipe.transport instanceof IFluidHandler && !hasBlockingPluggable(from)) {
/*  855 */       return ((IFluidHandler)this.pipe.transport).drain(from, maxDrain, doDrain);
/*      */     }
/*  857 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/*  863 */     if (BlockGenericPipe.isValid(this.pipe) && this.pipe.transport instanceof IFluidHandler && !hasBlockingPluggable(from)) {
/*  864 */       return ((IFluidHandler)this.pipe.transport).drain(from, resource, doDrain);
/*      */     }
/*  866 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/*  872 */     if (BlockGenericPipe.isValid(this.pipe) && this.pipe.transport instanceof IFluidHandler && !hasBlockingPluggable(from)) {
/*  873 */       return ((IFluidHandler)this.pipe.transport).canFill(from, fluid);
/*      */     }
/*  875 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/*  881 */     if (BlockGenericPipe.isValid(this.pipe) && this.pipe.transport instanceof IFluidHandler && !hasBlockingPluggable(from)) {
/*  882 */       return ((IFluidHandler)this.pipe.transport).canDrain(from, fluid);
/*      */     }
/*  884 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/*  890 */     return null;
/*      */   }
/*      */   
/*      */   public void scheduleRenderUpdate() {
/*  894 */     this.refreshRenderState = true;
/*      */   }
/*      */   
/*      */   public boolean hasFacade(ForgeDirection direction) {
/*  898 */     if (direction == null || direction == ForgeDirection.UNKNOWN) {
/*  899 */       return false;
/*      */     }
/*  901 */     return this.sideProperties.pluggables[direction.ordinal()] instanceof buildcraft.api.transport.pluggable.IFacadePluggable;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasGate(ForgeDirection direction) {
/*  906 */     if (direction == null || direction == ForgeDirection.UNKNOWN) {
/*  907 */       return false;
/*      */     }
/*  909 */     return this.sideProperties.pluggables[direction.ordinal()] instanceof GatePluggable;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setPluggable(ForgeDirection direction, PipePluggable pluggable) {
/*  914 */     return setPluggable(direction, pluggable, (EntityPlayer)null);
/*      */   }
/*      */   
/*      */   public boolean setPluggable(ForgeDirection direction, PipePluggable pluggable, EntityPlayer player) {
/*  918 */     if (this.field_145850_b != null && this.field_145850_b.field_72995_K) {
/*  919 */       return false;
/*      */     }
/*      */     
/*  922 */     if (direction == null || direction == ForgeDirection.UNKNOWN) {
/*  923 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  927 */     if (this.sideProperties.pluggables[direction.ordinal()] != null) {
/*  928 */       this.sideProperties.dropItem(this, direction, player);
/*  929 */       this.pipe.eventBus.unregisterHandler(this.sideProperties.pluggables[direction.ordinal()]);
/*      */     } 
/*      */     
/*  932 */     this.sideProperties.pluggables[direction.ordinal()] = pluggable;
/*  933 */     if (pluggable != null) {
/*  934 */       this.pipe.eventBus.registerHandler(pluggable);
/*  935 */       pluggable.onAttachedPipe(this, direction);
/*      */     } 
/*  937 */     notifyBlockChanged();
/*  938 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateCoreState() {}
/*      */   
/*      */   public boolean hasEnabledFacade(ForgeDirection direction) {
/*  945 */     return (hasFacade(direction) && !((FacadePluggable)getPipePluggable(direction)).isTransparent());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setGate(Gate gate, int direction) {
/*  950 */     if (this.sideProperties.pluggables[direction] == null) {
/*  951 */       gate.setDirection(ForgeDirection.getOrientation(direction));
/*  952 */       this.pipe.gates[direction] = gate;
/*  953 */       this.sideProperties.pluggables[direction] = (PipePluggable)new GatePluggable(gate);
/*      */     } 
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public IIconProvider getPipeIcons() {
/*  959 */     if (this.pipe == null) {
/*  960 */       return null;
/*      */     }
/*  962 */     return this.pipe.getIconProvider();
/*      */   }
/*      */ 
/*      */   
/*      */   public ISerializable getStateInstance(byte stateId) {
/*  967 */     switch (stateId) {
/*      */       case 0:
/*  969 */         return this.coreState;
/*      */       case 1:
/*  971 */         return this.renderState;
/*      */       case 2:
/*  973 */         return this.pluggableState;
/*      */       case 3:
/*  975 */         return (ISerializable)this.pipe;
/*      */     } 
/*  977 */     throw new RuntimeException("Unknown state requested: " + stateId + " this is a bug!");
/*      */   }
/*      */   public void afterStateUpdated(byte stateId) {
/*      */     PipePluggable[] newPluggables;
/*      */     int i;
/*  982 */     if (!this.field_145850_b.field_72995_K) {
/*      */       return;
/*      */     }
/*      */     
/*  986 */     switch (stateId) {
/*      */       case 0:
/*  988 */         if (this.pipe != null) {
/*      */           break;
/*      */         }
/*      */         
/*  992 */         if (this.coreState.pipeId != 0) {
/*  993 */           initialize(BlockGenericPipe.createPipe((Item)Item.field_150901_e.func_148754_a(this.coreState.pipeId)));
/*      */         }
/*      */         
/*  996 */         if (this.pipe == null) {
/*      */           break;
/*      */         }
/*      */         
/* 1000 */         this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1004 */         if (this.renderState.needsRenderUpdate()) {
/* 1005 */           this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 1006 */           this.renderState.clean();
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 2:
/* 1011 */         newPluggables = this.pluggableState.getPluggables();
/*      */ 
/*      */         
/* 1014 */         for (i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/* 1015 */           PipePluggable old = this.sideProperties.pluggables[i];
/* 1016 */           PipePluggable newer = newPluggables[i];
/* 1017 */           if (old != null || newer != null)
/*      */           {
/* 1019 */             if (old != null && newer != null && old.getClass() == newer.getClass()) {
/* 1020 */               if (newer.requiresRenderUpdate(old)) {
/* 1021 */                 this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } else {
/* 1026 */               this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*      */               break;
/*      */             }  } 
/*      */         } 
/* 1030 */         this.sideProperties.pluggables = (PipePluggable[])newPluggables.clone();
/*      */         
/* 1032 */         for (i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/* 1033 */           PipePluggable pluggable = getPipePluggable(ForgeDirection.getOrientation(i));
/* 1034 */           if (pluggable != null && pluggable instanceof GatePluggable) {
/* 1035 */             GatePluggable gatePluggable = (GatePluggable)pluggable;
/* 1036 */             Gate gate = this.pipe.gates[i];
/* 1037 */             if (gate == null || gate.logic != gatePluggable.getLogic() || gate.material != gatePluggable.getMaterial()) {
/* 1038 */               this.pipe.gates[i] = GateFactory.makeGate(this.pipe, gatePluggable.getMaterial(), gatePluggable.getLogic(), ForgeDirection.getOrientation(i));
/*      */             }
/*      */           } else {
/* 1041 */             this.pipe.gates[i] = null;
/*      */           } 
/*      */         } 
/*      */         
/* 1045 */         syncGateExpansions();
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void syncGateExpansions() {
/* 1052 */     this.resyncGateExpansions = false;
/* 1053 */     for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/* 1054 */       Gate gate = this.pipe.gates[i];
/* 1055 */       if (gate != null) {
/*      */ 
/*      */         
/* 1058 */         GatePluggable gatePluggable = (GatePluggable)this.sideProperties.pluggables[i];
/* 1059 */         if ((gatePluggable.getExpansions()).length > 0) {
/* 1060 */           for (IGateExpansion expansion : gatePluggable.getExpansions()) {
/* 1061 */             if (expansion != null) {
/* 1062 */               if (!gate.expansions.containsKey(expansion)) {
/* 1063 */                 gate.addGateExpansion(expansion);
/*      */               }
/*      */             } else {
/* 1066 */               this.resyncGateExpansions = true;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public double func_145833_n() {
/* 1076 */     return (DefaultProps.PIPE_CONTENTS_RENDER_DIST * DefaultProps.PIPE_CONTENTS_RENDER_DIST);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
/* 1081 */     return (oldBlock != newBlock);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSolidOnSide(ForgeDirection side) {
/* 1086 */     if (hasPipePluggable(side) && getPipePluggable(side).isSolidOnSide(this, side)) {
/* 1087 */       return true;
/*      */     }
/*      */     
/* 1090 */     if (BlockGenericPipe.isValid(this.pipe) && this.pipe instanceof ISolidSideTile && (
/* 1091 */       (ISolidSideTile)this.pipe).isSolidOnSide(side)) {
/* 1092 */       return true;
/*      */     }
/*      */     
/* 1095 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public PipePluggable getPipePluggable(ForgeDirection side) {
/* 1100 */     if (side == null || side == ForgeDirection.UNKNOWN) {
/* 1101 */       return null;
/*      */     }
/*      */     
/* 1104 */     return this.sideProperties.pluggables[side.ordinal()];
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasPipePluggable(ForgeDirection side) {
/* 1109 */     if (side == null || side == ForgeDirection.UNKNOWN) {
/* 1110 */       return false;
/*      */     }
/*      */     
/* 1113 */     return (this.sideProperties.pluggables[side.ordinal()] != null);
/*      */   }
/*      */   
/*      */   public Block getBlock() {
/* 1117 */     return func_145838_q();
/*      */   }
/*      */ 
/*      */   
/*      */   public World getWorld() {
/* 1122 */     return this.field_145850_b;
/*      */   }
/*      */   
/*      */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 1126 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeGuiData(ByteBuf data) {
/* 1131 */     if (BlockGenericPipe.isValid(this.pipe) && this.pipe instanceof IGuiReturnHandler) {
/* 1132 */       ((IGuiReturnHandler)this.pipe).writeGuiData(data);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void readGuiData(ByteBuf data, EntityPlayer sender) {
/* 1138 */     if (BlockGenericPipe.isValid(this.pipe) && this.pipe instanceof IGuiReturnHandler) {
/* 1139 */       ((IGuiReturnHandler)this.pipe).readGuiData(data, sender);
/*      */     }
/*      */   }
/*      */   
/*      */   private IEnergyHandler internalGetEnergyHandler(ForgeDirection side) {
/* 1144 */     if (hasPipePluggable(side)) {
/* 1145 */       PipePluggable pluggable = getPipePluggable(side);
/* 1146 */       if (pluggable instanceof IEnergyHandler)
/* 1147 */         return (IEnergyHandler)pluggable; 
/* 1148 */       if (pluggable.isBlocking(this, side)) {
/* 1149 */         return null;
/*      */       }
/*      */     } 
/* 1152 */     if (this.pipe instanceof IEnergyHandler) {
/* 1153 */       return (IEnergyHandler)this.pipe;
/*      */     }
/* 1155 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canConnectEnergy(ForgeDirection from) {
/* 1160 */     IEnergyHandler handler = internalGetEnergyHandler(from);
/* 1161 */     if (handler != null) {
/* 1162 */       return handler.canConnectEnergy(from);
/*      */     }
/* 1164 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
/* 1171 */     IEnergyHandler handler = internalGetEnergyHandler(from);
/* 1172 */     if (handler != null) {
/* 1173 */       return handler.receiveEnergy(from, maxReceive, simulate);
/*      */     }
/* 1175 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
/* 1182 */     IEnergyHandler handler = internalGetEnergyHandler(from);
/* 1183 */     if (handler != null) {
/* 1184 */       return handler.extractEnergy(from, maxExtract, simulate);
/*      */     }
/* 1186 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEnergyStored(ForgeDirection from) {
/* 1192 */     IEnergyHandler handler = internalGetEnergyHandler(from);
/* 1193 */     if (handler != null) {
/* 1194 */       return handler.getEnergyStored(from);
/*      */     }
/* 1196 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxEnergyStored(ForgeDirection from) {
/* 1202 */     IEnergyHandler handler = internalGetEnergyHandler(from);
/* 1203 */     if (handler != null) {
/* 1204 */       return handler.getMaxEnergyStored(from);
/*      */     }
/* 1206 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block getNeighborBlock(ForgeDirection dir) {
/* 1212 */     return getBlock(dir);
/*      */   }
/*      */ 
/*      */   
/*      */   public TileEntity getNeighborTile(ForgeDirection dir) {
/* 1217 */     return getTile(dir);
/*      */   }
/*      */ 
/*      */   
/*      */   public IPipe getNeighborPipe(ForgeDirection dir) {
/* 1222 */     TileEntity neighborTile = getTile(dir);
/* 1223 */     if (neighborTile instanceof IPipeTile) {
/* 1224 */       return ((IPipeTile)neighborTile).getPipe();
/*      */     }
/* 1226 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IPipe getPipe() {
/* 1232 */     return this.pipe;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canConnectRedstoneEngine(ForgeDirection side) {
/* 1237 */     if (this.pipe instanceof IRedstoneEngineReceiver) {
/* 1238 */       return ((IRedstoneEngineReceiver)this.pipe).canConnectRedstoneEngine(side);
/*      */     }
/* 1240 */     return (getPipeType() != IPipeTile.PipeType.POWER && getPipeType() != IPipeTile.PipeType.STRUCTURE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void getDebugInfo(List<String> info, ForgeDirection side, ItemStack debugger, EntityPlayer player) {
/* 1246 */     if (this.pipe instanceof IDebuggable) {
/* 1247 */       ((IDebuggable)this.pipe).getDebugInfo(info, side, debugger, player);
/*      */     }
/* 1249 */     if (this.pipe.transport instanceof IDebuggable) {
/* 1250 */       ((IDebuggable)this.pipe.transport).getDebugInfo(info, side, debugger, player);
/*      */     }
/* 1252 */     if (getPipePluggable(side) != null && getPipePluggable(side) instanceof IDebuggable) {
/* 1253 */       ((IDebuggable)getPipePluggable(side)).getDebugInfo(info, side, debugger, player);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IPipeConnection.ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
/* 1259 */     if (type == IPipeTile.PipeType.POWER && hasPipePluggable(with) && getPipePluggable(with) instanceof IEnergyHandler) {
/* 1260 */       return IPipeConnection.ConnectOverride.CONNECT;
/*      */     }
/* 1262 */     return IPipeConnection.ConnectOverride.DEFAULT;
/*      */   }
/*      */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\TileGenericPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */