/*     */ package buildcraft.transport.gui;
/*     */ 
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementMouseClick;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.core.lib.gui.AdvancedSlot;
/*     */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*     */ import buildcraft.core.lib.gui.StatementParameterSlot;
/*     */ import buildcraft.core.lib.gui.StatementSlot;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.transport.ActionActiveState;
/*     */ import buildcraft.transport.Gate;
/*     */ import buildcraft.transport.gates.GateDefinition;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiGateInterface
/*     */   extends GuiAdvancedInterface
/*     */ {
/*     */   IInventory playerInventory;
/*     */   private final ContainerGateInterface container;
/*     */   private final GuiGateInterface instance;
/*     */   private final IPipe pipe;
/*     */   private Gate gate;
/*     */   
/*     */   private class TriggerSlot
/*     */     extends StatementSlot
/*     */   {
/*     */     public TriggerSlot(int x, int y, IPipe pipe, int slot) {
/*  41 */       super(GuiGateInterface.this.instance, x, y, slot);
/*     */     }
/*     */ 
/*     */     
/*     */     public IStatement getStatement() {
/*  46 */       return GuiGateInterface.this.gate.getTrigger(this.slot);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ActionSlot extends StatementSlot {
/*     */     public ActionSlot(int x, int y, IPipe pipe, int slot) {
/*  52 */       super(GuiGateInterface.this.instance, x, y, slot);
/*     */     }
/*     */ 
/*     */     
/*     */     public IStatement getStatement() {
/*  57 */       return GuiGateInterface.this.gate.getAction(this.slot);
/*     */     }
/*     */   }
/*     */   
/*     */   class TriggerParameterSlot extends StatementParameterSlot {
/*     */     public TriggerParameterSlot(int x, int y, IPipe pipe, int slot, StatementSlot iStatementSlot) {
/*  63 */       super(GuiGateInterface.this.instance, x, y, slot, iStatementSlot);
/*     */     }
/*     */ 
/*     */     
/*     */     public IStatementParameter getParameter() {
/*  68 */       return GuiGateInterface.this.gate.getTriggerParameter(this.statementSlot.slot, this.slot);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setParameter(IStatementParameter param, boolean notifyServer) {
/*  73 */       GuiGateInterface.this.container.setTriggerParameter(this.statementSlot.slot, this.slot, param, notifyServer);
/*     */     }
/*     */   }
/*     */   
/*     */   class ActionParameterSlot extends StatementParameterSlot {
/*     */     public ActionParameterSlot(int x, int y, IPipe pipe, int slot, StatementSlot iStatementSlot) {
/*  79 */       super(GuiGateInterface.this.instance, x, y, slot, iStatementSlot);
/*     */     }
/*     */ 
/*     */     
/*     */     public IStatementParameter getParameter() {
/*  84 */       return GuiGateInterface.this.gate.getActionParameter(this.statementSlot.slot, this.slot);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setParameter(IStatementParameter param, boolean notifyServer) {
/*  89 */       GuiGateInterface.this.container.setActionParameter(this.statementSlot.slot, this.slot, param, notifyServer);
/*     */     }
/*     */   }
/*     */   
/*     */   public GuiGateInterface(IInventory playerInventory, IPipe pipe) {
/*  94 */     super(new ContainerGateInterface(playerInventory, pipe), null, null);
/*     */     
/*  96 */     this.container = (ContainerGateInterface)this.field_147002_h;
/*  97 */     this.container.gateCallback = this;
/*  98 */     this.pipe = pipe;
/*  99 */     this.playerInventory = playerInventory;
/* 100 */     this.instance = this;
/*     */   }
/*     */   
/*     */   public void setGate(Gate gate) {
/* 104 */     this.gate = gate;
/* 105 */     init();
/*     */   }
/*     */   
/*     */   public void init() {
/* 109 */     if (this.gate == null) {
/*     */       return;
/*     */     }
/* 112 */     this.field_146999_f = 176;
/* 113 */     this.field_147000_g = this.gate.material.guiHeight;
/*     */     
/* 115 */     int position = 0;
/*     */     
/* 117 */     this.slots.clear();
/*     */     
/* 119 */     if (this.gate.material == GateDefinition.GateMaterial.REDSTONE) {
/* 120 */       this.slots.add(new TriggerSlot(62, 26, this.pipe, 0));
/* 121 */       this.slots.add(new ActionSlot(98, 26, this.pipe, 0));
/* 122 */     } else if (this.gate.material == GateDefinition.GateMaterial.IRON) {
/* 123 */       this.slots.add(new TriggerSlot(62, 26, this.pipe, 0));
/* 124 */       this.slots.add(new TriggerSlot(62, 44, this.pipe, 1));
/* 125 */       this.slots.add(new ActionSlot(98, 26, this.pipe, 0));
/* 126 */       this.slots.add(new ActionSlot(98, 44, this.pipe, 1));
/* 127 */     } else if (this.gate.material == GateDefinition.GateMaterial.QUARTZ) {
/* 128 */       for (int i = 0; i < 2; i++) {
/* 129 */         TriggerSlot ts = new TriggerSlot(44, 26 + i * 18, this.pipe, i);
/* 130 */         ActionSlot as = new ActionSlot(98, 26 + i * 18, this.pipe, i);
/* 131 */         this.slots.add(ts);
/* 132 */         this.slots.add(as);
/* 133 */         this.slots.add(new TriggerParameterSlot(62, 26 + i * 18, this.pipe, 0, ts));
/* 134 */         this.slots.add(new ActionParameterSlot(116, 26 + i * 18, this.pipe, 0, as));
/*     */       } 
/* 136 */     } else if (this.gate.material == GateDefinition.GateMaterial.GOLD) {
/* 137 */       int k; for (k = 0; k < 4; k++) {
/* 138 */         this.slots.add(new TriggerSlot(53, 26 + 18 * k, this.pipe, position));
/* 139 */         position++;
/*     */       } 
/*     */       
/* 142 */       for (k = 0; k < 4; k++) {
/* 143 */         this.slots.add(new ActionSlot(107, 26 + 18 * k, this.pipe, position - 4));
/* 144 */         position++;
/*     */       } 
/*     */       
/* 147 */       for (k = 0; k < 4; k++) {
/* 148 */         this.slots.add(new TriggerParameterSlot(71, 26 + 18 * k, this.pipe, 0, this.slots.get(k)));
/* 149 */         position++;
/*     */       }
/*     */     
/* 152 */     } else if (this.gate.material == GateDefinition.GateMaterial.DIAMOND) {
/* 153 */       int k; for (k = 0; k < 4; k++) {
/* 154 */         this.slots.add(new TriggerSlot(8, 26 + 18 * k, this.pipe, position));
/* 155 */         position++;
/* 156 */         this.slots.add(new TriggerSlot(98, 26 + 18 * k, this.pipe, position));
/* 157 */         position++;
/*     */       } 
/*     */       
/* 160 */       for (k = 0; k < 4; k++) {
/* 161 */         this.slots.add(new ActionSlot(62, 26 + 18 * k, this.pipe, position - 8));
/* 162 */         position++;
/* 163 */         this.slots.add(new ActionSlot(152, 26 + 18 * k, this.pipe, position - 8));
/* 164 */         position++;
/*     */       } 
/*     */       
/* 167 */       for (k = 0; k < 4; k++) {
/* 168 */         this.slots.add(new TriggerParameterSlot(26, 26 + 18 * k, this.pipe, 0, this.slots
/* 169 */               .get(position - 16)));
/* 170 */         position++;
/* 171 */         this.slots.add(new TriggerParameterSlot(116, 26 + 18 * k, this.pipe, 0, this.slots
/* 172 */               .get(position - 16)));
/* 173 */         position++;
/*     */       } 
/* 175 */     } else if (this.gate.material == GateDefinition.GateMaterial.EMERALD) {
/*     */ 
/*     */       
/* 178 */       for (int y = 0; y < 4; y++) {
/* 179 */         this.slots.add(new TriggerSlot(8, 26 + 18 * y, this.pipe, y));
/* 180 */         int lastPos = position;
/* 181 */         position++;
/*     */         int x;
/* 183 */         for (x = 0; x < 3; x++) {
/* 184 */           this.slots.add(new TriggerParameterSlot(8 + 18 * (x + 1), 26 + 18 * y, this.pipe, x, this.slots
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 189 */                 .get(lastPos)));
/*     */           
/* 191 */           position++;
/*     */         } 
/*     */         
/* 194 */         this.slots.add(new ActionSlot(98, 26 + 18 * y, this.pipe, y));
/* 195 */         lastPos = position;
/* 196 */         position++;
/*     */         
/* 198 */         for (x = 0; x < 3; x++) {
/* 199 */           this.slots.add(new ActionParameterSlot(98 + 18 * (x + 1), 26 + 18 * y, this.pipe, x, this.slots
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 204 */                 .get(lastPos)));
/* 205 */           position++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 209 */     func_73866_w_();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 214 */     if (this.gate == null) {
/*     */       return;
/*     */     }
/* 217 */     String name = this.container.getGateName();
/*     */     
/* 219 */     this.field_146289_q.func_78276_b(name, getCenteredOffset(name), 10, 4210752);
/* 220 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 97, 4210752);
/*     */     
/* 222 */     drawTooltipForSlotAt(par1, par2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 227 */     this.container.synchronize();
/*     */     
/* 229 */     if (this.gate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 233 */     ResourceLocation texture = this.container.getGateGuiFile();
/*     */     
/* 235 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 236 */     this.field_146297_k.field_71446_o.func_110577_a(texture);
/*     */     
/* 238 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */     
/* 240 */     for (AdvancedSlot slot : this.slots) {
/* 241 */       if (slot instanceof TriggerSlot) {
/* 242 */         boolean halfWidth = (this.container.actionsState[((TriggerSlot)slot).slot] == ActionActiveState.Partial);
/*     */         
/* 244 */         if (this.container.actionsState[((TriggerSlot)slot).slot] != ActionActiveState.Deactivated) {
/* 245 */           this.field_146297_k.field_71446_o.func_110577_a(texture);
/*     */           
/* 247 */           func_73729_b(this.field_147003_i + slot.x + 17 + 18 * this.gate.material.numTriggerParameters, this.field_147009_r + slot.y + 6, 176, 18, halfWidth ? 9 : 18, 4);
/*     */         }  continue;
/*     */       } 
/* 250 */       if (slot instanceof StatementParameterSlot) {
/* 251 */         StatementParameterSlot paramSlot = (StatementParameterSlot)slot;
/* 252 */         StatementSlot statement = paramSlot.statementSlot;
/*     */         
/* 254 */         this.field_146297_k.field_71446_o.func_110577_a(texture);
/*     */         
/* 256 */         if (statement.isDefined()) {
/* 257 */           if (!paramSlot.isAllowed()) {
/* 258 */             func_73729_b(this.field_147003_i + slot.x - 1, this.field_147009_r + slot.y - 1, 176, 0, 18, 18); continue;
/* 259 */           }  if (paramSlot.isRequired() && paramSlot.getItemStack() == null)
/* 260 */             func_73729_b(this.field_147003_i + slot.x - 1, this.field_147009_r + slot.y - 1, 176, 22, 18, 18); 
/*     */           continue;
/*     */         } 
/* 263 */         func_73729_b(this.field_147003_i + slot.x - 1, this.field_147009_r + slot.y - 1, 176, 0, 18, 18);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 268 */     drawBackgroundSlots(x, y);
/*     */   }
/*     */   
/*     */   private void doSlotClick(AdvancedSlot slot, int k) {
/* 272 */     if (slot instanceof TriggerSlot && this.container.hasTriggers()) {
/* 273 */       TriggerSlot triggerSlot = (TriggerSlot)slot;
/*     */       
/* 275 */       IStatement changed = null;
/*     */       
/* 277 */       if (func_146272_n()) {
/* 278 */         changed = null;
/*     */       }
/* 280 */       else if (triggerSlot.getStatement() == null) {
/* 281 */         if (k == 0) {
/* 282 */           changed = this.container.getFirstTrigger();
/*     */         } else {
/* 284 */           changed = this.container.getLastTrigger();
/*     */         } 
/*     */       } else {
/* 287 */         Iterator<IStatement> it = this.container.getTriggerIterator((k != 0));
/*     */         
/* 289 */         while (it.hasNext()) {
/* 290 */           IStatement trigger = it.next();
/*     */           
/* 292 */           if (!it.hasNext()) {
/* 293 */             changed = null;
/*     */             
/*     */             break;
/*     */           } 
/* 297 */           if (trigger == triggerSlot.getStatement()) {
/* 298 */             changed = it.next();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 305 */       if (changed == null) {
/* 306 */         this.container.setTrigger(triggerSlot.slot, null, true);
/*     */       } else {
/* 308 */         this.container.setTrigger(triggerSlot.slot, changed.getUniqueTag(), true);
/*     */       } 
/*     */       
/* 311 */       for (StatementParameterSlot p : triggerSlot.parameters) {
/* 312 */         IStatementParameter parameter = null;
/* 313 */         if (changed != null && p.slot < changed.minParameters()) {
/* 314 */           parameter = changed.createParameter(p.slot);
/*     */         }
/* 316 */         this.container.setTriggerParameter(triggerSlot.slot, p.slot, parameter, true);
/*     */       } 
/* 318 */     } else if (slot instanceof ActionSlot) {
/* 319 */       ActionSlot actionSlot = (ActionSlot)slot;
/*     */       
/* 321 */       IStatement changed = null;
/*     */       
/* 323 */       if (func_146272_n()) {
/* 324 */         changed = null;
/*     */       }
/* 326 */       else if (actionSlot.getStatement() == null) {
/* 327 */         if (k == 0) {
/* 328 */           changed = this.container.getFirstAction();
/*     */         } else {
/* 330 */           changed = this.container.getLastAction();
/*     */         } 
/*     */       } else {
/*     */         
/* 334 */         Iterator<IStatement> it = this.container.getActionIterator((k != 0));
/*     */         
/* 336 */         while (it.hasNext()) {
/* 337 */           IStatement action = it.next();
/*     */           
/* 339 */           if (!it.hasNext()) {
/* 340 */             changed = null;
/*     */             
/*     */             break;
/*     */           } 
/* 344 */           if (action == actionSlot.getStatement()) {
/* 345 */             changed = it.next();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 352 */       if (changed == null) {
/* 353 */         this.container.setAction(actionSlot.slot, null, true);
/*     */       } else {
/* 355 */         this.container.setAction(actionSlot.slot, changed.getUniqueTag(), true);
/*     */       } 
/*     */       
/* 358 */       for (StatementParameterSlot p : actionSlot.parameters) {
/* 359 */         IStatementParameter parameter = null;
/* 360 */         if (changed != null && p.slot < changed.minParameters()) {
/* 361 */           parameter = changed.createParameter(p.slot);
/*     */         }
/* 363 */         this.container.setActionParameter(actionSlot.slot, p.slot, parameter, true);
/*     */       } 
/* 365 */     } else if (slot instanceof StatementParameterSlot) {
/* 366 */       StatementParameterSlot paramSlot = (StatementParameterSlot)slot;
/* 367 */       StatementSlot statement = paramSlot.statementSlot;
/*     */       
/* 369 */       if (statement.isDefined() && statement.getStatement().maxParameters() != 0) {
/* 370 */         IStatementParameter param = paramSlot.getParameter();
/*     */         
/* 372 */         if (param == null) {
/* 373 */           param = statement.getStatement().createParameter(paramSlot.slot);
/*     */         }
/*     */         
/* 376 */         if (param != null) {
/* 377 */           param.onClick((IStatementContainer)this.gate, statement.getStatement(), this.field_146297_k.field_71439_g.field_71071_by.func_70445_o(), new StatementMouseClick(k, 
/* 378 */                 func_146272_n()));
/* 379 */           paramSlot.setParameter(param, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 384 */     this.container.markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int i, int j, int k) {
/* 389 */     if (this.gate == null) {
/*     */       return;
/*     */     }
/* 392 */     super.func_73864_a(i, j, k);
/*     */     
/* 394 */     AdvancedSlot slot = getSlotAtLocation(i, j);
/*     */     
/* 396 */     if (slot != null) {
/* 397 */       doSlotClick(slot, k);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146274_d() {
/* 403 */     super.func_146274_d();
/*     */     
/* 405 */     int wheel = Mouse.getEventDWheel();
/* 406 */     if (wheel != 0) {
/* 407 */       int i = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c;
/* 408 */       int j = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
/* 409 */       doSlotClick(getSlotAtLocation(i, j), (wheel > 0) ? 0 : 1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\GuiGateInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */