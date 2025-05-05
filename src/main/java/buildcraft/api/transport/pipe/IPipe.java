/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*    */ 
/*    */ public interface IPipe
/*    */   extends ICapabilityProvider {
/*    */   IPipeHolder getHolder();
/*    */   
/*    */   PipeDefinition getDefinition();
/*    */   
/*    */   PipeBehaviour getBehaviour();
/*    */   
/*    */   PipeFlow getFlow();
/*    */   
/*    */   EnumDyeColor getColour();
/*    */   
/*    */   void setColour(EnumDyeColor paramEnumDyeColor);
/*    */   
/*    */   void markForUpdate();
/*    */   
/*    */   TileEntity getConnectedTile(EnumFacing paramEnumFacing);
/*    */   
/*    */   IPipe getConnectedPipe(EnumFacing paramEnumFacing);
/*    */   
/*    */   boolean isConnected(EnumFacing paramEnumFacing);
/*    */   
/*    */   ConnectedType getConnectedType(EnumFacing paramEnumFacing);
/*    */   
/*    */   public enum ConnectedType {
/* 33 */     TILE,
/* 34 */     PIPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\IPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */