/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransport;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.pipes.events.PipeEventItem;
/*     */ import buildcraft.transport.statements.ActionPipeColor;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
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
/*     */ public class PipeItemsLapis
/*     */   extends Pipe<PipeTransportItems>
/*     */ {
/*     */   public PipeItemsLapis(Item item) {
/*  38 */     super((PipeTransport)new PipeTransportItems(), item);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/*  44 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/*  49 */     if (this.container == null) {
/*  50 */       return PipeIconProvider.TYPE.PipeItemsLapis_Black.ordinal();
/*     */     }
/*  52 */     return PipeIconProvider.TYPE.PipeItemsLapis_Black.ordinal() + this.container.func_145832_p();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer player, ForgeDirection direction) {
/*  57 */     Item equipped = (player.func_71045_bC() != null) ? player.func_71045_bC().func_77973_b() : null;
/*  58 */     if (equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(player, this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e)) {
/*  59 */       if (player.func_70093_af()) {
/*  60 */         setColor(getColor().getPrevious());
/*     */       } else {
/*  62 */         setColor(getColor().getNext());
/*     */       } 
/*     */       
/*  65 */       ((IToolWrench)equipped).wrenchUsed(player, this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*  66 */       return true;
/*     */     } 
/*  68 */     int color = ColorUtils.getColorIDFromDye(player.func_71045_bC());
/*  69 */     if (color >= 0 && color < 16) {
/*  70 */       setColor(EnumColor.fromId(15 - color));
/*     */     }
/*     */ 
/*     */     
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   public EnumColor getColor() {
/*  78 */     return EnumColor.fromId(this.container.func_145832_p());
/*     */   }
/*     */   
/*     */   public void setColor(EnumColor color) {
/*  82 */     if (color.ordinal() != this.container.func_145832_p()) {
/*  83 */       this.container.func_145831_w().func_72921_c(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, color.ordinal(), 3);
/*  84 */       this.container.scheduleRenderUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void eventHandler(PipeEventItem.ReachedCenter event) {
/*  89 */     event.item.color = getColor();
/*     */   }
/*     */   
/*     */   public void eventHandler(PipeEventItem.AdjustSpeed event) {
/*  93 */     event.slowdownAmount /= 4.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionsActivated(Collection<StatementSlot> actions) {
/*  98 */     super.actionsActivated(actions);
/*     */     
/* 100 */     for (StatementSlot action : actions) {
/* 101 */       if (action.statement instanceof ActionPipeColor) {
/* 102 */         setColor(((ActionPipeColor)action.statement).color);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkedList<IActionInternal> getActions() {
/* 110 */     LinkedList<IActionInternal> result = super.getActions();
/* 111 */     result.addAll(Arrays.asList(BuildCraftTransport.actionPipeColor));
/*     */     
/* 113 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsLapis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */