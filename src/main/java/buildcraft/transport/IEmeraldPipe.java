/*    */ package buildcraft.transport;
/*    */ public interface IEmeraldPipe extends IFilteredPipe {
/*    */   EmeraldPipeSettings getSettings();
/*    */   
/*    */   boolean isValidFilterMode(FilterMode paramFilterMode);
/*    */   
/*    */   public enum FilterMode {
/*  8 */     WHITE_LIST, BLACK_LIST, ROUND_ROBIN;
/*    */   }
/*    */   
/*    */   public static class EmeraldPipeSettings {
/*    */     private IEmeraldPipe.FilterMode filterMode;
/*    */     
/*    */     public EmeraldPipeSettings(IEmeraldPipe.FilterMode defaultMode) {
/* 15 */       this.filterMode = defaultMode;
/*    */     }
/*    */     
/*    */     public IEmeraldPipe.FilterMode getFilterMode() {
/* 19 */       return this.filterMode;
/*    */     }
/*    */     
/*    */     public void setFilterMode(IEmeraldPipe.FilterMode mode) {
/* 23 */       this.filterMode = mode;
/*    */     }
/*    */     
/*    */     public void readFromNBT(NBTTagCompound nbt) {
/* 27 */       this.filterMode = IEmeraldPipe.FilterMode.values()[nbt.func_74771_c("filterMode")];
/*    */     }
/*    */     
/*    */     public void writeToNBT(NBTTagCompound nbt) {
/* 31 */       nbt.func_74774_a("filterMode", (byte)this.filterMode.ordinal());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\IEmeraldPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */