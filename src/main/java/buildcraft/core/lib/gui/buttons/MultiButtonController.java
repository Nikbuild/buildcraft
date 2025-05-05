/*    */ package buildcraft.core.lib.gui.buttons;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MultiButtonController<T extends IMultiButtonState>
/*    */ {
/*    */   private int currentState;
/*    */   private final T[] validStates;
/*    */   
/*    */   private MultiButtonController(int startState, T... validStates) {
/* 24 */     this.currentState = startState;
/* 25 */     this.validStates = validStates;
/*    */   }
/*    */   
/*    */   public static <T extends IMultiButtonState> MultiButtonController<T> getController(int startState, T... validStates) {
/* 29 */     return new MultiButtonController<T>(startState, validStates);
/*    */   }
/*    */   
/*    */   public MultiButtonController<?> copy() {
/* 33 */     return new MultiButtonController(this.currentState, (T[])this.validStates.clone());
/*    */   }
/*    */   
/*    */   public T[] getValidStates() {
/* 37 */     return this.validStates;
/*    */   }
/*    */   
/*    */   public int incrementState() {
/* 41 */     int newState = this.currentState + 1;
/* 42 */     if (newState >= this.validStates.length) {
/* 43 */       newState = 0;
/*    */     }
/* 45 */     this.currentState = newState;
/* 46 */     return this.currentState;
/*    */   }
/*    */   
/*    */   public void setCurrentState(int state) {
/* 50 */     this.currentState = state;
/*    */   }
/*    */   
/*    */   public void setCurrentState(T state) {
/* 54 */     for (int i = 0; i < this.validStates.length; i++) {
/* 55 */       if (this.validStates[i] == state) {
/* 56 */         this.currentState = i;
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getCurrentState() {
/* 63 */     return this.currentState;
/*    */   }
/*    */   
/*    */   public T getButtonState() {
/* 67 */     return this.validStates[this.currentState];
/*    */   }
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt, String tag) {
/* 71 */     nbt.func_74778_a(tag, getButtonState().name());
/*    */   }
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbt, String tag) {
/* 75 */     if (nbt.func_74781_a(tag) instanceof net.minecraft.nbt.NBTTagString) {
/* 76 */       String name = nbt.func_74779_i(tag);
/* 77 */       for (int i = 0; i < this.validStates.length; i++) {
/* 78 */         if (this.validStates[i].name().equals(name)) {
/* 79 */           this.currentState = i;
/*    */           break;
/*    */         } 
/*    */       } 
/* 83 */     } else if (nbt.func_74781_a(tag) instanceof net.minecraft.nbt.NBTTagByte) {
/* 84 */       this.currentState = nbt.func_74771_c(tag);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\MultiButtonController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */