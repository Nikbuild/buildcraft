/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.IAreaProvider;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.builders.blueprints.RecursiveBlueprintReader;
/*     */ import buildcraft.core.Box;
/*     */ import buildcraft.core.LaserData;
/*     */ import buildcraft.core.blueprints.BlueprintReadConfiguration;
/*     */ import buildcraft.core.internal.IBoxProvider;
/*     */ import buildcraft.core.internal.ILEDProvider;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
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
/*     */ public class TileArchitect
/*     */   extends TileBuildCraft
/*     */   implements IInventory, IBoxProvider, ICommandReceiver, ILEDProvider
/*     */ {
/*     */   public enum Mode
/*     */   {
/*  51 */     NONE, EDIT, COPY;
/*     */   }
/*     */   
/*  54 */   public String currentAuthorName = "";
/*  55 */   public Mode mode = Mode.NONE;
/*     */   
/*  57 */   public Box box = new Box();
/*  58 */   public String name = "";
/*  59 */   public BlueprintReadConfiguration readConfiguration = new BlueprintReadConfiguration();
/*     */   
/*  61 */   public ArrayList<LaserData> subLasers = new ArrayList<LaserData>();
/*  62 */   public ArrayList<BlockIndex> subBlueprints = new ArrayList<BlockIndex>();
/*     */   
/*  64 */   private SimpleInventory inv = new SimpleInventory(2, "Architect", 1);
/*     */   
/*     */   private RecursiveBlueprintReader reader;
/*     */   
/*     */   public TileArchitect() {
/*  69 */     this.box.kind = Box.Kind.BLUE_STRIPES;
/*     */   }
/*     */   private boolean clientIsWorking; private boolean initialized;
/*     */   public void storeBlueprintStack(ItemStack blueprintStack) {
/*  73 */     func_70299_a(1, blueprintStack);
/*  74 */     func_70298_a(0, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  79 */     super.func_145845_h();
/*     */     
/*  81 */     if (!this.field_145850_b.field_72995_K && 
/*  82 */       this.mode == Mode.COPY && this.reader != null) {
/*  83 */       this.reader.iterate();
/*     */       
/*  85 */       if (this.reader.isDone()) {
/*  86 */         this.reader = null;
/*  87 */         sendNetworkUpdate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  95 */     super.initialize();
/*     */     
/*  97 */     if (!this.field_145850_b.field_72995_K && !this.initialized) {
/*  98 */       if (!this.box.isInitialized()) {
/*  99 */         IAreaProvider a = Utils.getNearbyAreaProvider(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */ 
/*     */         
/* 102 */         if (a != null) {
/* 103 */           this.mode = Mode.COPY;
/* 104 */           this.box.initialize(a);
/* 105 */           a.removeFromWorld();
/* 106 */           sendNetworkUpdate();
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 112 */         this.mode = Mode.NONE;
/*     */       }
/*     */       else {
/*     */         
/* 116 */         this.mode = Mode.COPY;
/*     */       } 
/* 118 */       this.initialized = true;
/* 119 */       sendNetworkUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 125 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/* 130 */     return this.inv.func_70301_a(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int j) {
/* 135 */     ItemStack result = this.inv.func_70298_a(i, j);
/*     */     
/* 137 */     if (i == 0) {
/* 138 */       initializeBlueprint();
/*     */     }
/*     */     
/* 141 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 146 */     this.inv.func_70299_a(i, itemstack);
/*     */     
/* 148 */     if (i == 0) {
/* 149 */       initializeBlueprint();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/* 155 */     return this.inv.func_70304_b(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 160 */     return "Template";
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 165 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/* 170 */     return (this.mode != Mode.NONE && this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 175 */     super.func_145839_a(nbt);
/*     */     
/* 177 */     if (nbt.func_74764_b("box")) {
/* 178 */       this.box.initialize(nbt.func_74775_l("box"));
/*     */     }
/*     */     
/* 181 */     this.inv.readFromNBT(nbt);
/*     */     
/* 183 */     this.mode = Mode.values()[nbt.func_74771_c("mode")];
/* 184 */     this.name = nbt.func_74779_i("name");
/* 185 */     this.currentAuthorName = nbt.func_74779_i("lastAuthor");
/*     */     
/* 187 */     if (nbt.func_74764_b("readConfiguration")) {
/* 188 */       this.readConfiguration.readFromNBT(nbt.func_74775_l("readConfiguration"));
/*     */     }
/*     */     
/* 191 */     NBTTagList subBptList = nbt.func_150295_c("subBlueprints", 10);
/*     */     
/* 193 */     for (int i = 0; i < subBptList.func_74745_c(); i++) {
/* 194 */       BlockIndex index = new BlockIndex(subBptList.func_150305_b(i));
/*     */       
/* 196 */       addSubBlueprint(index);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 202 */     super.func_145841_b(nbt);
/*     */     
/* 204 */     if (this.box.isInitialized()) {
/* 205 */       NBTTagCompound boxStore = new NBTTagCompound();
/* 206 */       this.box.writeToNBT(boxStore);
/* 207 */       nbt.func_74782_a("box", (NBTBase)boxStore);
/*     */     } 
/*     */     
/* 210 */     this.inv.writeToNBT(nbt);
/*     */     
/* 212 */     nbt.func_74774_a("mode", (byte)this.mode.ordinal());
/* 213 */     nbt.func_74778_a("name", this.name);
/* 214 */     nbt.func_74778_a("lastAuthor", this.currentAuthorName);
/*     */     
/* 216 */     NBTTagCompound readConf = new NBTTagCompound();
/* 217 */     this.readConfiguration.writeToNBT(readConf);
/* 218 */     nbt.func_74782_a("readConfiguration", (NBTBase)readConf);
/*     */     
/* 220 */     NBTTagList subBptList = new NBTTagList();
/*     */     
/* 222 */     for (BlockIndex b : this.subBlueprints) {
/* 223 */       NBTTagCompound subBpt = new NBTTagCompound();
/* 224 */       b.writeTo(subBpt);
/* 225 */       subBptList.func_74742_a((NBTBase)subBpt);
/*     */     } 
/*     */     
/* 228 */     nbt.func_74782_a("subBlueprints", (NBTBase)subBptList);
/*     */   }
/*     */   
/*     */   private boolean getIsWorking() {
/* 232 */     return (this.mode == Mode.COPY) ? ((this.reader != null)) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 237 */     this.box.writeData(stream);
/* 238 */     NetworkUtils.writeUTF(stream, this.name);
/* 239 */     stream.writeBoolean(getIsWorking());
/* 240 */     stream.writeByte(this.mode.ordinal());
/* 241 */     if (this.mode == Mode.COPY) {
/* 242 */       this.readConfiguration.writeData(stream);
/* 243 */       stream.writeShort(this.subLasers.size());
/* 244 */       for (LaserData ld : this.subLasers) {
/* 245 */         ld.writeData(stream);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 252 */     this.box.readData(stream);
/* 253 */     this.name = NetworkUtils.readUTF(stream);
/* 254 */     this.clientIsWorking = stream.readBoolean();
/* 255 */     this.mode = Mode.values()[stream.readByte()];
/*     */     
/* 257 */     if (this.mode == Mode.COPY) {
/* 258 */       this.readConfiguration.readData(stream);
/* 259 */       int size = stream.readUnsignedShort();
/* 260 */       this.subLasers.clear();
/* 261 */       for (int i = 0; i < size; i++) {
/* 262 */         LaserData ld = new LaserData();
/* 263 */         ld.readData(stream);
/* 264 */         this.subLasers.add(ld);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 271 */     super.func_145843_s();
/* 272 */     destroy();
/*     */   }
/*     */   
/*     */   private void initializeBlueprint() {
/* 276 */     if ((func_145831_w()).field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 280 */     if (this.mode == Mode.COPY) {
/* 281 */       this.reader = new RecursiveBlueprintReader(this);
/*     */     }
/* 283 */     sendNetworkUpdate();
/*     */   }
/*     */   
/*     */   public int getComputingProgressScaled(int scale) {
/* 287 */     if (this.reader != null) {
/* 288 */       return (int)(this.reader.getComputingProgressScaled() * scale);
/*     */     }
/* 290 */     return 0;
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
/*     */   
/*     */   public boolean func_145818_k_() {
/* 304 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int var1, ItemStack var2) {
/* 309 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Box getBox() {
/* 314 */     return this.box;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getRenderBoundingBox() {
/* 319 */     Box completeBox = (new Box((TileEntity)this)).extendToEncompass(this.box);
/*     */     
/* 321 */     for (LaserData d : this.subLasers) {
/* 322 */       completeBox.extendToEncompass(d.tail);
/*     */     }
/*     */     
/* 325 */     return completeBox.getBoundingBox();
/*     */   }
/*     */   
/*     */   public Packet getPacketSetName() {
/* 329 */     return (Packet)new PacketCommand(this, "setName", new CommandWriter() {
/*     */           public void write(ByteBuf data) {
/* 331 */             NetworkUtils.writeUTF(data, TileArchitect.this.name);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 338 */     if ("setName".equals(command)) {
/* 339 */       this.name = NetworkUtils.readUTF(stream);
/* 340 */       if (side.isServer()) {
/* 341 */         BuildCraftCore.instance.sendToPlayersNear(getPacketSetName(), (TileEntity)this);
/*     */       }
/* 343 */     } else if (side.isServer() && 
/* 344 */       "setReadConfiguration".equals(command)) {
/* 345 */       this.readConfiguration.readData(stream);
/* 346 */       sendNetworkUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rpcSetConfiguration(BlueprintReadConfiguration conf) {
/* 352 */     this.readConfiguration = conf;
/*     */     
/* 354 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "setReadConfiguration", new CommandWriter() {
/*     */             public void write(ByteBuf data) {
/* 356 */               TileArchitect.this.readConfiguration.writeData(data);
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   public void addSubBlueprint(TileEntity sub) {
/* 362 */     if (this.mode == Mode.COPY) {
/* 363 */       addSubBlueprint(new BlockIndex(sub));
/* 364 */       sendNetworkUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addSubBlueprint(BlockIndex index) {
/* 369 */     this.subBlueprints.add(index);
/*     */     
/* 371 */     LaserData laser = new LaserData(new Position(index), new Position((TileEntity)this));
/*     */     
/* 373 */     laser.head.x += 0.5D;
/* 374 */     laser.head.y += 0.5D;
/* 375 */     laser.head.z += 0.5D;
/*     */     
/* 377 */     laser.tail.x += 0.5D;
/* 378 */     laser.tail.y += 0.5D;
/* 379 */     laser.tail.z += 0.5D;
/*     */     
/* 381 */     this.subLasers.add(laser);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLEDLevel(int led) {
/* 386 */     boolean condition = false;
/* 387 */     switch (led) {
/*     */       case 0:
/* 389 */         condition = this.clientIsWorking;
/*     */         break;
/*     */       case 1:
/* 392 */         condition = (this.mode == Mode.COPY && this.box != null && this.box.isInitialized());
/*     */         break;
/*     */       case 2:
/* 395 */         condition = (this.mode == Mode.EDIT);
/*     */         break;
/*     */     } 
/* 398 */     return condition ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public double func_145833_n() {
/* 404 */     return Double.MAX_VALUE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\TileArchitect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */