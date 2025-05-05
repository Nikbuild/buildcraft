/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import buildcraft.transport.PipeTransportFluids;
/*    */ import buildcraft.transport.pipes.events.PipeEventFluid;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.IFluidHandler;
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
/*    */ 
/*    */ public class PipeFluidsClay
/*    */   extends Pipe<PipeTransportFluids>
/*    */ {
/*    */   public PipeFluidsClay(Item item) {
/* 33 */     super((PipeTransport)new PipeTransportFluids(), item);
/*    */     
/* 35 */     ((PipeTransportFluids)this.transport).initFromPipe(getClass());
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIconProvider getIconProvider() {
/* 41 */     return BuildCraftTransport.instance.pipeIconProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconIndex(ForgeDirection direction) {
/* 46 */     return PipeIconProvider.TYPE.PipeFluidsClay.ordinal();
/*    */   }
/*    */   
/*    */   public void eventHandler(PipeEventFluid.FindDest event) {
/* 50 */     Set<ForgeDirection> machineDirs = new HashSet<ForgeDirection>();
/* 51 */     Set<ForgeDirection> pipeDirs = new HashSet<ForgeDirection>();
/*    */     
/* 53 */     for (ForgeDirection dir : event.destinations) {
/* 54 */       if (this.container.isPipeConnected(dir)) {
/* 55 */         TileEntity e = this.container.getTile(dir);
/* 56 */         if (e instanceof IFluidHandler) {
/* 57 */           IFluidHandler h = (IFluidHandler)e;
/* 58 */           if (h.fill(dir.getOpposite(), event.fluidStack, false) > 0) {
/* 59 */             if (e instanceof buildcraft.api.transport.IPipeTile) {
/* 60 */               pipeDirs.add(dir); continue;
/*    */             } 
/* 62 */             machineDirs.add(dir);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 69 */     event.destinations.clear();
/* 70 */     event.destinations.addAll((machineDirs.size() > 0) ? machineDirs : pipeDirs);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeFluidsClay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */