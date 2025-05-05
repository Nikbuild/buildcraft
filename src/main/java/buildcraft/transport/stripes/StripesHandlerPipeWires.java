/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.core.Position;
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import buildcraft.transport.TileGenericPipe;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StripesHandlerPipeWires
/*    */   implements IStripesHandler
/*    */ {
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 19 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 24 */     return stack.func_77973_b() instanceof buildcraft.transport.ItemPipeWire;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 29 */     int pipesToTry = 8;
/* 30 */     int pipeWireColor = stack.func_77960_j();
/*    */     
/* 32 */     Position p = new Position(x, y, z);
/* 33 */     p.orientation = direction;
/*    */     
/* 35 */     while (pipesToTry > 0) {
/* 36 */       p.moveBackwards(1.0D);
/*    */       
/* 38 */       TileEntity tile = world.func_147438_o((int)p.x, (int)p.y, (int)p.z);
/* 39 */       if (tile instanceof TileGenericPipe) {
/* 40 */         TileGenericPipe pipeTile = (TileGenericPipe)tile;
/*    */         
/* 42 */         if (!pipeTile.pipe.wireSet[pipeWireColor]) {
/* 43 */           pipeTile.pipe.wireSet[pipeWireColor] = true;
/* 44 */           pipeTile.pipe.wireSignalStrength[pipeWireColor] = 0;
/*    */           
/* 46 */           pipeTile.pipe.updateSignalState();
/* 47 */           pipeTile.scheduleRenderUpdate();
/* 48 */           world.func_147459_d(pipeTile.field_145851_c, pipeTile.field_145848_d, pipeTile.field_145849_e, pipeTile.getBlock());
/* 49 */           return true;
/*    */         } 
/* 51 */         pipesToTry--;
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerPipeWires.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */