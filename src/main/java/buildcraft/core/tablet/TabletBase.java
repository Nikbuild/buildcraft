/*    */ package buildcraft.core.tablet;
/*    */ 
/*    */ import buildcraft.api.core.BCLog;
/*    */ import buildcraft.api.tablet.ITablet;
/*    */ import buildcraft.api.tablet.TabletAPI;
/*    */ import buildcraft.api.tablet.TabletProgram;
/*    */ import buildcraft.api.tablet.TabletProgramFactory;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public abstract class TabletBase
/*    */   implements ITablet
/*    */ {
/* 14 */   protected final LinkedList<TabletProgram> programs = new LinkedList<TabletProgram>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick(float time) {
/* 21 */     if (this.programs.size() > 0) {
/* 22 */       ((TabletProgram)this.programs.getLast()).tick(time);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScreenWidth() {
/* 28 */     return 244;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScreenHeight() {
/* 33 */     return 306;
/*    */   }
/*    */   
/*    */   protected boolean launchProgramInternal(String name) {
/* 37 */     TabletProgramFactory factory = TabletAPI.getProgram(name);
/* 38 */     if (factory == null) {
/* 39 */       BCLog.logger.error("Tried to launch non-existent tablet program on side CLIENT: " + name);
/* 40 */       return false;
/*    */     } 
/* 42 */     TabletProgram program = factory.create(this);
/* 43 */     if (program == null) {
/* 44 */       BCLog.logger.error("Factory could not create program on side CLIENT: " + name);
/* 45 */       return false;
/*    */     } 
/* 47 */     this.programs.add(program);
/* 48 */     return true;
/*    */   }
/*    */   
/*    */   public abstract void receiveMessage(NBTTagCompound paramNBTTagCompound);
/*    */   
/*    */   protected boolean receiveMessageInternal(NBTTagCompound compound) {
/* 54 */     if (compound.func_74764_b("__program")) {
/* 55 */       compound.func_82580_o("__program");
/* 56 */       if (this.programs.getLast() != null) {
/* 57 */         ((TabletProgram)this.programs.getLast()).receiveMessage(compound);
/*    */       }
/* 59 */       return true;
/*    */     } 
/* 61 */     if (compound.func_74764_b("programToLaunch")) {
/* 62 */       launchProgramInternal(compound.func_74779_i("programToLaunch"));
/* 63 */       return true;
/*    */     } 
/*    */     
/* 66 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\TabletBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */