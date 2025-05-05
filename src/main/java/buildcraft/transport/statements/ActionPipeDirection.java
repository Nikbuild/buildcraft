/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
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
/*    */ 
/*    */ public class ActionPipeDirection
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public final ForgeDirection direction;
/*    */   
/*    */   public ActionPipeDirection(ForgeDirection direction) {
/* 29 */     super(new String[] { "buildcraft:pipe.dir." + direction.name().toLowerCase(Locale.ENGLISH), "buildcraft.pipe.dir." + direction.name().toLowerCase(Locale.ENGLISH) });
/*    */     
/* 31 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 36 */     return this.direction.name().substring(0, 1) + this.direction.name().substring(1).toLowerCase(Locale.ENGLISH) + " Pipe Direction";
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 41 */     this.icon = iconRegister.func_94245_a("buildcraftcore:triggers/trigger_dir_" + this.direction.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatement rotateLeft() {
/* 46 */     return (IStatement)BuildCraftTransport.actionPipeDirection[this.direction.getRotation(ForgeDirection.UP).ordinal()];
/*    */   }
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\ActionPipeDirection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */