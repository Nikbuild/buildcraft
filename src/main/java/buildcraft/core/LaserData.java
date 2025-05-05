/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import buildcraft.api.core.Position;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LaserData
/*    */   implements ISerializable
/*    */ {
/* 19 */   public Position head = new Position(0.0D, 0.0D, 0.0D);
/* 20 */   public Position tail = new Position(0.0D, 0.0D, 0.0D);
/*    */   
/*    */   public boolean isVisible = true;
/*    */   public boolean isGlowing = false;
/* 24 */   public double renderSize = 0.0625D;
/* 25 */   public double angleY = 0.0D;
/* 26 */   public double angleZ = 0.0D;
/*    */   
/* 28 */   public double wavePosition = 0.0D;
/* 29 */   public int laserTexAnimation = 0;
/*    */ 
/*    */   
/* 32 */   public float waveSize = 1.0F;
/*    */ 
/*    */   
/*    */   public LaserData() {}
/*    */ 
/*    */   
/*    */   public LaserData(Position tail, Position head) {
/* 39 */     this.tail.x = tail.x;
/* 40 */     this.tail.y = tail.y;
/* 41 */     this.tail.z = tail.z;
/*    */     
/* 43 */     this.head.x = head.x;
/* 44 */     this.head.y = head.y;
/* 45 */     this.head.z = head.z;
/*    */   }
/*    */   
/*    */   public void update() {
/* 49 */     double dx = this.head.x - this.tail.x;
/* 50 */     double dy = this.head.y - this.tail.y;
/* 51 */     double dz = this.head.z - this.tail.z;
/*    */     
/* 53 */     this.renderSize = Math.sqrt(dx * dx + dy * dy + dz * dz);
/* 54 */     this.angleZ = 360.0D - Math.atan2(dz, dx) * 180.0D / Math.PI + 180.0D;
/* 55 */     dx = Math.sqrt(this.renderSize * this.renderSize - dy * dy);
/* 56 */     this.angleY = -Math.atan2(dy, dx) * 180.0D / Math.PI;
/*    */   }
/*    */   
/*    */   public void iterateTexture() {
/* 60 */     this.laserTexAnimation = (this.laserTexAnimation + 1) % 40;
/*    */   }
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt) {
/* 64 */     NBTTagCompound headNbt = new NBTTagCompound();
/* 65 */     this.head.writeToNBT(headNbt);
/* 66 */     nbt.func_74782_a("head", (NBTBase)headNbt);
/*    */     
/* 68 */     NBTTagCompound tailNbt = new NBTTagCompound();
/* 69 */     this.tail.writeToNBT(tailNbt);
/* 70 */     nbt.func_74782_a("tail", (NBTBase)tailNbt);
/*    */     
/* 72 */     nbt.func_74757_a("isVisible", this.isVisible);
/*    */   }
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbt) {
/* 76 */     this.head.readFromNBT(nbt.func_74775_l("head"));
/* 77 */     this.tail.readFromNBT(nbt.func_74775_l("tail"));
/* 78 */     this.isVisible = nbt.func_74767_n("isVisible");
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf stream) {
/* 83 */     this.head.readData(stream);
/* 84 */     this.tail.readData(stream);
/* 85 */     int flags = stream.readUnsignedByte();
/* 86 */     this.isVisible = ((flags & 0x1) != 0);
/* 87 */     this.isGlowing = ((flags & 0x2) != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf stream) {
/* 92 */     this.head.writeData(stream);
/* 93 */     this.tail.writeData(stream);
/* 94 */     int flags = (this.isVisible ? 1 : 0) | (this.isGlowing ? 2 : 0);
/* 95 */     stream.writeByte(flags);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\LaserData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */