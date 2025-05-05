/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import buildcraft.transport.PipeTransportStructure;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.item.Item;
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
/*    */ public class PipeStructureCobblestone
/*    */   extends Pipe<PipeTransportStructure>
/*    */ {
/*    */   public PipeStructureCobblestone(Item item) {
/* 26 */     super((PipeTransport)new PipeTransportStructure(), item);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIconProvider getIconProvider() {
/* 32 */     return BuildCraftTransport.instance.pipeIconProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconIndex(ForgeDirection direction) {
/* 37 */     return PipeIconProvider.TYPE.PipeStructureCobblestone.ordinal();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeStructureCobblestone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */