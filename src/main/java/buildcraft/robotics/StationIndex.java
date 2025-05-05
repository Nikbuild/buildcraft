/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ public class StationIndex
/*    */ {
/* 20 */   public BlockIndex index = new BlockIndex();
/* 21 */   public ForgeDirection side = ForgeDirection.UNKNOWN;
/*    */ 
/*    */   
/*    */   protected StationIndex() {}
/*    */   
/*    */   public StationIndex(ForgeDirection iSide, int x, int y, int z) {
/* 27 */     this.side = iSide;
/* 28 */     this.index = new BlockIndex(x, y, z);
/*    */   }
/*    */   
/*    */   public StationIndex(DockingStation station) {
/* 32 */     this.side = station.side();
/* 33 */     this.index = station.index();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 38 */     if (obj == null || obj.getClass() != getClass()) {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     StationIndex compareId = (StationIndex)obj;
/*    */     
/* 44 */     return (this.index.equals(compareId.index) && this.side == compareId.side);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     return this.index.hashCode() * 37 + this.side.ordinal();
/*    */   }
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt) {
/* 54 */     NBTTagCompound indexNBT = new NBTTagCompound();
/* 55 */     this.index.writeTo(indexNBT);
/* 56 */     nbt.func_74782_a("index", (NBTBase)indexNBT);
/* 57 */     nbt.func_74774_a("side", (byte)this.side.ordinal());
/*    */   }
/*    */   
/*    */   protected void readFromNBT(NBTTagCompound nbt) {
/* 61 */     this.index = new BlockIndex(nbt.func_74775_l("index"));
/* 62 */     this.side = ForgeDirection.values()[nbt.func_74771_c("side")];
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\StationIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */