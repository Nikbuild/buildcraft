/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransportPower;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class PipePowerEmerald extends PipePowerWood {
/*    */   public PipePowerEmerald(Item item) {
/*  9 */     super(item);
/*    */     
/* 11 */     this.standardIconIndex = PipeIconProvider.TYPE.PipePowerEmerald_Standard.ordinal();
/* 12 */     this.solidIconIndex = PipeIconProvider.TYPE.PipeAllEmerald_Solid.ordinal();
/*    */     
/* 14 */     ((PipeTransportPower)this.transport).initFromPipe(getClass());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipePowerEmerald.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */