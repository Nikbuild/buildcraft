/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IActionInternalSided;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.api.statements.ITriggerInternalSided;
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ public abstract class PipeEventStatement
/*    */   extends PipeEvent
/*    */ {
/*    */   public PipeEventStatement(IPipeHolder holder) {
/* 16 */     super(holder);
/*    */   }
/*    */   
/*    */   public static class AddTriggerInternal
/*    */     extends PipeEventStatement {
/*    */     public final Collection<ITriggerInternal> triggers;
/*    */     
/*    */     public AddTriggerInternal(IPipeHolder holder, Collection<ITriggerInternal> triggers) {
/* 24 */       super(holder);
/* 25 */       this.triggers = triggers;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class AddTriggerInternalSided
/*    */     extends PipeEventStatement
/*    */   {
/*    */     public final Collection<ITriggerInternalSided> triggers;
/*    */     @Nonnull
/*    */     public final EnumFacing side;
/*    */     
/*    */     public AddTriggerInternalSided(IPipeHolder holder, Collection<ITriggerInternalSided> triggers, @Nonnull EnumFacing side) {
/* 37 */       super(holder);
/* 38 */       this.triggers = triggers;
/* 39 */       this.side = side;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class AddActionInternal
/*    */     extends PipeEventStatement {
/*    */     public final Collection<IActionInternal> actions;
/*    */     
/*    */     public AddActionInternal(IPipeHolder holder, Collection<IActionInternal> actions) {
/* 48 */       super(holder);
/* 49 */       this.actions = actions;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class AddActionInternalSided
/*    */     extends PipeEventStatement
/*    */   {
/*    */     public final Collection<IActionInternalSided> actions;
/*    */     @Nonnull
/*    */     public final EnumFacing side;
/*    */     
/*    */     public AddActionInternalSided(IPipeHolder holder, Collection<IActionInternalSided> actions, @Nonnull EnumFacing side) {
/* 61 */       super(holder);
/* 62 */       this.actions = actions;
/* 63 */       this.side = side;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeEventStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */