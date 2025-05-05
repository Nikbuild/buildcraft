/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.IAreaProvider;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.Box;
/*     */ import buildcraft.core.LaserData;
/*     */ import buildcraft.core.blueprints.Blueprint;
/*     */ import buildcraft.core.blueprints.BlueprintBase;
/*     */ import buildcraft.core.blueprints.BptBuilderBase;
/*     */ import buildcraft.core.blueprints.BptBuilderBlueprint;
/*     */ import buildcraft.core.blueprints.BptContext;
/*     */ import buildcraft.core.builders.BuildingItem;
/*     */ import buildcraft.core.builders.IBuildingItemsProvider;
/*     */ import buildcraft.core.internal.IBoxProvider;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileConstructionMarker
/*     */   extends TileBuildCraft
/*     */   implements IBuildingItemsProvider, IBoxProvider, ICommandReceiver
/*     */ {
/*  46 */   public static HashSet<TileConstructionMarker> currentMarkers = new HashSet<TileConstructionMarker>();
/*     */   
/*  48 */   public ForgeDirection direction = ForgeDirection.UNKNOWN;
/*     */   
/*     */   public LaserData laser;
/*     */   public ItemStack itemBlueprint;
/*  52 */   public Box box = new Box();
/*     */   
/*     */   public BptBuilderBase bluePrintBuilder;
/*     */   
/*     */   public BptContext bptContext;
/*  57 */   private ArrayList<BuildingItem> buildersInAction = new ArrayList<BuildingItem>();
/*     */   
/*     */   private NBTTagCompound initNBT;
/*     */   
/*     */   public void initialize() {
/*  62 */     super.initialize();
/*  63 */     this.box.kind = Box.Kind.BLUE_STRIPES;
/*     */     
/*  65 */     if (this.field_145850_b.field_72995_K) {
/*  66 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "uploadBuildersInAction", null));
/*     */     }
/*     */   }
/*     */   
/*     */   private Packet createLaunchItemPacket(final BuildingItem i) {
/*  71 */     return (Packet)new PacketCommand(this, "launchItem", new CommandWriter() {
/*     */           public void write(ByteBuf data) {
/*  73 */             i.writeData(data);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  80 */     super.func_145845_h();
/*     */     
/*  82 */     BuildingItem toRemove = null;
/*     */     
/*  84 */     for (BuildingItem i : this.buildersInAction) {
/*  85 */       i.update();
/*     */       
/*  87 */       if (i.isDone) {
/*  88 */         toRemove = i;
/*     */       }
/*     */     } 
/*     */     
/*  92 */     if (toRemove != null) {
/*  93 */       this.buildersInAction.remove(toRemove);
/*     */     }
/*     */     
/*  96 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 100 */     if (this.itemBlueprint != null && ItemBlueprint.getId(this.itemBlueprint) != null && this.bluePrintBuilder == null) {
/* 101 */       BlueprintBase bpt = ItemBlueprint.loadBlueprint(this.itemBlueprint);
/* 102 */       if (bpt != null && bpt instanceof Blueprint) {
/* 103 */         bpt = bpt.adjustToWorld(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, this.direction);
/* 104 */         if (bpt != null) {
/* 105 */           this.bluePrintBuilder = (BptBuilderBase)new BptBuilderBlueprint((Blueprint)bpt, this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 106 */           this.bptContext = this.bluePrintBuilder.getContext();
/* 107 */           this.box.initialize((IAreaProvider)this.bluePrintBuilder);
/* 108 */           sendNetworkUpdate();
/*     */         } 
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     if (this.laser == null && this.direction != ForgeDirection.UNKNOWN) {
/* 116 */       this.laser = new LaserData();
/* 117 */       this.laser.head = new Position((this.field_145851_c + 0.5F), (this.field_145848_d + 0.5F), (this.field_145849_e + 0.5F));
/* 118 */       this.laser.tail = new Position((this.field_145851_c + 0.5F + this.direction.offsetX * 0.5F), (this.field_145848_d + 0.5F + this.direction.offsetY * 0.5F), (this.field_145849_e + 0.5F + this.direction.offsetZ * 0.5F));
/*     */ 
/*     */       
/* 121 */       this.laser.isVisible = true;
/* 122 */       sendNetworkUpdate();
/*     */     } 
/*     */     
/* 125 */     if (this.initNBT != null) {
/* 126 */       if (this.bluePrintBuilder != null) {
/* 127 */         this.bluePrintBuilder.loadBuildStateToNBT(this.initNBT.func_74775_l("builderState"), this);
/*     */       }
/*     */       
/* 130 */       this.initNBT = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 136 */     super.func_145841_b(nbt);
/*     */     
/* 138 */     nbt.func_74774_a("direction", (byte)this.direction.ordinal());
/*     */     
/* 140 */     if (this.itemBlueprint != null) {
/* 141 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 142 */       this.itemBlueprint.func_77955_b(nBTTagCompound);
/* 143 */       nbt.func_74782_a("itemBlueprint", (NBTBase)nBTTagCompound);
/*     */     } 
/*     */     
/* 146 */     NBTTagCompound bptNBT = new NBTTagCompound();
/*     */     
/* 148 */     if (this.bluePrintBuilder != null) {
/* 149 */       NBTTagCompound builderCpt = new NBTTagCompound();
/* 150 */       this.bluePrintBuilder.saveBuildStateToNBT(builderCpt, this);
/* 151 */       bptNBT.func_74782_a("builderState", (NBTBase)builderCpt);
/*     */     } 
/*     */     
/* 154 */     nbt.func_74782_a("bptBuilder", (NBTBase)bptNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 159 */     super.func_145839_a(nbt);
/*     */     
/* 161 */     this.direction = ForgeDirection.getOrientation(nbt.func_74771_c("direction"));
/*     */     
/* 163 */     if (nbt.func_74764_b("itemBlueprint")) {
/* 164 */       this.itemBlueprint = ItemStack.func_77949_a(nbt.func_74775_l("itemBlueprint"));
/*     */     }
/*     */ 
/*     */     
/* 168 */     this.initNBT = (NBTTagCompound)nbt.func_74775_l("bptBuilder").func_74737_b();
/*     */   }
/*     */   
/*     */   public void setBlueprint(ItemStack currentItem) {
/* 172 */     this.itemBlueprint = currentItem;
/* 173 */     sendNetworkUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<BuildingItem> getBuilders() {
/* 178 */     return this.buildersInAction;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145829_t() {
/* 183 */     super.func_145829_t();
/* 184 */     if (!this.field_145850_b.field_72995_K) {
/* 185 */       currentMarkers.add(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 191 */     super.func_145843_s();
/* 192 */     if (!this.field_145850_b.field_72995_K) {
/* 193 */       currentMarkers.remove(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean needsToBuild() {
/* 198 */     return (!func_145837_r() && this.bluePrintBuilder != null && !this.bluePrintBuilder.isDone(this));
/*     */   }
/*     */   
/*     */   public BptContext getContext() {
/* 202 */     return this.bptContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAndLaunchBuildingItem(BuildingItem item) {
/* 207 */     this.buildersInAction.add(item);
/* 208 */     BuildCraftCore.instance.sendToPlayersNear(createLaunchItemPacket(item), (TileEntity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 213 */     if (side.isServer() && "uploadBuildersInAction".equals(command)) {
/* 214 */       for (BuildingItem i : this.buildersInAction) {
/* 215 */         BuildCraftCore.instance.sendToPlayer((EntityPlayer)sender, createLaunchItemPacket(i));
/*     */       }
/* 217 */     } else if (side.isClient() && "launchItem".equals(command)) {
/* 218 */       BuildingItem item = new BuildingItem();
/* 219 */       item.readData(stream);
/* 220 */       this.buildersInAction.add(item);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Box getBox() {
/* 226 */     return this.box;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getRenderBoundingBox() {
/* 231 */     Box renderBox = (new Box((TileEntity)this)).extendToEncompass(this.box);
/*     */     
/* 233 */     return renderBox.expand(50).getBoundingBox();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 238 */     this.box.writeData(stream);
/* 239 */     stream.writeByte(((this.laser != null) ? 1 : 0) | ((this.itemBlueprint != null) ? 2 : 0));
/* 240 */     if (this.laser != null) {
/* 241 */       this.laser.writeData(stream);
/*     */     }
/* 243 */     if (this.itemBlueprint != null) {
/* 244 */       NetworkUtils.writeStack(stream, this.itemBlueprint);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 250 */     this.box.readData(stream);
/* 251 */     int flags = stream.readUnsignedByte();
/* 252 */     if ((flags & 0x1) != 0) {
/* 253 */       this.laser = new LaserData();
/* 254 */       this.laser.readData(stream);
/*     */     } else {
/* 256 */       this.laser = null;
/*     */     } 
/* 258 */     if ((flags & 0x2) != 0) {
/* 259 */       this.itemBlueprint = NetworkUtils.readStack(stream);
/*     */     } else {
/* 261 */       this.itemBlueprint = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\TileConstructionMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */