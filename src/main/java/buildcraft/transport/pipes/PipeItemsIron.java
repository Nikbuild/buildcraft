/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.statements.ActionPipeDirection;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PipeItemsIron
/*     */   extends Pipe<PipeTransportItems>
/*     */ {
/*     */   private int standardIconIndex;
/*     */   private int solidIconIndex;
/*     */   private PipeLogicIron logic;
/*     */   
/*     */   public PipeItemsIron(Item item) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: new buildcraft/transport/PipeTransportItems
/*     */     //   4: dup
/*     */     //   5: invokespecial <init> : ()V
/*     */     //   8: aload_1
/*     */     //   9: invokespecial <init> : (Lbuildcraft/transport/PipeTransport;Lnet/minecraft/item/Item;)V
/*     */     //   12: aload_0
/*     */     //   13: getstatic buildcraft/transport/PipeIconProvider$TYPE.PipeItemsIron_Standard : Lbuildcraft/transport/PipeIconProvider$TYPE;
/*     */     //   16: invokevirtual ordinal : ()I
/*     */     //   19: putfield standardIconIndex : I
/*     */     //   22: aload_0
/*     */     //   23: getstatic buildcraft/transport/PipeIconProvider$TYPE.PipeAllIron_Solid : Lbuildcraft/transport/PipeIconProvider$TYPE;
/*     */     //   26: invokevirtual ordinal : ()I
/*     */     //   29: putfield solidIconIndex : I
/*     */     //   32: aload_0
/*     */     //   33: new buildcraft/transport/pipes/PipeItemsIron$1
/*     */     //   36: dup
/*     */     //   37: aload_0
/*     */     //   38: aload_0
/*     */     //   39: invokespecial <init> : (Lbuildcraft/transport/pipes/PipeItemsIron;Lbuildcraft/transport/Pipe;)V
/*     */     //   42: putfield logic : Lbuildcraft/transport/pipes/PipeLogicIron;
/*     */     //   45: aload_0
/*     */     //   46: getfield transport : Lbuildcraft/transport/PipeTransport;
/*     */     //   49: checkcast buildcraft/transport/PipeTransportItems
/*     */     //   52: iconst_1
/*     */     //   53: putfield allowBouncing : Z
/*     */     //   56: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #58	-> 0
/*     */     //   #35	-> 12
/*     */     //   #36	-> 22
/*     */     //   #37	-> 32
/*     */     //   #60	-> 45
/*     */     //   #61	-> 56
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	57	0	this	Lbuildcraft/transport/pipes/PipeItemsIron;
/*     */     //   0	57	1	item	Lnet/minecraft/item/Item;
/*     */   }
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection side) {
/*  65 */     return this.logic.blockActivated(entityplayer, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(int blockId) {
/*  70 */     this.logic.switchOnRedstone();
/*  71 */     super.onNeighborBlockChange(blockId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockPlaced() {
/*  76 */     this.logic.onBlockPlaced();
/*  77 */     super.onBlockPlaced();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  82 */     this.logic.initialize();
/*  83 */     super.initialize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean outputOpen(ForgeDirection to) {
/*  88 */     return (super.outputOpen(to) && this.logic.outputOpen(to));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/*  93 */     if (direction == ForgeDirection.UNKNOWN) {
/*  94 */       return this.standardIconIndex;
/*     */     }
/*  96 */     int metadata = this.container.func_145832_p();
/*     */     
/*  98 */     if (metadata != direction.ordinal()) {
/*  99 */       return this.solidIconIndex;
/*     */     }
/* 101 */     return this.standardIconIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/* 109 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionsActivated(Collection<StatementSlot> actions) {
/* 114 */     super.actionsActivated(actions);
/*     */     
/* 116 */     for (StatementSlot action : actions) {
/* 117 */       if (action.statement instanceof ActionPipeDirection) {
/* 118 */         this.logic.setFacing(((ActionPipeDirection)action.statement).direction);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkedList<IActionInternal> getActions() {
/* 126 */     LinkedList<IActionInternal> action = super.getActions();
/* 127 */     for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/* 128 */       if (this.container.isPipeConnected(direction)) {
/* 129 */         action.add(BuildCraftTransport.actionPipeDirection[direction.ordinal()]);
/*     */       }
/*     */     } 
/* 132 */     return action;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone() {
/* 137 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsIron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */