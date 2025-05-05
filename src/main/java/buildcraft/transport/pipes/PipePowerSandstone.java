/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import buildcraft.transport.IPipeConnectionForced;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import buildcraft.transport.PipeTransportPower;
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
/*    */ public class PipePowerSandstone
/*    */   extends Pipe<PipeTransportPower>
/*    */   implements IPipeConnectionForced
/*    */ {
/*    */   public PipePowerSandstone(Item item) {
/* 29 */     super((PipeTransport)new PipeTransportPower(), item);
/* 30 */     ((PipeTransportPower)this.transport).initFromPipe(getClass());
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIconProvider getIconProvider() {
/* 36 */     return BuildCraftTransport.instance.pipeIconProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconIndex(ForgeDirection direction) {
/* 41 */     return PipeIconProvider.TYPE.PipePowerSandstone.ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/* 46 */     return (tile instanceof buildcraft.api.transport.IPipeTile && super.canPipeConnect(tile, side));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean ignoreConnectionOverrides(ForgeDirection with) {
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipePowerSandstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */