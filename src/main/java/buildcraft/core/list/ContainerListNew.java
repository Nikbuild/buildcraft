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
/*     */ public class ContainerListNew
/*     */   extends BuildCraftContainer
/*     */   implements ICommandReceiver
/*     */ {
/*     */   public ListHandlerNew.Line[] lines;
/*     */   private EntityPlayer player;
/*     */   
/*     */   public ContainerListNew(EntityPlayer iPlayer) {
/*  32 */     super(iPlayer.field_71071_by.func_70302_i_());
/*     */     
/*  34 */     this.player = iPlayer;
/*     */     
/*  36 */     this.lines = ListHandlerNew.getLines(this.player.func_71045_bC());
/*     */     
/*  38 */     for (int sy = 0; sy < 3; sy++) {
/*  39 */       for (int i = 0; i < 9; i++) {
/*  40 */         func_75146_a(new Slot((IInventory)this.player.field_71071_by, i + sy * 9 + 9, 8 + i * 18, 103 + sy * 18));
/*     */       }
/*     */     } 
/*     */     
/*  44 */     for (int sx = 0; sx < 9; sx++) {
/*  45 */       func_75146_a(new Slot((IInventory)this.player.field_71071_by, sx, 8 + sx * 18, 161));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  51 */     return true;
/*     */   }
/*     */   
/*     */   public void setStack(final int lineIndex, final int slotIndex, final ItemStack stack) {
/*  55 */     this.lines[lineIndex].setStack(slotIndex, stack);
/*  56 */     ListHandlerNew.saveLines(this.player.func_71045_bC(), this.lines);
/*     */     
/*  58 */     if (this.player.field_70170_p.field_72995_K) {
/*  59 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "setStack", new CommandWriter() {
/*     */               public void write(ByteBuf data) {
/*  61 */                 data.writeByte(lineIndex);
/*  62 */                 data.writeByte(slotIndex);
/*  63 */                 NetworkUtils.writeStack(data, stack);
/*     */               }
/*     */             }));
/*     */     }
/*     */   }
/*     */   
/*     */   public void switchButton(final int lineIndex, final int button) {
/*  70 */     this.lines[lineIndex].toggleOption(button);
/*  71 */     ListHandlerNew.saveLines(this.player.func_71045_bC(), this.lines);
/*     */     
/*  73 */     if (this.player.field_70170_p.field_72995_K) {
/*  74 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "switchButton", new CommandWriter() {
/*     */               public void write(ByteBuf data) {
/*  76 */                 data.writeByte(lineIndex);
/*  77 */                 data.writeByte(button);
/*     */               }
/*     */             }));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setLabel(final String text) {
/*  84 */     ItemList.saveLabel(this.player.func_71045_bC(), text);
/*     */     
/*  86 */     if (this.player.field_70170_p.field_72995_K) {
/*  87 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "setLabel", new CommandWriter() {
/*     */               public void write(ByteBuf data) {
/*  89 */                 NetworkUtils.writeUTF(data, text);
/*     */               }
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/*  97 */     if (side.isServer())
/*  98 */       if ("setLabel".equals(command)) {
/*  99 */         setLabel(NetworkUtils.readUTF(stream));
/* 100 */       } else if ("switchButton".equals(command)) {
/* 101 */         switchButton(stream.readUnsignedByte(), stream.readUnsignedByte());
/* 102 */       } else if ("setStack".equals(command)) {
/* 103 */         setStack(stream.readUnsignedByte(), stream.readUnsignedByte(), NetworkUtils.readStack(stream));
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ContainerListNew.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */