/*     */ package buildcraft.core.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.blueprints.ITileBuilder;
/*     */ import buildcraft.core.LaserData;
/*     */ import buildcraft.core.internal.IBoxProvider;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.fluids.Tank;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public abstract class TileAbstractBuilder
/*     */   extends TileBuildCraft
/*     */   implements ITileBuilder, IInventory, IBoxProvider, IBuildingItemsProvider, ICommandReceiver
/*     */ {
/*  41 */   public LinkedList<LaserData> pathLasers = new LinkedList<LaserData>();
/*     */   
/*  43 */   public HashSet<BuildingItem> buildersInAction = new HashSet<BuildingItem>();
/*     */   
/*  45 */   private int rfPrev = 0;
/*  46 */   private int rfUnchangedCycles = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileAbstractBuilder() {
/*  54 */     setBattery(new RFBattery(30720, 1000, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  59 */     super.initialize();
/*     */     
/*  61 */     if (this.field_145850_b.field_72995_K) {
/*  62 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "uploadBuildersInAction", null));
/*     */     }
/*     */   }
/*     */   
/*     */   private Packet createLaunchItemPacket(final BuildingItem i) {
/*  67 */     return (Packet)new PacketCommand(this, "launchItem", new CommandWriter() {
/*     */           public void write(ByteBuf data) {
/*  69 */             i.writeData(data);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/*  76 */     if (side.isServer() && "uploadBuildersInAction".equals(command)) {
/*  77 */       for (BuildingItem i : this.buildersInAction) {
/*  78 */         BuildCraftCore.instance.sendToPlayer((EntityPlayer)sender, createLaunchItemPacket(i));
/*     */       }
/*  80 */     } else if (side.isClient() && "launchItem".equals(command)) {
/*  81 */       BuildingItem item = new BuildingItem();
/*  82 */       item.readData(stream);
/*  83 */       this.buildersInAction.add(item);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  89 */     super.func_145845_h();
/*     */     
/*  91 */     RFBattery battery = getBattery();
/*     */     
/*  93 */     if (this.rfPrev != battery.getEnergyStored()) {
/*  94 */       this.rfPrev = battery.getEnergyStored();
/*  95 */       this.rfUnchangedCycles = 0;
/*     */     } 
/*     */     
/*  98 */     Iterator<BuildingItem> itemIterator = this.buildersInAction.iterator();
/*     */ 
/*     */     
/* 101 */     while (itemIterator.hasNext()) {
/* 102 */       BuildingItem i = itemIterator.next();
/* 103 */       i.update();
/*     */       
/* 105 */       if (i.isDone()) {
/* 106 */         itemIterator.remove();
/*     */       }
/*     */     } 
/*     */     
/* 110 */     if (this.rfPrev != battery.getEnergyStored()) {
/* 111 */       this.rfPrev = battery.getEnergyStored();
/* 112 */       this.rfUnchangedCycles = 0;
/*     */     } 
/*     */     
/* 115 */     this.rfUnchangedCycles++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (this.rfUnchangedCycles > 100) {
/* 122 */       battery.useEnergy(0, 1000, false);
/*     */       
/* 124 */       this.rfPrev = battery.getEnergyStored();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWorking() {
/* 129 */     return (this.buildersInAction.size() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<BuildingItem> getBuilders() {
/* 134 */     return this.buildersInAction;
/*     */   }
/*     */   
/*     */   public LinkedList<LaserData> getPathLaser() {
/* 138 */     return this.pathLasers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAndLaunchBuildingItem(BuildingItem item) {
/* 143 */     this.buildersInAction.add(item);
/* 144 */     BuildCraftCore.instance.sendToPlayersNear(createLaunchItemPacket(item), (TileEntity)this);
/*     */   }
/*     */   
/*     */   public final int energyAvailable() {
/* 148 */     return getBattery().getEnergyStored();
/*     */   }
/*     */   
/*     */   public final boolean consumeEnergy(int quantity) {
/* 152 */     return (getBattery().useEnergy(quantity, quantity, false) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 157 */     super.func_145841_b(nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 162 */     super.func_145839_a(nbttagcompound);
/*     */     
/* 164 */     this.rfPrev = getBattery().getEnergyStored();
/* 165 */     this.rfUnchangedCycles = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 170 */     int size = stream.readUnsignedShort();
/* 171 */     this.pathLasers.clear();
/* 172 */     for (int i = 0; i < size; i++) {
/* 173 */       LaserData ld = new LaserData();
/* 174 */       ld.readData(stream);
/* 175 */       this.pathLasers.add(ld);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 181 */     stream.writeShort(this.pathLasers.size());
/* 182 */     for (LaserData ld : this.pathLasers) {
/* 183 */       ld.writeData(stream);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double func_145833_n() {
/* 189 */     return Double.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public boolean drainBuild(FluidStack fluidStack, boolean realDrain) {
/* 193 */     return false;
/*     */   }
/*     */   
/*     */   public Tank[] getFluidTanks() {
/* 197 */     return new Tank[0];
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\TileAbstractBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */