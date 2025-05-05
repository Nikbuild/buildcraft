/*    */ package buildcraft.api.statements;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.PacketBuffer;
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
/*    */ public interface IStatementParameter
/*    */   extends IGuiSlot
/*    */ {
/*    */   @Nonnull
/*    */   ItemStack getItemStack();
/*    */   
/*    */   default DrawType getDrawType() {
/* 24 */     return DrawType.SPRITE_STACK;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   IStatementParameter onClick(IStatementContainer paramIStatementContainer, IStatement paramIStatement, ItemStack paramItemStack, StatementMouseClick paramStatementMouseClick);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void writeToNbt(NBTTagCompound paramNBTTagCompound);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void writeToBuf(PacketBuffer buffer) {
/* 45 */     NBTTagCompound nbt = new NBTTagCompound();
/* 46 */     writeToNbt(nbt);
/* 47 */     buffer.func_150786_a(nbt);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   IStatementParameter rotateLeft();
/*    */ 
/*    */ 
/*    */   
/*    */   IStatementParameter[] getPossible(IStatementContainer paramIStatementContainer);
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean isPossibleOrdered() {
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   public enum DrawType
/*    */   {
/* 66 */     SPRITE_ONLY,
/*    */ 
/*    */     
/* 69 */     STACK_ONLY,
/*    */ 
/*    */ 
/*    */     
/* 73 */     STACK_ONLY_OR_QUESTION_MARK,
/*    */ 
/*    */     
/* 76 */     SPRITE_STACK,
/*    */ 
/*    */     
/* 79 */     SPRITE_STACK_OR_QUESTION_MARK,
/*    */ 
/*    */     
/* 82 */     STACK_SPRITE,
/*    */ 
/*    */     
/* 85 */     STACK_OR_QUESTION_MARK_THEN_SPRITE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\IStatementParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */