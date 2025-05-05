/*    */ package buildcraft.api.robots;
/*    */ 
/*    */ import buildcraft.api.core.EnumPipePart;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import org.apache.commons.lang3.builder.HashCodeBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceIdRequest
/*    */   extends ResourceIdBlock
/*    */ {
/*    */   private int slot;
/*    */   
/*    */   public ResourceIdRequest() {}
/*    */   
/*    */   public ResourceIdRequest(DockingStation station, int slot) {
/* 22 */     this.pos = station.index();
/* 23 */     this.side = EnumPipePart.fromFacing(station.side());
/* 24 */     this.slot = slot;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 29 */     if (obj == null) return false; 
/* 30 */     if (!super.equals(obj)) return false; 
/* 31 */     ResourceIdRequest compareId = (ResourceIdRequest)obj;
/*    */     
/* 33 */     return (this.slot == compareId.slot);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 38 */     return (new HashCodeBuilder()).append(super.hashCode()).append(this.slot).build().intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt) {
/* 43 */     super.writeToNBT(nbt);
/*    */     
/* 45 */     nbt.func_74768_a("localId", this.slot);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void readFromNBT(NBTTagCompound nbt) {
/* 50 */     super.readFromNBT(nbt);
/*    */     
/* 52 */     this.slot = nbt.func_74762_e("localId");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\ResourceIdRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */