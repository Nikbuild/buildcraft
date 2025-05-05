/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.IAreaProvider;
/*     */ import buildcraft.api.filler.FillerManager;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementManager;
/*     */ import buildcraft.api.tiles.IControllable;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.core.Box;
/*     */ import buildcraft.core.blueprints.BptBuilderTemplate;
/*     */ import buildcraft.core.builders.IBuildingItemsProvider;
/*     */ import buildcraft.core.builders.TileAbstractBuilder;
/*     */ import buildcraft.core.builders.patterns.FillerPattern;
/*     */ import buildcraft.core.builders.patterns.PatternFill;
/*     */ import buildcraft.core.internal.ILEDProvider;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileFiller
/*     */   extends TileAbstractBuilder
/*     */   implements IHasWork, IControllable, ICommandReceiver, IStatementContainer, ILEDProvider
/*     */ {
/*  44 */   private static int POWER_ACTIVATION = 500;
/*     */   
/*  46 */   public FillerPattern currentPattern = (FillerPattern)PatternFill.INSTANCE;
/*     */   
/*     */   public IStatementParameter[] patternParameters;
/*     */   
/*     */   private BptBuilderTemplate currentTemplate;
/*  51 */   private final Box box = new Box();
/*     */   private boolean done = false;
/*     */   private boolean excavate = true;
/*  54 */   private SimpleInventory inv = new SimpleInventory(27, "Filler", 64);
/*     */   
/*  56 */   private NBTTagCompound initNBT = null;
/*     */   
/*     */   public TileFiller() {
/*  59 */     this.inv.addListener((TileEntity)this);
/*  60 */     this.box.kind = Box.Kind.STRIPES;
/*     */   }
/*     */   
/*     */   public boolean isExcavate() {
/*  64 */     return this.excavate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  69 */     super.initialize();
/*     */     
/*  71 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     IAreaProvider a = Utils.getNearbyAreaProvider(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */ 
/*     */     
/*  78 */     if (a != null) {
/*  79 */       this.box.initialize(a);
/*  80 */       a.removeFromWorld();
/*  81 */       sendNetworkUpdate();
/*     */     } 
/*     */     
/*  84 */     if (this.currentTemplate == null) {
/*  85 */       initTemplate();
/*     */     }
/*     */     
/*  88 */     if (this.initNBT != null && this.currentTemplate != null) {
/*  89 */       this.currentTemplate.loadBuildStateToNBT(this.initNBT
/*  90 */           .func_74775_l("builderState"), (IBuildingItemsProvider)this);
/*     */     }
/*     */     
/*  93 */     this.initNBT = null;
/*     */   }
/*     */   
/*     */   private void initTemplate() {
/*  97 */     if (this.currentPattern != null && this.box.isInitialized() && this.box.sizeX() > 0 && this.box.sizeY() > 0 && this.box.sizeZ() > 0) {
/*  98 */       this.currentTemplate = this.currentPattern.getTemplateBuilder(this.box, func_145831_w(), this.patternParameters);
/*  99 */       this.currentTemplate.blueprint.excavate = this.excavate;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 105 */     super.func_145845_h();
/*     */     
/* 107 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     if (this.mode == IControllable.Mode.Off) {
/*     */       return;
/*     */     }
/*     */     
/* 115 */     if (!this.box.isInitialized()) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     if (getBattery().getEnergyStored() < POWER_ACTIVATION) {
/*     */       return;
/*     */     }
/*     */     
/* 123 */     boolean oldDone = this.done;
/*     */     
/* 125 */     if (this.done) {
/* 126 */       if (this.mode == IControllable.Mode.Loop) {
/* 127 */         this.done = false;
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 133 */     if (this.currentTemplate == null) {
/* 134 */       initTemplate();
/*     */     }
/*     */     
/* 137 */     if (this.currentTemplate != null) {
/* 138 */       this.currentTemplate.buildNextSlot(this.field_145850_b, this, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */       
/* 140 */       if (this.currentTemplate.isDone((IBuildingItemsProvider)this)) {
/* 141 */         this.done = true;
/* 142 */         this.currentTemplate = null;
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     if (oldDone != this.done) {
/* 147 */       sendNetworkUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final int func_70302_i_() {
/* 153 */     return this.inv.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slot) {
/* 158 */     return this.inv.func_70301_a(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int amount) {
/* 163 */     return this.inv.func_70298_a(slot, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack stack) {
/* 168 */     this.inv.func_70299_a(slot, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/* 173 */     return this.inv.func_70304_b(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 178 */     return "Filler";
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 183 */     super.func_145839_a(nbt);
/*     */     
/* 185 */     this.inv.readFromNBT(nbt);
/*     */     
/* 187 */     if (nbt.func_74764_b("pattern")) {
/* 188 */       this.currentPattern = (FillerPattern)FillerManager.registry.getPattern(nbt.func_74779_i("pattern"));
/*     */     }
/*     */     
/* 191 */     if (this.currentPattern == null) {
/* 192 */       this.currentPattern = (FillerPattern)PatternFill.INSTANCE;
/*     */     }
/*     */     
/* 195 */     if (nbt.func_74764_b("pp")) {
/* 196 */       readParametersFromNBT(nbt.func_74775_l("pp"));
/*     */     } else {
/* 198 */       initPatternParameters();
/*     */     } 
/*     */     
/* 201 */     if (nbt.func_74764_b("box")) {
/* 202 */       this.box.initialize(nbt.func_74775_l("box"));
/*     */     }
/*     */     
/* 205 */     this.done = nbt.func_74767_n("done");
/* 206 */     this.excavate = nbt.func_74764_b("excavate") ? nbt.func_74767_n("excavate") : true;
/*     */ 
/*     */     
/* 209 */     this.initNBT = (NBTTagCompound)nbt.func_74775_l("bpt").func_74737_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 214 */     super.func_145841_b(nbt);
/*     */     
/* 216 */     this.inv.writeToNBT(nbt);
/*     */     
/* 218 */     if (this.currentPattern != null) {
/* 219 */       nbt.func_74778_a("pattern", this.currentPattern.getUniqueTag());
/*     */     }
/*     */     
/* 222 */     NBTTagCompound boxStore = new NBTTagCompound();
/* 223 */     this.box.writeToNBT(boxStore);
/* 224 */     nbt.func_74782_a("box", (NBTBase)boxStore);
/*     */     
/* 226 */     nbt.func_74757_a("done", this.done);
/* 227 */     nbt.func_74757_a("excavate", this.excavate);
/*     */     
/* 229 */     NBTTagCompound bptNBT = new NBTTagCompound();
/*     */     
/* 231 */     if (this.currentTemplate != null) {
/* 232 */       NBTTagCompound builderCpt = new NBTTagCompound();
/* 233 */       this.currentTemplate.saveBuildStateToNBT(builderCpt, (IBuildingItemsProvider)this);
/* 234 */       bptNBT.func_74782_a("builderState", (NBTBase)builderCpt);
/*     */     } 
/*     */     
/* 237 */     nbt.func_74782_a("bpt", (NBTBase)bptNBT);
/*     */     
/* 239 */     NBTTagCompound ppNBT = new NBTTagCompound();
/* 240 */     writeParametersToNBT(ppNBT);
/* 241 */     nbt.func_74782_a("pp", (NBTBase)ppNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 246 */     return this.inv.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/* 251 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this) {
/* 252 */       return false;
/*     */     }
/*     */     
/* 255 */     return (entityplayer.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 261 */     super.func_145843_s();
/* 262 */     destroy();
/*     */   }
/*     */   
/*     */   private void initPatternParameters() {
/* 266 */     this.patternParameters = new IStatementParameter[this.currentPattern.maxParameters()];
/* 267 */     for (int i = 0; i < this.currentPattern.minParameters(); i++) {
/* 268 */       this.patternParameters[i] = this.currentPattern.createParameter(i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPattern(FillerPattern pattern) {
/* 273 */     if (pattern != null && this.currentPattern != pattern) {
/* 274 */       this.currentPattern = pattern;
/* 275 */       this.currentTemplate = null;
/* 276 */       this.done = false;
/* 277 */       initPatternParameters();
/* 278 */       sendNetworkUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeParametersToNBT(NBTTagCompound nbt) {
/* 283 */     nbt.func_74774_a("length", (byte)((this.patternParameters != null) ? this.patternParameters.length : 0));
/* 284 */     if (this.patternParameters != null) {
/* 285 */       for (int i = 0; i < this.patternParameters.length; i++) {
/* 286 */         if (this.patternParameters[i] != null) {
/* 287 */           NBTTagCompound patternData = new NBTTagCompound();
/* 288 */           patternData.func_74778_a("kind", this.patternParameters[i].getUniqueTag());
/* 289 */           this.patternParameters[i].writeToNBT(patternData);
/* 290 */           nbt.func_74782_a("p" + i, (NBTBase)patternData);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void readParametersFromNBT(NBTTagCompound nbt) {
/* 297 */     this.patternParameters = new IStatementParameter[nbt.func_74771_c("length")];
/* 298 */     for (int i = 0; i < this.patternParameters.length; i++) {
/* 299 */       if (nbt.func_74764_b("p" + i)) {
/* 300 */         NBTTagCompound patternData = nbt.func_74775_l("p" + i);
/* 301 */         this.patternParameters[i] = StatementManager.createParameter(patternData.func_74779_i("kind"));
/* 302 */         this.patternParameters[i].readFromNBT(patternData);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 309 */     this.box.writeData(data);
/* 310 */     data.writeByte((this.done ? 1 : 0) | (this.excavate ? 2 : 0));
/* 311 */     NetworkUtils.writeUTF(data, this.currentPattern.getUniqueTag());
/*     */     
/* 313 */     NBTTagCompound parameterData = new NBTTagCompound();
/* 314 */     writeParametersToNBT(parameterData);
/* 315 */     NetworkUtils.writeNBT(data, parameterData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 320 */     this.box.readData(data);
/* 321 */     int flags = data.readUnsignedByte();
/* 322 */     this.done = ((flags & 0x1) > 0);
/* 323 */     this.excavate = ((flags & 0x2) > 0);
/* 324 */     FillerPattern pattern = (FillerPattern)FillerManager.registry.getPattern(NetworkUtils.readUTF(data));
/* 325 */     NBTTagCompound parameterData = NetworkUtils.readNBT(data);
/* 326 */     readParametersFromNBT(parameterData);
/* 327 */     setPattern(pattern);
/*     */     
/* 329 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 334 */     return (!this.done && this.mode != IControllable.Mode.Off);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 347 */     return true;
/*     */   }
/*     */   
/*     */   public void rpcSetPatternFromString(final String name) {
/* 351 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "setPattern", new CommandWriter() {
/*     */             public void write(ByteBuf data) {
/* 353 */               NetworkUtils.writeUTF(data, name);
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 360 */     super.receiveCommand(command, side, sender, stream);
/* 361 */     if (side.isServer()) {
/* 362 */       if ("setPattern".equals(command)) {
/* 363 */         String name = NetworkUtils.readUTF(stream);
/* 364 */         setPattern((FillerPattern)FillerManager.registry.getPattern(name));
/*     */         
/* 366 */         this.done = false;
/* 367 */       } else if ("setParameters".equals(command)) {
/* 368 */         NBTTagCompound patternData = NetworkUtils.readNBT(stream);
/* 369 */         readParametersFromNBT(patternData);
/*     */         
/* 371 */         this.currentTemplate = null;
/* 372 */         this.done = false;
/* 373 */       } else if ("setFlags".equals(command)) {
/* 374 */         this.excavate = stream.readBoolean();
/* 375 */         this.currentTemplate = null;
/*     */         
/* 377 */         sendNetworkUpdate();
/* 378 */         this.done = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 385 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Box getBox() {
/* 390 */     return this.box;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getRenderBoundingBox() {
/* 395 */     return (new Box((TileEntity)this)).extendToEncompass(this.box).expand(50).getBoundingBox();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuildingMaterialSlot(int i) {
/* 400 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsControlMode(IControllable.Mode mode) {
/* 405 */     return (mode == IControllable.Mode.On || mode == IControllable.Mode.Off || mode == IControllable.Mode.Loop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity getTile() {
/* 412 */     return (TileEntity)this;
/*     */   }
/*     */   
/*     */   public void rpcSetParameter(int i, IStatementParameter patternParameter) {
/* 416 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "setParameters", new CommandWriter() {
/*     */             public void write(ByteBuf data) {
/* 418 */               NBTTagCompound parameterData = new NBTTagCompound();
/* 419 */               TileFiller.this.writeParametersToNBT(parameterData);
/* 420 */               NetworkUtils.writeNBT(data, parameterData);
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLEDLevel(int led) {
/* 427 */     return ((led == 0) ? this.done : (this.buildersInAction.size() > 0)) ? 15 : 0;
/*     */   }
/*     */   
/*     */   public void setExcavate(boolean excavate) {
/* 431 */     this.excavate = excavate;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\TileFiller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */