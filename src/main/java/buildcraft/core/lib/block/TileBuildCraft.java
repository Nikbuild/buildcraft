/*     */ package buildcraft.core.lib.block;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.api.tiles.IControllable;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.TileBuffer;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.PacketTileUpdate;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashSet;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
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
/*     */ 
/*     */ public abstract class TileBuildCraft
/*     */   extends TileEntity
/*     */   implements IEnergyHandler, ISerializable
/*     */ {
/*     */   protected TileBuffer[] cache;
/*  43 */   protected HashSet<EntityPlayer> guiWatchers = new HashSet<EntityPlayer>();
/*     */   
/*     */   protected IControllable.Mode mode;
/*     */   private boolean init = false;
/*  47 */   private String owner = "[BuildCraft]";
/*     */   private RFBattery battery;
/*     */   private int receivedTick;
/*     */   private int extractedTick;
/*     */   private long worldTimeEnergyReceive;
/*     */   
/*     */   public String getOwner() {
/*  54 */     return this.owner;
/*     */   }
/*     */   
/*     */   public void addGuiWatcher(EntityPlayer player) {
/*  58 */     if (!this.guiWatchers.contains(player)) {
/*  59 */       this.guiWatchers.add(player);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeGuiWatcher(EntityPlayer player) {
/*  64 */     if (this.guiWatchers.contains(player)) {
/*  65 */       this.guiWatchers.remove(player);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  71 */     if (!this.init && !func_145837_r()) {
/*  72 */       initialize();
/*  73 */       this.init = true;
/*     */     } 
/*     */     
/*  76 */     if (this.battery != null) {
/*  77 */       this.receivedTick = 0;
/*  78 */       this.extractedTick = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {}
/*     */ 
/*     */   
/*     */   public void func_145829_t() {
/*  88 */     super.func_145829_t();
/*  89 */     this.cache = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/*  94 */     this.init = false;
/*  95 */     super.func_145843_s();
/*  96 */     this.cache = null;
/*     */   }
/*     */   
/*     */   public void onBlockPlacedBy(EntityLivingBase entity, ItemStack stack) {
/* 100 */     if (entity instanceof EntityPlayer) {
/* 101 */       this.owner = ((EntityPlayer)entity).getDisplayName();
/*     */     }
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 106 */     this.cache = null;
/*     */   }
/*     */   
/*     */   public void sendNetworkUpdate() {
/* 110 */     if (this.field_145850_b != null && !this.field_145850_b.field_72995_K) {
/* 111 */       BuildCraftCore.instance.sendToPlayers(getPacketUpdate(), this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, 
/* 112 */           getNetworkUpdateRange());
/*     */     }
/*     */   }
/*     */   
/*     */   protected int getNetworkUpdateRange() {
/* 117 */     return DefaultProps.NETWORK_UPDATE_RANGE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {}
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {}
/*     */ 
/*     */   
/*     */   public Packet getPacketUpdate() {
/* 129 */     return (Packet)new PacketTileUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet func_145844_m() {
/* 134 */     return (Packet)Utils.toPacket(getPacketUpdate(), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 139 */     super.func_145841_b(nbt);
/* 140 */     nbt.func_74778_a("owner", this.owner);
/* 141 */     if (this.battery != null) {
/* 142 */       NBTTagCompound batteryNBT = new NBTTagCompound();
/* 143 */       this.battery.writeToNBT(batteryNBT);
/* 144 */       nbt.func_74782_a("battery", (NBTBase)batteryNBT);
/*     */     } 
/* 146 */     if (this.mode != null) {
/* 147 */       nbt.func_74774_a("lastMode", (byte)this.mode.ordinal());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 153 */     super.func_145839_a(nbt);
/* 154 */     if (nbt.func_74764_b("owner")) {
/* 155 */       this.owner = nbt.func_74779_i("owner");
/*     */     }
/* 157 */     if (this.battery != null) {
/* 158 */       this.battery.readFromNBT(nbt.func_74775_l("battery"));
/*     */     }
/* 160 */     if (nbt.func_74764_b("lastMode")) {
/* 161 */       this.mode = IControllable.Mode.values()[nbt.func_74771_c("lastMode")];
/*     */     }
/*     */   }
/*     */   
/*     */   protected int getTicksSinceEnergyReceived() {
/* 166 */     return (int)(this.field_145850_b.func_82737_E() - this.worldTimeEnergyReceive);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 172 */     return (this.field_145851_c * 37 + this.field_145848_d) * 37 + this.field_145849_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object cmp) {
/* 177 */     return (this == cmp);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 182 */     return (this.battery != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
/* 188 */     if (this.battery != null && canConnectEnergy(from)) {
/* 189 */       int received = this.battery.receiveEnergy(Math.min(maxReceive, this.battery.getMaxEnergyReceive() - this.receivedTick), simulate);
/* 190 */       if (!simulate) {
/* 191 */         this.receivedTick += received;
/* 192 */         this.worldTimeEnergyReceive = this.field_145850_b.func_82737_E();
/*     */       } 
/* 194 */       return received;
/*     */     } 
/* 196 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
/* 205 */     if (this.battery != null && canConnectEnergy(from)) {
/* 206 */       int extracted = this.battery.extractEnergy(Math.min(maxExtract, this.battery.getMaxEnergyExtract() - this.extractedTick), simulate);
/* 207 */       if (!simulate) {
/* 208 */         this.extractedTick += extracted;
/*     */       }
/* 210 */       return extracted;
/*     */     } 
/* 212 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection from) {
/* 218 */     if (this.battery != null && canConnectEnergy(from)) {
/* 219 */       return this.battery.getEnergyStored();
/*     */     }
/* 221 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection from) {
/* 227 */     if (this.battery != null && canConnectEnergy(from)) {
/* 228 */       return this.battery.getMaxEnergyStored();
/*     */     }
/* 230 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public RFBattery getBattery() {
/* 235 */     return this.battery;
/*     */   }
/*     */   
/*     */   protected void setBattery(RFBattery battery) {
/* 239 */     this.battery = battery;
/*     */   }
/*     */   
/*     */   public Block getBlock(ForgeDirection side) {
/* 243 */     if (this.cache == null) {
/* 244 */       this.cache = TileBuffer.makeBuffer(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, false);
/*     */     }
/* 246 */     return this.cache[side.ordinal()].getBlock();
/*     */   }
/*     */   
/*     */   public TileEntity getTile(ForgeDirection side) {
/* 250 */     if (this.cache == null) {
/* 251 */       this.cache = TileBuffer.makeBuffer(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, false);
/*     */     }
/* 253 */     return this.cache[side.ordinal()].getTile();
/*     */   }
/*     */   
/*     */   public IControllable.Mode getControlMode() {
/* 257 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setControlMode(IControllable.Mode mode) {
/* 261 */     this.mode = mode;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\block\TileBuildCraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */