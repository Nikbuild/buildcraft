/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import buildcraft.api.core.Position;
/*    */ import buildcraft.core.lib.utils.NetworkUtils;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StackAtPosition
/*    */   implements ISerializable
/*    */ {
/*    */   public ItemStack stack;
/*    */   public Position pos;
/*    */   public boolean display;
/*    */   public boolean generatedListId;
/*    */   public int glListId;
/*    */   
/*    */   public void readData(ByteBuf stream) {
/* 30 */     this.stack = NetworkUtils.readStack(stream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf stream) {
/* 35 */     NetworkUtils.writeStack(stream, this.stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 40 */     if (o == null || !(o instanceof StackAtPosition)) {
/* 41 */       return false;
/*    */     }
/* 43 */     StackAtPosition other = (StackAtPosition)o;
/* 44 */     return (other.stack.equals(this.stack) && other.pos.equals(this.pos) && other.display == this.display);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 49 */     return this.stack.hashCode() * 17 + this.pos.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\StackAtPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */