/*     */ package buildcraft.transport.network;
/*     */ 
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.MathHelper;
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
/*     */ public class PacketPipeTransportTraveler
/*     */   extends Packet
/*     */ {
/*     */   public int posX;
/*     */   public int posY;
/*     */   public int posZ;
/*     */   private TravelingItem item;
/*     */   private boolean forceStackRefresh;
/*     */   private int entityId;
/*     */   private ForgeDirection input;
/*     */   private ForgeDirection output;
/*     */   private EnumColor color;
/*     */   private float itemX;
/*     */   private float itemY;
/*     */   private float itemZ;
/*     */   private float speed;
/*     */   
/*     */   public PacketPipeTransportTraveler() {}
/*     */   
/*     */   public PacketPipeTransportTraveler(TravelingItem item, boolean forceStackRefresh) {
/*  43 */     this.item = item;
/*  44 */     this.forceStackRefresh = forceStackRefresh;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/*  49 */     data.writeFloat((float)this.item.xCoord);
/*  50 */     data.writeFloat((float)this.item.yCoord);
/*  51 */     data.writeFloat((float)this.item.zCoord);
/*     */     
/*  53 */     data.writeShort(this.item.id);
/*     */     
/*  55 */     byte flags = (byte)(this.item.output.ordinal() & 0x7 | (this.item.input.ordinal() & 0x7) << 3 | (this.forceStackRefresh ? 64 : 0));
/*  56 */     data.writeByte(flags);
/*     */     
/*  58 */     data.writeByte((this.item.color != null) ? this.item.color.ordinal() : -1);
/*     */     
/*  60 */     data.writeFloat(this.item.getSpeed());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/*  65 */     this.itemX = data.readFloat();
/*  66 */     this.itemY = data.readFloat();
/*  67 */     this.itemZ = data.readFloat();
/*     */     
/*  69 */     this.posX = MathHelper.func_76141_d(this.itemX);
/*  70 */     this.posY = MathHelper.func_76141_d(this.itemY);
/*  71 */     this.posZ = MathHelper.func_76141_d(this.itemZ);
/*     */     
/*  73 */     this.entityId = data.readShort();
/*     */     
/*  75 */     int flags = data.readUnsignedByte();
/*     */     
/*  77 */     this.input = ForgeDirection.getOrientation(flags >> 3 & 0x7);
/*  78 */     this.output = ForgeDirection.getOrientation(flags & 0x7);
/*     */     
/*  80 */     byte c = data.readByte();
/*  81 */     if (c != -1) {
/*  82 */       this.color = EnumColor.fromId(c);
/*     */     }
/*     */     
/*  85 */     this.speed = data.readFloat();
/*     */     
/*  87 */     this.forceStackRefresh = ((flags & 0x40) > 0);
/*     */   }
/*     */   
/*     */   public int getTravelingEntityId() {
/*  91 */     return this.entityId;
/*     */   }
/*     */   
/*     */   public ForgeDirection getInputOrientation() {
/*  95 */     return this.input;
/*     */   }
/*     */   
/*     */   public ForgeDirection getOutputOrientation() {
/*  99 */     return this.output;
/*     */   }
/*     */   
/*     */   public EnumColor getColor() {
/* 103 */     return this.color;
/*     */   }
/*     */   
/*     */   public double getItemX() {
/* 107 */     return this.itemX;
/*     */   }
/*     */   
/*     */   public double getItemY() {
/* 111 */     return this.itemY;
/*     */   }
/*     */   
/*     */   public double getItemZ() {
/* 115 */     return this.itemZ;
/*     */   }
/*     */   
/*     */   public float getSpeed() {
/* 119 */     return this.speed;
/*     */   }
/*     */   
/*     */   public boolean forceStackRefresh() {
/* 123 */     return this.forceStackRefresh;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getID() {
/* 128 */     return 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\network\PacketPipeTransportTraveler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */