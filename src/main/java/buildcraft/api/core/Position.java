/*     */ package buildcraft.api.core;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Position
/*     */   implements ISerializable
/*     */ {
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public ForgeDirection orientation;
/*     */   
/*     */   public Position() {
/*  22 */     this.x = 0.0D;
/*  23 */     this.y = 0.0D;
/*  24 */     this.z = 0.0D;
/*  25 */     this.orientation = ForgeDirection.UNKNOWN;
/*     */   }
/*     */   
/*     */   public Position(double ci, double cj, double ck) {
/*  29 */     this.x = ci;
/*  30 */     this.y = cj;
/*  31 */     this.z = ck;
/*  32 */     this.orientation = ForgeDirection.UNKNOWN;
/*     */   }
/*     */   
/*     */   public Position(double ci, double cj, double ck, ForgeDirection corientation) {
/*  36 */     this.x = ci;
/*  37 */     this.y = cj;
/*  38 */     this.z = ck;
/*  39 */     this.orientation = corientation;
/*     */     
/*  41 */     if (this.orientation == null) {
/*  42 */       this.orientation = ForgeDirection.UNKNOWN;
/*     */     }
/*     */   }
/*     */   
/*     */   public Position(Position p) {
/*  47 */     this.x = p.x;
/*  48 */     this.y = p.y;
/*  49 */     this.z = p.z;
/*  50 */     this.orientation = p.orientation;
/*     */   }
/*     */   
/*     */   public Position(NBTTagCompound nbttagcompound) {
/*  54 */     readFromNBT(nbttagcompound);
/*     */   }
/*     */   
/*     */   public Position(TileEntity tile) {
/*  58 */     this.x = tile.field_145851_c;
/*  59 */     this.y = tile.field_145848_d;
/*  60 */     this.z = tile.field_145849_e;
/*  61 */     this.orientation = ForgeDirection.UNKNOWN;
/*     */   }
/*     */   
/*     */   public Position(BlockIndex index) {
/*  65 */     this.x = index.x;
/*  66 */     this.y = index.y;
/*  67 */     this.z = index.z;
/*  68 */     this.orientation = ForgeDirection.UNKNOWN;
/*     */   }
/*     */   
/*     */   public void moveRight(double step) {
/*  72 */     switch (this.orientation) {
/*     */       case SOUTH:
/*  74 */         this.x -= step;
/*     */         break;
/*     */       case NORTH:
/*  77 */         this.x += step;
/*     */         break;
/*     */       case EAST:
/*  80 */         this.z += step;
/*     */         break;
/*     */       case WEST:
/*  83 */         this.z -= step;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveLeft(double step) {
/*  90 */     moveRight(-step);
/*     */   }
/*     */   
/*     */   public void moveForwards(double step) {
/*  94 */     switch (this.orientation) {
/*     */       case UP:
/*  96 */         this.y += step;
/*     */         break;
/*     */       case DOWN:
/*  99 */         this.y -= step;
/*     */         break;
/*     */       case SOUTH:
/* 102 */         this.z += step;
/*     */         break;
/*     */       case NORTH:
/* 105 */         this.z -= step;
/*     */         break;
/*     */       case EAST:
/* 108 */         this.x += step;
/*     */         break;
/*     */       case WEST:
/* 111 */         this.x -= step;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveBackwards(double step) {
/* 118 */     moveForwards(-step);
/*     */   }
/*     */   
/*     */   public void moveUp(double step) {
/* 122 */     switch (this.orientation) {
/*     */       case SOUTH:
/*     */       case NORTH:
/*     */       case EAST:
/*     */       case WEST:
/* 127 */         this.y += step;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveDown(double step) {
/* 135 */     moveUp(-step);
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound) {
/* 139 */     if (this.orientation == null) {
/* 140 */       this.orientation = ForgeDirection.UNKNOWN;
/*     */     }
/*     */     
/* 143 */     nbttagcompound.func_74780_a("i", this.x);
/* 144 */     nbttagcompound.func_74780_a("j", this.y);
/* 145 */     nbttagcompound.func_74780_a("k", this.z);
/* 146 */     nbttagcompound.func_74774_a("orientation", (byte)this.orientation.ordinal());
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound) {
/* 150 */     this.x = nbttagcompound.func_74769_h("i");
/* 151 */     this.y = nbttagcompound.func_74769_h("j");
/* 152 */     this.z = nbttagcompound.func_74769_h("k");
/* 153 */     this.orientation = ForgeDirection.values()[nbttagcompound.func_74771_c("orientation")];
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     return "{" + this.x + ", " + this.y + ", " + this.z + "}";
/*     */   }
/*     */   
/*     */   public Position min(Position p) {
/* 162 */     return new Position((p.x > this.x) ? this.x : p.x, (p.y > this.y) ? this.y : p.y, (p.z > this.z) ? this.z : p.z);
/*     */   }
/*     */   
/*     */   public Position max(Position p) {
/* 166 */     return new Position((p.x < this.x) ? this.x : p.x, (p.y < this.y) ? this.y : p.y, (p.z < this.z) ? this.z : p.z);
/*     */   }
/*     */   
/*     */   public boolean isClose(Position newPosition, float f) {
/* 170 */     double dx = this.x - newPosition.x;
/* 171 */     double dy = this.y - newPosition.y;
/* 172 */     double dz = this.z - newPosition.z;
/*     */     
/* 174 */     double sqrDis = dx * dx + dy * dy + dz * dz;
/*     */     
/* 176 */     return (sqrDis <= (f * f));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 181 */     this.x = stream.readDouble();
/* 182 */     this.y = stream.readDouble();
/* 183 */     this.z = stream.readDouble();
/* 184 */     this.orientation = ForgeDirection.getOrientation(stream.readByte());
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 189 */     stream.writeDouble(this.x);
/* 190 */     stream.writeDouble(this.y);
/* 191 */     stream.writeDouble(this.z);
/* 192 */     stream.writeByte(this.orientation.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 197 */     return 51 * (int)this.x + 13 * (int)this.y + (int)this.z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\core\Position.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */