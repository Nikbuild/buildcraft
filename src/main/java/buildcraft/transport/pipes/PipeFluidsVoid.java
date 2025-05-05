/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import buildcraft.transport.IPipeTransportFluidsHook;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import buildcraft.transport.PipeTransportFluids;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.item.Item;
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
/*    */ public class PipeFluidsVoid
/*    */   extends Pipe<PipeTransportFluids>
/*    */   implements IPipeTransportFluidsHook
/*    */ {
/*    */   public PipeFluidsVoid(Item item) {
/* 28 */     super((PipeTransport)new PipeTransportFluids(), item);
/*    */     
/* 30 */     ((PipeTransportFluids)this.transport).initFromPipe(getClass());
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
/* 41 */     return PipeIconProvider.TYPE.PipeFluidsVoid.ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 46 */     return resource.amount;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeFluidsVoid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */