/*     */ package buildcraft.robotics.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftRobotics;
/*     */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*     */ import buildcraft.core.lib.gui.slots.SlotOutput;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.render.DynamicTextureBC;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.robotics.TileZonePlan;
/*     */ import buildcraft.robotics.ZonePlan;
/*     */ import buildcraft.robotics.map.MapWorld;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
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
/*     */ public class ContainerZonePlan
/*     */   extends BuildCraftContainer
/*     */   implements ICommandReceiver
/*     */ {
/*     */   private static final int MAX_PACKET_LENGTH = 30000;
/*     */   public DynamicTextureBC mapTexture;
/*     */   public ZonePlan currentAreaSelection;
/*     */   public GuiZonePlan gui;
/*     */   private TileZonePlan map;
/*     */   
/*     */   public ContainerZonePlan(IInventory playerInventory, TileZonePlan iZonePlan) {
/*  43 */     super(0);
/*     */     
/*  45 */     this.map = iZonePlan;
/*     */     
/*  47 */     func_75146_a(new Slot((IInventory)iZonePlan, 0, 233, 9));
/*  48 */     func_75146_a((Slot)new SlotOutput((IInventory)iZonePlan, 1, 233, 57));
/*  49 */     func_75146_a(new Slot((IInventory)iZonePlan, 2, 8, 125));
/*     */ 
/*     */     
/*  52 */     for (int l = 0; l < 3; l++) {
/*  53 */       for (int k1 = 0; k1 < 9; k1++) {
/*  54 */         func_75146_a(new Slot(playerInventory, k1 + l * 9 + 9, 88 + k1 * 18, 146 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  58 */     for (int i1 = 0; i1 < 9; i1++) {
/*  59 */       func_75146_a(new Slot(playerInventory, i1, 88 + i1 * 18, 204));
/*     */     }
/*     */   }
/*     */   
/*     */   public TileZonePlan getTile() {
/*  64 */     return this.map;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  69 */     return true;
/*     */   }
/*     */   
/*     */   public void loadArea(final int index) {
/*  73 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "loadArea", new CommandWriter() {
/*     */             public void write(ByteBuf data) {
/*  75 */               data.writeByte(index);
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   public void saveArea(final int index) {
/*  81 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "saveArea", new CommandWriter() {
/*     */             public void write(ByteBuf data) {
/*  83 */               data.writeByte(index);
/*  84 */               ContainerZonePlan.this.currentAreaSelection.writeData(data);
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/*  91 */     if (side.isClient()) {
/*  92 */       if ("areaLoaded".equals(command)) {
/*  93 */         this.currentAreaSelection = new ZonePlan();
/*  94 */         this.currentAreaSelection.readData(stream);
/*  95 */         this.gui.refreshSelectedArea();
/*  96 */       } else if ("receiveImage".equals(command)) {
/*  97 */         int size = stream.readUnsignedMedium();
/*  98 */         int pos = stream.readUnsignedMedium();
/*     */         
/* 100 */         for (int i = 0; i < Math.min(size - pos, 30000); i++) {
/* 101 */           this.mapTexture.colorMap[pos + i] = 0xFF000000 | (MapColor.field_76281_a[stream.readUnsignedByte()]).field_76291_p;
/*     */         }
/*     */       } 
/* 104 */     } else if (side.isServer()) {
/* 105 */       if ("loadArea".equals(command)) {
/* 106 */         final int index = stream.readUnsignedByte();
/* 107 */         BuildCraftCore.instance.sendToPlayer((EntityPlayer)sender, (Packet)new PacketCommand(this, "areaLoaded", new CommandWriter() {
/*     */                 public void write(ByteBuf data) {
/* 109 */                   ContainerZonePlan.this.map.selectArea(index).writeData(data);
/*     */                 }
/*     */               }));
/* 112 */       } else if ("saveArea".equals(command)) {
/* 113 */         final int index = stream.readUnsignedByte();
/* 114 */         ZonePlan plan = new ZonePlan();
/* 115 */         plan.readData(stream);
/* 116 */         this.map.setArea(index, plan);
/* 117 */       } else if ("computeMap".equals(command)) {
/* 118 */         computeMap(stream.readInt(), stream.readInt(), stream
/* 119 */             .readUnsignedShort(), stream.readUnsignedShort(), stream
/* 120 */             .readFloat(), (EntityPlayer)sender);
/* 121 */       } else if ("setName".equals(command)) {
/* 122 */         this.map.mapName = NetworkUtils.readUTF(stream);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void computeMap(int cx, int cz, int width, int height, float blocksPerPixel, EntityPlayer player) {
/* 128 */     final byte[] textureData = new byte[width * height];
/*     */     
/* 130 */     MapWorld w = BuildCraftRobotics.manager.getWorld(this.map.func_145831_w());
/* 131 */     int startX = Math.round(cx - width * blocksPerPixel / 2.0F);
/* 132 */     int startZ = Math.round(cz - height * blocksPerPixel / 2.0F);
/* 133 */     int mapStartX = this.map.chunkStartX << 4;
/* 134 */     int mapStartZ = this.map.chunkStartZ << 4;
/*     */     
/* 136 */     for (int i = 0; i < width; i++) {
/* 137 */       for (int k = 0; k < height; k++) {
/* 138 */         int x = Math.round(startX + i * blocksPerPixel);
/* 139 */         int z = Math.round(startZ + k * blocksPerPixel);
/* 140 */         int ix = x - mapStartX;
/* 141 */         int iz = z - mapStartZ;
/*     */         
/* 143 */         if (ix >= 0 && iz >= 0 && ix < 2048 && iz < 2048) {
/* 144 */           textureData[i + k * width] = (byte)w.getColor(x, z);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     int len = 30000;
/*     */     
/* 151 */     for (int j = 0; j < textureData.length; j += 30000) {
/* 152 */       final int pos = j;
/* 153 */       BuildCraftCore.instance.sendToPlayer(player, (Packet)new PacketCommand(this, "receiveImage", new CommandWriter() {
/*     */               public void write(ByteBuf data) {
/* 155 */                 data.writeMedium(textureData.length);
/* 156 */                 data.writeMedium(pos);
/* 157 */                 data.writeBytes(textureData, pos, Math.min(textureData.length - pos, 30000));
/*     */               }
/*     */             }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\gui\ContainerZonePlan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */