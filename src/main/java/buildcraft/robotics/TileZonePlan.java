/*     */ package buildcraft.robotics;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftRobotics;
/*     */ import buildcraft.api.core.IZone;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.api.items.IMapLocation;
/*     */ import buildcraft.api.items.INamedItem;
/*     */ import buildcraft.core.ItemMapLocation;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.robotics.gui.ContainerZonePlan;
/*     */ import buildcraft.robotics.map.MapWorld;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
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
/*     */ public class TileZonePlan
/*     */   extends TileBuildCraft
/*     */   implements IInventory
/*     */ {
/*     */   public static final int RESOLUTION = 2048;
/*     */   public static final int CRAFT_TIME = 120;
/*     */   private static final int PREVIEW_BLOCKS_PER_PIXEL = 10;
/*  43 */   private static int RESOLUTION_CHUNKS = 128;
/*     */   public int chunkStartX;
/*     */   public int chunkStartZ;
/*  46 */   public short progress = 0;
/*  47 */   public String mapName = "";
/*     */   
/*  49 */   private final byte[] previewColors = new byte[80];
/*  50 */   private final SimpleInventory inv = new SimpleInventory(3, "inv", 64);
/*  51 */   private final SafeTimeTracker previewRecalcTimer = new SafeTimeTracker(100L);
/*     */   
/*     */   private boolean previewColorsPushed = false;
/*  54 */   private ZonePlan[] selectedAreas = new ZonePlan[16];
/*  55 */   private int currentSelectedArea = 0;
/*     */   
/*     */   public byte[] getPreviewTexture(boolean force) {
/*  58 */     if (!this.previewColorsPushed || force) {
/*  59 */       this.previewColorsPushed = true;
/*  60 */       return this.previewColors;
/*     */     } 
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  67 */     super.initialize();
/*     */     
/*  69 */     int cx = this.field_145851_c >> 4;
/*  70 */     int cz = this.field_145849_e >> 4;
/*     */     
/*  72 */     this.chunkStartX = cx - RESOLUTION_CHUNKS / 2;
/*  73 */     this.chunkStartZ = cz - RESOLUTION_CHUNKS / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  78 */     super.func_145845_h();
/*     */     
/*  80 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     if (this.previewRecalcTimer.markTimeIfDelay(this.field_145850_b)) {
/*  85 */       recalculatePreview();
/*     */     }
/*     */     
/*  88 */     if (this.inv.func_70301_a(0) != null && this.inv
/*  89 */       .func_70301_a(1) == null && this.inv
/*  90 */       .func_70301_a(0).func_77973_b() instanceof ItemMapLocation) {
/*     */       
/*  92 */       if (this.progress < 120) {
/*  93 */         this.progress = (short)(this.progress + 1);
/*     */         
/*  95 */         if (this.field_145850_b.func_82737_E() % 5L == 0L) {
/*  96 */           sendNetworkUpdate();
/*     */         }
/*     */       } else {
/*  99 */         ItemStack stack = this.inv.func_70298_a(0, 1);
/*     */         
/* 101 */         if (this.selectedAreas[this.currentSelectedArea] != null) {
/* 102 */           ItemMapLocation.setZone(stack, this.selectedAreas[this.currentSelectedArea]);
/* 103 */           ((INamedItem)stack.func_77973_b()).setName(stack, this.mapName);
/*     */         } 
/*     */         
/* 106 */         this.inv.func_70299_a(1, stack);
/*     */       } 
/* 108 */     } else if (this.progress != 0) {
/* 109 */       this.progress = 0;
/* 110 */       sendNetworkUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void recalculatePreview() {
/* 115 */     byte[] newPreviewColors = new byte[80];
/* 116 */     MapWorld mw = BuildCraftRobotics.manager.getWorld(this.field_145850_b);
/*     */     
/* 118 */     for (int y = 0; y < 8; y++) {
/* 119 */       for (int x = 0; x < 10; x++) {
/* 120 */         int tx = x * 10 - 50 + 5;
/* 121 */         int ty = y * 10 - 40 + 5;
/* 122 */         newPreviewColors[y * 10 + x] = (byte)mw.getColor(this.field_145851_c - this.field_145851_c % 10 + tx, this.field_145849_e - this.field_145849_e % 10 + ty);
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     if (!Arrays.equals(this.previewColors, newPreviewColors)) {
/* 127 */       System.arraycopy(newPreviewColors, 0, this.previewColors, 0, 80);
/* 128 */       sendNetworkUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 134 */     super.func_145841_b(nbt);
/* 135 */     nbt.func_74778_a("name", this.mapName);
/*     */     
/* 137 */     NBTTagCompound invNBT = new NBTTagCompound();
/* 138 */     this.inv.writeToNBT(invNBT);
/* 139 */     nbt.func_74782_a("inv", (NBTBase)invNBT);
/*     */     
/* 141 */     for (int i = 0; i < this.selectedAreas.length; i++) {
/* 142 */       if (this.selectedAreas[i] != null) {
/* 143 */         NBTTagCompound subNBT = new NBTTagCompound();
/* 144 */         this.selectedAreas[i].writeToNBT(subNBT);
/* 145 */         nbt.func_74782_a("selectedArea[" + i + "]", (NBTBase)subNBT);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 152 */     super.func_145839_a(nbt);
/*     */     
/* 154 */     this.mapName = nbt.func_74779_i("name");
/*     */     
/* 156 */     if (this.mapName == null) {
/* 157 */       this.mapName = "";
/*     */     }
/*     */     
/* 160 */     this.inv.readFromNBT(nbt.func_74775_l("inv"));
/*     */     
/* 162 */     for (int i = 0; i < this.selectedAreas.length; i++) {
/* 163 */       if (nbt.func_74764_b("selectedArea[" + i + "]")) {
/* 164 */         this.selectedAreas[i] = new ZonePlan();
/* 165 */         this.selectedAreas[i].readFromNBT(nbt.func_74775_l("selectedArea[" + i + "]"));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 172 */     stream.writeShort(this.progress);
/* 173 */     NetworkUtils.writeUTF(stream, this.mapName);
/* 174 */     stream.writeBytes(this.previewColors, 0, 80);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 179 */     this.progress = stream.readShort();
/* 180 */     this.mapName = NetworkUtils.readUTF(stream);
/* 181 */     stream.readBytes(this.previewColors, 0, 80);
/* 182 */     this.previewColorsPushed = false;
/*     */   }
/*     */   
/*     */   private void importMap(ItemStack stack) {
/* 186 */     if (stack != null && stack.func_77973_b() instanceof IMapLocation) {
/* 187 */       final IZone zone = ((IMapLocation)stack.func_77973_b()).getZone(stack);
/* 188 */       if (zone != null && zone instanceof ZonePlan) {
/* 189 */         this.selectedAreas[this.currentSelectedArea] = (ZonePlan)zone;
/*     */         
/* 191 */         for (EntityPlayer e : (MinecraftServer.func_71276_C().func_71203_ab()).field_72404_b) {
/* 192 */           if (e.field_71070_bA != null && e.field_71070_bA instanceof ContainerZonePlan && ((ContainerZonePlan)e.field_71070_bA)
/* 193 */             .getTile() == this) {
/* 194 */             PacketCommand packetCommand = new PacketCommand(e.field_71070_bA, "areaLoaded", new CommandWriter() {
/*     */                   public void write(ByteBuf data) {
/* 196 */                     ((ZonePlan)zone).writeData(data);
/*     */                   }
/*     */                 });
/*     */             
/* 200 */             BuildCraftCore.instance.sendToPlayer(e, (Packet)packetCommand);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ZonePlan selectArea(int index) {
/* 208 */     if (this.selectedAreas[index] == null) {
/* 209 */       this.selectedAreas[index] = new ZonePlan();
/*     */     }
/*     */     
/* 212 */     this.currentSelectedArea = index;
/*     */     
/* 214 */     return this.selectedAreas[index];
/*     */   }
/*     */   
/*     */   public void setArea(int index, ZonePlan area) {
/* 218 */     this.selectedAreas[index] = area;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 223 */     return this.inv.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slotId) {
/* 228 */     return this.inv.func_70301_a(slotId);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slotId, int count) {
/* 233 */     return this.inv.func_70298_a(slotId, count);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slotId) {
/* 238 */     return this.inv.func_70304_b(slotId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slotId, ItemStack itemstack) {
/* 243 */     this.inv.func_70299_a(slotId, itemstack);
/*     */     
/* 245 */     if (!this.field_145850_b.field_72995_K && slotId == 2) {
/* 246 */       importMap(itemstack);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 252 */     return this.inv.func_145825_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 257 */     return this.inv.func_145818_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 262 */     return this.inv.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/* 267 */     return this.inv.func_70300_a(entityplayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {
/* 272 */     this.inv.func_70295_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70305_f() {
/* 277 */     this.inv.func_70305_f();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 282 */     return this.inv.func_94041_b(i, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\TileZonePlan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */