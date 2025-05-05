/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftBuilders;
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.library.LibraryAPI;
/*     */ import buildcraft.api.library.LibraryTypeHandler;
/*     */ import buildcraft.api.library.LibraryTypeHandlerByteArray;
/*     */ import buildcraft.api.library.LibraryTypeHandlerNBT;
/*     */ import buildcraft.core.blueprints.LibraryId;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTSizeTracker;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ 
/*     */ public class TileBlueprintLibrary
/*     */   extends TileBuildCraft
/*     */   implements IInventory, ICommandReceiver
/*     */ {
/*     */   private static final int PROGRESS_TIME = 100;
/*     */   private static final int CHUNK_SIZE = 16384;
/*  49 */   public SimpleInventory inv = new SimpleInventory(4, "Electronic Library", 1);
/*     */   
/*  51 */   public int progressIn = 0;
/*  52 */   public int progressOut = 0;
/*     */   
/*     */   public List<LibraryId> entries;
/*     */   
/*  56 */   public int selected = -1;
/*     */   
/*  58 */   public EntityPlayer uploadingPlayer = null;
/*  59 */   public EntityPlayer downloadingPlayer = null;
/*     */ 
/*     */   
/*     */   private LibraryId blueprintDownloadId;
/*     */ 
/*     */   
/*     */   private byte[] blueprintDownload;
/*     */ 
/*     */   
/*     */   public void refresh() {
/*  69 */     if (this.field_145850_b.field_72995_K) {
/*  70 */       BuildCraftBuilders.clientDB.refresh();
/*  71 */       this.entries = BuildCraftBuilders.clientDB.getBlueprintIds();
/*  72 */       this.selected = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  78 */     super.initialize();
/*     */     
/*  80 */     refresh();
/*     */   }
/*     */   
/*     */   public void deleteSelectedBpt() {
/*  84 */     if (this.selected != -1) {
/*  85 */       BuildCraftBuilders.clientDB.deleteBlueprint(this.entries.get(this.selected));
/*  86 */       this.entries = BuildCraftBuilders.clientDB.getBlueprintIds();
/*  87 */       if (this.selected >= this.entries.size()) {
/*  88 */         this.selected--;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  95 */     super.func_145839_a(nbttagcompound);
/*     */     
/*  97 */     this.inv.readFromNBT(nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 102 */     super.func_145841_b(nbttagcompound);
/*     */     
/* 104 */     this.inv.writeToNBT(nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 109 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/* 114 */     return this.inv.func_70301_a(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int j) {
/* 119 */     ItemStack result = this.inv.func_70298_a(i, j);
/*     */     
/* 121 */     if (i == 0 && 
/* 122 */       func_70301_a(0) == null) {
/* 123 */       this.progressIn = 0;
/*     */     }
/*     */ 
/*     */     
/* 127 */     if (i == 2 && 
/* 128 */       func_70301_a(2) == null) {
/* 129 */       this.progressOut = 0;
/*     */     }
/*     */ 
/*     */     
/* 133 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 138 */     this.inv.func_70299_a(i, itemstack);
/*     */     
/* 140 */     if (i == 0) {
/* 141 */       if (func_70301_a(0) != null && findHandler(0, LibraryTypeHandler.HandlerType.STORE) != null) {
/* 142 */         this.progressIn = 1;
/*     */       } else {
/* 144 */         this.progressIn = 0;
/*     */       } 
/*     */     }
/*     */     
/* 148 */     if (i == 2) {
/* 149 */       if (func_70301_a(2) != null && findHandler(2, LibraryTypeHandler.HandlerType.LOAD) != null) {
/* 150 */         this.progressOut = 1;
/*     */       } else {
/* 152 */         this.progressOut = 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/* 159 */     return this.inv.func_70304_b(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 164 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 169 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/* 179 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */   
/*     */   private LibraryTypeHandler findHandler(int slot, LibraryTypeHandler.HandlerType type) {
/* 191 */     if (!this.field_145850_b.field_72995_K) {
/* 192 */       ItemStack stack = func_70301_a(slot);
/*     */       
/* 194 */       for (LibraryTypeHandler h : LibraryAPI.getHandlerSet()) {
/* 195 */         if (h.isHandler(stack, type)) {
/* 196 */           return h;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 206 */     super.func_145845_h();
/*     */     
/* 208 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 212 */     if (this.progressIn > 0 && this.progressIn < 100) {
/* 213 */       this.progressIn++;
/*     */     }
/*     */     
/* 216 */     if (this.progressOut > 0 && this.progressOut < 100) {
/* 217 */       if (this.selected != -1) {
/* 218 */         this.progressOut++;
/*     */       } else {
/* 220 */         this.progressOut = 1;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 226 */     if (this.progressIn == 100 && func_70301_a(1) == null) {
/* 227 */       LibraryTypeHandler handler = findHandler(0, LibraryTypeHandler.HandlerType.STORE);
/*     */       
/* 229 */       if (handler == null) {
/* 230 */         this.uploadingPlayer = null;
/*     */         
/*     */         return;
/*     */       } 
/* 234 */       byte[] data = null;
/*     */       
/* 236 */       if (handler instanceof LibraryTypeHandlerNBT) {
/* 237 */         NBTTagCompound nbt = new NBTTagCompound();
/* 238 */         if (((LibraryTypeHandlerNBT)handler).store(func_70301_a(0), nbt)) {
/* 239 */           data = NBTUtils.save(nbt);
/*     */         }
/* 241 */       } else if (handler instanceof LibraryTypeHandlerByteArray) {
/* 242 */         data = ((LibraryTypeHandlerByteArray)handler).store(func_70301_a(0));
/*     */       } 
/*     */       
/* 245 */       if (data == null) {
/* 246 */         this.uploadingPlayer = null;
/*     */         
/*     */         return;
/*     */       } 
/* 250 */       func_70299_a(1, func_70301_a(0));
/* 251 */       func_70299_a(0, (ItemStack)null);
/*     */       
/* 253 */       final byte[] dataOut = data;
/* 254 */       final LibraryId id = new LibraryId();
/* 255 */       id.name = handler.getName(func_70301_a(1));
/* 256 */       id.extension = handler.getOutputExtension();
/*     */       
/* 258 */       if (this.uploadingPlayer != null) {
/* 259 */         BuildCraftCore.instance.sendToPlayer(this.uploadingPlayer, (Packet)new PacketCommand(this, "downloadBlueprintToClient", new CommandWriter()
/*     */               {
/*     */                 public void write(ByteBuf data) {
/* 262 */                   id.generateUniqueId(dataOut);
/* 263 */                   id.writeData(data);
/* 264 */                   NetworkUtils.writeByteArray(data, dataOut);
/*     */                 }
/*     */               }));
/* 267 */         this.uploadingPlayer = null;
/*     */       } 
/*     */     } 
/*     */     
/* 271 */     if (this.progressOut == 100 && func_70301_a(3) == null) {
/* 272 */       BuildCraftCore.instance.sendToPlayer(this.downloadingPlayer, (Packet)new PacketCommand(this, "requestSelectedBlueprint", null));
/* 273 */       this.progressOut = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 284 */     if (side.isClient()) {
/* 285 */       if ("requestSelectedBlueprint".equals(command)) {
/* 286 */         if (isOutputConsistent()) {
/* 287 */           if (this.selected > -1 && this.selected < this.entries.size()) {
/*     */ 
/*     */             
/* 290 */             NBTTagCompound compound = BuildCraftBuilders.clientDB.load(this.entries.get(this.selected));
/* 291 */             compound.func_74778_a("__filename", ((LibraryId)this.entries.get(this.selected)).name);
/* 292 */             final byte[] bptData = NBTUtils.save(compound);
/* 293 */             final int chunks = (bptData.length + 16384 - 1) / 16384;
/*     */             
/* 295 */             BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "uploadServerBegin", new CommandWriter()
/*     */                   {
/*     */                     public void write(ByteBuf data) {
/* 298 */                       ((LibraryId)TileBlueprintLibrary.this.entries.get(TileBlueprintLibrary.this.selected)).writeData(data);
/* 299 */                       data.writeShort(chunks);
/*     */                     }
/*     */                   }));
/*     */             
/* 303 */             for (int i = 0; i < chunks; i++) {
/* 304 */               final int chunk = i;
/* 305 */               final int start = 16384 * chunk;
/* 306 */               final int length = Math.min(16384, bptData.length - start);
/* 307 */               BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "uploadServerChunk", new CommandWriter()
/*     */                     {
/*     */                       public void write(ByteBuf data) {
/* 310 */                         data.writeShort(chunk);
/* 311 */                         data.writeShort(length);
/* 312 */                         data.writeBytes(bptData, start, length);
/*     */                       }
/*     */                     }));
/*     */             } 
/*     */             
/* 317 */             BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "uploadServerEnd", null));
/*     */           } else {
/* 319 */             BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "uploadNothingToServer", null));
/*     */           } 
/*     */         }
/* 322 */       } else if ("downloadBlueprintToClient".equals(command)) {
/* 323 */         LibraryId id = new LibraryId();
/* 324 */         id.readData(stream);
/* 325 */         byte[] data = NetworkUtils.readByteArray(stream);
/*     */         
/*     */         try {
/* 328 */           LibraryTypeHandler handler = LibraryAPI.getHandlerFor(id.extension);
/* 329 */           if (handler == null) {
/*     */             return;
/*     */           }
/*     */           
/* 333 */           NBTTagCompound nbt = CompressedStreamTools.func_152457_a(data, NBTSizeTracker.field_152451_a);
/* 334 */           BuildCraftBuilders.clientDB.add(id, nbt);
/* 335 */           this.entries = BuildCraftBuilders.clientDB.getBlueprintIds();
/* 336 */         } catch (IOException e) {
/* 337 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/* 340 */     } else if (side.isServer()) {
/* 341 */       if ("uploadNothingToServer".equals(command)) {
/* 342 */         func_70299_a(3, func_70301_a(2));
/* 343 */         func_70299_a(2, (ItemStack)null);
/*     */         
/* 345 */         this.downloadingPlayer = null;
/* 346 */       } else if ("uploadServerBegin".equals(command)) {
/* 347 */         this.blueprintDownloadId = new LibraryId();
/* 348 */         this.blueprintDownloadId.readData(stream);
/* 349 */         this.blueprintDownload = new byte[16384 * stream.readUnsignedShort()];
/* 350 */       } else if ("uploadServerChunk".equals(command)) {
/* 351 */         final int start = stream.readUnsignedShort() * 16384;
/* 352 */         final int length = stream.readUnsignedShort();
/* 353 */         if (this.blueprintDownload != null) {
/* 354 */           stream.readBytes(this.blueprintDownload, start, length);
/*     */         } else {
/* 356 */           stream.skipBytes(length);
/*     */         } 
/* 358 */       } else if ("uploadServerEnd".equals(command)) {
/*     */         try {
/* 360 */           LibraryTypeHandler handler = LibraryAPI.getHandlerFor(this.blueprintDownloadId.extension);
/*     */           
/* 362 */           if (handler != null) {
/* 363 */             ItemStack output = null;
/*     */             
/* 365 */             if (handler instanceof LibraryTypeHandlerNBT) {
/* 366 */               NBTTagCompound nbt = CompressedStreamTools.func_152457_a(this.blueprintDownload, NBTSizeTracker.field_152451_a);
/* 367 */               output = ((LibraryTypeHandlerNBT)handler).load(func_70301_a(2), nbt);
/* 368 */             } else if (handler instanceof LibraryTypeHandlerByteArray) {
/* 369 */               output = ((LibraryTypeHandlerByteArray)handler).load(func_70301_a(2), this.blueprintDownload);
/*     */             } 
/*     */             
/* 372 */             if (output != null) {
/* 373 */               func_70299_a(3, output);
/* 374 */               func_70299_a(2, (ItemStack)null);
/*     */             } 
/*     */           } 
/* 377 */         } catch (IOException e) {
/* 378 */           e.printStackTrace();
/*     */         } 
/*     */         
/* 381 */         this.blueprintDownloadId = null;
/* 382 */         this.blueprintDownload = null;
/* 383 */         this.downloadingPlayer = null;
/* 384 */       } else if ("selectBlueprint".equals(command)) {
/* 385 */         this.selected = stream.readInt();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void selectBlueprint(int index) {
/* 391 */     this.selected = index;
/* 392 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "selectBlueprint", new CommandWriter()
/*     */           {
/*     */             public void write(ByteBuf data) {
/* 395 */               data.writeInt(TileBlueprintLibrary.this.selected);
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   private boolean isOutputConsistent() {
/* 401 */     if (this.selected <= -1 || this.selected >= this.entries.size() || func_70301_a(2) == null) {
/* 402 */       return false;
/*     */     }
/*     */     
/* 405 */     return LibraryAPI.getHandlerFor(((LibraryId)this.entries.get(this.selected)).extension).isHandler(func_70301_a(2), LibraryTypeHandler.HandlerType.LOAD);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\TileBlueprintLibrary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */