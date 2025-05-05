/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import buildcraft.api.transport.PipeManager;
/*    */ import buildcraft.api.transport.pluggable.PipePluggable;
/*    */ import buildcraft.transport.utils.ConnectionMatrix;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public class PipePluggableState
/*    */   implements ISerializable
/*    */ {
/* 13 */   private PipePluggable[] pluggables = new PipePluggable[6];
/* 14 */   private final ConnectionMatrix pluggableMatrix = new ConnectionMatrix();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PipePluggable[] getPluggables() {
/* 21 */     return this.pluggables;
/*    */   }
/*    */   
/*    */   public void setPluggables(PipePluggable[] pluggables) {
/* 25 */     this.pluggables = pluggables;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 30 */     this.pluggableMatrix.clean();
/*    */     
/* 32 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 33 */       this.pluggableMatrix.setConnected(dir, (this.pluggables[dir.ordinal()] != null));
/*    */     }
/*    */     
/* 36 */     this.pluggableMatrix.writeData(data);
/*    */     
/* 38 */     for (PipePluggable p : this.pluggables) {
/* 39 */       if (p != null) {
/* 40 */         data.writeShort(PipeManager.pipePluggables.indexOf(p.getClass()));
/* 41 */         p.writeData(data);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 48 */     this.pluggableMatrix.readData(data);
/*    */     
/* 50 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 51 */       if (this.pluggableMatrix.isConnected(dir)) {
/*    */         try {
/* 53 */           Class<? extends PipePluggable> pc = PipeManager.pipePluggables.get(data.readUnsignedShort());
/* 54 */           if (this.pluggables[dir.ordinal()] == null || pc != this.pluggables[dir.ordinal()].getClass()) {
/* 55 */             PipePluggable p = pc.newInstance();
/* 56 */             this.pluggables[dir.ordinal()] = p;
/*    */           } 
/* 58 */         } catch (Exception e) {
/* 59 */           e.printStackTrace();
/*    */         } 
/* 61 */         if (this.pluggables[dir.ordinal()] != null) {
/* 62 */           this.pluggables[dir.ordinal()].readData(data);
/*    */         }
/*    */       } else {
/* 65 */         this.pluggables[dir.ordinal()] = null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipePluggableState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */