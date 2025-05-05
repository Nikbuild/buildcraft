/*     */ package buildcraft.transport.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementManager;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.transport.ActionActiveState;
/*     */ import buildcraft.transport.Gate;
/*     */ import buildcraft.transport.gates.GateDefinition;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NavigableSet;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public class ContainerGateInterface
/*     */   extends BuildCraftContainer
/*     */   implements ICommandReceiver
/*     */ {
/*  46 */   public ActionActiveState[] actionsState = new ActionActiveState[8];
/*     */   public GuiGateInterface gateCallback;
/*     */   IInventory playerIInventory;
/*     */   private final IPipe pipe;
/*     */   private Gate gate;
/*     */   
/*  52 */   private final NavigableSet<IStatement> potentialTriggers = new TreeSet<IStatement>(new Comparator<IStatement>()
/*     */       {
/*     */         public int compare(IStatement o1, IStatement o2) {
/*  55 */           return o1.getUniqueTag().compareTo(o2.getUniqueTag());
/*     */         }
/*     */       });
/*     */   
/*  59 */   private final NavigableSet<IStatement> potentialActions = new TreeSet<IStatement>(new Comparator<IStatement>()
/*     */       {
/*     */         public int compare(IStatement o1, IStatement o2) {
/*  62 */           return o1.getUniqueTag().compareTo(o2.getUniqueTag());
/*     */         }
/*     */       });
/*     */   
/*     */   private boolean isSynchronized = false;
/*     */   private boolean isNetInitialized = false;
/*  68 */   private int lastTriggerState = 0;
/*     */   
/*     */   public ContainerGateInterface(IInventory playerInventory, IPipe pipe) {
/*  71 */     super(0);
/*     */     
/*  73 */     for (int i = 0; i < this.actionsState.length; i++) {
/*  74 */       this.actionsState[i] = ActionActiveState.Deactivated;
/*     */     }
/*     */     
/*  77 */     this.pipe = pipe;
/*  78 */     this.playerIInventory = playerInventory;
/*     */     
/*  80 */     for (int y = 0; y < 3; y++) {
/*  81 */       for (int j = 0; j < 9; j++) {
/*  82 */         func_75146_a(new Slot(this.playerIInventory, j + y * 9 + 9, 8 + j * 18, 0));
/*     */       }
/*     */     } 
/*     */     
/*  86 */     for (int x = 0; x < 9; x++) {
/*  87 */       func_75146_a(new Slot(this.playerIInventory, x, 8 + x * 18, 0));
/*     */     }
/*     */   }
/*     */   
/*     */   public void init() {
/*  92 */     if (this.gate == null) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     for (int y = 0; y < 3; y++) {
/*  97 */       for (int i = 0; i < 9; i++) {
/*  98 */         (func_75139_a(i + y * 9)).field_75221_f = this.gate.material.guiHeight - 84 + y * 18;
/*     */       }
/*     */     } 
/*     */     
/* 102 */     for (int x = 0; x < 9; x++) {
/* 103 */       (func_75139_a(x + 27)).field_75221_f = this.gate.material.guiHeight - 26;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 108 */     if (!(this.pipe.getTile().getWorld()).field_72995_K) {
/* 109 */       this.potentialTriggers.addAll(this.gate.getAllValidTriggers());
/* 110 */       this.potentialActions.addAll(this.gate.getAllValidActions());
/*     */       
/* 112 */       Iterator<IStatement> it = this.potentialTriggers.iterator();
/*     */       
/* 114 */       while (it.hasNext()) {
/* 115 */         IStatement trigger = it.next();
/*     */         
/* 117 */         if (trigger.minParameters() > this.gate.material.numTriggerParameters) {
/* 118 */           it.remove();
/*     */         }
/*     */       } 
/*     */       
/* 122 */       it = this.potentialActions.iterator();
/*     */       
/* 124 */       while (it.hasNext()) {
/* 125 */         IStatement action = it.next();
/*     */         
/* 127 */         if (action.minParameters() > this.gate.material.numActionParameters) {
/* 128 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/* 132 */     if (this.gateCallback != null) {
/* 133 */       this.gateCallback.setGate(this.gate);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String[] statementsToStrings(Collection<IStatement> statements) {
/* 138 */     int size = statements.size();
/* 139 */     String[] array = new String[size];
/* 140 */     int pos = 0;
/* 141 */     for (IStatement statement : statements) {
/* 142 */       array[pos++] = statement.getUniqueTag();
/*     */     }
/* 144 */     return array;
/*     */   }
/*     */   
/*     */   private static void stringsToStatements(Collection<IStatement> statements, String[] strings) {
/* 148 */     statements.clear();
/* 149 */     for (String id : strings) {
/* 150 */       statements.add((IStatement)StatementManager.statements.get(id));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/* 156 */     return (this.gate != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 167 */     this.isSynchronized = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void synchronize() {
/* 175 */     if (!this.isNetInitialized && (this.pipe.getTile().getWorld()).field_72995_K) {
/* 176 */       this.isNetInitialized = true;
/* 177 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "initRequest", null));
/*     */     } 
/*     */     
/* 180 */     if (!this.isSynchronized && (this.pipe.getTile().getWorld()).field_72995_K && this.gate != null) {
/* 181 */       this.isSynchronized = true;
/* 182 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "selectionRequest", null));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75137_b(int id, int state) {
/* 188 */     if (id == 0) {
/* 189 */       for (int i = 0; i < 8; i++)
/*     */       {
/* 191 */         this.actionsState[i] = ActionActiveState.values()[state >> i * 2 & 0x3];
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calculateTriggerState() {
/* 200 */     if (this.gate == null) {
/* 201 */       return 0;
/*     */     }
/*     */     
/* 204 */     int state = 0;
/*     */     
/* 206 */     for (int i = 0; i < this.actionsState.length; i++) {
/* 207 */       this.actionsState[i] = getActionState(i);
/* 208 */       state |= (this.actionsState[i].ordinal() & 0x3) << i * 2;
/*     */     } 
/*     */     
/* 211 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75142_b() {
/* 216 */     super.func_75142_b();
/*     */     
/* 218 */     int state = calculateTriggerState();
/*     */     
/* 220 */     if (state != this.lastTriggerState) {
/* 221 */       for (Object crafter : this.field_75149_d) {
/* 222 */         ICrafting viewingPlayer = (ICrafting)crafter;
/*     */         
/* 224 */         viewingPlayer.func_71112_a((Container)this, 0, state);
/*     */       } 
/*     */       
/* 227 */       this.lastTriggerState = state;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTriggers() {
/* 236 */     return (this.potentialTriggers.size() > 0);
/*     */   }
/*     */   
/*     */   public IStatement getFirstTrigger() {
/* 240 */     if (this.potentialTriggers.isEmpty()) {
/* 241 */       return null;
/*     */     }
/* 243 */     return this.potentialTriggers.first();
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatement getLastTrigger() {
/* 248 */     if (this.potentialTriggers.isEmpty()) {
/* 249 */       return null;
/*     */     }
/* 251 */     return this.potentialTriggers.last();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<IStatement> getTriggerIterator(boolean descending) {
/* 256 */     return descending ? this.potentialTriggers.descendingIterator() : this.potentialTriggers.iterator();
/*     */   }
/*     */   
/*     */   public ActionActiveState getActionState(int i) {
/* 260 */     if (this.gate == null) {
/* 261 */       return ActionActiveState.Deactivated;
/*     */     }
/* 263 */     return this.gate.actionsState[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter getTriggerParameter(int trigger, int param) {
/* 268 */     if (this.gate == null) {
/* 269 */       return null;
/*     */     }
/* 271 */     return this.gate.getTriggerParameter(trigger, param);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasActions() {
/* 279 */     return !this.potentialActions.isEmpty();
/*     */   }
/*     */   
/*     */   public IStatement getFirstAction() {
/* 283 */     if (this.potentialActions.isEmpty()) {
/* 284 */       return null;
/*     */     }
/* 286 */     return this.potentialActions.first();
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatement getLastAction() {
/* 291 */     if (this.potentialActions.isEmpty()) {
/* 292 */       return null;
/*     */     }
/* 294 */     return this.potentialActions.last();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<IStatement> getActionIterator(boolean descending) {
/* 299 */     return descending ? this.potentialActions.descendingIterator() : this.potentialActions.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet getStatementPacket(String name, final int slot, IStatement statement) {
/* 304 */     final String statementKind = (statement != null) ? statement.getUniqueTag() : null;
/* 305 */     return (Packet)new PacketCommand(this, name, new CommandWriter() {
/*     */           public void write(ByteBuf data) {
/* 307 */             data.writeByte(slot);
/* 308 */             NetworkUtils.writeUTF(data, statementKind);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet getStatementParameterPacket(String name, final int slot, final int paramSlot, IStatementParameter parameter) {
/* 315 */     final String parameterName = (parameter != null) ? parameter.getUniqueTag() : null;
/* 316 */     final NBTTagCompound parameterNBT = new NBTTagCompound();
/* 317 */     if (parameter != null) {
/* 318 */       parameter.writeToNBT(parameterNBT);
/*     */     }
/* 320 */     return (Packet)new PacketCommand(this, name, new CommandWriter() {
/*     */           public void write(ByteBuf data) {
/* 322 */             data.writeByte(slot);
/* 323 */             data.writeByte(paramSlot);
/* 324 */             NetworkUtils.writeUTF(data, parameterName);
/* 325 */             NetworkUtils.writeNBT(data, parameterNBT);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void setGate(int direction) {
/* 331 */     this.gate = (Gate)this.pipe.getGate(ForgeDirection.getOrientation(direction));
/* 332 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 337 */     if (side.isServer()) {
/* 338 */       EntityPlayer player = (EntityPlayer)sender;
/* 339 */       if ("initRequest".equals(command)) {
/* 340 */         final String[] triggerStrings = statementsToStrings(this.potentialTriggers);
/* 341 */         final String[] actionStrings = statementsToStrings(this.potentialActions);
/*     */         
/* 343 */         BuildCraftCore.instance.sendToPlayer(player, (Packet)new PacketCommand(this, "init", new CommandWriter() {
/*     */                 public void write(ByteBuf data) {
/* 345 */                   data.writeByte(ContainerGateInterface.this.gate.getDirection().ordinal());
/* 346 */                   data.writeShort(triggerStrings.length);
/* 347 */                   data.writeShort(actionStrings.length);
/* 348 */                   for (String trigger : triggerStrings) {
/* 349 */                     NetworkUtils.writeUTF(data, trigger);
/*     */                   }
/* 351 */                   for (String action : actionStrings) {
/* 352 */                     NetworkUtils.writeUTF(data, action);
/*     */                   }
/*     */                 }
/*     */               }));
/* 356 */       } else if ("selectionRequest".equals(command)) {
/* 357 */         for (int position = 0; position < this.gate.material.numSlots; position++) {
/* 358 */           IStatement action = this.gate.getAction(position);
/* 359 */           IStatement trigger = this.gate.getTrigger(position);
/* 360 */           BuildCraftCore.instance.sendToPlayer(player, getStatementPacket("setAction", position, action));
/* 361 */           BuildCraftCore.instance.sendToPlayer(player, getStatementPacket("setTrigger", position, trigger));
/* 362 */           for (int p = 0; p < this.gate.material.numActionParameters; p++) {
/* 363 */             BuildCraftCore.instance.sendToPlayer(player, getStatementParameterPacket("setActionParameter", position, p, this.gate
/* 364 */                   .getActionParameter(position, p)));
/*     */           }
/* 366 */           for (int q = 0; q < this.gate.material.numTriggerParameters; q++) {
/* 367 */             BuildCraftCore.instance.sendToPlayer(player, getStatementParameterPacket("setTriggerParameter", position, q, this.gate
/* 368 */                   .getTriggerParameter(position, q)));
/*     */           }
/*     */         } 
/*     */       } 
/* 372 */     } else if (side.isClient() && 
/* 373 */       "init".equals(command)) {
/* 374 */       setGate(stream.readByte());
/* 375 */       final String[] triggerStrings = new String[stream.readShort()];
/* 376 */       final String[] actionStrings = new String[stream.readShort()]; int i;
/* 377 */       for (i = 0; i < triggerStrings.length; i++) {
/* 378 */         triggerStrings[i] = NetworkUtils.readUTF(stream);
/*     */       }
/* 380 */       for (i = 0; i < actionStrings.length; i++) {
/* 381 */         actionStrings[i] = NetworkUtils.readUTF(stream);
/*     */       }
/*     */       
/* 384 */       stringsToStatements(this.potentialTriggers, triggerStrings);
/* 385 */       stringsToStatements(this.potentialActions, actionStrings);
/*     */     } 
/*     */ 
/*     */     
/* 389 */     if ("setAction".equals(command)) {
/* 390 */       setAction(stream.readUnsignedByte(), NetworkUtils.readUTF(stream), false);
/* 391 */     } else if ("setTrigger".equals(command)) {
/* 392 */       setTrigger(stream.readUnsignedByte(), NetworkUtils.readUTF(stream), false);
/* 393 */     } else if ("setActionParameter".equals(command) || "setTriggerParameter".equals(command)) {
/* 394 */       int slot = stream.readUnsignedByte();
/* 395 */       int param = stream.readUnsignedByte();
/* 396 */       String parameterName = NetworkUtils.readUTF(stream);
/* 397 */       NBTTagCompound parameterData = NetworkUtils.readNBT(stream);
/* 398 */       IStatementParameter parameter = null;
/* 399 */       if (parameterName != null && parameterName.length() > 0) {
/* 400 */         parameter = StatementManager.createParameter(parameterName);
/*     */       }
/*     */       
/* 403 */       if (parameter != null) {
/* 404 */         parameter.readFromNBT(parameterData);
/* 405 */         if ("setActionParameter".equals(command)) {
/* 406 */           setActionParameter(slot, param, parameter, false);
/*     */         } else {
/* 408 */           setTriggerParameter(slot, param, parameter, false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setAction(int action, String tag, boolean notifyServer) {
/* 415 */     if (this.gate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 419 */     IStatement statement = null;
/*     */     
/* 421 */     if (tag != null && tag.length() > 0) {
/* 422 */       statement = (IStatement)StatementManager.statements.get(tag);
/*     */     }
/* 424 */     this.gate.setAction(action, statement);
/*     */     
/* 426 */     if ((this.pipe.getTile().getWorld()).field_72995_K && notifyServer) {
/* 427 */       BuildCraftCore.instance.sendToServer(getStatementPacket("setAction", action, statement));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTrigger(int trigger, String tag, boolean notifyServer) {
/* 432 */     if (this.gate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 436 */     IStatement statement = null;
/*     */     
/* 438 */     if (tag != null && tag.length() > 0) {
/* 439 */       statement = (IStatement)StatementManager.statements.get(tag);
/*     */     }
/* 441 */     this.gate.setTrigger(trigger, statement);
/*     */     
/* 443 */     if ((this.pipe.getTile().getWorld()).field_72995_K && notifyServer) {
/* 444 */       BuildCraftCore.instance.sendToServer(getStatementPacket("setTrigger", trigger, statement));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setActionParameter(int action, int param, IStatementParameter parameter, boolean notifyServer) {
/* 449 */     if (this.gate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 453 */     this.gate.setActionParameter(action, param, parameter);
/*     */     
/* 455 */     if ((this.pipe.getTile().getWorld()).field_72995_K && notifyServer) {
/* 456 */       BuildCraftCore.instance.sendToServer(getStatementParameterPacket("setActionParameter", action, param, parameter));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTriggerParameter(int trigger, int param, IStatementParameter parameter, boolean notifyServer) {
/* 461 */     if (this.gate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 465 */     this.gate.setTriggerParameter(trigger, param, parameter);
/*     */     
/* 467 */     if ((this.pipe.getTile().getWorld()).field_72995_K && notifyServer) {
/* 468 */       BuildCraftCore.instance.sendToServer(getStatementParameterPacket("setTriggerParameter", trigger, param, parameter));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getGateGuiFile() {
/* 476 */     return this.gate.material.guiFile;
/*     */   }
/*     */   
/*     */   public String getGateName() {
/* 480 */     return GateDefinition.getLocalizedName(this.gate.material, this.gate.logic);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\ContainerGateInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */