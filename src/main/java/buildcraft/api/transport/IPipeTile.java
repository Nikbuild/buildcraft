/*    */ package buildcraft.api.transport;public interface IPipeTile extends IInjectable { PipeType getPipeType();
/*    */   World getWorld();
/*    */   int x();
/*    */   int y();
/*    */   int z();
/*    */   boolean isPipeConnected(ForgeDirection paramForgeDirection);
/*    */   Block getNeighborBlock(ForgeDirection paramForgeDirection);
/*    */   TileEntity getNeighborTile(ForgeDirection paramForgeDirection);
/*    */   IPipe getNeighborPipe(ForgeDirection paramForgeDirection);
/*    */   IPipe getPipe();
/*    */   int getPipeColor();
/*    */   PipePluggable getPipePluggable(ForgeDirection paramForgeDirection);
/*    */   boolean hasPipePluggable(ForgeDirection paramForgeDirection);
/*    */   boolean hasBlockingPluggable(ForgeDirection paramForgeDirection);
/*    */   void scheduleNeighborChange();
/*    */   void scheduleRenderUpdate();
/*    */   int injectItem(ItemStack paramItemStack, boolean paramBoolean, ForgeDirection paramForgeDirection, EnumColor paramEnumColor);
/*    */   
/*    */   @Deprecated
/*    */   int injectItem(ItemStack paramItemStack, boolean paramBoolean, ForgeDirection paramForgeDirection);
/*    */   
/* 22 */   public enum PipeType { ITEM, FLUID, POWER, STRUCTURE; }
/*    */    }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\transport\IPipeTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */