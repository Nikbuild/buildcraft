/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.gates.GateExpansionController;
/*     */ import buildcraft.api.gates.IGate;
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.api.statements.IActionExternal;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.IActionReceptor;
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.ITriggerExternal;
/*     */ import buildcraft.api.statements.ITriggerExternalOverride;
/*     */ import buildcraft.api.statements.ITriggerInternal;
/*     */ import buildcraft.api.statements.StatementManager;
/*     */ import buildcraft.api.statements.StatementParameterItemStack;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.statements.containers.IRedstoneStatementContainer;
/*     */ import buildcraft.api.statements.containers.ISidedStatementContainer;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.PipeWire;
/*     */ import buildcraft.transport.gates.GateDefinition;
/*     */ import buildcraft.transport.gates.ItemGate;
/*     */ import buildcraft.transport.gui.ContainerGateInterface;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.google.common.collect.HashMultiset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ 
/*     */ public final class Gate
/*     */   implements IGate, ISidedStatementContainer, IRedstoneStatementContainer
/*     */ {
/*  55 */   public static int MAX_STATEMENTS = 8;
/*  56 */   public static int MAX_PARAMETERS = 3;
/*     */   
/*     */   public final Pipe<?> pipe;
/*     */   public final GateDefinition.GateMaterial material;
/*     */   public final GateDefinition.GateLogic logic;
/*  61 */   public final BiMap<IGateExpansion, GateExpansionController> expansions = (BiMap<IGateExpansion, GateExpansionController>)HashBiMap.create();
/*     */   
/*  63 */   public IStatement[] triggers = new IStatement[MAX_STATEMENTS];
/*  64 */   public IStatementParameter[][] triggerParameters = new IStatementParameter[MAX_STATEMENTS][MAX_PARAMETERS];
/*     */   
/*  66 */   public IStatement[] actions = new IStatement[MAX_STATEMENTS];
/*  67 */   public IStatementParameter[][] actionParameters = new IStatementParameter[MAX_STATEMENTS][MAX_PARAMETERS];
/*     */   
/*  69 */   public ActionActiveState[] actionsState = new ActionActiveState[MAX_STATEMENTS];
/*  70 */   public ArrayList<StatementSlot> activeActions = new ArrayList<StatementSlot>();
/*     */   public byte broadcastSignal;
/*     */   public byte prevBroadcastSignal;
/*  73 */   public int redstoneOutput = 0;
/*  74 */   public int redstoneOutputSide = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPulsing = false;
/*     */ 
/*     */   
/*     */   private ForgeDirection direction;
/*     */ 
/*     */   
/*  84 */   private HashMultiset<IStatement> statementCounts = HashMultiset.create();
/*  85 */   private int[] actionGroups = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
/*     */ 
/*     */   
/*     */   public Gate(Pipe<?> pipe, GateDefinition.GateMaterial material, GateDefinition.GateLogic logic, ForgeDirection direction) {
/*  89 */     this.pipe = pipe;
/*  90 */     this.material = material;
/*  91 */     this.logic = logic;
/*  92 */     this.direction = direction;
/*     */     
/*  94 */     for (int i = 0; i < this.actionsState.length; i++) {
/*  95 */       this.actionsState[i] = ActionActiveState.Deactivated;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTrigger(int position, IStatement trigger) {
/* 100 */     if (trigger != this.triggers[position]) {
/* 101 */       for (int i = 0; i < (this.triggerParameters[position]).length; i++) {
/* 102 */         this.triggerParameters[position][i] = null;
/*     */       }
/*     */     }
/* 105 */     this.triggers[position] = trigger;
/*     */   }
/*     */   
/*     */   public IStatement getTrigger(int position) {
/* 109 */     return this.triggers[position];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAction(int position, IStatement action) {
/* 115 */     if (this.actions[position] instanceof buildcraft.transport.statements.ActionValve && this.pipe != null && this.pipe.transport != null) {
/* 116 */       for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 117 */         this.pipe.transport.allowInput(side, true);
/* 118 */         this.pipe.transport.allowOutput(side, true);
/*     */       } 
/*     */     }
/*     */     
/* 122 */     if (action != this.actions[position]) {
/* 123 */       for (int i = 0; i < (this.actionParameters[position]).length; i++) {
/* 124 */         this.actionParameters[position][i] = null;
/*     */       }
/*     */     }
/*     */     
/* 128 */     this.actions[position] = action;
/*     */     
/* 130 */     recalculateActionGroups();
/*     */   }
/*     */   
/*     */   public IStatement getAction(int position) {
/* 134 */     return this.actions[position];
/*     */   }
/*     */   
/*     */   public void setTriggerParameter(int trigger, int param, IStatementParameter p) {
/* 138 */     this.triggerParameters[trigger][param] = p;
/*     */   }
/*     */   
/*     */   public void setActionParameter(int action, int param, IStatementParameter p) {
/* 142 */     this.actionParameters[action][param] = p;
/*     */     
/* 144 */     recalculateActionGroups();
/*     */   }
/*     */   
/*     */   public IStatementParameter getTriggerParameter(int trigger, int param) {
/* 148 */     return this.triggerParameters[trigger][param];
/*     */   }
/*     */   
/*     */   public IStatementParameter getActionParameter(int action, int param) {
/* 152 */     return this.actionParameters[action][param];
/*     */   }
/*     */   
/*     */   public ForgeDirection getDirection() {
/* 156 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setDirection(ForgeDirection direction) {
/* 160 */     this.direction = direction;
/*     */   }
/*     */   
/*     */   public void addGateExpansion(IGateExpansion expansion) {
/* 164 */     if (!this.expansions.containsKey(expansion)) {
/* 165 */       this.expansions.put(expansion, expansion.makeController((this.pipe != null) ? this.pipe.container : null));
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStatementsToNBT(NBTTagCompound data) {
/* 170 */     for (int i = 0; i < this.material.numSlots; i++) {
/* 171 */       if (this.triggers[i] != null) {
/* 172 */         data.func_74778_a("trigger[" + i + "]", this.triggers[i].getUniqueTag());
/*     */       }
/*     */       
/* 175 */       if (this.actions[i] != null) {
/* 176 */         data.func_74778_a("action[" + i + "]", this.actions[i].getUniqueTag());
/*     */       }
/*     */       int j;
/* 179 */       for (j = 0; j < this.material.numTriggerParameters; j++) {
/* 180 */         if (this.triggerParameters[i][j] != null) {
/* 181 */           NBTTagCompound cpt = new NBTTagCompound();
/* 182 */           cpt.func_74778_a("kind", this.triggerParameters[i][j].getUniqueTag());
/* 183 */           this.triggerParameters[i][j].writeToNBT(cpt);
/* 184 */           data.func_74782_a("triggerParameters[" + i + "][" + j + "]", (NBTBase)cpt);
/*     */         } 
/*     */       } 
/*     */       
/* 188 */       for (j = 0; j < this.material.numActionParameters; j++) {
/* 189 */         if (this.actionParameters[i][j] != null) {
/* 190 */           NBTTagCompound cpt = new NBTTagCompound();
/* 191 */           cpt.func_74778_a("kind", this.actionParameters[i][j].getUniqueTag());
/* 192 */           this.actionParameters[i][j].writeToNBT(cpt);
/* 193 */           data.func_74782_a("actionParameters[" + i + "][" + j + "]", (NBTBase)cpt);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound data) {
/* 201 */     data.func_74778_a("material", this.material.name());
/* 202 */     data.func_74778_a("logic", this.logic.name());
/* 203 */     data.func_74768_a("direction", this.direction.ordinal());
/* 204 */     NBTTagList exList = new NBTTagList();
/* 205 */     for (GateExpansionController con : this.expansions.values()) {
/* 206 */       NBTTagCompound conNBT = new NBTTagCompound();
/* 207 */       conNBT.func_74778_a("type", con.getType().getUniqueIdentifier());
/* 208 */       NBTTagCompound conData = new NBTTagCompound();
/* 209 */       con.writeToNBT(conData);
/* 210 */       conNBT.func_74782_a("data", (NBTBase)conData);
/* 211 */       exList.func_74742_a((NBTBase)conNBT);
/*     */     } 
/* 213 */     data.func_74782_a("expansions", (NBTBase)exList);
/*     */     
/* 215 */     writeStatementsToNBT(data);
/*     */     
/* 217 */     data.func_74774_a("wireState", this.broadcastSignal);
/*     */     
/* 219 */     data.func_74774_a("redstoneOutput", (byte)this.redstoneOutput);
/*     */   }
/*     */   
/*     */   public void readStatementsFromNBT(NBTTagCompound data) {
/*     */     int i;
/* 224 */     for (i = 0; i < this.material.numSlots; i++) {
/* 225 */       this.triggers[i] = null;
/* 226 */       this.actions[i] = null; int j;
/* 227 */       for (j = 0; j < this.material.numTriggerParameters; j++) {
/* 228 */         this.triggerParameters[i][j] = null;
/*     */       }
/* 230 */       for (j = 0; j < this.material.numActionParameters; j++) {
/* 231 */         this.actionParameters[i][j] = null;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 236 */     for (i = 0; i < this.material.numSlots; i++) {
/* 237 */       if (data.func_74764_b("trigger[" + i + "]")) {
/* 238 */         this.triggers[i] = (IStatement)StatementManager.statements.get(data.func_74779_i("trigger[" + i + "]"));
/*     */       }
/*     */       
/* 241 */       if (data.func_74764_b("action[" + i + "]")) {
/* 242 */         this.actions[i] = (IStatement)StatementManager.statements.get(data.func_74779_i("action[" + i + "]"));
/*     */       }
/*     */ 
/*     */       
/* 246 */       if (data.func_74764_b("triggerParameters[" + i + "]")) {
/* 247 */         this.triggerParameters[i][0] = (IStatementParameter)new StatementParameterItemStack();
/* 248 */         this.triggerParameters[i][0].readFromNBT(data.func_74775_l("triggerParameters[" + i + "]"));
/*     */       } 
/*     */       int j;
/* 251 */       for (j = 0; j < this.material.numTriggerParameters; j++) {
/* 252 */         if (data.func_74764_b("triggerParameters[" + i + "][" + j + "]")) {
/* 253 */           NBTTagCompound cpt = data.func_74775_l("triggerParameters[" + i + "][" + j + "]");
/* 254 */           this.triggerParameters[i][j] = StatementManager.createParameter(cpt.func_74779_i("kind"));
/* 255 */           this.triggerParameters[i][j].readFromNBT(cpt);
/*     */         } 
/*     */       } 
/*     */       
/* 259 */       for (j = 0; j < this.material.numActionParameters; j++) {
/* 260 */         if (data.func_74764_b("actionParameters[" + i + "][" + j + "]")) {
/* 261 */           NBTTagCompound cpt = data.func_74775_l("actionParameters[" + i + "][" + j + "]");
/* 262 */           this.actionParameters[i][j] = StatementManager.createParameter(cpt.func_74779_i("kind"));
/* 263 */           this.actionParameters[i][j].readFromNBT(cpt);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     recalculateActionGroups();
/*     */   }
/*     */   
/*     */   public boolean verifyGateStatements() {
/* 272 */     List<IStatement> triggerList = getAllValidTriggers();
/* 273 */     List<IStatement> actionList = getAllValidActions();
/* 274 */     boolean warning = false;
/*     */     
/* 276 */     for (int i = 0; i < MAX_STATEMENTS; i++) {
/* 277 */       if ((this.triggers[i] != null || this.actions[i] != null) && i >= this.material.numSlots) {
/* 278 */         this.triggers[i] = null;
/* 279 */         this.actions[i] = null;
/* 280 */         warning = true;
/*     */       }
/*     */       else {
/*     */         
/* 284 */         if (this.triggers[i] != null && (
/* 285 */           !triggerList.contains(this.triggers[i]) || this.triggers[i]
/* 286 */           .minParameters() > this.material.numTriggerParameters)) {
/* 287 */           this.triggers[i] = null;
/* 288 */           warning = true;
/*     */         } 
/*     */ 
/*     */         
/* 292 */         if (this.actions[i] != null && (
/* 293 */           !actionList.contains(this.actions[i]) || this.actions[i]
/* 294 */           .minParameters() > this.material.numActionParameters)) {
/* 295 */           this.actions[i] = null;
/* 296 */           warning = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 301 */     if (warning) {
/* 302 */       recalculateActionGroups();
/*     */     }
/*     */     
/* 305 */     return !warning;
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound data) {
/* 309 */     readStatementsFromNBT(data);
/*     */     
/* 311 */     if (data.func_74764_b("wireState[0]")) {
/* 312 */       for (PipeWire wire : PipeWire.VALUES) {
/* 313 */         if (data.func_74767_n("wireState[" + wire.ordinal() + "]")) {
/* 314 */           this.broadcastSignal = (byte)(this.broadcastSignal | 1 << wire.ordinal());
/*     */         }
/*     */       } 
/*     */     } else {
/* 318 */       this.broadcastSignal = data.func_74771_c("wireState");
/*     */     } 
/*     */     
/* 321 */     this.redstoneOutput = data.func_74771_c("redstoneOutput");
/*     */   }
/*     */ 
/*     */   
/*     */   public void openGui(EntityPlayer player) {
/* 326 */     if (!player.field_70170_p.field_72995_K) {
/* 327 */       player.openGui(BuildCraftTransport.instance, 51, this.pipe.container.func_145831_w(), this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e);
/*     */       
/* 329 */       if (player.field_71070_bA instanceof ContainerGateInterface) {
/* 330 */         ((ContainerGateInterface)player.field_71070_bA).setGate(this.direction.ordinal());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 337 */     for (GateExpansionController expansion : this.expansions.values()) {
/* 338 */       expansion.tick(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack getGateItem() {
/* 343 */     return ItemGate.makeGateItem(this);
/*     */   }
/*     */   
/*     */   public void dropGate() {
/* 347 */     this.pipe.dropItem(getGateItem());
/*     */   }
/*     */   
/*     */   public void resetGate() {
/* 351 */     if (this.redstoneOutput != 0) {
/* 352 */       this.redstoneOutput = 0;
/* 353 */       this.pipe.updateNeighbors(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isGateActive() {
/* 358 */     for (ActionActiveState state : this.actionsState) {
/* 359 */       if (state == ActionActiveState.Activated) {
/* 360 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 364 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isGatePulsing() {
/* 368 */     return this.isPulsing;
/*     */   }
/*     */   
/*     */   public int getRedstoneOutput() {
/* 372 */     return this.redstoneOutput;
/*     */   }
/*     */   
/*     */   public int getSidedRedstoneOutput() {
/* 376 */     return this.redstoneOutputSide;
/*     */   }
/*     */   
/*     */   public void setRedstoneOutput(boolean sideOnly, int value) {
/* 380 */     this.redstoneOutputSide = value;
/*     */     
/* 382 */     if (!sideOnly) {
/* 383 */       this.redstoneOutput = value;
/*     */     }
/*     */   }
/*     */   
/*     */   public void startResolution() {
/* 388 */     for (GateExpansionController expansion : this.expansions.values()) {
/* 389 */       expansion.startResolution();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resolveActions() {
/* 394 */     int oldRedstoneOutput = this.redstoneOutput;
/* 395 */     this.redstoneOutput = 0;
/*     */     
/* 397 */     int oldRedstoneOutputSide = this.redstoneOutputSide;
/* 398 */     this.redstoneOutputSide = 0;
/*     */     
/* 400 */     boolean wasActive = (this.activeActions.size() > 0);
/*     */     
/* 402 */     this.prevBroadcastSignal = this.broadcastSignal;
/* 403 */     this.broadcastSignal = 0;
/*     */ 
/*     */     
/* 406 */     startResolution();
/*     */     
/*     */     int it;
/* 409 */     for (it = 0; it < MAX_STATEMENTS; it++) {
/* 410 */       this.actionsState[it] = ActionActiveState.Deactivated;
/*     */       
/* 412 */       IStatement trigger = this.triggers[it];
/* 413 */       IStatementParameter[] parameter = this.triggerParameters[it];
/*     */       
/* 415 */       if (trigger != null && 
/* 416 */         isTriggerActive(trigger, parameter)) {
/* 417 */         this.actionsState[it] = ActionActiveState.Partial;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 422 */     this.activeActions.clear();
/*     */     
/* 424 */     for (it = 0; it < MAX_STATEMENTS; it++) {
/* 425 */       boolean allActive = true;
/* 426 */       boolean oneActive = false;
/*     */       
/* 428 */       if (this.actions[it] != null) {
/*     */         int j;
/*     */ 
/*     */         
/* 432 */         for (j = 0; j < MAX_STATEMENTS; j++) {
/* 433 */           if (this.actionGroups[j] == it) {
/* 434 */             if (this.actionsState[j] != ActionActiveState.Partial) {
/* 435 */               allActive = false;
/*     */             } else {
/* 437 */               oneActive = true;
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 442 */         if ((this.logic == GateDefinition.GateLogic.AND && allActive && oneActive) || (this.logic == GateDefinition.GateLogic.OR && oneActive)) {
/* 443 */           if (this.logic == GateDefinition.GateLogic.AND) {
/* 444 */             for (j = 0; j < MAX_STATEMENTS; j++) {
/* 445 */               if (this.actionGroups[j] == it) {
/* 446 */                 this.actionsState[j] = ActionActiveState.Activated;
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/* 451 */           StatementSlot slot = new StatementSlot();
/* 452 */           slot.statement = this.actions[it];
/* 453 */           slot.parameters = this.actionParameters[it];
/* 454 */           this.activeActions.add(slot);
/*     */         } 
/*     */         
/* 457 */         if (this.logic == GateDefinition.GateLogic.OR && this.actionsState[it] == ActionActiveState.Partial) {
/* 458 */           this.actionsState[it] = ActionActiveState.Activated;
/*     */         }
/*     */       } 
/*     */     } 
/* 462 */     this.statementCounts.clear();
/*     */     
/* 464 */     for (it = 0; it < MAX_STATEMENTS; it++) {
/* 465 */       if (this.actionsState[it] == ActionActiveState.Activated) {
/* 466 */         this.statementCounts.add(this.actions[it], 1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 471 */     for (StatementSlot slot : this.activeActions) {
/* 472 */       IStatement action = slot.statement;
/* 473 */       if (action instanceof IActionInternal) {
/* 474 */         ((IActionInternal)action).actionActivate((IStatementContainer)this, slot.parameters);
/* 475 */       } else if (action instanceof IActionExternal) {
/* 476 */         for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 477 */           TileEntity tile = getPipe().getTile().getNeighborTile(side);
/* 478 */           if (tile != null) {
/* 479 */             ((IActionExternal)action).actionActivate(tile, side, (IStatementContainer)this, slot.parameters);
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 487 */       if (resolveAction(action)) {
/*     */         continue;
/*     */       }
/*     */       
/* 491 */       for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 492 */         TileEntity tile = this.pipe.container.getTile(side);
/* 493 */         if (tile instanceof IActionReceptor) {
/* 494 */           IActionReceptor recept = (IActionReceptor)tile;
/* 495 */           recept.actionActivated(action, slot.parameters);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 500 */     this.pipe.actionsActivated(this.activeActions);
/*     */     
/* 502 */     if (oldRedstoneOutput != this.redstoneOutput || oldRedstoneOutputSide != this.redstoneOutputSide) {
/* 503 */       this.pipe.updateNeighbors(true);
/*     */     }
/*     */     
/* 506 */     if (this.prevBroadcastSignal != this.broadcastSignal) {
/* 507 */       this.pipe.scheduleWireUpdate();
/*     */     }
/*     */     
/* 510 */     boolean isActive = (this.activeActions.size() > 0);
/*     */     
/* 512 */     if (wasActive != isActive) {
/* 513 */       this.pipe.container.scheduleRenderUpdate();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean resolveAction(IStatement action) {
/* 518 */     for (GateExpansionController expansion : this.expansions.values()) {
/* 519 */       if (expansion.resolveAction(action, this.statementCounts.count(action))) {
/* 520 */         return true;
/*     */       }
/*     */     } 
/* 523 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isTriggerActive(IStatement trigger, IStatementParameter[] parameters) {
/* 527 */     if (trigger == null) {
/* 528 */       return false;
/*     */     }
/*     */     
/* 531 */     if (trigger instanceof ITriggerInternal) {
/* 532 */       if (((ITriggerInternal)trigger).isTriggerActive((IStatementContainer)this, parameters)) {
/* 533 */         return true;
/*     */       }
/* 535 */     } else if (trigger instanceof ITriggerExternal) {
/* 536 */       for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 537 */         TileEntity tile = getPipe().getTile().getNeighborTile(side);
/* 538 */         if (tile != null) {
/* 539 */           if (tile instanceof ITriggerExternalOverride) {
/* 540 */             ITriggerExternalOverride.Result result = ((ITriggerExternalOverride)tile).override(side, (IStatementContainer)this, parameters);
/* 541 */             if (result == ITriggerExternalOverride.Result.TRUE)
/* 542 */               return true; 
/* 543 */             if (result == ITriggerExternalOverride.Result.FALSE) {
/*     */               continue;
/*     */             }
/*     */           } 
/* 547 */           if (((ITriggerExternal)trigger).isTriggerActive(tile, side, (IStatementContainer)this, parameters)) {
/* 548 */             return true;
/*     */           }
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */     } 
/*     */     
/* 556 */     for (GateExpansionController expansion : this.expansions.values()) {
/* 557 */       if (expansion.isTriggerActive(trigger, parameters)) {
/* 558 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 562 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addTriggers(List<ITriggerInternal> list) {
/* 567 */     for (PipeWire wire : PipeWire.VALUES) {
/* 568 */       if (this.pipe.wireSet[wire.ordinal()] && wire.ordinal() < this.material.maxWireColor) {
/* 569 */         list.add(BuildCraftTransport.triggerPipeWireActive[wire.ordinal()]);
/* 570 */         list.add(BuildCraftTransport.triggerPipeWireInactive[wire.ordinal()]);
/*     */       } 
/*     */     } 
/*     */     
/* 574 */     for (GateExpansionController expansion : this.expansions.values()) {
/* 575 */       expansion.addTriggers(list);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<IStatement> getAllValidTriggers() {
/* 580 */     ArrayList<IStatement> allTriggers = new ArrayList<IStatement>(64);
/* 581 */     allTriggers.addAll(StatementManager.getInternalTriggers((IStatementContainer)this));
/*     */     
/* 583 */     for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
/* 584 */       TileEntity tile = this.pipe.container.getTile(o);
/* 585 */       allTriggers.addAll(StatementManager.getExternalTriggers(o, tile));
/*     */     } 
/*     */     
/* 588 */     return allTriggers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addActions(List<IActionInternal> list) {
/* 593 */     for (PipeWire wire : PipeWire.VALUES) {
/* 594 */       if (this.pipe.wireSet[wire.ordinal()] && wire.ordinal() < this.material.maxWireColor) {
/* 595 */         list.add(BuildCraftTransport.actionPipeWire[wire.ordinal()]);
/*     */       }
/*     */     } 
/*     */     
/* 599 */     for (GateExpansionController expansion : this.expansions.values()) {
/* 600 */       expansion.addActions(list);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<IStatement> getAllValidActions() {
/* 605 */     ArrayList<IStatement> allActions = new ArrayList<IStatement>(64);
/* 606 */     allActions.addAll(StatementManager.getInternalActions((IStatementContainer)this));
/*     */     
/* 608 */     for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
/* 609 */       TileEntity tile = this.pipe.container.getTile(o);
/* 610 */       allActions.addAll(StatementManager.getExternalActions(o, tile));
/*     */     } 
/*     */     
/* 613 */     return allActions;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPulsing(boolean pulsing) {
/* 618 */     if (pulsing != this.isPulsing) {
/* 619 */       this.isPulsing = pulsing;
/* 620 */       this.pipe.container.scheduleRenderUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void recalculateActionGroups() {
/* 625 */     for (int i = 0; i < MAX_STATEMENTS; i++) {
/* 626 */       this.actionGroups[i] = i;
/*     */       
/* 628 */       for (int j = i - 1; j >= 0; j--) {
/* 629 */         if (this.actions[i] != null && this.actions[j] != null && this.actions[i]
/* 630 */           .getUniqueTag().equals(this.actions[j].getUniqueTag())) {
/* 631 */           boolean sameParams = true;
/*     */           
/* 633 */           for (int p = 0; p < MAX_PARAMETERS; p++) {
/* 634 */             if ((this.actionParameters[i][p] != null && this.actionParameters[j][p] == null) || (this.actionParameters[i][p] == null && this.actionParameters[j][p] != null) || (this.actionParameters[i][p] != null && this.actionParameters[j][p] != null && 
/*     */ 
/*     */ 
/*     */               
/* 638 */               !this.actionParameters[i][p].equals(this.actionParameters[j][p]))) {
/* 639 */               sameParams = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 644 */           if (sameParams) {
/* 645 */             this.actionGroups[i] = j;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void broadcastSignal(PipeWire color) {
/* 653 */     this.broadcastSignal = (byte)(this.broadcastSignal | 1 << color.ordinal());
/*     */   }
/*     */   
/*     */   public IPipe getPipe() {
/* 657 */     return this.pipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForgeDirection getSide() {
/* 662 */     return this.direction;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity getTile() {
/* 667 */     return this.pipe.container;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IStatement> getTriggers() {
/* 672 */     return Arrays.<IStatement>asList(this.triggers).subList(0, this.material.numSlots);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IStatement> getActions() {
/* 677 */     return Arrays.<IStatement>asList(this.actions).subList(0, this.material.numSlots);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<StatementSlot> getActiveActions() {
/* 682 */     return this.activeActions;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IStatementParameter> getTriggerParameters(int index) {
/* 687 */     if (index < 0 || index >= this.material.numSlots) {
/* 688 */       return null;
/*     */     }
/* 690 */     return Arrays.<IStatementParameter>asList(this.triggerParameters[index]).subList(0, this.material.numTriggerParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IStatementParameter> getActionParameters(int index) {
/* 695 */     if (index < 0 || index >= this.material.numSlots) {
/* 696 */       return null;
/*     */     }
/* 698 */     return Arrays.<IStatementParameter>asList(this.actionParameters[index]).subList(0, this.material.numActionParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRedstoneInput(ForgeDirection side) {
/* 703 */     return (side == ForgeDirection.UNKNOWN) ? this.pipe.container.redstoneInput : this.pipe.container.redstoneInputSide[side.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setRedstoneOutput(ForgeDirection side, int value) {
/* 708 */     if (side != getSide() && side != ForgeDirection.UNKNOWN) {
/* 709 */       return false;
/*     */     }
/*     */     
/* 712 */     setRedstoneOutput((side != ForgeDirection.UNKNOWN), value);
/* 713 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\Gate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */