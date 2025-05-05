/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeTransportFluids;
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
/*     */ public class PipeFluidsIron
/*     */   extends Pipe<PipeTransportFluids>
/*     */ {
/*     */   protected int standardIconIndex;
/*     */   protected int solidIconIndex;
/*     */   private PipeLogicIron logic;
/*     */   
/*     */   public PipeFluidsIron(Item item) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: new buildcraft/transport/PipeTransportFluids
/*     */     //   4: dup
/*     */     //   5: invokespecial <init> : ()V
/*     */     //   8: aload_1
/*     */     //   9: invokespecial <init> : (Lbuildcraft/transport/PipeTransport;Lnet/minecraft/item/Item;)V
/*     */     //   12: aload_0
/*     */     //   13: getstatic buildcraft/transport/PipeIconProvider$TYPE.PipeFluidsIron_Standard : Lbuildcraft/transport/PipeIconProvider$TYPE;
/*     */     //   16: invokevirtual ordinal : ()I
/*     */     //   19: putfield standardIconIndex : I
/*     */     //   22: aload_0
/*     */     //   23: getstatic buildcraft/transport/PipeIconProvider$TYPE.PipeAllIron_Solid : Lbuildcraft/transport/PipeIconProvider$TYPE;
/*     */     //   26: invokevirtual ordinal : ()I
/*     */     //   29: putfield solidIconIndex : I
/*     */     //   32: aload_0
/*     */     //   33: new buildcraft/transport/pipes/PipeFluidsIron$1
/*     */     //   36: dup
/*     */     //   37: aload_0
/*     */     //   38: aload_0
/*     */     //   39: invokespecial <init> : (Lbuildcraft/transport/pipes/PipeFluidsIron;Lbuildcraft/transport/Pipe;)V
/*     */     //   42: putfield logic : Lbuildcraft/transport/pipes/PipeLogicIron;
/*     */     //   45: aload_0
/*     */     //   46: getfield transport : Lbuildcraft/transport/PipeTransport;
/*     */     //   49: checkcast buildcraft/transport/PipeTransportFluids
/*     */     //   52: aload_0
/*     */     //   53: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   56: invokevirtual initFromPipe : (Ljava/lang/Class;)V
/*     */     //   59: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #54	-> 0
/*     */     //   #35	-> 12
/*     */     //   #36	-> 22
/*     */     //   #37	-> 32
/*     */     //   #56	-> 45
/*     */     //   #57	-> 59
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	60	0	this	Lbuildcraft/transport/pipes/PipeFluidsIron;
/*     */     //   0	60	1	item	Lnet/minecraft/item/Item;
/*     */   }
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection side) {
/*  61 */     return this.logic.blockActivated(entityplayer, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(int blockId) {
/*  66 */     this.logic.switchOnRedstone();
/*  67 */     super.onNeighborBlockChange(blockId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockPlaced() {
/*  72 */     this.logic.onBlockPlaced();
/*  73 */     super.onBlockPlaced();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  78 */     this.logic.initialize();
/*  79 */     super.initialize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean outputOpen(ForgeDirection to) {
/*  84 */     return (super.outputOpen(to) && this.logic.outputOpen(to));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/*  90 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/*  95 */     if (direction == ForgeDirection.UNKNOWN) {
/*  96 */       return this.standardIconIndex;
/*     */     }
/*  98 */     if (this.container != null && this.container.func_145832_p() == direction.ordinal()) {
/*  99 */       return this.standardIconIndex;
/*     */     }
/* 101 */     return this.solidIconIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionsActivated(Collection<StatementSlot> actions) {
/* 107 */     super.actionsActivated(actions);
/*     */     
/* 109 */     for (StatementSlot action : actions) {
/* 110 */       if (action.statement instanceof ActionPipeDirection) {
/* 111 */         this.logic.setFacing(((ActionPipeDirection)action.statement).direction);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkedList<IActionInternal> getActions() {
/* 119 */     LinkedList<IActionInternal> action = super.getActions();
/* 120 */     for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/* 121 */       if (this.container.isPipeConnected(direction)) {
/* 122 */         action.add(BuildCraftTransport.actionPipeDirection[direction.ordinal()]);
/*     */       }
/*     */     } 
/* 125 */     return action;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone() {
/* 130 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeFluidsIron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */