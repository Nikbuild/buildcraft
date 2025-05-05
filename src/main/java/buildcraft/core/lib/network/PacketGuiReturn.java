/*     */ package buildcraft.core.lib.network;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.DimensionManager;
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
/*     */ public class PacketGuiReturn
/*     */   extends Packet
/*     */ {
/*     */   private EntityPlayer sender;
/*     */   private IGuiReturnHandler obj;
/*     */   private byte[] extraData;
/*     */   
/*     */   public PacketGuiReturn() {}
/*     */   
/*     */   public PacketGuiReturn(EntityPlayer sender) {
/*  33 */     this.sender = sender;
/*     */   }
/*     */   
/*     */   public PacketGuiReturn(IGuiReturnHandler obj) {
/*  37 */     this.obj = obj;
/*  38 */     this.extraData = null;
/*     */   }
/*     */   
/*     */   public PacketGuiReturn(IGuiReturnHandler obj, byte[] extraData) {
/*  42 */     this.obj = obj;
/*  43 */     this.extraData = extraData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/*  48 */     data.writeInt((this.obj.getWorld()).field_73011_w.field_76574_g);
/*     */     
/*  50 */     if (this.obj instanceof TileEntity) {
/*  51 */       TileEntity tile = (TileEntity)this.obj;
/*  52 */       data.writeBoolean(true);
/*  53 */       data.writeInt(tile.field_145851_c);
/*  54 */       data.writeInt(tile.field_145848_d);
/*  55 */       data.writeInt(tile.field_145849_e);
/*  56 */     } else if (this.obj instanceof Entity) {
/*  57 */       Entity entity = (Entity)this.obj;
/*  58 */       data.writeBoolean(false);
/*  59 */       data.writeInt(entity.func_145782_y());
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/*  64 */     this.obj.writeGuiData(data);
/*     */     
/*  66 */     if (this.extraData != null) {
/*  67 */       data.writeBytes(this.extraData);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/*  73 */     int dim = data.readInt();
/*  74 */     WorldServer worldServer = DimensionManager.getWorld(dim);
/*  75 */     boolean tileReturn = data.readBoolean();
/*     */     
/*  77 */     if (tileReturn) {
/*  78 */       int x = data.readInt();
/*  79 */       int y = data.readInt();
/*  80 */       int z = data.readInt();
/*     */       
/*  82 */       TileEntity t = worldServer.func_147438_o(x, y, z);
/*     */       
/*  84 */       if (t instanceof IGuiReturnHandler) {
/*  85 */         ((IGuiReturnHandler)t).readGuiData(data, this.sender);
/*     */       }
/*     */     } else {
/*  88 */       int entityId = data.readInt();
/*  89 */       Entity entity = worldServer.func_73045_a(entityId);
/*     */       
/*  91 */       if (entity instanceof IGuiReturnHandler) {
/*  92 */         ((IGuiReturnHandler)entity).readGuiData(data, this.sender);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendPacket() {
/*  98 */     BuildCraftCore.instance.sendToServer(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getID() {
/* 103 */     return 80;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketGuiReturn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */