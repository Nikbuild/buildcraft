/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import buildcraft.transport.IPipeConnectionForced;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import buildcraft.transport.PipeTransportItems;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public class PipeItemsSandstone
/*    */   extends Pipe<PipeTransportItems>
/*    */   implements IPipeConnectionForced
/*    */ {
/*    */   public PipeItemsSandstone(Item item) {
/* 29 */     super((PipeTransport)new PipeTransportItems(), item);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIconProvider getIconProvider() {
/* 35 */     return BuildCraftTransport.instance.pipeIconProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconIndex(ForgeDirection direction) {
/* 40 */     return PipeIconProvider.TYPE.PipeItemsSandstone.ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/* 45 */     return (tile instanceof buildcraft.api.transport.IPipeTile && super.canPipeConnect(tile, side));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean ignoreConnectionOverrides(ForgeDirection with) {
/* 50 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsSandstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */