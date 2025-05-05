/*     */ package buildcraft.core.list;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.ItemList;
/*     */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerListOld
/*     */   extends BuildCraftContainer
/*     */   implements ICommandReceiver
/*     */ {
/*     */   public ListHandlerOld.StackLine[] lines;
/*     */   private EntityPlayer player;
/*     */   
/*     */   public ContainerListOld(EntityPlayer iPlayer) {
/*  33 */     super(iPlayer.field_71071_by.func_70302_i_());
/*     */     
/*  35 */     this.player = iPlayer;
/*     */     
/*  37 */     this.lines = ListHandlerOld.getLines(this.player.func_71045_bC());
/*     */     
/*  39 */     for (int sy = 0; sy < 3; sy++) {
/*  40 */       for (int i = 0; i < 9; i++) {
/*  41 */         func_75146_a(new Slot((IInventory)this.player.field_71071_by, i + sy * 9 + 9, 8 + i * 18, 153 + sy * 18));
/*     */       }
/*     */     } 
/*     */     
/*  45 */     for (int sx = 0; sx < 9; sx++) {
/*  46 */       func_75146_a(new Slot((IInventory)this.player.field_71071_by, sx, 8 + sx * 18, 211));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  52 */     return true;
/*     */   }
/*     */   
/*     */   public void setStack(final int lineIndex, final int slotIndex, final ItemStack stack) {
/*  56 */     this.lines[lineIndex].setStack(slotIndex, stack);
/*  57 */     ListHandlerOld.saveLine(this.player.func_71045_bC(), this.lines[lineIndex], lineIndex);
/*     */     
/*  59 */     if (this.player.field_70170_p.field_72995_K) {
/*  60 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "setStack", new CommandWriter() {
/*     */               public void write(ByteBuf data) {
/*  62 */                 data.writeByte(lineIndex);
/*  63 */                 data.writeByte(slotIndex);
/*  64 */                 NetworkUtils.writeStack(data, stack);
/*     */               }
/*     */             }));
/*     */     }
/*     */   }
/*     */   
/*     */   public void switchButton(final int lineIndex, final int button) {
/*  71 */     if (button == 0) {
/*  72 */       (this.lines[lineIndex]).oreWildcard = false;
/*  73 */       (this.lines[lineIndex]).subitemsWildcard = !(this.lines[lineIndex]).subitemsWildcard;
/*  74 */     } else if (button == 1 && (this.lines[lineIndex]).isOre) {
/*  75 */       (this.lines[lineIndex]).subitemsWildcard = false;
/*  76 */       (this.lines[lineIndex]).oreWildcard = !(this.lines[lineIndex]).oreWildcard;
/*     */     } 
/*     */     
/*  79 */     ListHandlerOld.saveLine(this.player.func_71045_bC(), this.lines[lineIndex], lineIndex);
/*     */     
/*  81 */     if (this.player.field_70170_p.field_72995_K) {
/*  82 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "switchButton", new CommandWriter() {
/*     */               public void write(ByteBuf data) {
/*  84 */                 data.writeByte(lineIndex);
/*  85 */                 data.writeByte(button);
/*     */               }
/*     */             }));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setLabel(final String text) {
/*  92 */     ItemList.saveLabel(this.player.func_71045_bC(), text);
/*     */     
/*  94 */     if (this.player.field_70170_p.field_72995_K) {
/*  95 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "setLabel", new CommandWriter() {
/*     */               public void write(ByteBuf data) {
/*  97 */                 NetworkUtils.writeUTF(data, text);
/*     */               }
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 105 */     if (side.isServer())
/* 106 */       if ("setLabel".equals(command)) {
/* 107 */         setLabel(NetworkUtils.readUTF(stream));
/* 108 */       } else if ("switchButton".equals(command)) {
/* 109 */         switchButton(stream.readUnsignedByte(), stream.readUnsignedByte());
/* 110 */       } else if ("setStack".equals(command)) {
/* 111 */         setStack(stream.readUnsignedByte(), stream.readUnsignedByte(), NetworkUtils.readStack(stream));
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ContainerListOld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */