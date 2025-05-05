/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.pipes.events.PipeEventItem;
/*     */ import buildcraft.transport.statements.ActionPipeColor;
/*     */ import buildcraft.transport.statements.ActionPipeDirection;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ public class PipeItemsDaizuli
/*     */   extends Pipe<PipeTransportItems>
/*     */   implements ISerializable
/*     */ {
/*     */   private int standardIconIndex;
/*     */   private int solidIconIndex;
/*     */   private int color;
/*     */   private PipeLogicIron logic;
/*     */   
/*     */   public PipeItemsDaizuli(Item item) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: new buildcraft/transport/PipeTransportItems
/*     */     //   4: dup
/*     */     //   5: invokespecial <init> : ()V
/*     */     //   8: aload_1
/*     */     //   9: invokespecial <init> : (Lbuildcraft/transport/PipeTransport;Lnet/minecraft/item/Item;)V
/*     */     //   12: aload_0
/*     */     //   13: getstatic buildcraft/transport/PipeIconProvider$TYPE.PipeItemsDaizuli_Black : Lbuildcraft/transport/PipeIconProvider$TYPE;
/*     */     //   16: invokevirtual ordinal : ()I
/*     */     //   19: putfield standardIconIndex : I
/*     */     //   22: aload_0
/*     */     //   23: getstatic buildcraft/transport/PipeIconProvider$TYPE.PipeAllDaizuli_Solid : Lbuildcraft/transport/PipeIconProvider$TYPE;
/*     */     //   26: invokevirtual ordinal : ()I
/*     */     //   29: putfield solidIconIndex : I
/*     */     //   32: aload_0
/*     */     //   33: getstatic buildcraft/api/core/EnumColor.BLACK : Lbuildcraft/api/core/EnumColor;
/*     */     //   36: invokevirtual ordinal : ()I
/*     */     //   39: putfield color : I
/*     */     //   42: aload_0
/*     */     //   43: new buildcraft/transport/pipes/PipeItemsDaizuli$1
/*     */     //   46: dup
/*     */     //   47: aload_0
/*     */     //   48: aload_0
/*     */     //   49: invokespecial <init> : (Lbuildcraft/transport/pipes/PipeItemsDaizuli;Lbuildcraft/transport/Pipe;)V
/*     */     //   52: putfield logic : Lbuildcraft/transport/pipes/PipeLogicIron;
/*     */     //   55: aload_0
/*     */     //   56: getfield transport : Lbuildcraft/transport/PipeTransport;
/*     */     //   59: checkcast buildcraft/transport/PipeTransportItems
/*     */     //   62: iconst_1
/*     */     //   63: putfield allowBouncing : Z
/*     */     //   66: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #69	-> 0
/*     */     //   #45	-> 12
/*     */     //   #46	-> 22
/*     */     //   #47	-> 32
/*     */     //   #48	-> 42
/*     */     //   #71	-> 55
/*     */     //   #72	-> 66
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	67	0	this	Lbuildcraft/transport/pipes/PipeItemsDaizuli;
/*     */     //   0	67	1	item	Lnet/minecraft/item/Item;
/*     */   }
/*     */   
/*     */   public EnumColor getColor() {
/*  75 */     return EnumColor.fromId(this.color);
/*     */   }
/*     */   
/*     */   public void setColor(EnumColor c) {
/*  79 */     if (this.color != c.ordinal()) {
/*  80 */       this.color = c.ordinal();
/*  81 */       this.container.scheduleRenderUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer player, ForgeDirection side) {
/*  87 */     if (player.func_70093_af()) {
/*  88 */       Item equipped = (player.func_71045_bC() != null) ? player.func_71045_bC().func_77973_b() : null;
/*  89 */       if (equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(player, this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e)) {
/*  90 */         setColor(getColor().getNext());
/*  91 */         ((IToolWrench)equipped).wrenchUsed(player, this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*  92 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     int newColor = ColorUtils.getColorIDFromDye(player.func_71045_bC());
/*  97 */     if (newColor >= 0 && newColor < 16) {
/*  98 */       setColor(EnumColor.fromId(15 - newColor));
/*  99 */       return true;
/*     */     } 
/*     */     
/* 102 */     return this.logic.blockActivated(player, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockPlaced() {
/* 107 */     this.logic.onBlockPlaced();
/* 108 */     super.onBlockPlaced();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 113 */     this.logic.initialize();
/* 114 */     super.initialize();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/* 119 */     if (direction == ForgeDirection.UNKNOWN) {
/* 120 */       return this.standardIconIndex + this.color;
/*     */     }
/* 122 */     if (this.container != null && this.container.func_145832_p() == direction.ordinal()) {
/* 123 */       return this.standardIconIndex + this.color;
/*     */     }
/* 125 */     return this.solidIconIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/* 131 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstone() {
/* 136 */     return true;
/*     */   }
/*     */   
/*     */   public void eventHandler(PipeEventItem.FindDest event) {
/* 140 */     ForgeDirection output = ForgeDirection.getOrientation(this.container.func_145832_p());
/* 141 */     if (event.item.color == getColor() && event.destinations.contains(output)) {
/* 142 */       event.destinations.clear();
/* 143 */       event.destinations.add(output);
/*     */       return;
/*     */     } 
/* 146 */     event.destinations.remove(output);
/*     */   }
/*     */   
/*     */   public void eventHandler(PipeEventItem.AdjustSpeed event) {
/* 150 */     event.slowdownAmount /= 4.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionsActivated(Collection<StatementSlot> actions) {
/* 155 */     super.actionsActivated(actions);
/*     */     
/* 157 */     for (StatementSlot action : actions) {
/* 158 */       if (action.statement instanceof ActionPipeColor) {
/* 159 */         setColor(((ActionPipeColor)action.statement).color);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 164 */     for (StatementSlot action : actions) {
/* 165 */       if (action.statement instanceof ActionPipeDirection) {
/* 166 */         this.logic.setFacing(((ActionPipeDirection)action.statement).direction);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkedList<IActionInternal> getActions() {
/* 174 */     LinkedList<IActionInternal> action = super.getActions();
/* 175 */     action.addAll(Arrays.asList(BuildCraftTransport.actionPipeColor));
/* 176 */     for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/* 177 */       if (this.container.isPipeConnected(direction)) {
/* 178 */         action.add(BuildCraftTransport.actionPipeDirection[direction.ordinal()]);
/*     */       }
/*     */     } 
/* 181 */     return action;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound data) {
/* 186 */     super.writeToNBT(data);
/* 187 */     data.func_74774_a("color", (byte)this.color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound data) {
/* 192 */     super.readFromNBT(data);
/* 193 */     this.color = data.func_74771_c("color");
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 198 */     data.writeByte(this.color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 203 */     this.color = data.readByte();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsDaizuli.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */