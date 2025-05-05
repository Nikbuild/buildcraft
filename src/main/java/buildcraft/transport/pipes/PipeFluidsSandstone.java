/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import buildcraft.transport.IPipeConnectionForced;
/*    */ import buildcraft.transport.IPipeTransportFluidsHook;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import buildcraft.transport.PipeTransportFluids;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PipeFluidsSandstone
/*    */   extends Pipe<PipeTransportFluids>
/*    */   implements IPipeTransportFluidsHook, IPipeConnectionForced
/*    */ {
/*    */   public PipeFluidsSandstone(Item item) {
/* 31 */     super((PipeTransport)new PipeTransportFluids(), item);
/*    */     
/* 33 */     ((PipeTransportFluids)this.transport).initFromPipe(getClass());
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIconProvider getIconProvider() {
/* 39 */     return BuildCraftTransport.instance.pipeIconProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconIndex(ForgeDirection direction) {
/* 44 */     return PipeIconProvider.TYPE.PipeFluidsSandstone.ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 49 */     if (!(this.container.getTile(from) instanceof buildcraft.api.transport.IPipeTile)) {
/* 50 */       return 0;
/*    */     }
/* 52 */     return ((PipeTransportFluids)this.transport).sections[from.ordinal()].fill(resource.amount, doFill);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/* 58 */     return (tile instanceof buildcraft.api.transport.IPipeTile && super.canPipeConnect(tile, side));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean ignoreConnectionOverrides(ForgeDirection with) {
/* 63 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeFluidsSandstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */