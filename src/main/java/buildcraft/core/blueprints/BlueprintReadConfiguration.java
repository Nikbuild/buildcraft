/*    */ package buildcraft.core.blueprints;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlueprintReadConfiguration
/*    */   implements ISerializable
/*    */ {
/*    */   public boolean rotate = true;
/*    */   public boolean excavate = true;
/*    */   public boolean allowCreative = false;
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbttagcompound) {
/* 23 */     nbttagcompound.func_74757_a("rotate", this.rotate);
/* 24 */     nbttagcompound.func_74757_a("excavate", this.excavate);
/* 25 */     nbttagcompound.func_74757_a("allowCreative", this.allowCreative);
/*    */   }
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbttagcompound) {
/* 29 */     this.rotate = nbttagcompound.func_74767_n("rotate");
/* 30 */     this.excavate = nbttagcompound.func_74767_n("excavate");
/* 31 */     this.allowCreative = nbttagcompound.func_74767_n("allowCreative");
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf stream) {
/* 36 */     int flags = stream.readUnsignedByte();
/* 37 */     this.rotate = ((flags & 0x1) != 0);
/* 38 */     this.excavate = ((flags & 0x2) != 0);
/* 39 */     this.allowCreative = ((flags & 0x4) != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf stream) {
/* 44 */     stream.writeByte((this.rotate ? 1 : 0) | (this.excavate ? 2 : 0) | (this.allowCreative ? 4 : 0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\BlueprintReadConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */