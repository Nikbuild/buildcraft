/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import buildcraft.core.PowerMode;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransport;
/*     */ import buildcraft.transport.PipeTransportPower;
/*     */ import buildcraft.transport.statements.ActionPowerLimiter;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PipePowerIron
/*     */   extends Pipe<PipeTransportPower>
/*     */ {
/*     */   public PipePowerIron(Item item) {
/*  39 */     super((PipeTransport)new PipeTransportPower(), item);
/*  40 */     ((PipeTransportPower)this.transport).initFromPipe(getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/*  45 */     if (this.container == null) {
/*  46 */       return PipeIconProvider.TYPE.PipePowerIronM128.ordinal();
/*     */     }
/*  48 */     return PipeIconProvider.TYPE.PipePowerIronM2.ordinal() + this.container.func_145832_p();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer player, ForgeDirection direction) {
/*  53 */     Item equipped = (player.func_71045_bC() != null) ? player.func_71045_bC().func_77973_b() : null;
/*  54 */     if (equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(player, this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e)) {
/*  55 */       if (player.func_70093_af()) {
/*  56 */         setMode(getMode().getPrevious());
/*     */       } else {
/*  58 */         setMode(getMode().getNext());
/*     */       } 
/*  60 */       if ((getWorld()).field_72995_K && !(player instanceof net.minecraftforge.common.util.FakePlayer)) {
/*  61 */         if (BuildCraftCore.hidePowerNumbers) {
/*  62 */           player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.pipe.power.iron.mode.numberless", new Object[] {
/*  63 */                   StringUtils.localize("chat.pipe.power.iron.level." + (getMode()).maxPower) }));
/*     */         } else {
/*  65 */           player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.pipe.power.iron.mode", new Object[] {
/*  66 */                   Integer.valueOf((getMode()).maxPower)
/*     */                 }));
/*     */         } 
/*     */       }
/*  70 */       ((IToolWrench)equipped).wrenchUsed(player, this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*  71 */       return true;
/*     */     } 
/*     */     
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/*  79 */     super.updateEntity();
/*  80 */     ((PipeTransportPower)this.transport).maxPower = (getMode()).maxPower;
/*     */   }
/*     */   
/*     */   public PowerMode getMode() {
/*  84 */     return PowerMode.fromId(this.container.func_145832_p());
/*     */   }
/*     */   
/*     */   public void setMode(PowerMode mode) {
/*  88 */     if (mode.ordinal() != this.container.func_145832_p()) {
/*  89 */       this.container.func_145831_w().func_72921_c(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, mode.ordinal(), 3);
/*  90 */       this.container.scheduleRenderUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/*  97 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionsActivated(Collection<StatementSlot> actions) {
/* 102 */     super.actionsActivated(actions);
/*     */     
/* 104 */     for (StatementSlot action : actions) {
/* 105 */       if (action.statement instanceof ActionPowerLimiter) {
/* 106 */         setMode(((ActionPowerLimiter)action.statement).limit);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkedList<IActionInternal> getActions() {
/* 114 */     LinkedList<IActionInternal> action = super.getActions();
/*     */     
/* 116 */     for (PowerMode mode : PowerMode.VALUES) {
/* 117 */       action.add(BuildCraftTransport.actionPowerLimiter[mode.ordinal()]);
/*     */     }
/* 119 */     return action;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipePowerIron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */