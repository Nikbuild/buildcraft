/*     */ package buildcraft.transport.network;
/*     */ 
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.lib.network.PacketCoordinates;
/*     */ import buildcraft.core.lib.utils.BitSetUtils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeTransportFluids;
/*     */ import buildcraft.transport.utils.FluidRenderData;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.BitSet;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
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
/*     */ public class PacketFluidUpdate
/*     */   extends PacketCoordinates
/*     */ {
/*  30 */   public FluidRenderData renderCache = new FluidRenderData();
/*     */   public BitSet delta;
/*     */   private boolean largeFluidCapacity;
/*     */   
/*     */   public PacketFluidUpdate(int xCoord, int yCoord, int zCoord) {
/*  35 */     super(3, xCoord, yCoord, zCoord);
/*     */   }
/*     */   
/*     */   public PacketFluidUpdate(int xCoord, int yCoord, int zCoord, boolean chunkPacket, boolean largeFluidCapacity) {
/*  39 */     super(3, xCoord, yCoord, zCoord);
/*  40 */     this.isChunkDataPacket = chunkPacket;
/*  41 */     this.largeFluidCapacity = largeFluidCapacity;
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketFluidUpdate() {}
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/*  49 */     super.readData(data);
/*     */     
/*  51 */     World world = CoreProxy.proxy.getClientWorld();
/*  52 */     if (!world.func_72899_e(this.posX, this.posY, this.posZ)) {
/*     */       return;
/*     */     }
/*     */     
/*  56 */     TileEntity entity = world.func_147438_o(this.posX, this.posY, this.posZ);
/*  57 */     if (!(entity instanceof IPipeTile)) {
/*     */       return;
/*     */     }
/*     */     
/*  61 */     IPipeTile pipeTile = (IPipeTile)entity;
/*  62 */     if (!(pipeTile.getPipe() instanceof Pipe)) {
/*     */       return;
/*     */     }
/*     */     
/*  66 */     Pipe<?> pipe = (Pipe)pipeTile.getPipe();
/*     */     
/*  68 */     if (!(pipe.transport instanceof PipeTransportFluids)) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     PipeTransportFluids transLiq = (PipeTransportFluids)pipe.transport;
/*     */     
/*  74 */     this.largeFluidCapacity = (transLiq.getCapacity() > 255);
/*  75 */     this.renderCache = transLiq.renderCache;
/*     */     
/*  77 */     byte[] dBytes = new byte[1];
/*  78 */     data.readBytes(dBytes);
/*  79 */     this.delta = BitSetUtils.fromByteArray(dBytes);
/*     */     
/*  81 */     if (this.delta.get(0)) {
/*  82 */       this.renderCache.fluidID = data.readShort();
/*  83 */       this.renderCache.color = (this.renderCache.fluidID != 0) ? data.readInt() : 16777215;
/*  84 */       this.renderCache.flags = (this.renderCache.fluidID != 0) ? data.readUnsignedByte() : 0;
/*     */     } 
/*     */     
/*  87 */     for (ForgeDirection dir : ForgeDirection.values()) {
/*  88 */       if (this.delta.get(dir.ordinal() + 1)) {
/*  89 */         this.renderCache.amount[dir.ordinal()] = Math.min(transLiq.getCapacity(), this.largeFluidCapacity ? data
/*  90 */             .readUnsignedShort() : data.readUnsignedByte());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/*  97 */     super.writeData(data);
/*     */     
/*  99 */     byte[] dBytes = BitSetUtils.toByteArray(this.delta, 1);
/* 100 */     data.writeBytes(dBytes);
/*     */     
/* 102 */     if (this.delta.get(0)) {
/* 103 */       data.writeShort(this.renderCache.fluidID);
/* 104 */       if (this.renderCache.fluidID != 0) {
/* 105 */         data.writeInt(this.renderCache.color);
/* 106 */         data.writeByte(this.renderCache.flags);
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     for (ForgeDirection dir : ForgeDirection.values()) {
/* 111 */       if (this.delta.get(dir.ordinal() + 1)) {
/* 112 */         if (this.largeFluidCapacity) {
/* 113 */           data.writeShort(this.renderCache.amount[dir.ordinal()]);
/*     */         } else {
/* 115 */           data.writeByte(this.renderCache.amount[dir.ordinal()]);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getID() {
/* 123 */     return 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\network\PacketFluidUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */