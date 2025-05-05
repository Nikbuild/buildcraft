/*    */ package buildcraft.api.robots;
/*    */ 
/*    */ import buildcraft.api.core.EnumPipePart;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import org.apache.commons.lang3.builder.HashCodeBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceIdBlock
/*    */   extends ResourceId
/*    */ {
/* 17 */   public BlockPos pos = new BlockPos(0, 0, 0);
/* 18 */   public EnumPipePart side = EnumPipePart.CENTER;
/*    */ 
/*    */   
/*    */   public ResourceIdBlock() {}
/*    */ 
/*    */   
/*    */   public ResourceIdBlock(int x, int y, int z) {
/* 25 */     this.pos = new BlockPos(x, y, z);
/*    */   }
/*    */   
/*    */   public ResourceIdBlock(BlockPos iIndex) {
/* 29 */     this.pos = iIndex;
/*    */   }
/*    */   
/*    */   public ResourceIdBlock(TileEntity tile) {
/* 33 */     this.pos = tile.func_174877_v();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 38 */     if (this == obj) return true; 
/* 39 */     if (obj == null || obj.getClass() != getClass()) {
/* 40 */       return false;
/*    */     }
/*    */     
/* 43 */     ResourceIdBlock compareId = (ResourceIdBlock)obj;
/*    */     
/* 45 */     return (this.pos.equals(compareId.pos) && this.side == compareId.side);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     return (new HashCodeBuilder()).append(this.pos.hashCode()).append((this.side != null) ? this.side.ordinal() : 0).build().intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt) {
/* 55 */     super.writeToNBT(nbt);
/*    */     
/* 57 */     int[] arr = { this.pos.func_177958_n(), this.pos.func_177956_o(), this.pos.func_177952_p() };
/* 58 */     nbt.func_74783_a("pos", arr);
/*    */     
/* 60 */     nbt.func_74782_a("side", this.side.writeToNBT());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void readFromNBT(NBTTagCompound nbt) {
/* 65 */     super.readFromNBT(nbt);
/* 66 */     int[] arr = nbt.func_74759_k("pos");
/* 67 */     this.pos = new BlockPos(arr[0], arr[1], arr[2]);
/*    */     
/* 69 */     this.side = EnumPipePart.readFromNBT(nbt.func_74781_a("side"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\ResourceIdBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */